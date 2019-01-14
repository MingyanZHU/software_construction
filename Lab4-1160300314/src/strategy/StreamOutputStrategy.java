package strategy;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamOutputStrategy implements OutputStrategy {

  private DataOutputStream dataOutputStream;

  public StreamOutputStrategy(String filePath) throws FileNotFoundException {
    dataOutputStream = new DataOutputStream(new FileOutputStream(filePath));
  }

  @Override
  public void write(String string) throws IOException {
    dataOutputStream.writeBytes(string);
  }

  @Override
  public void close() throws IOException {
    dataOutputStream.flush();
    dataOutputStream.close();
  }
}
