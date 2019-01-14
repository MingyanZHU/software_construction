package vertex;

import exception.IllegalParamsNumberException;
import exception.IllegalVertexParamsException;

import java.util.Objects;

/**
 * Describe the cast People in Movie Graph with his/her age and gender.
 *
 * @author Zhu Mingyan
 */
public abstract class CastMember extends Vertex {
    /*
    AF: Represents the node of actor or director in Movie Graph
    RI: Age of cast is an integer above zero.
        Gender of cast is 'M' or 'F'.
    Safety for Rep Exposure:
        Other fields except label are modified by private. Clients can not access the field by other function
        except getter function and fillVertexInfo which is strictly limited.
     */

    private int age = 18;
    private String gender = "M";

    public CastMember(String label) throws IllegalVertexParamsException {
        super(label);
    }

    @Override
    protected void checkRep() throws IllegalVertexParamsException {
        super.checkRep();
        if (gender == null) {
            throw new IllegalVertexParamsException("Fill vertex in MovieGraph with null gender!");
        }
        if (age <= 0 || (!gender.equals("M") && !gender.equals("F"))) {
            throw new IllegalVertexParamsException("Fill vertex in MovieGraph with illegal params!");
        }
//        assert age > 0;
//        assert gender != null && (gender.equals("M") || gender.equals("F"));
    }

    /**
     * Fill the other fields in CastMember with a string array with two strings, which is age and gender of
     * the cast in order.
     *
     * @param args String array
     */
    @Override
    public void fillVertexInfo(String[] args) throws IllegalParamsNumberException, IllegalVertexParamsException {
        if (args == null) {
            throw new IllegalParamsNumberException("Fill vertices in MovieGraph with null args!");
        }
        if (args.length != 2) {
            throw new IllegalParamsNumberException("Fill vertices in Movie Graph with " + args.length + " param(s)");
        }
//        this.age = Integer.valueOf(args[0]);
        this.age = Integer.parseInt(args[0]);
        this.gender = args[1];
        checkRep();
    }

    /**
     * Get the age of the cast
     *
     * @return an integer describing age
     */
    public int getAge() {
        return age;
    }

    /**
     * Get the gender of the cast
     *
     * @return 'M' or 'F'
     */
    public String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastMember)) return false;
        if (!super.equals(o)) return false;
        CastMember that = (CastMember) o;
        return age == that.age &&
                gender.equals(that.gender);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), age, gender);
    }
}
