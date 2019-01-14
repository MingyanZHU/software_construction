package profiler;

import basic.ConfigReader;
import basic.Direction;
import basic.Params;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import monkey.Monkey;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ThroughputProfiler {

  /**
   * Get the current event of monkeys strategy river and compute throughput and justice.
   *
   * @return throughput and justice answer.
   */
  public static String computeRecord()
      throws IOException, ConfigurationException {
    final BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(new FileInputStream("./log/monkey.log"), "utf-8"));
    final BufferedWriter bufferedWriter = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream("./log/record.txt"), "utf-8"));
    int numberOfMonkey = ConfigReader.getConfig(Params.N);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Params.n.name()).append("\t")
        .append(ConfigReader.getConfig(Params.n)).append("\n");
    stringBuilder.append(Params.h.name()).append("\t")
        .append(ConfigReader.getConfig(Params.h)).append("\n");
    stringBuilder.append(Params.t.name()).append("\t")
        .append(ConfigReader.getConfig(Params.t)).append("\n");
    stringBuilder.append(Params.N.name()).append("\t")
        .append(numberOfMonkey).append("\n");
    stringBuilder.append(Params.k.name()).append("\t")
        .append(ConfigReader.getConfig(Params.k)).append("\n");
    stringBuilder.append(Params.MV.name()).append("\t")
        .append(ConfigReader.getConfig(Params.MV)).append("\n");
    String monkeyInfo = "Monkey = <([\\d]+), ([\\w]+), ([\\d]+), ([\\w]+)>$";
    Pattern monkeyInfoPatter = Pattern.compile(monkeyInfo);
    Map<Integer, String> idStrategy = new HashMap();
    Map<Integer, Integer> idVelocity = new HashMap();
    Map<Integer, Direction> idDirection = new HashMap<>();

    String arrivingInfo = "Monkey([\\d]+) arrived at ([\\d]+)th ladder (R|L) at"
        + " ([\\d]+) after ([\\d]+)$";
    Pattern arrivingInfoPattern = Pattern.compile(arrivingInfo);
    Map<Integer, Integer> idArrivingTime = new HashMap<>();
    Map<Integer, Integer> idLadder = new HashMap<>();
    stringBuilder.append("ID\tStrategy\tDirection\tArriveTime\tVelocity\tLadder\n");

    String input;
    while ((input = bufferedReader.readLine()) != null) {
      Matcher matcherInfo = monkeyInfoPatter.matcher(input);
      Matcher arrivingInfoMatcher = arrivingInfoPattern.matcher(input);
      if (matcherInfo.find()) {
        int id = Integer.parseInt(matcherInfo.group(1));
        idDirection.put(id, Direction.valueOf(matcherInfo.group(2)));
        idVelocity.put(id, Integer.valueOf(matcherInfo.group(3)));
        idStrategy.put(id, matcherInfo.group(4));
      }
      if (arrivingInfoMatcher.find()) {
        int id = Integer.parseInt(arrivingInfoMatcher.group(1));
        idLadder.put(id, Integer.valueOf(arrivingInfoMatcher.group(2)));
        idArrivingTime.put(id, Integer.valueOf(arrivingInfoMatcher.group(4)));
      }
    }

    int maxArrivingTime = -1;
    List<Integer> idList = new ArrayList(idArrivingTime.keySet());
    for (int i : idList) {
      if (idArrivingTime.get(i) > maxArrivingTime) {
        maxArrivingTime = idArrivingTime.get(i);
      }
      stringBuilder.append(i).append("\t")
          .append(idStrategy.get(i)).append("\t")
          .append(idDirection.get(i)).append("\t")
          .append(idArrivingTime.get(i)).append("\t")
          .append(idVelocity.get(i)).append("\t")
          .append(idLadder.get(i)).append("\n");
    }

    stringBuilder.append("Throughput = ").append((double) (numberOfMonkey) / maxArrivingTime)
        .append("\n");
    List<Integer> arrivingList = new ArrayList(idArrivingTime.values());

    BigDecimal sum = BigDecimal.ZERO;
    for (int i = 0; i < idList.size() - 1; i++) {
      for (int j = i + 1; j < arrivingList.size(); j++) {
        sum = sum.add(
            ((idList.get(i) / Monkey.ID_OFFSET - idList.get(j) / Monkey.ID_OFFSET) * (
                arrivingList.get(i) - arrivingList.get(j))) >= 0 ? BigDecimal.ONE
                : BigDecimal.valueOf(-1));
      }
    }

    stringBuilder.append("Justice = ").append(sum.divide(combine(numberOfMonkey, 2), 10,
        BigDecimal.ROUND_HALF_UP)).append("\n");

    bufferedWriter.write(stringBuilder.toString());
    bufferedWriter.close();
    bufferedReader.close();
    return stringBuilder.toString();
  }

  private static BigDecimal combine(int n, int m) {
    if (n < m) {
      throw new RuntimeException("Can not compute combine with n < m!");
    }
    return factorial(n).divide(factorial(m).multiply(factorial(n - m)));
  }

  private static BigDecimal factorial(int n) {
    BigDecimal result = BigDecimal.ONE;
    for (int i = 2; i <= n; i++) {
      result = result.multiply(BigDecimal.valueOf(i));
    }
    return result;
  }
}
