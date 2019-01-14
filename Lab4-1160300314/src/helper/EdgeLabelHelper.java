package helper;

import edge.Edge;

/**
 * Used to help display label of edge in visualization of graph better.
 *
 * @param <E> edge type which is one of all edge types we have defined before
 */
public class EdgeLabelHelper<E> extends
    edu.uci.ics.jung.visualization.decorators.ToStringLabeller<Edge> {

  @Override
  public String transform(Edge v) {
    return v.getClass().getSimpleName() + ":\"" + v.getLabel() + "\" " + (v.getWeight() < 0 ? ""
        : v.getWeight());
  }
}
