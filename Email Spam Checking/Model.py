import pandas as pd
import numpy as np
import string
import nltk
from nltk.corpus import stopwords
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import train_test_split
from nltk.stem.porter import PorterStemmer
import torch
import torch.nn as nn
import torchmetrics
from torchmetrics import Accuracy
import pickle
from termcolor import colored

# Data Preprocessing
PATH = "dataset.csv"
data = pd.read_csv(PATH, encoding='latin-1')

data = data.rename(columns={"v1": "Labels", "v2": "Email"})
data['Labels'] = data['Labels'].apply(lambda x: 0 if x == "ham" else 1)

# Preprocessing emails
stemmer = PorterStemmer()
corpus = []
sp_words = set(stopwords.words('english'))
nltk.download('stopwords')

for i in range(len(data)):
    emails = data['Email'].iloc[i].lower()
    emails = emails.translate(str.maketrans('', '', string.punctuation)).split()
    emails = [stemmer.stem(word) for word in emails if word not in sp_words]
    emails = ' '.join(emails)
    corpus.append(emails)

# Vectorization
vectorizer = CountVectorizer()
X = vectorizer.fit_transform(corpus).toarray()
Y = data['Labels']
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.2)

# Device setup (GPU or CPU)
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
X_train = torch.tensor(X_train).type(torch.float).to(device)
X_test = torch.tensor(X_test).type(torch.float).to(device)
Y_train = torch.tensor(np.array(Y_train)).type(torch.float).to(device)
Y_test = torch.tensor(np.array(Y_test)).type(torch.float).to(device)

# Model Definition
class MyModel(nn.Module):
    def __init__(self, input_features):
        super(MyModel, self).__init__()
        self.model = nn.Linear(input_features, 1)

    def forward(self, X):
        return torch.sigmoid(self.model(X))

# Initialize Model
torch.manual_seed(42)
input_features = X_train.shape[1]
model = MyModel(input_features).to(device)

# Loss function and optimizer
loss_fn = nn.BCELoss() 
optimizer = torch.optim.Adam(model.parameters(), lr=0.001)

# Accuracy Metric
acc = Accuracy(task="binary", num_classes=2).to(device)

# Training Loop
epochs = 7000
for epoch in range(epochs):
    model.train()
    
    # Forward pass
    y_pred = model(X_train).squeeze()
    
    # Compute the loss
    loss = loss_fn(y_pred, Y_train)
    
    # Zero gradients, backward pass, optimizer step
    optimizer.zero_grad()
    loss.backward()
    optimizer.step()

    # Print progress every 100 epochs
    if (epoch + 1) % 100 == 0:
        print(f"Epoch [{epoch+1}/{epochs}], Loss: {loss.item():.4f}")

# Model Evaluation
model.eval()
with torch.no_grad():
    y_test_pred = model(X_test).squeeze()
    y_test_pred = y_test_pred.round()  # Convert probabilities to binary predictions

    # Calculate accuracy
    accuracy = (y_test_pred.cpu().numpy() == Y_test.cpu().numpy()).mean()
    print(f"Test Accuracy: {accuracy * 100:.2f}%")

# Save Model
torch.save(model.state_dict(), 'model.pth')
with open('count_vectorizer.pkl', 'wb') as f:
    pickle.dump(vectorizer, f)

# User Input Loop for Spam Detection
while True:
    print(colored("Spam or Not Spam Detector 🌟", 'cyan', attrs=['bold']))
    message = input(colored("\nEnter the email text to check: ", 'yellow'))
    
    if not message.strip():
        print(colored("You didn't enter any text. Please try again.", 'yellow'))
        continue

    # Preprocess and transform message
    message = message.lower().translate(str.maketrans('', '', string.punctuation)).split()
    message = [stemmer.stem(word) for word in message if word not in sp_words]
    message = ' '.join(message)
    X_message = vectorizer.transform([message])

    # Convert to tensor
    X_message = torch.tensor(X_message.toarray(), dtype=torch.float).to(device)

    # Make prediction
    model.eval()
    with torch.no_grad():
        y_test_pred = model(X_message).squeeze()
        y_test_pred = (y_test_pred >= 0.5).float()

    # Display result
    if y_test_pred.item() == 1:
        print(colored("\n🚨 Provided Email is Spam! 🚨", 'red', attrs=['bold']))
    else:
        print(colored("\n✅ Provided Email is Not Spam!", 'green', attrs=['bold']))

    user_choice = input(colored("\nDo you want to check another email? (y/n): ", 'yellow')).lower()
    if user_choice != 'y':
        print(colored("\nExiting the Spam Detector. Goodbye! 👋", 'cyan'))
        break
