package one.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import one.main.JSONHelper;

import org.tartarus.snowball.SnowballStemmer;

public class Indexer {
	
	public List<Tweet> tweets;

	private final static List<String> STOP_WORDS = Arrays.asList(new String[]{
		"a", "able", "about", "above", "abst", "accordance", "according", "accordingly", "across", "act", "actually", "added", "adj", "affected", "affecting", "affects", "after", "afterwards", "again", "against", "ah", "all", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "announce", "another", "any", "anybody", "anyhow", "anymore", "anyone", "anything", "anyway", "anyways", "anywhere", "apparently", "approximately", "are", "aren", "arent", "arise", "around", "as", "aside", "ask", "asking", "at", "auth", "available", "away", "awfully", "b", "back", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "begin", "beginning", "beginnings", "begins", "behind", "being", "believe", "below", "beside", "besides", "between", "beyond", "biol", "both", "brief", "briefly", "but", "by", "c", "ca", "came", "can", "cannot", "can't", "cause", "causes", "certain", "certainly", "co", "com", "come", "comes", "contain", "containing", "contains", "could", "couldnt", "d", "date", "did", "didn't", "different", "do", "does", "doesn't", "doing", "done", "don't", "down", "downwards", "due", "during", "e", "each", "ed", "edu", "effect", "eg", "eight", "eighty", "either", "else", "elsewhere", "end", "ending", "enough", "especially", "et", "et-al", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "except", "f", "far", "few", "ff", "fifth", "first", "five", "fix", "followed", "following", "follows", "for", "former", "formerly", "forth", "found", "four", "from", "further", "furthermore", "g", "gave", "get", "gets", "getting", "give", "given", "gives", "giving", "go", "goes", "gone", "got", "gotten", "h", "had", "happens", "hardly", "has", "hasn't", "have", "haven't", "having", "he", "hed", "hence", "her", "here", "hereafter", "hereby", "herein", "heres", "hereupon", "hers", "herself", "hes", "hi", "hid", "him", "himself", "his", "hither", "home", "how", "howbeit", "however", "hundred", "i", "id", "ie", "if", "i'll", "im", "immediate", "immediately", "importance", "important", "in", "inc", "indeed", "index", "instead", "into", "inward", "is", "isn't", "it", "itd", "it'll", "its", "itself", "i've", "j", "just", "k", "keep", "keeps", "kept", "kg", "km", "know", "known", "knows", "l", "largely", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "line", "little", "'ll", "look", "looking", "looks", "ltd", "m", "made", "mainly", "make", "makes", "many", "may", "maybe", "me", "mean", "means", "meantime", "meanwhile", "merely", "mg", "might", "million", "miss", "ml", "more", "moreover", "most", "mostly", "mr", "mrs", "much", "mug", "must", "my", "myself", "n", "na", "name", "namely", "nay", "nd", "near", "nearly", "necessarily", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "ninety", "no", "nobody", "non", "none", "nonetheless", "noone", "nor", "normally", "nos", "not", "noted", "nothing", "now", "nowhere", "o", "obtain", "obtained", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "omitted", "on", "once", "one", "ones", "only", "onto", "or", "ord", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "owing", "own", "p", "part", "particular", "particularly", "past", "per", "perhaps", "placed", "please", "plus", "poorly", "possible", "possibly", "potentially", "pp", "predominantly", "present", "previously", "primarily", "probably", "promptly", "proud", "provides", "put", "q", "que", "quickly", "quite", "qv", "r", "ran", "rather", "rd", "re", "readily", "really", "recent", "recently", "ref", "refs", "regarding", "regardless", "regards", "related", "relatively", "research", "respectively", "right", "run", "s", "said", "same", "saw", "say", "saying", "says", "sec", "section", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sent", "seven", "several", "shall", "she", "shed", "she'll", "shes", "should", "shouldn't", "show", "showed", "shown", "showns", "shows", "significant", "significantly", "similar", "similarly", "since", "six", "slightly", "so", "some", "somebody", "somehow", "someone", "somethan", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specifically", "specified", "specify", "specifying", "still", "stop", "strongly", "sub", "substantially", "successfully", "such", "sufficiently", "suggest", "sup", "sure", "t", "take", "taken", "taking", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "that'll", "thats", "that've", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "thered", "therefore", "therein", "there'll", "thereof", "therere", "theres", "thereto", "thereupon", "there've", "these", "they", "theyd", "they'll", "theyre", "they've", "think", "this", "those", "thou", "though", "thoughh", "thousand", "throug", "through", "throughout", "thru", "thus", "til", "tip", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "ts", "twice", "two", "u", "un", "under", "unfortunately", "unless", "unlike", "unlikely", "until", "unto", "up", "upon", "ups", "us", "use", "used", "useful", "usefully", "usefulness", "uses", "using", "usually", "v", "value", "various", "'ve", "very", "via", "viz", "vol", "vols", "vs", "w", "want", "wants", "was", "wasn't", "way", "we", "wed", "welcome", "we'll", "went", "were", "weren't", "we've", "what", "whatever", "what'll", "whats", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "wheres", "whereupon", "wherever", "whether", "which", "while", "whim", "whither", "who", "whod", "whoever", "whole", "who'll", "whom", "whomever", "whos", "whose", "why", "widely", "willing", "wish", "with", "within", "without", "won't", "words", "world", "would", "wouldn't", "www", "x", "y", "yes", "yet", "you", "youd", "you'll", "your", "youre", "yours", "yourself", "yourselves", "you've", "z", "zero"
	});
	
