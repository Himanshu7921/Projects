# Wine Quality Dataset

This dataset is publicly available for research purposes and is used to model wine preferences based on physicochemical properties. The dataset was originally created by Paulo Cortez and colleagues (Cortez et al., 2009) and is available for academic use. If you plan to use this dataset, please include the following citation:

**Citation:**
Cortez, P., Cerdeira, A., Almeida, F., Matos, T., & Reis, J. (2009). Modeling wine preferences by data mining from physicochemical properties. *Decision Support Systems, 47(4), 547-553.* Elsevier.  
Available at: [DOI: 10.1016/j.dss.2009.05.016](http://dx.doi.org/10.1016/j.dss.2009.05.016)  

## Dataset Overview

The dataset consists of wine samples from the Portuguese "Vinho Verde" wine, and it includes two separate datasets—one for red wine and one for white wine. The dataset's input features are based on objective tests, while the output is a subjective score from sensory evaluations by wine experts.

### Key Information:
- **Created by:** Paulo Cortez (Univ. Minho), Antonio Cerdeira, Fernando Almeida, Telmo Matos, and Jose Reis (CVRVV) @ 2009
- **Number of Instances:**
  - Red wine: 1599
  - White wine: 4898
- **Number of Attributes:** 11 input attributes + 1 output attribute
- **Task Type:** Classification or regression
- **Missing Values:** None

### Dataset Description:

#### Input Variables (Physicochemical Properties):
1. **Fixed Acidity**
2. **Volatile Acidity**
3. **Citric Acid**
4. **Residual Sugar**
5. **Chlorides**
6. **Free Sulfur Dioxide**
7. **Total Sulfur Dioxide**
8. **Density**
9. **pH**
10. **Sulphates**
11. **Alcohol**

#### Output Variable (Sensory Evaluation):
- **Quality**: Wine quality score, ranging from 0 (very bad) to 10 (very excellent).

### Usage

The dataset can be approached as either a classification or regression task. The output quality score is ordered, but the classes are not balanced, with more normal wines than excellent or poor ones. Outlier detection methods could be used to identify wines with extreme ratings (excellent or poor). It may also be beneficial to experiment with feature selection methods to improve model performance, as some input variables may be correlated.

### Relevant Links:
- For more details, visit [Vinho Verde](http://www.vinhoverde.pt/en/).
- [Decision Support Systems paper by Cortez et al., 2009](http://dx.doi.org/10.1016/j.dss.2009.05.016)

### Research Example:
In the original paper, various data mining techniques were applied to model the wine quality based on the physicochemical properties. A support vector machine (SVM) model produced the best results, and several metrics such as MAD and confusion matrices were computed for evaluation.

---

Project made by **Himanshu Singh**. Feel free to use this dataset for your own research, experimentation, and model development.
