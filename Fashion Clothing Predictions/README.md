# Fashion MNIST CNN Models with PyTorch

An end-to-end project exploring various deep learning models—from a simple baseline to convolutional neural networks (CNNs), applied to the [Fashion MNIST dataset](https://github.com/zalandoresearch/fashion-mnist). This repository demonstrates how to load data, create mini-batches, build models, and visualize predictions using PyTorch and its ecosystem.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Models](#models)
- [Results & Visualizations](#results--visualizations)
- [License](#license)

---

## Overview

This project walks through the following steps:

1. **Data Preparation**  
   - Download and transform the Fashion MNIST dataset.
   - Visualize individual samples and mini-batches.
2. **Baseline Model Creation**  
   - Build a simple fully connected model using `nn.Sequential`.
3. **Improved Model with Non-Linearity**  
   - Enhance the baseline model with activation functions.
4. **Convolutional Neural Network (CNN)**  
   - Build a CNN model to better capture image features.
5. **Training & Evaluation**  
   - Implement training loops with mini-batches, loss calculation, and accuracy metrics.
   - Visualize training progress and evaluate model performance.
6. **Prediction & Visualization**  
   - Generate predictions on test data.
   - Plot comparisons between true and predicted labels.

---

## Features

- **Data Loading & Preprocessing:** Uses `torchvision.datasets` and `torchvision.transforms` for seamless data handling.
- **Model Variations:** Includes a baseline dense model, an improved version with ReLU activations, and a convolutional network.
- **Training Utilities:** Custom training loops with timing functions and progress tracking using `tqdm`.
- **Evaluation & Metrics:** Utilizes `torchmetrics.Accuracy` for accurate model assessment.
- **Visualization:** Comprehensive plotting of images and prediction curves using `matplotlib`.

---

## Requirements

- Python 3.7+
- [PyTorch](https://pytorch.org/) >= 1.7.0
- [Torchvision](https://pytorch.org/vision/stable/index.html)
- [Torchmetrics](https://torchmetrics.readthedocs.io/)
- [Matplotlib](https://matplotlib.org/)
- [TQDM](https://github.com/tqdm/tqdm)

---

## Models

### Model 0: Baseline Model
- **Architecture:** Fully connected network with a flattening layer and three linear layers.
- **Purpose:** Serves as a starting point to understand the data and training process.

### Model 1: Improved Model with ReLU
- **Architecture:** Baseline model enhanced with `ReLU` activation functions between linear layers.
- **Purpose:** Introduces non-linearity to capture more complex relationships in the data.

### Model 2: Convolutional Neural Network (CNN)
- **Architecture:** Two convolutional layers (with ReLU and max-pooling) followed by a classifier.
- **Purpose:** Leverages the spatial structure of images for improved feature extraction and classification.

---

## Results & Visualizations

- **Training & Testing Metrics:**  
  Each model is trained for a number of epochs with batch-wise updates. Training and testing losses along with accuracy scores are logged during training.

- **Image Visualization:**  
  Visualize sample images with true and predicted labels to understand model performance.

- **Prediction Curves:**  
  Compare prediction curves against actual labels using `matplotlib`.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

*Happy coding and happy experimenting with CNNs on Fashion MNIST!*

---

## Made By Himanshu Singh