package debug.webDownloader;

public class TextDownloader extends WebDownloader {

  public TextDownloader(String w, String d, String fr) {
    fileExtension = ".txt";
    website = w;
    destination = d;
    fileRoot = fr;
  }

}
