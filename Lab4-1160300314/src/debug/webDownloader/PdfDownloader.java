package debug.webDownloader;

public class PdfDownloader extends WebDownloader {

  public PdfDownloader(String w, String d, String fr) {
    fileExtension = ".pdf";
    website = w;
    destination = d;
    fileRoot = fr;
  }

}
