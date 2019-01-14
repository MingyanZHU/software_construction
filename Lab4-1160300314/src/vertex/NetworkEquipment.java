package vertex;

import exception.ContinueRunningException;
import exception.IllegalParamsNumberException;
import exception.IllegalVertexParamsException;
import exception.InputFileAgainException;
import factory.LoggerFactory;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import memento.Caretaker;
import memento.Memento;
import org.apache.log4j.Logger;
import state.NetworkState;
import state.Open;
import state.State;

/**
 * Describe all kinds of equipment in network with its ip address.
 *
 * @author Zhu Mingyan
 */
public abstract class NetworkEquipment extends Vertex {

  /*
  AI: Represents a node of network graph.
  RI: ip address of any network equipment instance are valid.
  Safety for Rep Exposure:
      The ip address in field is modified by key word ip. Clients can access it only by the getter
      function, and there is no any other setter function can change its ip except the function
      fillVertexInfo which is strictly limited.
   */
  private String ip = "192.168.1.1";
  private NetworkState state = Open.instance;
  private Caretaker caretaker = new Caretaker();
  private static final Logger LOGGER = LoggerFactory.createLogger(NetworkEquipment.class);

  NetworkEquipment(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
    save();
  }

  @Override
  protected void checkRep() throws IllegalVertexParamsException {
    // TODO Defencive Programming
    super.checkRep();
    //        assert ip != null;
    if (ip == null) {
      throw new IllegalVertexParamsException("ip address can not be null!");
    }
    Pattern pattern = Pattern.compile(
        "^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}"
            + "(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$");
    // The ip address should be valid.
    Matcher matcher = pattern.matcher(ip);
    //        assert matcher.matches();
    if (!matcher.matches()) {
      throw new IllegalVertexParamsException("ip address is invalid!");
    }
  }

  @Override
  public void fillVertexInfo(String[] args) throws InputFileAgainException {
    if (args == null) {
      throw new IllegalParamsNumberException("Fill Vertices in Network with a null args!");
    }
    if (args.length != 1) {
      throw new IllegalParamsNumberException(
          "Fill Vertices in Network with " + args.length + " param(s)");
    }
    this.ip = args[0];
    checkRep();
  }

  /**
   * Get the ip address.
   *
   * @return an ip address.
   */
  public String getIp() {
    return ip;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NetworkEquipment)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    NetworkEquipment that = (NetworkEquipment) o;
    return ip.equals(that.ip);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), ip);
  }

  public State getState() {
    return state;
  }

  public void close() {
    this.state = state.close();
    this.save();
  }

  public void open() {
    this.state = state.open();
    this.save();
  }

  public void save() {
    caretaker.save(new Memento<>(this.state));
  }


  /**
   * Restore to the latest state.
   *
   * @return the latest state.
   */
  public State restore() {
    try {
      this.state = (NetworkState) this.caretaker.restore().getState();
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return this.state;
  }

  /**
   * Restore to the i-th latest state.
   *
   * @param i the i-th
   * @return the i-th latest state.
   */
  public State restore(int i) {
    try {
      this.state = (NetworkState) this.caretaker.restore(i).getState();
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return this.state;
  }

  public String outputMementos() {
    return this.caretaker.toString();
  }
}
