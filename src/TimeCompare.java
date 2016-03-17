import java.util.*;

/**
* Compares the time of two edges, a and b.
*/
public class TimeCompare implements Comparator<Edge<String>> {
	
	/**
	* @return 1 (int)	if the time value of the second edge is smaller then the time value of the 
	*					first edge.
	* @return 0 (int)	if the time values of the two edges are equal.
	* @return -1 (int)	if the time value of the first edge is smaller then the time value of the 
	*					second edge.
	*/
	public int compare(Edge<String> a, Edge<String> b){
		if(a.getTime() < b.getTime())
			return -1;
		else if(a.getTime()==b.getTime()) 
			return 0;
		else
			return 1;
	}
}