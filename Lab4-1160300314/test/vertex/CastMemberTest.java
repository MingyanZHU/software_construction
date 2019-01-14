package vertex;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
      args: null, illegal number params, correct number of params
            but illegal param, legal param args
   */

  @Test
  public void testFillVertexInfo() {
    String label = "TomHanks";
    int age = 62;
    String gender = "M";
    String[] args = {gender, String.valueOf(age)};
    String[] args1 = {String.valueOf(age)};
    String[] args2 = {null, String.valueOf(age)};
    String[] args3 = {gender, String.valueOf(-1)};

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
    String[] args = {gender, String.valueOf(age)};

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
    String[] args = {gender, String.valueOf(age)};

    try {
      Director director = new Director(label);
      director.fillVertexInfo(args);

      assertEquals(label, director.getLabel());
      assertEquals(age, director.getAge());
      assertEquals(gender, director.getGender());
      Vertex vertex = VertexFactory.createVertex(label, directorType,
          new String[]{"F", String.valueOf(60)});
      assertFalse(director.equals(vertex));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testActorOverride() {
    String label = "TomHanks";
    int age = 62;
    String gender = "M";
    String[] args = {gender, String.valueOf(age)};

    try {
      Actor actor1 = new Actor(label);
      Actor actor2 = new Actor(label);
      Director director = new Director(label);

      actor1.fillVertexInfo(args);
      actor2.fillVertexInfo(args);
      director.fillVertexInfo(args);

      assertEquals(actor1, actor1);
      assertEquals(actor1, actor2);
      assertEquals(actor2, actor1);
      assertEquals(actor1.hashCode(), actor2.hashCode());

      assertNotEquals(actor1, director);
      assertNotEquals(director, actor2);

      assertThat(actor1.toString(), containsString("name='" + actor1.getLabel() + '\''
          + ", age=" + age + ", gender='" + gender + '\''));
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
    String[] args = {gender, String.valueOf(age)};

    try {
      Director director1 = new Director(label);
      Director director2 = new Director(label);
      Actor actor = new Actor(label);

      director1.fillVertexInfo(args);
      director2.fillVertexInfo(args);
      actor.fillVertexInfo(args);

      assertEquals(director1, director1);
      assertEquals(director1, director2);
      assertEquals(director2, director1);
      assertEquals(director1.hashCode(), director2.hashCode());

      assertNotEquals(director1, actor);
      assertNotEquals(actor, director2);

      assertThat(director1.toString(), containsString("name='" + director1.getLabel()
          + '\'' + ", age=" + age + ", gender='" + gender + '\''));
      assertThat(director1.toString(), containsString("Director"));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
