
package edu.union.adt.graph;
import edu.union.adt.graph.Graph;
import edu.union.adt.graph.GraphImplementation;

/**
* Graph Factory that return the graph depends on certain criteria
*
*
*/

class GraphFactory<V> {

	public static Graph<V> createGraph() {
		return new GraphImplementation<V>();
	}
}