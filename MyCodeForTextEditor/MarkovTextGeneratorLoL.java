package textgen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}


	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method

	String [] words = sourceText.split(" +");
	if (words.length == 0) throw new NullPointerException();
	starter = words[0];
	wordList.add(new ListNode(words[0]));


	for (int i = 0; i < words.length - 1; i++) {
		boolean check = false;
		for (int j = 0; j < wordList.size(); j++) {
			String a = words[i];
			String b = wordList.get(j).getWord();
			if ( a.contains(b)) {
				wordList.get(j).addNextWord(words[i+1]);
				check = true;

			}
		}

		if (!check) {
			ListNode n = new ListNode(words[i]);
			wordList.add(n);
			wordList.get(wordList.size()-1).addNextWord(words[i+1]);

		}

	}
	wordList.add(new ListNode(words[words.length-1]));
	wordList.get(wordList.size()-1).addNextWord(words[0]);

	}






	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (wordList.size() == 0 || numWords <= 0) return "";
		int count = 0;
		boolean chack = true;
		String out = starter;
		while (chack) {
			for( ListNode w: wordList) {
				if (starter.contains(w.getWord())) {
					String next = w.getRandomNextWord(rnGenerator);
					out = out + " " + next;
					count++;
					starter = next;
				}
				if (count == numWords - 1) {
					chack = false;
					break;

				}
			}

		}

			return out;
	}


	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.

		wordList.clear();
		starter = "";
		train(sourceText);
	}

	// TODO: Add any private helper methods you need here.


	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.
	 * @param args
	 */
	public static void main(String[] args)
	{

		/*MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random());
		gen.generateText(20);
		gen.train("");
		gen.generateText(20);
		String input = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";
        gen.retrain(input);
        String res = gen.generateText(500);
        String[] words = res.split("[\\s]+");
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();

        for (String w : words) {
            if (wordCounts.containsKey(w)) {
                wordCounts.put(w, wordCounts.get(w) + 1);
            }
            else {
                wordCounts.put(w, 1);
            }
        }
        wordCounts.get("I");
        gen.generateText(0);
		/*String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		gen.train(textString);
		System.out.println(gen);*/

		// feed the generator a fixed random value for repeatable behavior
		/*MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(20));
		String textString = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";//"Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(500));
		/*String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));*/
	}

}

/** Links a word to the next words in the list
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from
	    // the MarkovTextGeneratorLoL class
		if (nextWords.size() == 0) return "";
		int num = generator.nextInt(nextWords.size());

	    return nextWords.get(num);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}


