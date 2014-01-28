package test;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;
 
public class Driver {
 
  public static void main(String[] args) throws Exception{

    // Define categories
    FastVector categories = new FastVector(3);
    categories.addElement("DBS");
    categories.addElement("NUS");
    categories.addElement("Starhub");
    
    // Declare the feature vector
    FastVector features = new FastVector(2);
    features.addElement(new Attribute("category", categories));
    features.addElement(new Attribute("tweet", (FastVector) null));

    // Prepare training set
    Instances trainingSet = new Instances("Training Set #1", features, 10);         
    trainingSet.setClassIndex(0); // Classify on feature at index 0
    Instance instance = new Instance(2);
    instance.setValue((Attribute) features.elementAt(0), "NUS");
    instance.setValue((Attribute) features.elementAt(1), "This tweet should be in NUS.");
    trainingSet.add(instance);
    
    // Train the classifier
    StringToWordVector filter = new StringToWordVector();
    filter.setAttributeIndices("last");
    FilteredClassifier classifier = new FilteredClassifier();
    classifier.setFilter(filter);
    classifier.setClassifier(new NaiveBayes());
    classifier.buildClassifier(trainingSet); 
    
    // Prepare testing set
    Instances testingSet = new Instances("Training Set #1", features, 10);         
    testingSet.setClassIndex(0); // Classify on feature at index 0
    Instance testInstance = new Instance(2);
    testInstance.setValue((Attribute) features.elementAt(0), "NUS");
    testInstance.setValue((Attribute) features.elementAt(1), "This must be in NUS.");
    testingSet.add(testInstance);
    testInstance = new Instance(2);
    testInstance.setValue((Attribute) features.elementAt(0), "DBS");
    testInstance.setValue((Attribute) features.elementAt(1), "This must be in DBS.");
    testingSet.add(testInstance);
    
    // Test
    Evaluation eTest = new Evaluation(testingSet);
    eTest.evaluateModel(classifier, testingSet);

    // Print the result ˆ la Weka explorer:
    String strSummary = eTest.toSummaryString();
    System.out.println(strSummary);

    // Use the classifier

    /*
    // Test the model

*/
    // Get the confusion matrix
/*
    double[][] cmMatrix = eTest.confusionMatrix();
    for(int row_i=0; row_i<cmMatrix.length; row_i++){
    for(int col_i=0; col_i<cmMatrix.length; col_i++){
      System.out.print(cmMatrix[row_i][col_i]);
      System.out.print("|");
    }
    System.out.println();
    }
*/

  }
}