package application.guihelper;

import java.awt.Dialog;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.log4j.Logger;

public class LoggingQuery {

  private List<LogRecord> records = new ArrayList<>();
  private final Set<String> classes = new HashSet<>();
  private final Set<String> methods = new HashSet<>();

  private JDialog query;
  //    private JDialog display;

  private JComboBox<String> typeComboBox = new JComboBox<>();
  private JComboBox<String> classComboBox = new JComboBox<>();
  private JComboBox<String> methodComboBox = new JComboBox<>();
  private JTextField startTime = new JTextField("2018-05-05T20:00:00Z");
  private JTextField endTime = new JTextField("2018-05-07T10:00:00Z");
  private JTextArea displayArea = new JTextArea(20, 60);
  private JButton button = new JButton("Query");

  private JLabel start = new JLabel("Start time:");
  private JLabel end = new JLabel("End time:");
  private JLabel typeLabel = new JLabel("Type:");
  private JLabel classLabel = new JLabel("Class:");
  private JLabel methodLabel = new JLabel("Method:");

  private JPanel all = new JPanel();
  private JPanel panel = new JPanel();
  private JPanel startPane = new JPanel();
  private JPanel endPane = new JPanel();
  private JPanel typePane = new JPanel();
  private JPanel classPane = new JPanel();
  private JPanel methodPane = new JPanel();
  //    private JPanel displayPane = new JPanel();
  private JScrollPane displayPane = new JScrollPane(displayArea);

  private GroupLayout layout = new GroupLayout(panel);

  /**
   * Logging query windows.
   */
  public LoggingQuery() {
    query = new JDialog((Dialog) null, "Log Query", true);
    String string =
        "^((?:19|20)[\\d]+-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) "
            + "(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]) {2}"
            + "\\[ ([\\w]+(.[\\w]+)*) \\]"
            + " - \\[ (ERROR|DEBUG|INFO|FATAL|WARN) \\]"
            + " - \\[ [\\w.<>$]+\\(([\\w]+.java:[\\d]+)\\) \\] (([^\\r\\n]+))$";
    //String string = "^((?:19|20)[\\d]+-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))
    // "
    //  + "(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]) {2}"
    //  + "\\[ [\\w]+.([\\w]+) \\] - \\[ (ERROR|DEBUG|INFO|FATAL|WARN) \\] "
    //  + "- \\[ [\\w]+.[\\w]+.([\\w]+)\\(([\\w]+.java:[\\d]+)\\) \\] (([^\\r\\n]+))$";
    Pattern pattern = Pattern.compile(string);
    InputStreamReader reader;
    BufferedReader bufferedReader;
    try {
      reader = new InputStreamReader(new FileInputStream(
          "log/debug.log"), "utf-8");
      bufferedReader = new BufferedReader(reader);

      String stringInput;
      while ((stringInput = bufferedReader.readLine()) != null) {
        Matcher matcher = pattern.matcher(stringInput);
        if (matcher.matches()) {
          classes.add(matcher.group(2));
          methods.add(matcher.group(4));
          records.add(new LogRecord(Instant.parse(matcher.group(1)
              .replace(" ", "T") + "Z"),
              matcher.group(2), matcher.group(4), matcher.group(5), matcher.group(6)));
        }
      }
      ini();
      reader.close();
      bufferedReader.close();
    } catch (IOException e) {
      Logger.getLogger(LoggingQuery.class);
    }
  }

  private void ini() {
    query.setSize(1200, 500);
    //        display = new JDialog((Dialog) null, "Display", true);
    //        display.setSize(600, 800);

    typeComboBox.addItem("");
    typeComboBox.addItem(Type.DEBUG.name());
    typeComboBox.addItem(Type.ERROR.name());
    typeComboBox.addItem(Type.INFO.name());
    typeComboBox.addItem(Type.WARN.name());
    typeComboBox.addItem(Type.FATAL.name());

    //        methodComboBox.addItem("");
    //        for (String string : methods) {
    //            methodComboBox.addItem(string);
    //        }

    classComboBox.addItem("");
    for (String string : classes) {
      classComboBox.addItem(string);
    }

    startPane.add(start);
    startPane.add(startTime);

    endPane.add(end);
    endPane.add(endTime);

    typePane.add(typeLabel);
    typePane.add(typeComboBox);

    classPane.add(classLabel);
    classPane.add(classComboBox);

    methodPane.add(methodLabel);
    methodPane.add(methodComboBox);

    //        displayPane.add(displayArea);
    displayPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    displayPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    displayArea.setFont(new Font("Consolas", Font.PLAIN, 16));
    panel.setLayout(layout);
    GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(typePane).addComponent(classPane)
        .addComponent(startPane).addComponent(endPane).addComponent(button));
    hgroup.addGap(5);
    layout.setHorizontalGroup(hgroup);

    GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
    vgroup.addGap(10);
    vgroup.addComponent(typePane);
    vgroup.addGap(10);
    vgroup.addComponent(classPane);
    vgroup.addGap(10);
    //        vGroup.addComponent(methodPane);
    //        vGroup.addGap(10);
    vgroup.addComponent(startPane);
    vgroup.addGap(10);
    vgroup.addComponent(endPane);
    vgroup.addGap(10);
    vgroup.addComponent(button);
    vgroup.addGap(10);
    layout.setVerticalGroup(vgroup);

    button.addActionListener(e -> {
      String answer = searchLog((String) typeComboBox.getSelectedItem(),
          (String) classComboBox.getSelectedItem(),
          Instant.parse(startTime.getText()), Instant.parse(endTime.getText()));
      displayArea.setText(answer);
      System.out.println(answer);
    });

    all.add(panel);
    all.add(displayPane);

    query.setContentPane(all);
    query.setVisible(true);

    //        display.setContentPane(displayPane);
  }

  private String searchLog(String type, String classes, Instant start, Instant end) {
    StringBuilder stringBuilder = new StringBuilder();
    for (LogRecord logRecord : records) {
      if (type.length() == 0 || logRecord.getType().equals(type)) {
        if (classes.length() == 0 || logRecord.getClassName().equals(classes)) {
          if (logRecord.getTime().isAfter(start) && logRecord.getTime().isBefore(end)) {
            stringBuilder.append(logRecord.toString());
          }
        }
      }
    }
    return stringBuilder.toString();
  }

}

