package Lab3Help;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.lang.Runtime;

/** Implements the <code>Path</code> interface by calling an external
    program.
    <p>
    This program must take four arguments:
    <ol>
    <li>A stop file.
    <li>A line file.
    <li>The point of departure.
    <li>The point of arrival.
    </ol>
    <p>
    If a shortest path is found, then the program must produce output
    in the following format:
    <ol>
    <li>The number of minutes required to perform the trip (a decimal
    number).
    <li>Every stop along the way, including the points of departure
    and arrival.
    </ol>
    <p>
    If no shortest path is found, then the program must produce output
    in a format that cannot be confused with the format given above.
 */

public class External implements Path<String> {
    private static int BUFFERSIZE = 4096;
    private void writeStops(List<BStop> stops, File stopsFile) {
        try {
            Writer fileWriter = new BufferedWriter(new FileWriter(stopsFile), BUFFERSIZE);
            for (BStop stop : stops) {
                fileWriter.write(stop.getName() + "\t" + stop.getX() + "\t" + stop.getY() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLines(List<BLineTable> lines, File linesFile) {
        try {
            Writer fileWriter = new BufferedWriter(new FileWriter(linesFile), BUFFERSIZE);
            for (int i = 0; i < lines.size(); i++) {
                BLineTable line = lines.get(i);
                BLineStop[] stops = line.getStops();
                fileWriter.write("" + line.getLineNo() + "\t" + stops.length + "\n");
                for (int j = 0; j < stops.length; j++) {
                    fileWriter.write(stops[j].getName() + "\t");
                    if (j > 0) {
                        fileWriter.write("" + stops[j].getTime() + "\n");
                    } else {
                        fileWriter.write("\n");
                    }
                }
                if (i != lines.size() - 1) {
                    fileWriter.write("\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String run(String[] args) {
        try {
            // run process with args as arguments
            List<String> command = new LinkedList<>();
            command.add(program);
            command.addAll(Arrays.asList(args));
            Process external;
            try {
                external =
                    new java.lang.ProcessBuilder(command).redirectErrorStream(true).start();
            } catch (IOException e) {
                command.set(0, program + ".bat");
                external =
                    new java.lang.ProcessBuilder(command).redirectErrorStream(true).start();
            }

            BufferedWriter externalInput = new BufferedWriter(new OutputStreamWriter(external.getOutputStream()));
            BufferedReader externalOutput = new BufferedReader(new InputStreamReader(external.getInputStream()));
            externalInput.close();
            // wait for it to finish
            external.waitFor();

            // read and return output
            StringBuilder output = new StringBuilder();
            String line = null;
            while ((line = externalOutput.readLine()) != null) {
                output.append(line);
                output.append("\n");
            }
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private String program;
    private File stopsFile;
    private File linesFile;
    private static List<File> tempFiles = new ArrayList<File>();

    /** Creates an instance of this class for the program
        <code>program</code> (or, if the use of <code>program</code>
        fails in certain ways, <code>program + ".bat"</code>) and the
        transport network defined by <code>stops</code> and
        <code>lines</code>.
        <p>
        Preconditions: The program must accept input and produce
        output in a certain format (see {@link External}). All stop
        names must be unique. All stop names in <code>lines</code>
        must also exist in <code>stops</code>.
    */
    public External(String program,
                    List<BStop> stops, List<BLineTable> lines) {
        this.program = program;
        // open a pipe to the external process, with stdin argument.
        try {
            // remove old temporary files, if any
            Iterator<File> it = tempFiles.iterator();
            while (it.hasNext()) {
                File f = it.next();
                if (f.exists()) {
                    f.delete();
                }
                it.remove();
            }

            stopsFile = File.createTempFile("stops", ".txt");
            linesFile = File.createTempFile("lines", ".txt");
            writeStops(stops, stopsFile);
            writeLines(lines, linesFile);
            stopsFile.deleteOnExit();
            linesFile.deleteOnExit();
            tempFiles.add(stopsFile);
            tempFiles.add(linesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Creates an instance of this class for the program
        <code>Lab3</code> (or, if the use of <code>Lab3</code> fails
        in certain ways, <code>Lab3.bat</code>) in the current
        directory and the transport network defined by
        <code>stops</code> and <code>lines</code>.
        <p>
        Preconditions: The program must accept input and produce
        output in a certain format (see {@link External}). All stop
        names must be unique. All stop names in <code>lines</code>
        must also exist in <code>stops</code>.
    */
    public External(List<BStop> stops, List<BLineTable> lines) {
        this(Paths.get(".", "Lab3").toString(), stops, lines);
    }

    private int pathLength;
    private List<String> pathStops;
    public void computePath(String from, String to) {
        try {
            String[] args = { stopsFile.getCanonicalPath(), linesFile.getCanonicalPath(), from, to };
            String output = run(args);
            BufferedReader outputReader = new BufferedReader(new StringReader(output), BUFFERSIZE);
            String line = outputReader.readLine();
            try {
                this.pathLength = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                this.pathLength = -1;
            }
            this.pathStops = new ArrayList<String>();
            while ((line = outputReader.readLine()) != null) {
                this.pathStops.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Iterator<String> getPath() {
        return this.pathStops.iterator();
    }
    public int getPathLength() {
        return this.pathLength;
    }
}
