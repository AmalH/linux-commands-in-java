package pragmatictheories.tech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Grep {

    public static void main(String args[]) {
        try {
            String[] cmd = {"/bin/sh", "-c", "grep 'Report Process started' /.../server.log|wc -l"};
            Process proc = Runtime.getRuntime().exec(cmd);
            printStream(proc.getInputStream());
            printStream(proc.getErrorStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


        private static void printStream(InputStream in) throws IOException {
        BufferedReader is = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = is.readLine()) != null) {
            System.out.println(line);
        }
    }

   /* public static void main (String [] argv) throws Exception
    {
        if (argv.length < 2) {
            System.out.println ("Usage: regex file [ ... ]");
            return;
        }

        Pattern pattern = Pattern.compile (argv [0]);
        Matcher matcher = pattern.matcher ("");

        for (int i = 1; i < argv.length; i++) {
            String file = argv [i];
            BufferedReader br = null;
            String line;

            try {
                br = new BufferedReader (new FileReader(file));
            } catch (IOException e) {
                System.err.println ("Cannot read '" + file
                        + "': " + e.getMessage());
                continue;
            }

            while ((line = br.readLine()) != null) {
                matcher.reset (line);

                if (matcher.find()) {
                    System.out.println (file + ": " + line);
                }
            }

            br.close();
        }
    }*/

}
