package Lab3Help;

import java.io.*;
import java.util.*;

/** Some methods that can be used to read stop and line files. */

public class Lab3File {
    // Known stop names.
    private Set<String> names = new HashSet<String>();

    /** Checks that the given name is known, and throws an exception
       if it isn't. */
    private void checkKnown(String name) throws MalformedData {
        if (! names.contains(name)) {
            throw new MalformedData("Unknown stop: " + name);
        }
    }

    /** Helper method that reads a string from the given StreamTokenizer.
     *   The method terminates the program with an error if the token read 
     *   is not a string.
     *
     *  @param st   the StreamTokenizer
     *  @return     the read string
     */

    private String getString(StreamTokenizer st)
        throws MalformedData, IOException {

	int ttype = st.nextToken();
	
	if (ttype != StreamTokenizer.TT_WORD) {
	    throw new MalformedData("String expected");
	}
	
	return st.sval;
    }

    /** Helper method that reads a natural number from the given
     *   StreamTokenizer. The method terminates the program with an
     *   error if the token read is not a natural number.
     *
     *  @param st   the StreamTokenizer
     *  @return     the read int 
     */

    private int getNatural(StreamTokenizer st)
        throws MalformedData, IOException {

	int ttype = st.nextToken();
	
	if (ttype != StreamTokenizer.TT_NUMBER) {
	    throw new MalformedData("Number expected");
	}

        int value = (int) st.nval;

        if (value < 0) {
	    throw new MalformedData("Non-negative number expected");
        }

	return value;
    }

    /** Reads the bus stops in the given file into a List. Each bus stop
     *   is represented by the BStop class.
     *  <p>
     *  Postcondition: All stop names are unique. (If a duplicate name
     *  is encountered, then an exception is thrown.)
     *
     *  @param fname   the name of the file containing the bus stop data
     *  @return        a List of BStop
     *
     *  @see BStop
     */

    public List<BStop> readStops(String fname)
        throws MalformedData, IOException {

	List<BStop> stops = new ArrayList<BStop>();
        Reader r = new BufferedReader(new FileReader(fname));
        StreamTokenizer st = new StreamTokenizer(r);

        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            st.pushBack();
            String name = getString(st);
            int x       = getNatural(st);
            int y       = getNatural(st);

            if (x > 1000 || y > 1000) {
                throw new MalformedData("Coordinate out of bounds");
            }

            if (names.contains(name)) {
                throw new MalformedData("Duplicate stop: " + name);
            }

            stops.add(new BStop(name, x, y));
            names.add(name);
        }

	return stops;
    }

    
    /** Reads the bus line information from the given file returning a
     *   List of BLineTable. A BLineTable is a time table for a
     *   specific line. It contains an array of BLineStop,
     *   representing the list of bus stops the line stops at.
     *  <p>
     *  This method must be called after <code>readStops</code>.
     *  <p>
     *  Postcondition: All stop names are known. (If an unknown name
     *  is encountered, then an exception is thrown.)
     *
     *  @param fname   the name of the file containing the bus stop data
     *  @return        a List of BLineTable, one BLineTable for each line
     *
     *  @see BLineTable
     *  @see BLineStop */


    public List<BLineTable> readLines(String fname)
        throws MalformedData, IOException {

	List<BLineTable> res = new ArrayList<BLineTable>();
        Reader r = new BufferedReader(new FileReader(fname));
        StreamTokenizer st = new StreamTokenizer(r);

        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            st.pushBack();

            int lineNr  = getNatural(st);
            int stopCnt = getNatural(st);

            BLineStop[] stops = new BLineStop[stopCnt];

            String firstname = getString(st);
            checkKnown(firstname);
            stops[0] = new BLineStop(firstname, 0);

            for (int i = 1; i < stopCnt; i++) {
                String name = getString(st);
                int    time = getNatural(st);

                checkKnown(name);
                stops[i] = new BLineStop(name, time);
            }

            res.add(new BLineTable(stops, lineNr));
        }
	
	return res;	    
    }

}
