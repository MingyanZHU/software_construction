//Notes:
//If the user already entered a decimal, all subsequent decimals will be ignored for current input
//If the user enters a decimal without any other numbers that input will be ignored

package debug.calculator;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorGui extends JFrame {

  private JButton[] btnNum = new JButton[10];
  private JButton[] btnOp = new JButton[5];
  private JButton btnCe;
  private JButton btnDecimal;
  private JPanel pnlNum;
  private JPanel pnlOp;
  private JLabel txtResult;

  private String buffer;
  private String operation;
  private float result;
  private boolean init;

  private Font largeFont = new Font("Arial", Font.BOLD, 22);

  public CalculatorGui() {
    // variable to store the value of the state of the calculator
    result = 0;
    // String buffer for multi-digit inputs
    buffer = "";
    // String container for the type of arithmetic operation to perform
    operation = "";
    // Set whether the calculator state variables are storing valid values
    // to handle edge cases
    init = false;

    // set attributes
    setSize(330, 350);
    setTitle("COSC121 Calculator");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // create objects
    txtResult = new JLabel("" + result);
    txtResult.setFont(largeFont);
    txtResult.setHorizontalAlignment(JTextField.RIGHT);

    // buttons
    for (int i = 0; i < btnNum.length; i++) {
      btnNum[i] = new JButton(i + "");
      btnNum[i].setFont(largeFont);
      btnNum[i].addActionListener(new DigitActionListener());
    }

    btnDecimal = new JButton(".");
    btnCe = new JButton("CE");
    btnDecimal.setFont(largeFont);
    btnCe.setFont(largeFont);
    btnDecimal.addActionListener(new DigitActionListener());
    btnCe.addActionListener(new CEActionListener());

    btnOp[0] = new JButton("+");
    btnOp[1] = new JButton("-");
    btnOp[2] = new JButton("x");
    btnOp[3] = new JButton("/");
    btnOp[4] = new JButton("=");

    // Set font of Operations and add Action Listeners
    for (JButton aBtnOp : btnOp) {
      aBtnOp.setFont(largeFont);
      aBtnOp.addActionListener(new OpActionListener());
    }

    // panels
    pnlNum = new JPanel(new GridLayout(3, 4));
    pnlOp = new JPanel(new GridLayout(5, 1));

    // 3) ADDING
    // add to panels
    for (JButton aBtnNum : btnNum) {
      pnlNum.add(aBtnNum);
    }

    pnlNum.add(btnDecimal);
    pnlNum.add(btnCe);

    for (JButton aBtnOp : btnOp) {
      pnlOp.add(aBtnOp);
    }

    // add to JFrame
    add(txtResult, "North");
    add(pnlNum);
    add(pnlOp, "East");
  }

  //Method to process the input of digits and decimal points
  public void processDigitInput(JButton value) {
    // ignore decimal points if they are already exists one in buffer
    if (!(buffer.contains(".") && value.getText().equals("."))) {
      buffer = buffer + value.getText();
    }
  }

  // method to handle input from operation buttons
  public void processOperationInput(String in) {
    switch (in) {
      case "+":
        operation = "+";
        break;
      case "-":
        operation = "-";
        break;
      case "x":
        operation = "*";
        break;
      case "/":
        operation = "/";
        break;
      default:
    }
  }


  // Carries out appropriate arithmetic operation based on state variables
  public void performOperation() {
    if (!buffer.equals(".")) {
      // Handles edge case of first input after default state where buffer is empty and result is zero
      if (!init && !buffer.equals("")) {
        result = Float.parseFloat(buffer);
        txtResult.setText("" + result);
        buffer = "";
        init = true;
      }

      //  Find appropriate arithmetic operation to perform given the user's input
      else if (!operation.equals("") && !buffer.equals("")) {
        switch (operation) {
          case "+":
            result = result + Float.parseFloat(buffer);
            buffer = "";
            txtResult.setText("" + result);
            break;
          case "-":
            result = result - Float.parseFloat(buffer);
            buffer = "";
            txtResult.setText("" + result);
            break;
          case "x":
            result = result * Float.parseFloat(buffer);
            buffer = "";
            txtResult.setText("" + result);
            break;
          case "/":
            result = result / Float.parseFloat(buffer);
            buffer = "";
            txtResult.setText("" + result);
            break;
          case "=":
            txtResult.setText("" + result);
            buffer = "";
            operation = "";
            result = 0;
            init = false;
            break;
          default:
        }
        System.out.println("Current result: " + result);
      }
    }
  }

  // Method to reset calculator's state to default
  public void clear() {
    result = 0;
    txtResult.setText("" + result);
    buffer = "";
    operation = "";
    init = false;
  }

  // Action listener for operation buttons
  class OpActionListener extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent op) {
      performOperation();
      operation = ((JButton) op.getSource()).getText();
      System.out.println(operation);
    }
  }

  // Action listener for digit buttons
  class DigitActionListener extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent digit) {
      processDigitInput((JButton) digit.getSource());
      System.out.println(buffer);
    }
  }

  // Action listener for digit buttons
  class CEActionListener extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ce) {
      clear();
    }
  }

  public static void main(String[] args) {
    new CalculatorGui().setVisible(true);
  }
}
