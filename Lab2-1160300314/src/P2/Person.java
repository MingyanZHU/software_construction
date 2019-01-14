package P2;

/**
 * The vertex in friendship graph which is an immutable object.
 */
public class Person {
    // fields
    private final String name;

    // Abstraction function:
    // the vertex in graph with label.
    //
    // Representation invariant:
    // name is non-null and non empty string. Any two person who has equal
    // name are the same person
    //
    // Safety from rep exposure
    // Fields are modified by keyword private and final. And only getter
    // method without setter method so clients can not change the object.

    /**
     * Constructor with label
     * 
     * @param name
     *            the label of person
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Get name of the person
     * 
     * @return name 
     * */
    public String getName() {
        return this.name;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof Person) {
            Person anotherThat = (Person) that;
            return this.getName().equals(anotherThat.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }
}