# 4400_FlightReview_NLP
## Title: Air Travel Satisfaction Prediction With Natural Language Process Project 
## Group Members: Wenting Yue, Xiaofei Xie, Yuxi Shen

## Abstract:
In this project, we aimed to predict the overall rating of airline flights based on a dataset containing both numerical features, as well as text reviews from customers. The dataset includes features such as airline, author, review date, customer review, aircraft, traveler type, cabin, route, date flew, seat comfort, cabin service, food and beverage quality, entertainment, ground service, value for money, and a recommended binary indicator. We explored four machine learning models since our project is trying to solve a classification problem, so we included Decision Tree, Random Forest, K Nearest Neighbours, and Natural Language Processing (NLP) models algorithms, and preprocessed the data by encoding categorical variables, scaling numerical variables, and tokenizing, stemming, or lemmatizing the text data.
We evaluated the performance of models using a confusion matrix and accuracy. We also examined the importance of each feature in the dataset to gain insights into which factors most strongly influence customers' overall rating of flights. Ultimately, we identified the best-performing model for predicting the overall rating of airline flights based on both numerical features and text reviews.

## Documentation:
### Introduction  
**What is the problem?**   
In this project, we aim to develop a predictive model to determine whether airline passengers will recommend their trip based on the quality of service they received during their air travel. The motivation behind this project is to help leading airline companies understand the priorities of their passengers, enabling them to optimize their resources and efforts in improving the service quality. Additionally, this model will provide insights into the cognitive processes of passengers as they decide whether to recommend the airline to others.  

**Why it is interesting?**  
This problem is interesting because understanding passenger satisfaction and their likelihood to recommend the airline is crucial for companies to maintain their competitive advantage in the industry. By identifying the key factors that influence passengers' recommendations, airlines can enhance their service offerings and create a more enjoyable flying experience for their passengers. This, in turn, can lead to increased customer loyalty, positive word-of-mouth, and higher profitability for the airline companies. The use cases for this problem can be found in the strategic decision-making processes of airlines, crew training and development, and resource allocation for various in-flight services.

**What is the approach you propose to tackle the problem?**  
To tackle the problem of predicting whether a customer will recommend a trip based on the quality of service they received, we propose a two-step approach. First, we will apply a natural language processing (NLP) algorithm to analyze the attitudes of passengers' reviews, extracting valuable insights into their sentiment towards the airline's services. For this purpose, we will use PyTorch to set up our sentiment analysis model, which will be trained on the current dataset we have. We hope this NLP model could give us the predicted attitudes based on the review texts. This approach makes sense for the problem as NLP can effectively process and analyze unstructured text data, such as reviews, and identify patterns related to passengers' opinions and preferences. The tree-based models and KNN models, on the other hand, can handle both categorical and numerical data, making them suitable for this task. It is also easy to interpret and can provide insights into the relative importance of different factors affecting passengers' recommendations.

**Why is the approach good compared with other competing methods?**  
The proposed approach, combining NLP for sentiment analysis and a decision tree model for prediction, is good compared to other competing methods. NLP has proven to be an effective method for sentiment analysis, as it can capture the nuances and complexities of human language. The decision tree model is a suitable choice for this problem because it can handle a mix of categorical and numerical data, and it offers a high degree of interpretability, which is beneficial when understanding the key factors influencing passengers' recommendations.

