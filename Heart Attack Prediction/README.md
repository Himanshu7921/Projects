# **Heart Attack Prediction Using Machine Learning**

## **Objective**

This project aims to predict the risk of a heart attack based on various health metrics and patient data. The dataset includes features like blood pressure, diet, triglycerides, and demographic details.

---

## **About Dataset**

- **Rows:** 8,763
- **Columns:** 12 (after cleaning)
- **Target Variable:** `Heart Attack Risk`
  - 1: High Risk
  - 0: Low Risk

### **Dataset Information**

- Key features include:
  - **Triglycerides, Blood Pressure, Diet, Sex, etc.**
  - **Outcome (Target):** Heart Attack Risk
- Some columns like `Country`, `Continent`, `Hemisphere`, and `Patient ID` were removed for being irrelevant.

---

## **Data Cleaning**

1. **Dropped Columns:**

   - Removed non-essential columns: `Country`, `Continent`, `Hemisphere`, `Patient ID`.

2. **Categorical Data Transformation:**

   - `Diet` converted to numeric:
     - `Unhealthy = -1`, `Average = 0`, `Healthy = 1`
   - `Sex` converted to numeric:
     - `Male = 1`, `Female = 0`

3. **Blood Pressure Parsing:**

   - Split `Blood Pressure` into `Systolic` and `Diastolic`.
   - Dropped the original `Blood Pressure` column.

4. **Checked for Class Imbalance:**
   - Verified the number of samples for `Heart Attack Risk = 1` and `Heart Attack Risk = 0`.

---

## **Resampling**

- Used **SMOTE (Synthetic Minority Oversampling Technique)** to handle class imbalance in the dataset.
- Resampled features and target to create a balanced dataset for training.

---

## **Data Preparation**

- **Feature Matrix (X):** All columns except `Heart Attack Risk`.
- **Target Variable (Y):** `Heart Attack Risk`.
- **Split:** 80% training, 20% testing.

---

## **Model Training**

- **Model Used:** `RandomForestClassifier` (from Scikit-learn).
  - **Parameters:**
    - Max Depth: 20
    - Random State: 0

### **Steps:**

1. Split the dataset into training and testing sets.
2. Trained the Random Forest model on the training set.
3. Predicted the outcomes on the test set.

---

## **Evaluation Metrics**

1. **Confusion Matrix:**

   - Visualized using Seaborn heatmap.
   - Showed the True Positive, True Negative, False Positive, and False Negative counts.

2. **Accuracy:**

   - Measured the percentage of correct predictions:
     ```python
     Accuracy = (Correct Predictions / Total Samples) * 100
     ```

3. **Feature Importance:**
   - Analyzed the contribution of each feature to the model's predictions using a bar plot.

---

## **Visualization**

1. **Confusion Matrix:**

   - A heatmap showing prediction accuracy for each class.

2. **Feature Importance:**

   - Bar plot to highlight which features had the most influence on predictions.

3. **Correlation Matrix:**

   - Heatmap to analyze the relationship between features.

4. **2D Classification Boundary:**
   - Visualized decision boundaries for a 2-feature dataset (if applicable).

---

## **Key Results**

- **Accuracy:** ~`67%`
- **Confusion Matrix:**
  - True Positives: ...
  - True Negatives: ...
  - False Positives: ...
  - False Negatives: ...

---

## **Dependencies**

- **Python Libraries Used:**
  - `pandas`: For data manipulation.
  - `numpy`: For numerical computations.
  - `torch`: For tensor operations.
  - `sklearn`: For machine learning models and metrics.
  - `imblearn`: For resampling using SMOTE.
  - `matplotlib`, `seaborn`: For data visualization.

---

## **How to Run the Code**

1. **Install Dependencies:**
   ```bash
   pip install pandas numpy torch scikit-learn imbalanced-learn matplotlib seaborn
   ```
2. **Place the dataset in the same directory as the script.**
3. **Run the script:**
   ```bash
   python heart_attack_prediction.py
   ```

---

## **Future Improvements**

- Experiment with other models (e.g., Gradient Boosting, Neural Networks).
- Optimize hyperparameters using Grid Search or Random Search.
- Incorporate additional features to improve accuracy.
- Deploy the model as an API or web application.

---

## **Author**

- **Himanshu Singh**
