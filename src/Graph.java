import java.util.*;

/**
 * A class that implements a graph using adjacency lists
 * @author Sara Finati
 * @author Sandra Jansson
 * @version 1.7.0_51
 */
public class Graph<E> {
	private HashMap<Integer, HashMap<E, Integer>> adjList;
	private Hashtable<E, Integer> ints;

	/**
	 * Default constructor 
	 */
	public Graph() {
		adjList = new HashMap<Integer, HashMap<E, Integer>>();
		ints = new Hashtable<E, Integer>();
	}

	/**
	 * Adds a node to the graph
	 * @param node node (E)
	 * @param i index (int)
	 */
	public void addNode(E node, int i) {
		ints.put(node, i);
		adjList.put(ints.get(node), new HashMap<E, Integer>());
	}

	/**
	 * Adds an edge between node "from" and node "to" in the graph. If the edge already
	 * exist, the weights are compared and only the smallest weight are stored. 
	 * @param from from node (E)
	 * @param to to node (E)
	 * @param time weights
	 */
	public void addEdge(E from, E to, int time){
		if(time < 0) {
			System.out.println("Error: Weights must be positive");
		} else {
			if (adjList.containsKey(ints.get(from))) {
				if(adjList.get(ints.get(from)).containsKey(to)) {
					if (time < adjList.get(ints.get(from)).get(to)) {
						adjList.get(ints.get(from)).put(to, time);
					}
				} else {
					adjList.get(ints.get(from)).put(to, time);
				}
			} else {
				System.out.println("Error: Node does not exist");
			}
		}
	}

	/**
	 * @return adjList the representation of the graph
	 */
	public HashMap<Integer, HashMap<E, Integer>> getGraph(){
		return adjList;
	}

	/**
	 * @return ints a hashtable that maps the node to integers (Hashtable)
	 */
	public Hashtable<E, Integer> getTable() {
		return ints;
	}

	/**
	 * @return size the size of the graph, i.e the number of nodes
	 */
	public int size() {
		return adjList.size();
	}
}