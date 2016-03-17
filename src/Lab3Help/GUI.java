package Lab3Help;

import java.awt.event.*;
import java.util.*;

/** A GUI for programming assignment 3. */
public class GUI {
    private List<BStop> stops;
    private List<BLineTable> lines;
    private Path<String> p;

    // Maps known stop names to stops.
    private Map<String,BStop> stopMap;

    private Lab3Frame f;

    /** A GUI for programming assignment 3.
        <p>
        Preconditions: All stop names must be unique. All stop names in
        <code>lines</code> must also exist in <code>stops</code>. The
        <code>Path</code> must be a path for the transport network
        defined by <code>lines</code> and <code>stops</code>.
     */
    public GUI(List<BStop> stops, List<BLineTable> lines,
               Path<String> p) {
        this.stops = stops;
        this.lines = lines;
        this.p = p;

        stopMap = new HashMap<String,BStop>();
        for (BStop stop : stops) {
            stopMap.put(stop.toString(), stop);
        }

        // Set up the text window.
        f = new Lab3Frame();
        for (BStop stop : stops) {
            f.addStop(stop);
        }
        f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        f.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    shortestPath(f.getFrom().toString(),
                                 f.getTo().toString());
                }
            });
        f.pack();
        f.setVisible(true);
    }

    /** A GUI for programming assignment 3.
        <p>
        The program takes three arguments:
        <ol>
        <li>A program that performs the computations. This program
        must accept input and produce output in certain formats (see
        {@link External}).
        <li>A stop file.
        <li>A line file.
        </ol>
     */
    public static void main(String[] args) {

        if (args.length != 3) {
            System.err.println
                ("Arguments: <program> <stop file> <line file>");
            System.exit(1);
        }

        try {
            Lab3File f = new Lab3File();
            List<BStop>      stops = f.readStops(args[1]);
            List<BLineTable> lines = f.readLines(args[2]);

            GUI gui = new GUI(stops, lines,
                              new External(args[0], stops, lines));
        } catch (Exception e) {
            System.err.println
                ("A problem has been encountered: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }

    /** Draws the line consisting of the given stops.

        Segments between unknown stops are ignored.
     */
    private void drawLine(BusMapFrame b, Iterator<String> line) {
        if (line.hasNext()) {
            BStop prev = stopMap.get(line.next());
            while (line.hasNext()) {
                BStop next = stopMap.get(line.next());
                if (prev != null && next != null) {
                    b.drawEdge(prev.getX(), prev.getY(), next.getX(), next.getY());
                }
                prev = next;
            }
        }
    }

    /** Computes a shortest path and displays some information about
        it.
    */
    private void shortestPath(String from, String to) {
        p.computePath(from, to);

        // Update the text box.
        Iterator<String> path = p.getPath();

        f.clearText();

        if (f.getFrom() == f.getTo() || path.hasNext()) {
            f.writeln("Total travel time: " + p.getPathLength() + " minutes");
        } else {
            f.writeln("No path found.");
        }

        while (path.hasNext()) {
            f.writeln(path.next());
        }

        // Create a new map.
        BusMapFrame b = new BusMapFrame();
        b.initMap();
        b.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        b.setLocationRelativeTo(null);  // Centred on the screen.

        for (BStop stop : stops) {
            b.drawStop(stop.getX(), stop.getY(), stop.toString());
        }

        for (final BLineTable line : lines) {
            drawLine(b, new Iterator<String>() {
                    private Iterator<BLineStop> stops =
                        Arrays.asList(line.getStops()).iterator();

                    public boolean hasNext() {
                        return stops.hasNext();
                    }

                    public String next() {
                        return stops.next().getName();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
            b.nextColor();
        }

        b.initShortestPath();
        drawLine(b, p.getPath());

        b.finalMap();
    }
}
