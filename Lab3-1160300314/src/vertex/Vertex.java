package vertex;

import exception.IllegalParamsNumberException;
import exception.IllegalVertexParamsException;
import exception.InputFileAgainException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A abstract class of vertex which defined the Spec all kinds of vertices should be obeyed.
 *
 * @author Zhu Mingyan
 */
public abstract class Vertex implements Cloneable {

    /*
    AF: Represents the node of Graph.
    RI: All the label of vertex instance is unique.
        label of vertex is non-null and non-empty String.
    Safety for Rep Exposure:
        The label filed is modified by private and final. Clients can not access it outside this class.
     */

    private final String label;

    /**
     * Constructor of Vertex with its label as param.
     *
     * @param label
     */
    public Vertex(String label) {
        this.label = label;
    }

    protected void checkRep() throws IllegalVertexParamsException {
        if (label == null) {
            throw new IllegalVertexParamsException("Label is null!");
        }
//        assert label != null;
//        assert label.length() != 0;
        Pattern pattern = Pattern.compile("^[\\w]+$");
        // All label consists of [A-Za-z_0-9]
        Matcher matcher = pattern.matcher(label);
//        assert matcher.matches();
        if (label.length() == 0 || !matcher.matches()) {
            throw new IllegalVertexParamsException("Label is illegal!");
        }
    }

    /**
     * Fill the vertex other fields except label with an array of String.
     *
     * @param args String array
     *             // TODO @throws
     */
    public abstract void fillVertexInfo(String[] args) throws InputFileAgainException;

    /**
     * Get the label of vertex instance
     *
     * @return label of vertex
     */
    public String getLabel() {
        return this.label;
    }

    // 由于Vertex为抽象类并且所有继承Vertex类的子类均重写了toString方法
    // 因此 Vertex类中的toString方法可以删去
//    @Override
//    public String toString() {
//        return "Vertex{" +
//                "label='" + getLabel() + '\'' +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return label.equals(vertex.label);
    }

    @Override
    public int hashCode() {

        return Objects.hash(label);
    }

    @Override
    public Vertex clone() throws CloneNotSupportedException {
        return (Vertex) super.clone();
    }
}
