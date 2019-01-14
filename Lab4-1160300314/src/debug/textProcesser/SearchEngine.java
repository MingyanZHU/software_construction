package debug.textProcesser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class SearchEngine {

  //Dictionary that maps a string key to a linked list containing all the pages a word occurs on
  HashMap<String, LinkedList<Integer>> map = new HashMap<String, LinkedList<Integer>>();

  //Trie containing all the words of a text file
  Trie trie = new Trie();

  //Current page of text being searched
  int pageCounter = 0;

  //Delimiter used to decide when increment the pageCounter
  String pageDelimiter = "!!!";

  //setter method for the page delimiter
  public void setPageDelimiter(String s) {
    pageDelimiter = s;
  }


  public void processText(String filename) throws FileNotFoundException {
    Scanner in = new Scanner(new File(filename), "utf-8");
    // add all words in text to an array
    String word;
    while (in.hasNext()) {
      word = in.next();

      // page delimeter reached, increment page counter for storing values
      if (word.equals(pageDelimiter)) {
        System.out.println("Page Delimiter Reached, on page" + pageCounter);
        continue;
      } else if (!map.containsKey(word)) {
        trie.addWord(word);
        map.put(word, new LinkedList<Integer>());
        map.get(word).add(pageCounter);
      } else if (map.containsKey(word)) {
        map.get(word).add(pageCounter);
      }
    }

  }
}
