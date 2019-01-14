package debug.webDownloader;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

public class TextDownloaderTest {

  @Test
  public void testTextDownloader() {
    String fileURLSource = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/index.html";
    String fileURLRoot = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/";
    String destination = "src/txt/debug/download";
    TextDownloader downloader = new TextDownloader(fileURLSource, destination, fileURLRoot);
    downloader.init();
    downloader.printFiles();
    downloader.printHtml();
    assertThat(downloader.html, containsString("</html>"));
    for (String string : downloader.files) {
      assertThat(string, containsString(".txt"));
    }
    String test = "Hello";
    downloader.setFileDestination(test);
    assertEquals(test, downloader.destination);
    downloader.setFileRoot(test);
    assertEquals(test, downloader.fileRoot);
  }

}