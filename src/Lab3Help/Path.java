package Lab3Help;

import java.util.Iterator;

/**
A <i>path</i> is a sequence of nodes describing how to get from one
node to another in a graph. A node is represented by its name (a
{@link String}). A node cannot occur more than once in a single path.
The path is initialized by a call to <code>computePath()</code> after
which the relevant information can be retrieved using the methods
<code>getPath()</code> and <code>getPathLength()</code>.
<p>
A class implementing <code>Path</code> could perhaps be used as follows:
<pre>
  Lab3File f = new Lab3File();
  List&lt;BStop&gt;      stops = f.readStops("stops-gbg.txt");
  List&lt;BLineTable&gt; lines = f.readLines("lines-gbg.txt");
  Path&lt;String&gt; p = new MyPath(stops, lines);
  p.computePath("Chalmers","Angered");
  System.out.println("Distance: " + p.getPathLength());
  p.computePath("Chalmers","GuldHeden");
  System.out.println("Distance: " + p.getPathLength());
</pre>
*/
public interface Path<E> {

    /**
    Computes the path from <code>from</code> to <code>to</code> (if any). Path
    information can be retrieved by subsequent calls to
    <code>getPath()</code> and <code>getPathLength()</code>. It must be
    possible to call this method any number of times.
    <p>
    Precondition: The underlying graph must not contain any negative
    edge weights.
    */
    public void computePath(E from, E to);

    /**
    Returns an iterator over the nodes in the path.
    <p>
    If a path has been found the first node in the iterator is the
    argument <code>from</code> passed to <code>computePath</code> and
    the last node is <code>to</code>.
    <p>
    If no path was found or if no call to computePath has been made the
    iterator is empty.

    @return An iterator over the computed path.
    */
    public Iterator<E> getPath();

    /**
    Returns the length of the computed path, that is, the sum of the
    weights of each arc on the path.
    <p>
    If no path was found the return value is an arbitrary integer. It
    is appropriate but not required to return a special value such as
    -1 in this case.

    @return The length of the computed path.
    */
    public int getPathLength();
}
