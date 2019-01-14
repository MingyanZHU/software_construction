package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.HyperEdgeFactory;
import vertex.Actor;

import org.junit.Test;

import java.util.Arrays;

public class SameMovieHyperEdgeTest {
    @Test
    public void testSameMovieHyperEdge() throws InputFileAgainException {
        String label = "ActorsInSR";

        Actor actor1 = new Actor("TimRobbins");
        String[] args1 = {"60", "M"};
        actor1.fillVertexInfo(args1);

        Actor actor2 = new Actor("TomHanks");
        String[] args2 = {"62", "M"};
        actor2.fillVertexInfo(args2);

        Actor actor3 = new Actor("MorganFreeman");
        String[] args3 = {"81", "M"};
        actor3.fillVertexInfo(args3);

        SameMovieHyperEdge sameMovieHyperEdge = new SameMovieHyperEdge(label, -1);
        sameMovieHyperEdge.addVertices(Arrays.asList(actor1, actor2, actor3));

        assertEquals(label, sameMovieHyperEdge.getLabel());
        assertTrue(sameMovieHyperEdge.containVertex(actor1));
        assertTrue(sameMovieHyperEdge.containVertex(actor2));
        assertTrue(sameMovieHyperEdge.containVertex(actor3));

        assertThat(sameMovieHyperEdge.toString(), containsString(actor1.toString()));
        assertThat(sameMovieHyperEdge.toString(), containsString(actor2.toString()));
        assertThat(sameMovieHyperEdge.toString(), containsString(actor3.toString()));
    }

    @Test
    public void testHyperEdgeFactory() throws InputFileAgainException {
        String label = "ActorsInSR";

        Actor actor1 = new Actor("TimRobbins");
        String[] args1 = {"60", "M"};
        actor1.fillVertexInfo(args1);

        Actor actor2 = new Actor("TomHanks");
        String[] args2 = {"62", "M"};
        actor2.fillVertexInfo(args2);

        Actor actor3 = new Actor("MorganFreeman");
        String[] args3 = {"81", "M"};
        actor3.fillVertexInfo(args3);

        HyperEdge sameMovieHyperEdge = HyperEdgeFactory.createEdge(label, "SameMovieHyperEdge", Arrays.asList(actor1, actor2, actor3));

        assertEquals(label, sameMovieHyperEdge.getLabel());
        assertTrue(sameMovieHyperEdge.containVertex(actor1));
        assertTrue(sameMovieHyperEdge.containVertex(actor2));
        assertTrue(sameMovieHyperEdge.containVertex(actor3));

        assertThat(sameMovieHyperEdge.toString(), containsString(actor1.toString()));
        assertThat(sameMovieHyperEdge.toString(), containsString(actor2.toString()));
        assertThat(sameMovieHyperEdge.toString(), containsString(actor3.toString()));
    }

    @Test
    public void testOverride() throws InputFileAgainException {
        String label = "ActorsInSR";

        Actor actor1 = new Actor("TimRobbins");
        String[] args1 = {"60", "M"};
        actor1.fillVertexInfo(args1);

        Actor actor2 = new Actor("TomHanks");
        String[] args2 = {"62", "M"};
        actor2.fillVertexInfo(args2);

        Actor actor3 = new Actor("MorganFreeman");
        String[] args3 = {"81", "M"};
        actor3.fillVertexInfo(args3);

        SameMovieHyperEdge sameMovieHyperEdge = new SameMovieHyperEdge(label, -1);
        sameMovieHyperEdge.addVertices(Arrays.asList(actor1, actor2, actor3));

        HyperEdge hyperEdge = HyperEdgeFactory.createEdge(label, "SameMovieHyperEdge", Arrays.asList(actor1, actor2, actor3));

        assertTrue(hyperEdge.equals(sameMovieHyperEdge));
        assertTrue(sameMovieHyperEdge.equals(hyperEdge));

        assertEquals(hyperEdge.hashCode(), sameMovieHyperEdge.hashCode());
    }

}
