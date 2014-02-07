package main;

import java.util.List;

import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Main {

  // CONSTANTS

  public static final boolean DEBUG = true;
  public static final String TRAIN_DIR = "data/train/";
  public static final String TEST_DIR = "data/test/";

  // ATTRIBUTES

  public Indexer indexer;
  private FastVector features;
  private FilteredClassifier classifier;

  // CONSTRUCTOR

  public Main() {

    // Set categories
    FastVector categories = new FastVector(6);
    categories.addElement("DBS");
    categories.addElement("DBS (Dublin)");
    categories.addElement("NUS");
    categories.addElement("NUS (UK)");
    categories.addElement("Starhub");
    categories.addElement("Unclassified");

    // Set features
    this.features = new FastVector(2);
    this.features.addElement(new Attribute("category", categories));
    this.features.addElement(new Attribute("tweet", (FastVector) null));

    // Init the classifier
    this.resetClassifier();

    // Train and test
    this.train();
    this.test();

  }

  // METHODS

  private void train() {

    // Parse tweets
    List<Tweet> tweets = new Indexer(Main.TRAIN_DIR, true).getTweets();

    // Convert tweets to Instance
    Instances training = this.makeInstances(tweets);

    // Execute training, return new classifier
    this.resetClassifier();
    try {
      this.classifier.buildClassifier(training);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void test() {

    // Parse tweets
    List<Tweet> tweets = new Indexer(Main.TEST_DIR, true).getTweets();

    // Convert tweets to Instance
    Instances testingSet = this.makeInstances(tweets);

    /*
    try {
      Evaluation eTest = new Evaluation(testingSet);
      eTest.evaluateModel(this.classifier, testingSet);
      System.out.println(eTest.toSummaryString());

      double[][] cmMatrix = eTest.confusionMatrix();
      for (int i=0; i<cmMatrix.length; i++) {
        for (int j=0; j<cmMatrix[0].length; j++) {
          System.out.print(cmMatrix[i][j] + " ");
        }
        System.out.println();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    */

    // Execute testing
    int total = testingSet.numInstances(), correct = 0, wrong = 0;
    for (int i=0; i<total; i++) {
      try {
        double pred = this.classifier.classifyInstance(testingSet.instance(i));
        String actual = testingSet.classAttribute().value((int) testingSet.instance(i).classValue());
        String predicted = testingSet.classAttribute().value((int) pred);
        if (actual.equals(predicted)) {
          correct++;
        } else {
          wrong++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    System.out.printf("correct = %d\n", correct);
    System.out.printf("wrong = %d\n", wrong);

  }

  private void resetClassifier() {

    StringToWordVector filter = new StringToWordVector();
    filter.setAttributeIndices("last");
    this.classifier = new FilteredClassifier();
    this.classifier.setFilter(filter);
    this.classifier.setClassifier(new IBk());

  }

  private Instances makeInstances(List<Tweet> tweets) {

    return makeInstances(tweets, 1);

  }

  private Instances makeInstances(List<Tweet> tweets, double weight) {

    Instances set = new Instances("Training", features, tweets.size());
    set.setClassIndex(0); // Classify on feature at index 0

    for (Tweet tweet: tweets) {
      Instance instance = new Instance(2);
      instance.setWeight(weight);
      instance.setValue((Attribute) this.features.elementAt(0), tweet.getCategory());
      instance.setValue((Attribute) this.features.elementAt(1), tweet.getText());
      set.add(instance);
    }

    return set;

  }

  // DRIVER
  @SuppressWarnings("unused")
  public static void main(String args[]){
    Main main = new Main();
  }

}
