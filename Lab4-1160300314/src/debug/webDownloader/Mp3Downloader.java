package debug.webDownloader;

public class Mp3Downloader extends WebDownloader {

  public Mp3Downloader(String w, String d, String fr) {
    fileExtension = ".mp3";
    website = w;
    destination = d;
    fileRoot = fr;
  }

}
