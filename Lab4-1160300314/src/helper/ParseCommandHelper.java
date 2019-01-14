package helper;

import exception.IllegalGrammarTextException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Used to process input string to get information.
 *
 * @author Zhu Mingyan
 */
public class ParseCommandHelper {

  private static final Logger LOGGER = Logger.getLogger(ParseCommandHelper.class);
  private static String typeString = "^(GraphType|VertexType|EdgeType|GraphName) *"
      + "= *(\\\"[\\w]+\\\")(, *\\\"[\\w]+\\\")* *$";
  private static Pattern typePattern = Pattern.compile(typeString);

  private static String vertexString = "^Vertex *= *<\\\"[\\w]+\\\", *\\\"[A-Za-z]+"
      + "\\\"(, *<(\\\"[\\w.]+\\\")(, *\\\"[\\w.]+\\\")*>)?> *$";
  private static Pattern vertexPattern = Pattern.compile(vertexString);

  private static String edgeString =
      "^Edge *= *<\\\"[\\w]+\\\", *\\\"[a-zA-Z]+\\\", *\\\"([+-]?[0-9]*"
          + "\\.?[0-9]+|[0-9]+\\.?[0-9]*)\\\", *\\\"[\\w]+\\\", *"
          + "\\\"[\\w]+\\\", *\\\"(Yes|No)\\\"> *$";
  private static Pattern edgePattern = Pattern.compile(edgeString);


  private static String hyperEdgeString = "^HyperEdge *= *<\\\"[\\w]+\\\", *\\\"[a-zA-Z]+\\\", *"
      + "\\{ *\\\"[\\w]+\\\"(, *\\\"[\\w]+\\\")* *\\}> *$";
  private static Pattern hyperEdgePattern = Pattern.compile(hyperEdgeString);

  /**
   * Used to process input string to get information.
   *
   * @param input string which is non-null string
   * @return a list of string and each string of list is the information of input
   */
  public static List<String> fileCommandHelper(String input) throws IllegalGrammarTextException {
    List<String> answerList = new ArrayList<>();
    if (input.length() == 0) {
      return answerList;
    }
    Matcher matcher1 = typePattern.matcher(input);
    boolean answer1 = matcher1.matches();
    Matcher matcher2 = vertexPattern.matcher(input);
    boolean answer2 = matcher2.matches();
    Matcher matcher3 = edgePattern.matcher(input);
    boolean answer3 = matcher3.matches();
    Matcher matcher4 = hyperEdgePattern.matcher(input);
    boolean answer4 = matcher4.matches();
    if (!answer1 && !answer2 && !answer3 && !answer4) {
      throw new IllegalGrammarTextException("Input file has grammar errors " + input);
    }
    //    if (!typePattern.matcher(input).matches() && !vertexPattern.matcher(input).matches()
    //        && !edgePattern.matcher(input).matches() && !hyperEdgePattern.matcher(input)
    //        .matches()) {
    //      throw new IllegalGrammarTextException(input);
    //      // LOGGER.error(input, e);
    //      // throw e;
    //    }
    String inputWithoutSpaces = input.replace(" ", "").replace("\"", "");
    String[] stringList = inputWithoutSpaces.split("=");
    assert stringList.length == 2;
    switch (stringList[0]) {
      case "GraphType":
        answerList.add("GraphType");
        answerList.add(stringList[1]);
        break;
      case "GraphName":
        answerList.add("GraphName");
        answerList.add(stringList[1]);
        break;
      case "VertexType":
        answerList.add("VertexType");
        answerList.addAll(Arrays.asList(stringList[1].split(",")));
        break;
      case "Vertex":
        answerList.add("Vertex");
        answerList.addAll(Arrays.asList(stringList[1]
            .replace("<", "")
            .replace(">", " ")
            .replace(",", " ")
            .split(" ")));
        break;
      case "EdgeType":
        answerList.add("EdgeType");
        answerList.addAll(Arrays.asList(stringList[1].split(",")));
        break;
      case "Edge":
        answerList.add("Edge");
        answerList.addAll(Arrays.asList(stringList[1]
            .replace("<", "")
            .replace(">", " ")
            .replace(",", " ")
            .split(" ")));
        break;
      case "HyperEdge":
        answerList.add("HyperEdge");
        answerList.addAll(Arrays.asList(stringList[1]
            .replace("<", "")
            .replace(">", " ")
            .replace(",", " ")
            .replace("{", "")
            .replace("}", " ")
            .split(" ")));
        break;
      default:
        return Collections.emptyList();
    }
    return answerList;
  }

  // 用作测试
  //    public static void main(String[] args) {
  //        try {
  //            FileReader reader = new FileReader("src/txt/SocialNetworkForTestPattern.txt");
  //            BufferedReader bufferedReader = new BufferedReader(reader);
  //
  //            String input;
  //            while ((input = bufferedReader.readLine()) != null) {
  //                List<String> list = ParseCommandHelper.fileCommandHelper(input);
  //                for (String string : list) {
  //                    System.out.println(string);
  //                }
  //                System.out.println("-----------------------------");
  //            }
  //        } catch (IOException e) {
  //            e.printStackTrace();
  //        } catch (InputFileAgainException e) {
  //            e.printStackTrace();
  //        }
  //
  //    }
}
