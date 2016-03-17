import java.util.*;

/**
 * A class that computes the shortest path between two nodes using the Dijkstra algorithm
 * @author Sandra Jansson
 * @author Sara Finati
 */
public class Dijkstra<E>{
	public PriorityQueue<Edge<E>> queue;
	public HashMap<Integer, HashMap<E, Integer>> graph;
	public Hashtable<E, Integer> ints;

	public int[] time;
	public boolean[] known;
	public ArrayList<E> prev;
	public int inf = 10000000;

	/**
	 * Three argument constructor 
	 * @param graph a graph represented by adjacency lists
	 * @param comp Comparator<? super Edge<E>>
	 * @param ints a hashtable that maps the nodes to unique integers
	 */
	public Dijkstra(HashMap<Integer, HashMap<E, Integer>> graph, Comparator<? super Edge<E>> c, Hashtable<E, Integer> ints) {
		this.ints = ints;
		queue = new PriorityQueue<Edge<E>>(graph.size(), c);
		this.graph = graph;
		prev = new ArrayList<E>();
	}

	/**
	 * Computes the shortest path from the start node to the end node using Dijkstra's
	 * algorithm with a priority queue.
	 * @param from start node (E)
	 * @param to end node (E)
	 */
	public void dijkstra(E from, E to) {
		if (graph.containsKey(ints.get(from)) && graph.containsKey(ints.get(to))) {
			time = new int[graph.size()];
			known = new boolean[graph.size()];
			for (int i = 0; i < graph.size(); i++) {
				time[i] = inf;
				known[i] = false;
				prev.add(null);
			}
			time[ints.get(from)] = 0;
			known[ints.get(from)] = true;

			Set<E> keys = graph.get(ints.get(from)).keySet();
			Iterator<E> iter = keys.iterator();
			for (; iter.hasNext();) {
				E tmp = iter.next();
				Edge<E> e = new Edge<E>(from, tmp, graph.get(ints.get(from)).get(tmp));
				queue.add(e);
				time[ints.get(e.getTo())] = graph.get(ints.get(from)).get(tmp);
			}

			while(known[ints.get(to)] != true) {
				int tmp = 0;
				Edge<E> min = queue.poll();
				tmp = min.getTime();
				known[ints.get(min.getTo())] = true;
				if(min.getTime() < time[ints.get(min.getTo())]) {
					time[ints.get(min.getTo())] = min.getTime(); 
					prev.set(ints.get(min.getTo()), min.getFrom());
				}

				Set<E> keysMin = graph.get(ints.get(min.getTo())).keySet();
				Iterator<E> iterMin = keysMin.iterator();
				for(; iterMin.hasNext();) {
					E tmpIterMin = iterMin.next();
					if (known[ints.get(tmpIterMin)] != true) {
						Edge<E> eMin = new Edge<E>(min.getTo(), tmpIterMin, graph.get(ints.get(min.getTo())).get(tmpIterMin));
						int minTime = eMin.getTime();

						if (tmp + minTime < time[ints.get(eMin.getTo())]) {
							time[ints.get(eMin.getTo())] = tmp + minTime;
							prev.set(ints.get(eMin.getTo()), eMin.getFrom());
						}
						eMin.setTime(minTime + tmp);
						queue.add(eMin);
					}
				}
			}
		}

	}

}