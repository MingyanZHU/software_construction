package ladder;

import basic.ConfigReader;
import basic.Params;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class LadderGenerator {

  private int numberOfLadders;
  private int length;

  /*
  Rep invariant:
    numberOfLadders is equal to Params.n
    length is equal to Params.h
  Abstraction function:
    represents a generator can generate ladders with same pedals on them.
  Safety from rep exposure:
    all fields are private and immutable.
  Thread safety arguments:
    there is no sharing data between threads and list of returning is thread safe.
   */
  // 不做更多的优化 在寻找梯子的时候直接遍历梯子的列表
  // 优化的方式可以是维护一个集合 其中的所有的梯子都没有猴子使用

  /**
   * Can generating Params.n ladders in config.
   *
   * @throws ConfigurationException if config is wrong.
   */
  public LadderGenerator() throws ConfigurationException {
    this.length = ConfigReader.getConfig(Params.h);
    this.numberOfLadders = ConfigReader.getConfig(Params.n);
    this.checkRep();
  }

  private void checkRep() {
    try {
      int number = ConfigReader.getConfig(Params.n);
      int numberOfPedals = ConfigReader.getConfig(Params.h);

      assert length == numberOfPedals;
      assert numberOfLadders == number;
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Generating Params.h ladders.
   *
   * @return list of all generated ladders and make sure list is thread safe.
   */
  public List<Ladder> ladders() {
    List<Ladder> ladders = new ArrayList<>();
    for (int id = 1; id <= numberOfLadders; id++) {
      ladders.add(new Ladder(id, length));
    }
    assert numberOfLadders == ladders.size();
    return Collections.synchronizedList(ladders);
  }
}
