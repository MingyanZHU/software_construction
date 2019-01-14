package vertex;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.IllegalParamsNumberException;
import exception.InputFileAgainException;
import factory.LoggerFactory;
import factory.VertexFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class CastMemberTest {
    private static final Logger LOGGER = LoggerFactory.createLogger(CastMemberTest.class);
    private static final String actorType = "Actor";
    private static final String directorType = "Director";

    /*
    Test strategy
    Partition for inputs of fillVertexInfo(args)
        args: null, illegal number params, correct number of params but illegal param, legal param args
     */

    @Test
    public void testFillVertexInfo() {
        String label = "TomHanks";
        int age = 62;
        String gender = "M";
        String[] args = {String.valueOf(age), gender};
        String[] args1 = {String.valueOf(age)};
        String[] args2 = {String.valueOf(age), null};
        String[] args3 = {String.valueOf(-1), gender};

        try {
            Actor actor = (Actor) VertexFactory.createVertex(label, actorType, args);

            assertEquals(label, actor.getLabel());
            assertEquals(age, actor.getAge());
            assertEquals(gender, actor.getGender());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            Director director = (Director) VertexFactory.createVertex(label, directorType, null);

            assertEquals(label, director.getLabel());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            Actor actor = (Actor) VertexFactory.createVertex(label, actorType, args1);

            assertEquals(label, actor.getLabel());
            assertEquals(age, actor.getAge());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            Director director = (Director) VertexFactory.createVertex(label, directorType, args2);

            assertEquals(label, director.getLabel());
            assertEquals(age, director.getAge());
            assertEquals(gender, director.getGender());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            Actor actor = (Actor) VertexFactory.createVertex(label, actorType, args3);

            assertEquals(label, actor.getLabel());
            assertEquals(age, actor.getAge());
            assertEquals(gender, actor.getGender());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void testActor() {
        String label = "TomHanks";
        int age = 62;
        String gender = "M";
        String[] args = {String.valueOf(age), gender};

        try {
            Actor actor = new Actor(label);
            actor.fillVertexInfo(args);

            assertEquals(label, actor.getLabel());
            assertEquals(age, actor.getAge());
            assertEquals(gender, actor.getGender());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void testDirector() {
        String label = "FrankDarabont";
        int age = 59;
        String gender = "M";
        String[] args = {String.valueOf(age), gender};

        try {
            Director director = new Director(label);
            director.fillVertexInfo(args);

            assertEquals(label, director.getLabel());
            assertEquals(age, director.getAge());
            assertEquals(gender, director.getGender());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void testActorOverride() {
        String label = "TomHanks";
        int age = 62;
        String gender = "M";
        String[] args = {String.valueOf(age), gender};

        try {
            Actor actor1 = new Actor(label);
            Actor actor2 = new Actor(label);
            Director director = new Director(label);

            actor1.fillVertexInfo(args);
            actor2.fillVertexInfo(args);
            director.fillVertexInfo(args);

            assertTrue(actor1.equals(actor1));
            assertTrue(actor1.equals(actor2));
            assertTrue(actor2.equals(actor1));
            assertTrue(actor1.hashCode() == actor2.hashCode());

            assertFalse(actor1.equals(director));
            assertFalse(director.equals(actor2));

            assertThat(actor1.toString(), containsString("name='" + actor1.getLabel() + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\''));
            assertThat(actor1.toString(), containsString("Actor"));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void testDirectorOverride() {
        String label = "FrankDarabont";
        int age = 59;
        String gender = "M";
        String[] args = {String.valueOf(age), gender};

        try {
            Director director1 = new Director(label);
            Director director2 = new Director(label);
            Actor actor = new Actor(label);

            director1.fillVertexInfo(args);
            director2.fillVertexInfo(args);
            actor.fillVertexInfo(args);

            assertTrue(director1.equals(director1));
            assertTrue(director1.equals(director2));
            assertTrue(director2.equals(director1));
            assertTrue(director1.hashCode() == director2.hashCode());

            assertFalse(director1.equals(actor));
            assertFalse(actor.equals(director2));

            assertThat(director1.toString(), containsString("name='" + director1.getLabel() + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\''));
            assertThat(director1.toString(), containsString("Director"));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
