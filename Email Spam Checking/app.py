from flask import Flask, render_template, request, jsonify
import pickle
import string
import torch
from nltk.stem import PorterStemmer
from nltk.corpus import stopwords
from Model import MyModel
from flask_cors import CORS  # To handle CORS issues in case the frontend and backend are on different origins
import os

app = Flask(__name__)

# Enable CORS for all routes
CORS(app)

# Initialize stemmer and stop words list
stemmer = PorterStemmer()
sp_words = set(stopwords.words('english'))

# Load the pre-trained CountVectorizer
with open('count_vectorizer.pkl', 'rb') as f:
    vectorizer = pickle.load(f)

# Check if CUDA (GPU) is available
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

# Initialize the model architecture and load the weights
input_features = vectorizer.transform(["test"]).shape[1]  # Assuming the input size matches the vectorizer
model = MyModel(input_features).to(device)
model.load_state_dict(torch.load('model.pth', map_location=device, weights_only=True))

@app.route('/')
def home():
    return render_template('index.html')  # Render the HTML template when visiting the home route

@app.route('/info')
def info():
    return render_template('/static/info.html')
    
@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get the email content from the request body
        data = request.get_json()

        # Debugging: Log the received data to check what is being sent
        print(f"Received data: {data}")  # This will print the incoming request data to your terminal

        # Ensure 'email' key exists in the request data
        if 'email' not in data:
            return jsonify({'error': 'Missing "email" field in the request'}), 400

        email_content = data['email']

        # Check if email content is empty
        if not email_content.strip():  # Strip leading/trailing spaces and check if empty
            return jsonify({'error': 'Email content cannot be empty. Please enter a message.'}), 400

        # Preprocess the email content
        email_content = email_content.lower()  # Convert to lowercase
        email_content = email_content.translate(str.maketrans('', '', string.punctuation))  # Remove punctuation
        email_content = email_content.split()  # Split into words

        # Remove stopwords and apply stemming
        email_content = [stemmer.stem(word) for word in email_content if word not in sp_words]
        email_content = ' '.join(email_content)  # Join words back into a string

        # Vectorize the email content using the loaded vectorizer
        vectorized_input = vectorizer.transform([email_content])

        # Convert to PyTorch tensor
        vectorized_input_tensor = torch.tensor(vectorized_input.toarray(), dtype=torch.float).to(device)

        # Predict using the loaded model
        model.eval()  # Set the model to evaluation mode
        with torch.no_grad():
            prediction = model(vectorized_input_tensor).squeeze()
            prediction = (prediction >= 0.5).float().cpu().numpy()  # Convert probabilities to binary predictions

        # Convert prediction to an integer (1 for spam, 0 for not spam)
        prediction_int = int(prediction.item())

        # Determine the message
        if prediction_int == 1:
            message = "Spam"
        else:
            message = "Not Spam"

        # Return the prediction result as JSON with a message
        return jsonify({
            'prediction': prediction_int,
            'message': message
        })

    except Exception as e:
        # If an error occurs, return it as JSON with status 500
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    port = int(os.environ.get('PORT', 5000))  # Use the environment-provided port
    app.run(host='0.0.0.0', port=port, debug=False)
