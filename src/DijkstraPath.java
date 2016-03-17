import java.util.*;
import Lab3Help.*;

/**
* A subclass of Dijkstra that computes the shortest path between two nodes by calling its superclass,
*  which uses the Dijkstra-algorithm, and returns the traveling time and the route.
* @author Sara Finati
* @author Sandra Jansson
* @version 1.7.0_51
*/
public class DijkstraPath<E> extends Dijkstra<E> implements Path<E> {
	private E from;
	private E to;
	private ArrayList<E> iterPath;
	
	/**
	 * Three argument constructor
	 * @param graph the graph represented by adjacency lists
	 * @param c Comparator (? super Edge<E>)
	 * @param ints HashTable mapping the nodes to unique integers
	 */
	public DijkstraPath(HashMap<Integer, HashMap<E, Integer>> graph, Comparator<? super Edge<E>> c, Hashtable<E, Integer> ints){
		super(graph,c,ints);
	}
	
	@Override
	/**
	 * Calls the method dijkstra from the Superclass Dijkstra.
	 * @param from start node (E)
	 * @param to end node (E)
	 */
	public void computePath(E from, E to) {
		this.from=from;
		this.to=to;
		super.dijkstra(from, to);
	}

	@Override
	/**
	 * @return the iterator over the traveling route
	 */
	public Iterator<E> getPath(){
		if(ints.get(to)!=null){
			iterPath = new ArrayList<E>();
			iterPath.add(to);
		
			E next = prev.get(ints.get(to));
			if(!from.toString().equals(to.toString())){
				if(next == null){
					iterPath.add(from);
				}

				if(next != null){
					iterPath.add(next);
					while(prev.get(ints.get(next)) != null) {
						next = prev.get(ints.get(next));
						iterPath.add(next);
					}
					iterPath.add(from);
				}
			}
			Collections.reverse(iterPath);

			Iterator<E> iterP = iterPath.iterator();

			return iterP;
		}
		Iterator<E> iterNull = null;
		return iterNull;
	}
	
	@Override
	/**
	 * @return the route length 
	 * @return -1 if it does not exist any path
	 */
	public int getPathLength() {
		if(ints.get(to)!=null){
			return time[ints.get(to)];
		} else {
			return -1;
		}
	}
		
	
}

