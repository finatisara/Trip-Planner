/**
* A class that creates an edge between two nodes.
* @author Sara Finati
* @author Sandra Jansson
* @version 1.7.0_51
*/

public class Edge<E> {
	private E from;
	private E to;
	private int time;
	
	
	/**
	* Three argument constructor
	* @param from prev node (E)
	* @param to next node (E) 
	* @param time weight (int)
	*/
	public Edge(E from, E to, int time){
		this.from=from;
		this.to=to;
		this.time=time;
	}
	
	/**
	* @return prev node (E)
	*/
	public E getFrom(){
		return from;
	}
	
	/**
	* @return next node (E)
	*/
	public E getTo(){
		return to;
	}
	
	/**
	* @return time (int)
	*/
	public int getTime(){
		return time;
	}
	
	/**
	 * Sets new weight to an edge
	 * @param time (int)
	 */
	public void setTime(int time){
		this.time=time;
	}
	
}
