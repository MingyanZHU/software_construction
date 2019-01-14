package memento;

import exception.ContinueRunningException;
import java.util.ArrayList;

/**
 * Memo manager.
 *
 * @author Zhu Mingyan
 */
public class Caretaker {

  // AF: Representing a Caretaker for memo
  // RI: mementos should not be null.
  // Safety for Rep Exposure:
  //      mementos are modified by keyword private. And there is no setter functions.
  private ArrayList<Memento> mementos = new ArrayList<>();

  /**
   * Add memento at the end.
   *
   * @param memento needed to be saved.
   */
  public boolean save(Memento memento) {
    if (memento == null) {
      return false;
    } else {
      return mementos.add(memento);
    }
  }

  /**
   * Get the latest memento.
   *
   * @return latest memento.
   * @throws ContinueRunningException if mementos is empty.
   */
  public Memento restore() throws ContinueRunningException {
    if (mementos.isEmpty()) {
      throw new ContinueRunningException("Can not roll back because of empty mementos!");
    }
    return mementos.get(mementos.size() - 1);
  }


  /**
   * Get the i-th latest memento.
   *
   * @return the i-th latest memento.
   * @throws ContinueRunningException if i < 0 or i > number of memento.
   */
  public Memento restore(int i) throws ContinueRunningException {
    if (mementos.size() - i < 0 || i < 1) {
      throw new ContinueRunningException("Cannot rollback so many back");
    }
    Memento answer = mementos.get(mementos.size() - i);
    for (int j = mementos.size() - 1; j > i; j--) {
      Memento memento = mementos.get(j);
      mementos.remove(memento);
    }
    return answer;
  }

  //    /**
  //     * Get the state recorded in the latest memento.
  //     *
  //     * @return state
  //     */
  //    public State getState() {
  //        return mementos.get(mementos.size() - 1).getState();
  //    }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Memento memento : mementos) {
      stringBuilder.append(memento.toString());
    }
    return stringBuilder.toString();
  }
}
