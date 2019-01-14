package graph;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import edge.Edge;
import edge.HyperEdge;
import exception.IllegalGrammarTextException;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.HyperEdgeFactory;
import factory.VertexFactory;
import org.junit.Test;
import vertex.Vertex;

import java.io.IOException;
import java.util.Arrays;

public class MovieGraphTest {
    @Test
    public void testMovieGraph() {
        Graph graph = null;
        try {
            graph = GraphFactory.createGraph("src/txt/inputMovieGraph.txt");
            assertEquals(6, graph.vertices().size());
            assertEquals(6, graph.edges().size());

            Vertex movie1 = VertexFactory.createVertex("TheShawshanRedemption", "Movie", new String[]{"1994", "USA", "9.3"});
            Vertex movie2 = VertexFactory.createVertex("TheGreenMile", "Movie", new String[]{"1999", "USA", "8.5"});

            Vertex actor1 = VertexFactory.createVertex("MorganFreeman", "Actor", new String[]{"81", "M"});
            Vertex actor2 = VertexFactory.createVertex("TimRobbins", "Actor", new String[]{"60", "M"});
            Vertex actor3 = VertexFactory.createVertex("TomHanks", "Actor", new String[]{"62", "M"});

            Vertex director = VertexFactory.createVertex("FrankDarabont", "Director", new String[]{"59", "M"});

            Edge srfd = EdgeFactory.createEdge("SRFD", "MovieDirectorRelation", -1, Arrays.asList(movie1, director));
            Edge gmfd = EdgeFactory.createEdge("GMFD", "MovieDirectorRelation", -1, Arrays.asList(movie2, director));
            Edge srmf = EdgeFactory.createEdge("SRMF", "MovieActorRelation", 1, Arrays.asList(movie1, actor2));
            Edge srtr = EdgeFactory.createEdge("SRTR", "MovieActorRelation", 2, Arrays.asList(movie1, actor1));
            Edge gmth = EdgeFactory.createEdge("GMTH", "MovieActorRelation", 1, Arrays.asList(movie2, actor3));

            HyperEdge hyperEdge = HyperEdgeFactory.createEdge("ActorInSR", "SameMovieHyperEdge", Arrays.asList(actor1, actor2));

            assertTrue(graph.vertices().contains(movie1));
            assertTrue(graph.vertices().contains(actor1));
            assertTrue(graph.edges().contains(srfd));
            assertTrue(graph.edges().contains(gmfd));
            assertTrue(graph.edges().contains(srtr));
            assertTrue(graph.edges().contains(srmf));
            assertTrue(graph.edges().contains(gmth));
            assertTrue(graph.edges().contains(hyperEdge));

            assertFalse(graph.addEdge(srfd));
            assertFalse(graph.addVertex(actor1));

            assertEquals(3, graph.targets(movie1).size());
            assertEquals(3, graph.sources(movie1).size());

            assertThat(graph.toString(), containsString(actor3.toString()));
            assertThat(graph.toString(), containsString(gmfd.toString()));
        } catch (IOException | InputFileAgainException e) {
            e.printStackTrace();
        }

    }
}
