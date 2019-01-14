package debug.calculator;

import java.awt.AWTException;
import org.junit.Test;

public class CalculatorGuiTest {

  @Test
  public void testRobot() throws AWTException {
    CalculatorGui calculatorGui = new CalculatorGui();
    System.out.println(calculatorGui);
  }
}