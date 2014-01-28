package one.main;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Tweet {

	private String tweet_raw_text;
	private String tweet_text;
	private String tweet_id;
	private String user_id;
	private int retweet_count;
	private int favourite_count;
	private String category;
	
	public Tweet(Map<String,Object> map) {
		this.tweet_raw_text = (String) map.get("tweet_raw_text");
		this.tweet_text = (String) map.get("tweet_text");
//		this.tweet_id = (String) map.get("tweet_id");
//		this.user_id = (String) map.get("user_id");
//		this.retweet_count = Integer.parseInt((String) map.get("retweet_count"));
//		this.favourite_count = Integer.parseInt((String) map.get("favourite_count"));
		this.category = (String) map.get("category");
	}
	
	@Override
	public String toString() {
		return this.tweet_text;
	}

}
