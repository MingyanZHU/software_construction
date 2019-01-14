package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

  @Test
  public void testMain() throws InterruptedException {
    Main.main(new String[]{});

    Thread.sleep(100);
    Main.main(new String[]{});
  }
}