# Iris Classification using K-Nearest Neighbors (KNN)

## Introduction
This project demonstrates the classification of the Iris dataset using the K-Nearest Neighbors (KNN) algorithm. The dataset consists of three species of iris flowers, classified based on sepal length, sepal width, petal length, and petal width.

## Dataset
The dataset used in this project is `dataset.csv`, which contains the following features:
- `sepal_length`
- `sepal_width`
- `petal_length`
- `petal_width`
- `species` (target variable: Iris-setosa, Iris-versicolor, Iris-virginica)

## Technologies Used
- Python
- NumPy
- Pandas
- Matplotlib
- Seaborn
- Scikit-learn
- PyTorch

## Project Workflow
### 1. Data Preprocessing
- The dataset is loaded using Pandas.
- Non-numeric categorical values in the `species` column are replaced with numerical values (0, 1, 2) for model compatibility.

### 2. Data Visualization
- Scatter plots are used to visualize the relationships between sepal length and sepal width for different species.
- A heatmap is generated to display feature correlations.
- Pair plots help in analyzing feature distributions and relationships.

### 3. Model Training and Evaluation
- The dataset is split into training (67%) and testing (33%) sets.
- A KNN model (k=3) is trained using Scikit-learn’s `KNeighborsClassifier`.
- The model’s accuracy is calculated based on test predictions.

## Results
- The model classifies iris species with a good accuracy, showcasing the effectiveness of the KNN algorithm for this dataset.

## Usage
1. Install required dependencies using:
   ```sh
   pip install numpy pandas torch matplotlib seaborn scikit-learn
   ```
2. Place `dataset.csv` in the project directory.
3. Run the Python script to train the model and visualize results.
   ```sh
   python script.py
   ```

## Future Improvements
- Experimenting with different values of `k` to find the optimal number of neighbors.
- Implementing more complex models like Decision Trees or Neural Networks.
- Adding cross-validation for better evaluation.

---

### Made by Himanshu Singh
