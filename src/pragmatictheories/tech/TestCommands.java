package pragmatictheories.tech;


import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestCommands {


    /**
     * ls
     */
    public static void ls() {
        for (String child : new File(System.getProperty("user.dir")).list()) {
            System.out.println(child);
        }
    }

    /**
     * pwd
     */
    public static void pwd() {
        System.out.println(System.getProperty("user.dir"));
    }

    /**
     * cat
     */
    public static void cat(String filePath) {
        if (new File(filePath).isDirectory())
            System.out.println("cat: " + filePath + ": Is a directory");
        else
            try {
                BufferedReader in = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = in.readLine()) != null)
                    System.out.println(line);
            } catch (IOException ex) {
                System.out.println("cat: " + filePath + ": No such file or directory");
            }
    }

    /**
     * cd
     */
    public static void cd(String goToPath) {
        if (!new File(goToPath).exists())
            System.out.println("bash: cd: " + goToPath + ": No such file or directory");
        else {
            if (new File(goToPath).isDirectory() == true)
                System.setProperty("user.dir", new File(goToPath).getAbsolutePath());
            else
                System.out.println("bash: cd: " + goToPath + ": Not a directory");

            System.out.println("Current directory: " + System.getProperty("user.dir"));
        }

    }

    /**
     * cp  // coping files only, i'll fix this
     */
    public static void cp(String src, String dst) throws IOException {
        if (!new File(src).exists())
            System.out.println("cp: cannot stat \'" + src + "\': No such file or directory");
        else if (new File(src).isDirectory())
            System.out.println("cp: -r not specified; omitting directory \'" + src + "\'");
        else {
            InputStream input = null;
            OutputStream output = null;
            try {
                input = new FileInputStream(new File(src));
                output = new FileOutputStream(new File(dst));
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) > 0) {
                    output.write(buf, 0, bytesRead);
                }
            } finally {
                input.close();
                output.close();
            }
        }

    }

    /**
     * mv
     */
    public static void mv(String filePath, String dst) throws FileSystemException {
        if (!new File(filePath).exists())
            System.out.println("mv: cannot stat \'" + filePath + "\': No such file or directory");
        else {
            File fileToMove = new File(filePath);
            boolean isMoved = fileToMove.renameTo(new File(dst));
            if (!isMoved) {
                throw new FileSystemException("");
            }
        }
    }

    /**
     * rm
     */
    public static void rm(String fileOrDirectoryPath) {

        File toRemove = new File(fileOrDirectoryPath);
        if (!toRemove.exists()) {

            System.out.println("rm: cannot remove " + toRemove.getName() + " : No such file or directory");

        } else {

            if (toRemove.isDirectory())
                System.out.println("rm: cannot remove " + toRemove.getName() + " : Is a directory");
            else
                toRemove.delete();

        }

    }

    /**
     * rm -r
     */
    public static void rmr(String directoryPath) {

        File directory = new File(directoryPath);
        if (!directory.exists()) {

            System.out.println("rm: cannot remove " + directory.getName() + " : No such file or directory");

        } else {
            if (directory.list().length == 0)
                directory.delete();
            else {
                System.out.println(directory.list().toString());
                for (String temp : directory.list()) {
                    (new File(directory, temp)).delete();
                }
                if (directory.list().length == 0) {
                    directory.delete();
                }
            }
        }
    }

    /**
     * rm -i
     */
    public static void rmi(String directoryPath) {

        File toRemove = new File(directoryPath);
        Scanner reader = new Scanner(System.in);

        if (!toRemove.exists()) {

            System.out.println("rm: cannot remove " + toRemove.getName() + " : No such file or directory");

        } else {

            if (toRemove.isFile() && toRemove.length() == 0)
                System.out.println("rm: remove regular empty file " + toRemove.getName() + " ? y");

            if (toRemove.isFile() && toRemove.length() > 0)
                System.out.println("rm: remove regular file " + toRemove.getName() + " ? y");

            if (toRemove.isDirectory())
                System.out.println("rm: remove directory " + toRemove.getName() + " ?");

            if (reader.nextLine().equals("y")) {
                if ((new File(directoryPath)).isDirectory())
                    rmr(directoryPath);
                if ((new File(directoryPath)).isFile())
                    rm(directoryPath);
                reader.close();
            } else
                reader.close();
        }

    }

    /**
     * tail
     */
    public static final List<String> tail(final Path source) throws IOException {

        try (Stream<String> stream = Files.lines(source)) {
            RingBuffer buffer = new RingBuffer(10);
            stream.forEach(line -> buffer.collect(line));
            return buffer.contents();
        }

    }

    private static final class RingBuffer {

        private final int limit;
        private final String[] data;
        private int counter = 0;

        public RingBuffer(int limit) {
            this.limit = limit;
            this.data = new String[limit];
        }

        public void collect(String line) {
            data[counter++ % limit] = line;
        }

        public List<String> contents() {
            return IntStream.range(counter < limit ? 0 : counter - limit, counter)
                    .mapToObj(index -> data[index % limit])
                    .collect(Collectors.toList());
        }

    }


    /**
     * date
     */
    public static void date(String[] args) {
        System.out.println("**** date ****");
        DateFormat dateFormat = (new SimpleDateFormat("E MMM d HH:mm:ss z Y"));
        if (args.length == 0)
            System.out.println(dateFormat.format(new Date()));
        else if (args[0].equals("-u")) {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            System.out.println(dateFormat.format(new Date()));
        } else if (args[0].equals("--date") && args[1].equals("tomorrow")) {
            System.out.println(dateFormat.format(new Date().getTime() + 86400000));
        } else if (args[0].equals("--set") && args.length > 1) { // u could write this better
            //
        }
    }


    /**
     * ping
     */
    public static void ping(String address) {
        try {
            new PingResult((Runtime.getRuntime().exec(address)).getErrorStream()).start();
            new PingResult((Runtime.getRuntime().exec(address)).getInputStream()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PingResult extends Thread {

        private InputStream inputStream;

        PingResult(InputStream is) {
            this.inputStream = is;
        }

        public void run() {
            try {
                while ((new BufferedReader(new InputStreamReader(inputStream))).readLine() != null) {
                    System.out.println((new BufferedReader(new InputStreamReader(inputStream))).readLine());
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * echo
     */
    public void echo(String[] args) {
        System.out.println("**** echo ****");
        System.out.println(String.join(" ", args));
    }


}