**Our approach differs from the reference**  
In our project, we use categorical data as our target variable (whether the passenger will recommend the airline or not), while the [reference](https://github.com/bentrevett/pytorch-sentiment-analysis) uses numerical data as the target variable (sentiment scores). This difference allows our approach to focus more on the binary outcome of recommendations rather than a continuous range of sentiment scores.

**What are the key components of my approach and results? Also, include any specific limitations.**  
The key components of our approach include the application of NLP algorithms for sentiment analysis using PyTorch and the implementation of a decision tree model for predicting passengers' likelihood to recommend the airline. Our approach offers valuable insights into passengers' opinions and preferences, as well as the factors that drive their decision to recommend the airline to others.
However, our approach has some limitations. For instance, the performance of the sentiment analysis model depends on the quality and representativeness of the dataset used for training. Although our dataset contains a large pool of flight reviews, it is still limited on build the sentiment library for accurately analyzing the attitudes of each text review. Additionally, the decision tree model, while interpretable, might be prone to overfitting, especially when dealing with complex relationships between various factors. To address the limitations, we have utilized a random forest model, an ensemble learning technique that combines multiple decision trees, enhancing prediction accuracy and robustness. This approach mitigates overfitting and improves the prediction of passengers' recommendations.

### Setup:
**Dataset Description**  
Our dataset was gathered from the file capston_airlin_review3.csv which was found on Kaggle. This original dataset includes airline reviews from 2006 to 2019 for popular airlines around the world with multiple choices and free test questions. This dataset contains 17 features, including 'airline', 'overall', 'author', 'review_date',' customer_review', 'aircraft', 'traveller_type', 'cabin', 'route', 'date_flown', 'seat_comfort', 'cabin_service', 'food_bev', 'entertainment', 'ground_service', 'value_for_money', 'recommended'. We added another field called "sentiment" to hold sentiment scores for each line of customer reviews calculated by the nltk library. This flight customer review dataset contains 14211 rows of data after we dropped all the NaN values. Since this dataset contains a few categorical features and a rich text field as well, we decide to implement both Decision Tree Classifier model and Natural Language Process model to make predictions and evaluate the prediction accuracy. 
Feature descriptions are provided as follows:  
- airline: Name of the airline  
- overall: Overall point given to the trip between 1 to 10  
- author: Author of the trip  
- review_date: Date of the Review  
- customer_review: Review of the customers in free text format  
- aircraft: Type of the aircraft  
- traveller_type: Type of traveler (e.g. business, leisure)  
- cabin: Cabin at the flight  
- date_flown: Flight date  
- seat_comfort: Rated between 1-5  
- cabin_service: Rated between 1-5  
- food_bev: Rated between 1-5  
- entertainment: Rated between 1-5  
- ground_service: Rated between 1-5  
- value_for_money: Rated between 1-5  
- recommended: Binary, target variable  

**Data source**  
If you want to have a look at the original dataset, please find it on [Kaggle](https://www.kaggle.com/datasets/efehandanisman/skytrax-airline-reviews)

To load the dataset into python:
Before loading, please save the “capstone_airline_reviews3.csv” file in the same direction as the project_dtc.ipynb file.
```
import pandas as pd
df_flight = pd.read_csv("capstone_airline_reviews3.csv", encoding="utf-8", encoding_errors='ignore').dropna()
```
**Visualization for a glimpse of the dataset**  
![Screenshot](histogram.png)  
The histogram shows the distribution of each quantitative data for each airline. 

![Screenshot](heatmap.png)  
This correlation heatmap presents the correlations between six features in the flight dataset, including seat comfort score, cabin service score, food or beverage score, entertainment score, ground service score, and evaluation score of the value of money. 

![Screenshot](violin_distribution.png)  
The violin plot visually represents the distribution of overall scores (ranging from 0 to 10) for airline journeys, with the scores on the y-axis and recommendations on the x-axis, divided into two categories: "yes" and "no." 

**Decision Tree Classifier Model Setup**  
For the Decision Tree Classier model, we use 'overall', 'seat_comfort', 'cabin_service', 'food_bev', 'entertainment', 'ground_service', 'value_for_money', and 'sentiment' features. To train a decision tree classifier, we preprocess the dataset by encoding categorical variables into dummy variables, scaling numerical variables, and splitting the data into training and testing sets.
```
from sklearn.model_selection import train_test_split
dummy = pd.get_dummies(df_flight['airline'])
dummy_travel = pd.get_dummies(df_flight['traveller_type'])
dummy_cabin = pd.get_dummies(df_flight['cabin'])
df_flight = pd.concat([df_flight, dummy,dummy_travel,dummy_cabin], axis=1)
X = df_flight.drop(['author', 'review_date','route','recommended','aircraft','airline','customer_review','date_flown','traveller_type', 'cabin'], axis = 1)
y = df_flight['recommended']
X_train, X_test, y_train,y_test = train_test_split(X, y, test_size = 0.2, random_state = 100)
```
We use scikit-learn's DecisionTreeClassifier with default hyperparameters, and evaluate the model on the testing set using accuracy, confusion matric, and MSE score with the optimal min_sample_split value and optimal min_sample_leaf value. The aim is to predict customers’ responses to recommend the airlines they took based on the satisfaction score of each service they enjoyed during the flight. In addition, we apply the Bagging approach to understand the importance of each feature contained in the training dataset.
```
from sklearn.tree import DecisionTreeClassifier
tree= DecisionTreeClassifier()
tree.fit(X_train, y_train)
```

**Random Forest Classifier Model Setup**  
Moreover, we use scikit-learn's RandomForestClassifier with default hyperparameters and evaluate the model on the testing set using accuracy and MSE with the optimal max_depth and the optimal min_samples_split. 
```
from sklearn.ensemble import RandomForestClassifier
# Train the random forest
random_forest = RandomForestClassifier(n_estimators = 100, n_jobs = -1)
random_forest.fit(X_train, y_train)
```

**Natural Language Process Model Setup**  
For NLP model, we applied the 'customer_review' and 'recommended' feature. To run the NLP model, you will first need to install torch related libraries on your local computer:  

```
pip uninstall torch torchtext -y
pip install torch==1.9.0 torchtext==0.10.0
pip install spacy
!python -m spacy download en_core_web_sm
!pip install --upgrade torchtext
```
And also import the following libraries:  
```
import pandas as pd
from textblob import TextBlob
import torch
import torch.nn as nn
import torch.optim as optim
from torchtext.legacy import data
from torchtext.legacy import datasets
from torchtext.datasets import IMDB
from sklearn.model_selection import train_test_split
import spacy
```
To save running time, we laod dataset again and implement this NLP model in another ipynb file which is named "project_nlp.ipynb". For convenience, we saved the two columns(review, recommendation decision) in a individual csv file, which is the "flight_nlp.csv". 

```
df_flight = pd.read_csv("capstone_airline_reviews3.csv", encoding="utf-8", encoding_errors='ignore').dropna()
df_flight["airline"].unique()

df_nlp = df_flight[["customer_review", "recommended"]]
df_nlp.to_csv("flight_nlp.csv", index = False)
```

To build an NLP model for classifying customer reviews as positive or negative, we'll first preprocess the text data by tokenizing, removing stop words, and stemming or lemmatizing the words. Then, we'll represent the text using a numerical format such as word embeddings or bag-of-words matrix.

```
import spacy
import re
from torchtext.legacy import data

nlp = spacy.load('en_core_web_sm')
def custom_tokenizer(text):
    special_char_pattern = re.compile(r'[^a-zA-Z0-9\s]')
    tokens = [token.text for token in nlp(text) if not token.is_stop]
    tokens = [re.sub(special_char_pattern, '', token) for token in tokens]
    tokens = [token for token in tokens if token]
    return tokens
```

Next, we'll use a Recurrent Neural Network (RNN) with a Long Short-Term Memory (LSTM) or Gated Recurrent Unit (GRU) architecture to model the sequence of words in each review. The RNN will take in the numerical representation of the text and output a probability distribution over the three classes. We'll train the model on a portion of the dataset, evaluate its performance on a validation set based on the validation loss score, validation accuracy score, and sentiment score, and tune its hyperparameters to optimize performance.

In summary, our goal is to build an NLP model that can accurately classify customer reviews as recommended or not. We'll achieve this by using an RNN to model the sequence of words in each review and training the model using a numerical representation of the text. Please find the detailed implementation in the project_nlp.ipynb file.


## Results
**Main results**
- Natural Language Process Model

The training loss decreases from 0.642 in the first epoch to 0.316 in the fifth epoch, indicating that the model is learning and improving its predictions as it iterates through the training data. The training accuracy increases from 67.58% in the first epoch to 86.83% in the fifth epoch, which shows that the model is getting better at classifying the reviews correctly. The validation loss is 0.618, which is higher than the training loss in the fifth epoch (0.316). This discrepancy suggests that the model might be overfitting the training data. The validation accuracy is 81.44%, which is lower than the training accuracy in the fifth epoch (86.83%). This difference also indicates potential overfitting.

- Decision Tree: 

The baseline Decision Tree classifier achieved an accuracy of 0.9025. Optimized parameter: By tuning the max_depth parameter, the optimal max_depth was found to be 7, and the accuracy improved slightly to 0.9117. Tuning the min_samples_split parameter led to an optimal value of 100, yielding an accuracy of 0.9106. Tuning the min_samples_leaf parameter resulted in an optimal value of 59, with an accuracy of 0.9149. The final optimal Decision Tree classifier, with all three parameters tuned, achieved an accuracy of 0.9149, and a training accuracy of 0.9346. The model is well-fitted for prediction also no obvious overfitting problem exists.
Using a Bagging Classifier with the optimal Decision Tree as the base estimator, the accuracy was 0.9149, which is comparable to the optimal Decision Tree classifier. Overall, the accuracy of the optimized decision tree is high indicating the model is well-fitted.
Random Forest: The experiments conducted using the RandomForestClassifier indicate that it performs better than the previous Decision Tree and Bagging Classifier models. The accuracy achieved by the RandomForestClassifier with default settings (n_estimators = 100) is 0.93. The optimized random forest classifier with n_estimaters and max_features has been tuned, and the accuracy can reach 0.93. The random forest classifier model is well-fitted. 

- KNN: 

The training accuracy is 1.0, which means the model correctly classified all the training samples. This could be a sign of overfitting, as the model has perfectly learned the training data but might not generalize well to new, unseen data. The testing accuracy is 0.9261, which means that the model correctly classified around 92.61% of the test samples. This is a good performance, but it's important to note that the accuracy is significantly lower than the training accuracy, which further supports the suspicion of overfitting.


**Supplementary results**
- Natural Language Process Model

The model architecture chosen for this task is a bidirectional LSTM with the following parameters:
Input dimension: Length of the TEXT vocabulary (INPUT_DIM)
Embedding dimension: 100 (EMBEDDING_DIM)
Hidden dimension: 128 (HIDDEN_DIM)
Output dimension: 3 (OUTPUT_DIM)
Number of layers: 2 (N_LAYERS)
Bidirectional: True (BIDIRECTIONAL)
Dropout: 0.5 (DROPOUT)
Padding index: Index of the padding token in TEXT vocabulary (PAD_IDX)
The optimizer chosen for training is Adam with a default learning rate, and the loss function is CrossEntropyLoss. The model is trained for 5 epochs, and the batch size is set to 64.
The parameter choices for the model architecture and training process seem reasonable, but the results indicate that the model might be overfitting the training data. This might be due to various factors such as insufficient training data, suboptimal hyperparameter choices, or the model architecture not being suitable for the task. Further experiments with different model architectures, hyperparameters, and additional training data might help in improving the performance and generalization of unseen data.

- Decision Tree： 

The max_depth parameter controls the maximum depth of the tree. The chosen range is 2 to 50 The optimal value (5) helps prevent overfitting by limiting the tree's depth.
The min_samples_split parameter determines the minimum number of samples required to split an internal node. Range from 2 to 100 was explored. The optimal value (100) ensures that the tree does not become too complex.
The min_samples_leaf parameter sets the minimum number of samples required to be at a leaf node. A range of values from 1 to the number of leaves was considered. The optimal value (59) helps ensure that the tree does not create leaves with very few samples.
In conclusion, the experiments show that tuning the parameters of the Decision Tree classifier can lead to improved performance. The Bagging Classifier provides a comparable result, showing the potential of ensemble methods to further enhance classification tasks.
- Random Forest

To improve the performance of the RandomForestClassifier, the max_features and n_estimators hyperparameters were tuned. For max_features, a range of 1 to 20 was considered, and cross-validation was used to determine the optimal value. The optimal max_features value was found to be 6.
Similarly, the n_estimators parameter was tuned using a range of 1 to 20. Cross-validation was again employed to find the optimal value. The optimal n_estimators value was found to be 20, leading to an accuracy of 0.929. By exploring the importance of each feature, we find, different than a single decision tree, the random forest model doesn't have a single feature that takes a high percentage of the importance but has multiple features that take the importance together which could cover more variance.

- KNN

We implement the gird search to find out the best parameter of KNN. Specifically, we want to find the best number of neighbors, weighted method, and metric techniques. The result turns out to be 11 nearest neighbors, calculate the distance by Euclidean, and use distance as the weight.  


## Discussion:
The model is trained using the LSTM architecture with bidirectional processing, and the accuracy obtained is quite satisfactory. After 5 epochs, the model achieves a training accuracy of 86.83% and a validation accuracy of 81.44%. These results indicate that the model has learned to generalize well.
While the results are promising, it's important to compare them with other approaches or models in order to have a better understanding of their performance：
CNN-based models: Convolutional Neural Networks (CNNs) have also been applied to NLP tasks, including sentiment analysis, and have demonstrated competitive performance. They are especially good at capturing local patterns in the text, which could help improve sentiment classification.
Ensemble methods: Combining the predictions of multiple models (e.g., an RNN, a CNN, and a Transformer) might lead to better performance, as each model might capture different aspects of the data.
Transformer-based models: Models such as BERT, RoBERTa, or GPT have achieved remarkable performance in a wide range of NLP tasks, including sentiment analysis. These models can be fine-tuned for the specific task and have been known to yield even better results than the RNN model used in this code.

Although our classification prediction model with Decision Tree classifier, Random Forest, and KNN algorithms have included multiple numerical features to analyze the whole dataset, the open-ended flight review pool can provide richer information and detailed explanations of customers’ decisions of recommendation.  For example, Tommy may rate the food service with a 3/10 score cause he likes hot beverages due to his stomachache, but Robert the other passenger likes a cold beverage with full ice. There are individual differences between passengers and our NLP model can based on review data and recommendation results to make predictions of the attitudes of customers. If we only focus on the numerical values from the surveys, then we cannot make an effortful improvement to provide personalized services to each customer. 

For now, our NLP model can be based on each individual review to give an attitude classification score(1: positive, 2: negative). Our future improvement of the model will dip into details to present the words related to the positive/negative attitudes. 


## Reference: 
Our implementation uses bentrevett's [Updated Sentiment Analysis](https://github.com/bentrevett/pytorch-sentiment-analysis/blob/master/2%20-%20Upgraded%20Sentiment%20Analysis.ipynb) code as a starting point. 