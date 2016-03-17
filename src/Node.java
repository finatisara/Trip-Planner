/**
* A class that creates a node.
* @author Sara Finati
* @author Sandra Jansson
* @version 1.7.0_51
*/
public class Node<E> {
	private E name;
	
	/**
	* One argument constructor
	* @param name name (E)
	* @param x x-koord (int)
	* @param y y-koord (int)
	*/
	public Node(E name){
		this.name=name;
	}
	
	/**
	* @return name (String)
	*/
	public String getName(){
		return name.toString();
	}

}
