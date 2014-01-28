package one.main;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	// CONSTANTS
	public static final boolean DEBUG = true;
	public static final String DATA_DIR = "data/";

	// ATTRIBUTES
	public List<Tweet> tweets;
	public Indexer indexer;
	
	// CONSTRUCTOR
	public Main() {
		this.tweets = new ArrayList<Tweet>();	
		this.indexer = new Indexer(tweets);
		this.learn();
		this.test();
		Debug.out(this);
	}
	
	// METHODS
	public void learn() {
		// TODO
	}
	public void test() {
		// TODO
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(Tweet tweet: this.tweets) {
			sb.append(tweet.toString() + "\n");
		}
		return sb.toString();
	}
	
	// DRIVER
	@SuppressWarnings("unused")
	public static void main(String args[]){
		Main main = new Main();
	}
	
}