	private static Hashtable <String, String> wordDict = new Hashtable<String, String>(); 
	String dictFileName = "dataDict/dict.txt";
	
	public Indexer(List<Tweet> tweets) {
		this.tweets = tweets;
		this.index();
	}
	
	public void index() {
		try {
			wordDict = buildDict(dictFileName);
			final File dataDir = new File(Main.DATA_DIR);
			if (dataDir.canRead() && dataDir.isDirectory()) {
				File[] files = dataDir.listFiles();
				if (files != null) {
					for (File file: files) {
						String filename = file.getName();
						if ( !file.canRead() || filename.indexOf(".txt") == -1 ) {
							continue;
						}
						BufferedReader br = new BufferedReader(new FileReader(file));
						String line;
						Map<String,Object> json, map;
						for (int i=0; i<750; i++) {
							line = br.readLine();
							if (line == null) {
								break;
							}
							System.out.println(line);
							json = JSONHelper.fromJSON(line);
							map = new LinkedHashMap<String,Object>();
							map.put("tweet_raw_text", json.get("text"));
							map.put("tweet_text", this.processTweet((String) json.get("text")));
							String category = file.getName();
							category = category.substring(0, category.indexOf(".txt"));
							map.put("category", category);
							this.tweets.add(new Tweet(map));
						}
						br.close();
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Hashtable <String, String> buildDict(String file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		
		while ((line = in.readLine()) != null) {
			// process the line.
			String[] words = line.split("\\s+");
			wordDict.put(words[0],words[1]);			
		}
		in.close();
		return wordDict;
	}

	public String processTweet(String tweet) {
		List<String> result = new ArrayList<String>();
		String[] list = tweet.trim().toLowerCase().split(" ");
		for (String word: list) {
			word.trim();
			word = this.stripPunctuation(word);
			if (word.length() != 0 && !this.isStopWord(word)) {
				word = this.stem(word);
				word = this.norm(word);
				result.add(word);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String word: result) {
			sb.append(word + " ");
		}
		return sb.toString();
	}

	private String norm(String word) {
		
		if (wordDict.containsKey(word) == true) {
			word = wordDict.get(word);
		}		
		return word;
	}

	public boolean isStopWord(String word) {
		return Indexer.STOP_WORDS.contains(word);
	}

	public String stripPunctuation(String word) {
		StringBuilder sb = new StringBuilder();
		for (char c: word.toCharArray()) {
			if (Character.isLetterOrDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public String stem(String word) {
		SnowballStemmer stemmer;
		Class stemClass;
		try {
			stemClass = Class.forName("org.tartarus.snowball.ext.englishStemmer");
			stemmer = (SnowballStemmer) stemClass.newInstance();
			stemmer.setCurrent(word);
			stemmer.stem();
			return stemmer.getCurrent();
		} catch ( Exception e ) {
			System.exit( 1 );
			e.printStackTrace();
		}	
		return null;
	}	
	
}
