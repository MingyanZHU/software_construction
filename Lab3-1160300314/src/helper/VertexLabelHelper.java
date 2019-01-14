package helper;

import vertex.Vertex;

/**
 * Used to help display the visualization of graph better
 *
 * @param <V> vertex type which should one of all vertex types we have defined before
 */
public class VertexLabelHelper<V> extends edu.uci.ics.jung.visualization.decorators.ToStringLabeller<Vertex> {
    @Override
    public String transform(Vertex v) {
        return v.getClass().getSimpleName() + ":" + v.getLabel();
    }
}
