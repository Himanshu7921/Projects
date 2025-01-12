from waitress import serve
from app import app  # Ensure app is the Flask instance
import os  # Import the os module

if __name__ == '__main__':
    port = int(os.environ.get('PORT', 8000))  # Use the environment-provided port
    serve(app, host='0.0.0.0', port=port)
