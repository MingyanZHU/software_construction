import edge.Edge;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.VertexFactory;
import graph.Graph;
import helper.OutputGraph;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import strategy.BufferInputStrategy;
import strategy.BufferOutputStrategy;
import strategy.ReaderInputStrategy;
import strategy.StreamInputStrategy;
import strategy.StreamOutputStrategy;
import strategy.WriterOutputStrategy;
import vertex.Vertex;

public class Main {

  /**
   * For some test of Cpu and memory Profiler.
   */
  public static void main(String[] args)
      throws IOException, InputFileAgainException, InterruptedException {
    final String filePath = "src/txt/Spring2018_HITCS_SC_Lab5/file1.txt";
    final String fileName = "src/txt/outputGraph.txt";
    final String ip = "192.138.2.5";
    Thread.sleep(20000);
    String[] argsss = {ip};
    final Vertex vertex = VertexFactory.createVertex("Computer10001", "Computer", argsss);
    final Vertex vertex1 = VertexFactory.createVertex("Computer1002", "Computer", argsss);
    final Vertex vertex2 = VertexFactory.createVertex("Server4", "Server", argsss);
    final Graph graph = GraphFactory
        .createGraphStrategy(new BufferInputStrategy(filePath), filePath);
    System.err.println("Finished");
    Thread.sleep(20000);
    long startTime;
    long endTime;
    //System.out.println(graph.vertices().contains(vertex2));
    final Edge edge = EdgeFactory.createEdge("edge001", "NetworkConnection",
        11, Arrays.asList(vertex, vertex1));
    //Thread.sleep(10000);
    startTime = System.currentTimeMillis();
    graph.addVertex(vertex);
    endTime = System.currentTimeMillis();
    System.err.println("Add Vertex " + (endTime - startTime) + " ms");
    //Thread.sleep(10000);
    startTime = System.currentTimeMillis();
    graph.addVertex(vertex1);
    endTime = System.currentTimeMillis();
    System.err.println("Add Vertex " + (endTime - startTime) + " ms");
    //Thread.sleep(10000);
    startTime = System.currentTimeMillis();
    graph.addEdge(edge);
    endTime = System.currentTimeMillis();
    System.err.println("Add Edge " + (endTime - startTime) + " ms");
    startTime = System.currentTimeMillis();
    graph.removeVertex(vertex2);
    endTime = System.currentTimeMillis();
    System.err.println("Remove Vertex " + (endTime - startTime) + " ms");
    startTime = System.currentTimeMillis();
    graph.removeEdge(edge);
    endTime = System.currentTimeMillis();
    System.err.println("Remove Edge " + (endTime - startTime) + " ms");
    startTime = System.currentTimeMillis();
    final Set vertices = graph.vertices();
    endTime = System.currentTimeMillis();
    System.err.println("Vertices " + (endTime - startTime) + " ms");
    startTime = System.currentTimeMillis();
    graph.sources(vertex2);
    endTime = System.currentTimeMillis();
    System.err.println("Sources " + (endTime - startTime) + " ms");
    startTime = System.currentTimeMillis();
    graph.targets(vertex2);
    endTime = System.currentTimeMillis();
    System.err.println("Targets " + (endTime - startTime) + " ms");
    startTime = System.currentTimeMillis();
    final Set edges = graph.edges();
    endTime = System.currentTimeMillis();
    System.err.println("edges " + (endTime - startTime) + " ms");
    System.out.println(vertices);
    System.out.println(edges);
    //Thread.sleep(20000);
    //for (int i = 1; i <= 100; i++) {
    //System.err.println(i + "#");
    //startTime = System.currentTimeMillis();
    //Graph graph = GraphFactory.createGraphStrategy(new BufferInputStrategy(filePath), filePath);
    //endTime = System.currentTimeMillis();
    //System.err.println("Read " + graph.getGraphName() + " " + (endTime - startTime) + " ms");
    //startTime = System.currentTimeMillis();
    //OutputGraph.outputGraphStrategy(graph, fileName, new BufferOutputStrategy(fileName));
    //endTime = System.currentTimeMillis();
    //System.err.println("Output " + graph.getGraphName() + " " + (endTime - startTime) + " ms");
    //}
  }
}
