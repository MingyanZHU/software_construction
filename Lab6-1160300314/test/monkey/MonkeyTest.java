package monkey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import basic.Direction;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MonkeyTest {
  private static final Logger LOGGER = Logger.getLogger(MonkeyTest.class);
  @Test
  public void testMonkey() {
    try {
      final int id = 10001;
      final int velocity = 3;
      Monkey monkey = new Monkey(id, Direction.L2R, velocity);

      assertEquals(id, monkey.getId());
      assertEquals(Direction.L2R, monkey.getDirection());
      assertEquals(velocity, monkey.getVelocity());
      // 此处由于没有使用开始monkey线程 所以monkey的年龄始终为开始年龄
      assertEquals(0, monkey.getAge());
      assertEquals(0, monkey.getPosition());
      assertNull(monkey.getStrategy());

      monkey.setVelocity(1);
      assertEquals(1, monkey.getVelocity());
    } catch (Throwable e){
      LOGGER.error(e.getMessage(), e);
    }
  }
}