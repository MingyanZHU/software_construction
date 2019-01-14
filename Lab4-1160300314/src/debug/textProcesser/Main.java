package debug.textProcesser;

import java.io.FileNotFoundException;

public class Main {

  public static void main(String[] args) {
    SearchEngine google = new SearchEngine();
    try {
      google.processText("src/txt/test.txt");
      google.trie.display();

      System.out.println("\n");
      System.out.println(google.map.toString());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
