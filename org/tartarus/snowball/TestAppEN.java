
package org.tartarus.snowball;


import org.tartarus.snowball.ext.englishStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TestAppEN {
    public static void main(String [] args){

    	//text examples
		String text = "Skoda Auto, more commonly known as Skoda, is a Czech automobile manufacturer founded in 1895 as Laurin & Klement. It is headquartered in Mlada Boleslav, Czech Republic. In 1925 Laurin & Klement was acquired by Skoda Works which itself became state owned during the days of the Communist government. After 1991 it was gradually privatized and in 2000 Skoda became a wholly owned subsidiary of the Volkswagen Group. Initially, the company was meant to serve the role of VW Group’s entry brand. Over time, however, the Skoda brand has shifted progressively more upmarket, with most models overlapping with their Volkswagen counterparts on price and features, while eclipsing them on space. Its total global sales reached 1.13 million cars in 2016 and had risen annually by 6.8 percent, profit rose by 6.5% in 2015.";

		ArrayList<String> tokens = new ArrayList<>();

		Reader reader;
		reader = new StringReader(text);
		reader = new BufferedReader(reader);

		StringBuffer input = new StringBuffer();

		int character;

		// only count letters for tokens, everything else get removed

		try {
			while ((character = reader.read()) != -1) {
				char ch = (char) character;
				if (!Character.isLetter((char) ch)) {
					if (input.length() > 0) {
						tokens.add(input.toString());
						input.delete(0, input.length());
					}
				} else {
					input.append(Character.toLowerCase(ch));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("--- All tokens ---");
		System.out.println(tokens);
		System.out.println("--- Stop word parsed tokens ---");
		System.out.println(tokens=remStopWords(tokens));
		System.out.println("--- Porter stemmer and Stop word parsed tokens ---");
		System.out.println(tokens=stem(tokens));
	}

	/**
	 * Applies Porter stemmer according to the library available here http://snowball.tartarus.org/download.html
	 * @return this
	 */
	public static ArrayList<String> stem(ArrayList<String> tokens) {
		englishStemmer stemmer =  new englishStemmer();

		for (int i = 0; i < tokens.size(); i++) {
			stemmer.setCurrent(tokens.get(i).toString());
			stemmer.stem();
			tokens.set(i, stemmer.getCurrent());
		}

		return tokens;
	}

	/**
	 * Stop words list, according to this list http://snowball.tartarus.org/algorithms/english/stop.txt
	 */
	private final static ArrayList<String> stopWords = new ArrayList<String>(
			Arrays.asList("i","me","my","myself","we","us","our","ours","ourselves","you","your","yours","yourself","yourselves","he","him","his","himself","she","her","hers","herself","it","its","itself","they","them","their","theirs","themselves","what","which","who","whom","this","that","these","those","am","is","are","was","were","be","been","being","have","has","had","having","do","does","did","doing","will","would","shall","should","can","could","may","might","must","ought","i'm","you're","he's","she's","it's","we're","they're","i've","you've","we've","they've","i'd","you'd","he'd","she'd","we'd","they'd","i'll","you'll","he'll","she'll","we'll","they'll","isn't","aren't","wasn't","weren't","hasn't","haven't","hadn't","doesn't","don't","didn't","won't","wouldn't","shan't","shouldn't","can't","cannot","couldn't","mustn't","let's","that's","who's","what's","here's","there's","when's","where's","why's","how's","daren't","needn't","oughtn't","mightn't","a","an","the","and","but","if","or","because","as","until","while","of","at","by","for","with","about","against","between","into","through","during","before","after","above","below","to","from","up","down","in","out","on","off","over","under","again","further","then","once","here","there","when","where","why","how","all","any","both","each","few","more","most","other","some","such","no","nor","not","only","own","same","so","than","too","very","one","every","least","less","many","now","ever","never","say","says","said","also","get","go","goes","just","made","make","put","see","seen","whether","like","well","back","even","still","way","take","since","another","however","two","three","four","five","first","second","new","old","high","long"));

	/**
	 * Applies stop words filter.
	 * @return
	 */
	public static ArrayList<String> remStopWords(ArrayList<String> tokens) {
		for (int i = tokens.size()-1; i >= 0; i--) {
			if (isStopWord(tokens.get(i))) {
				tokens.remove(i);
			}
		}

		return tokens;
	}

	private static boolean isStopWord(String word){
		return stopWords.contains(word);
	}
}
