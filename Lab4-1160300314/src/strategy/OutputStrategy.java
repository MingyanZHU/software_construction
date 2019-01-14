package strategy;

import java.io.IOException;

public interface OutputStrategy {

  public void write(String string) throws IOException;

  public void close() throws IOException;

}
