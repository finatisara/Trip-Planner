package Lab3Help;

/** A BLineStop represents a bus stop visited by a line. Each line has
 * an associated BLineTable, containing an array of BLineStops. Each
 * BLineStop contains the name of the stop and the amount of time
 * needed for the transition from the stop before to the stop itself.
 *
 */

public class BLineStop {
    
    protected String stop;
    protected int time;
   
    /** Creates a new BLineStop with the given name and the given
     *  transition time.
     * 
     *  @param stop   The name of the stop.
     *  @param time   The transition time from the previous stop to this
     *                one. (Must be non-negative. 0 for the first stop.)
     */
 
    public BLineStop(String stop, int time) {
	this.stop = stop;
	this.time = time;
    }
    
    /** Returns the time needed from the stop before to this stop.
     *
     *  @return   the time needed from the stop before to this stop.
     *
     */

    public int getTime() {
	return time;
    }

    /** Returns the name of this stop.
     *
     *  @return   the name of this stop.
     *
     */
    
    public String getName() {
	return stop;
    }
}

