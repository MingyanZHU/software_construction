package memento;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import state.State;

/**
 * State and the time for recording changes to this status.
 *
 * @param <L> subclass of State
 * @author Zhu Mingyan
 */
public class Memento<L extends State> {

  // AF: Representing a record with state and the time change to this state.
  // RI: L should be subclass of State
  // Safety from Rep exposure:
  //      All fields are modified by keyword private and there is no other ways to get the field.
  private L state;
  private String timeStamp;

  /**
   * Construct a instance with param state.
   */
  public Memento(L state) {
    this.state = state;
    this.timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        .format(Calendar.getInstance().getTime());
  }

  /**
   * Get the state recorded in this memento.
   *
   * @return instances of state
   */
  public L getState() {
    return this.state;
  }

  @Override
  public String toString() {
    return timeStamp + '\t' + state.toString() + '\n';
  }
}
