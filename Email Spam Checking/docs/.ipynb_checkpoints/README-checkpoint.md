# Spam or Not Spam Email Classification Documentation

## Introduction

This project aims to classify emails as either "Spam" or "Not Spam" using a machine learning model built with PyTorch. The model is trained using labeled email data and employs preprocessing steps such as tokenization, stemming, and stop-word removal. The primary components involved include data preprocessing, vectorization, model creation, and evaluation.

### Key Libraries Used

- **Pandas**: Used for data manipulation and loading the dataset.
  - Documentation: [Pandas Documentation](https://pandas.pydata.org/pandas-docs/stable/)
- **NumPy**: Used for numerical operations, particularly for handling arrays.
  - Documentation: [NumPy Documentation](https://numpy.org/doc/)
- **NLTK**: Natural Language Toolkit for text processing and stopword removal.
  - Documentation: [NLTK Documentation](https://www.nltk.org/)
  - Special Note: NLTK requires citation in any published work. If you use NLTK for your work, please cite the following:
    - Bird, Steven, Edward Loper, and Ewan Klein (2009), *Natural Language Processing with Python*. O’Reilly Media Inc. ([Link to Book](http://www.nltk.org/book/))
- **Scikit-learn**: Used for feature extraction (CountVectorizer) and splitting the dataset into training and testing sets.
  - Documentation: [Scikit-learn Documentation](https://scikit-learn.org/stable/)
- **Torch**: PyTorch library for deep learning, used here to build and train the model.
  - Documentation: [PyTorch Documentation](https://pytorch.org/docs/stable/)
- **Torchmetrics**: For measuring the accuracy of the model.
  - Documentation: [Torchmetrics Documentation](https://torchmetrics.readthedocs.io/en/stable/)

## Overview of the Workflow

The following steps are involved in building the spam classification model:

1. **Data Preprocessing**: 
   - Load and clean the dataset.
   - Rename columns to make them more meaningful (`v1` to `Labels`, `v2` to `Email`).
   - Convert "ham" to 0 and "spam" to 1.
   - Apply text preprocessing, including removing punctuation, stemming, and stopword removal.

2. **Vectorization**:
   - Convert email text into numerical features using `CountVectorizer`.

3. **Model Construction**:
   - Build a simple neural network using PyTorch's `nn.Module` to classify emails based on their features.

4. **Model Training**:
   - Train the model using binary cross-entropy loss and the Adam optimizer.
   - Run training for a specified number of epochs.

5. **Model Evaluation**:
   - After training, evaluate the model's accuracy on the test set.

6. **Model Saving**:
   - Save the trained model and vectorizer for future use.

7. **Interactive Email Detection**:
   - The model can be used interactively to classify new emails as either spam or not spam.

---

## Code Walkthrough

### 1. Data Preprocessing

```python
import pandas as pd
import numpy as np

PATH = "dataset.csv"
data = pd.read_csv(PATH, encoding='latin-1')

# Renaming columns
data = data.rename(columns={"v1": "Labels", "v2": "Email"})
data['Labels'] = data['Labels'].apply(lambda x: 0 if x == "ham" else 1)
```

Here, we load the dataset and preprocess it by renaming columns and encoding labels (ham = 0, spam = 1).

### 2. Text Preprocessing

```python
import string
import nltk
from nltk.corpus import stopwords
from nltk.stem.porter import PorterStemmer

nltk.download('stopwords')
stemmer = PorterStemmer()
corpus = []
sp_words = set(stopwords.words('english'))

# Process each email
for i in range(len(data)):
    emails = data['Email'].iloc[i].lower()
    emails = emails.translate(str.maketrans('', '', string.punctuation)).split()
    emails = [stemmer.stem(word) for word in emails if word not in sp_words]
    emails = ' '.join(emails)
    corpus.append(emails)
```

This block handles text cleaning by:
- Lowercasing the email text.
- Removing punctuation.
- Stemming each word.
- Filtering out stopwords.

### 3. Vectorization

```python
from sklearn.feature_extraction.text import CountVectorizer

vectorizer = CountVectorizer()
X = vectorizer.fit_transform(corpus).toarray()
Y = data['Labels']
```

We use `CountVectorizer` to convert the cleaned email text into numerical feature vectors.

### 4. Model Definition

```python
import torch
import torch.nn as nn

class MyModel(nn.Module):
    def __init__(self, input_features):
        super(MyModel, self).__init__()
        self.model = nn.Linear(input_features, 1)

    def forward(self, X):
        return torch.sigmoid(self.model(X))

# Initialize the model
model = MyModel(input_features).to(device)
```

We define a simple neural network model that consists of a single linear layer with sigmoid activation.

### 5. Training

```python
# Loss function and optimizer
loss_fn = nn.BCELoss() 
optimizer = torch.optim.Adam(model.parameters(), lr=0.001)

# Training loop
for epoch in range(epochs):
    model.train()
    y_pred = model(X_train).squeeze()
    loss = loss_fn(y_pred, Y_train)
    
    optimizer.zero_grad()
    loss.backward()
    optimizer.step()
```

The model is trained for several epochs using binary cross-entropy loss and the Adam optimizer.

### 6. Evaluation

```python
# Evaluate the model
model.eval()
with torch.no_grad():
    y_test_pred = model(X_test).squeeze()
    y_test_pred = y_test_pred.round()
    
    accuracy = (y_test_pred.cpu().numpy() == Y_test.cpu().numpy()).mean()
    print(f"Test Accuracy: {accuracy * 100:.2f}%")
```

Once trained, the model is evaluated on the test set, and its accuracy is calculated.

### 7. Saving the Model

```python
torch.save(model.state_dict(), 'model.pth')

# Save the vectorizer using pickle
with open('count_vectorizer.pkl', 'wb') as f:
    pickle.dump(vectorizer, f)
```

We save both the trained model and the vectorizer to disk for later use.

### 8. Interactive Email Detection

```python
from termcolor import colored
import emoji

while True:
    # User input
    message = input(colored("\nEnter the email text to check: ", 'yellow'))
    
    # Preprocess and predict
    message = message.lower().translate(str.maketrans('', '', string.punctuation)).split()
    message = [stemmer.stem(word) for word in message if word not in sp_words]
    message = ' '.join(message)
    
    # Predict spam or not spam
    X_message = vectorizer.transform([message])
    X_message = torch.tensor(X_message.toarray(), dtype=torch.float).to(device)
    
    model.eval()
    with torch.no_grad():
        y_test_pred = model(X_message).squeeze()
        y_test_pred = (y_test_pred >= 0.5).float()

    # Display result
    if y_test_pred.item() == 1:
        print(colored("\n🚨 Provided Email is Spam! 🚨", 'red'))
    else:
        print(colored("\n✅ Provided Email is Not Spam!", 'green'))
```

This section allows users to input an email text and see whether it is classified as spam or not spam.

---

## Installation

To run this project, you need to install the necessary libraries:
```bash
pip install -r requirements.txt

```
---
## References

- **NLTK**: For natural language processing tasks (tokenization, stemming, stopwords). [NLTK Documentation](https://www.nltk.org/). 
- **PyTorch**: For building and training the model. [PyTorch Documentation](https://pytorch.org/docs/stable/).
- **Scikit-learn**: For feature extraction and dataset splitting. [Scikit-learn Documentation](https://scikit-learn.org/stable/).

### Citation for NLTK:

If you use NLTK in your work, please cite it as follows:
```
Bird, Steven, Edward Loper and Ewan Klein (2009), Natural Language Processing with Python. O’Reilly Media Inc.
```
