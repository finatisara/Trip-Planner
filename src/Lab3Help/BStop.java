package Lab3Help;

/** The BStop class represents a bus stop. It contains the name of the 
 *   bus stop and the x and y coordinates of it.
 *
 */

public class BStop {
    
    protected String name;
    protected int x,y;

    /** Creates a new bus stop with the given name at the given coordinates.
     *
     *  @param name   the name of the bus stop
     *  @param x      the x coordinate of the bus stop (between 0 and
     *                1000, inclusive)
     *  @param y      the y coordinate of the bus stop (between 0 and
     *                1000, inclusive)
     *
     */
    
    public BStop(String name, int x, int y) {
	this.name = name;
	this.x    = x;
	this.y    = y;
    }

    /** Returns the x coordinate of the bus stop 
     *
     *  @return the x coordinate of the bus stop
     */

    public int getX() { return x; }

    /** Returns the y coordinate of the bus stop 
     *
     *  @return the y coordinate of the bus stop
     */

    public int getY() { return y; }

    /** Returns the name of the bus stop 
     *
     *  @return the name of the bus stop
     */

    public String getName() { return name; }

    /** Returns the name of the bus stop
     *
     *  @return the name of the bus stop
     */

    public String toString() { return getName(); }
    
}
