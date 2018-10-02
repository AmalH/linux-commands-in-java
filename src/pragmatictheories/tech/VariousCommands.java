package pragmatictheories.tech;


import java.io.*;
import java.nio.file.FileSystemException;

public class VariousCommands {

    public static void main(String[] args) throws IOException {

        //ls();
        //pwd();
        //cat("/../../sampleFile");
        //cd("/../../sampleFile");
        //cp("/home/ihavenoname/Desktop/32", "/home/ihavenoname/Desktop/2");
        mv("/home/ihavenoname/Desktop/a","/home/ihavenoname/Desktop/music");
        /*String[] toEcho={"hi","ammoul","are you here"};
        echo(toEcho);*/

    }

    private static void ls() {
        System.out.println("**** ls ****");
        for (String child : new File(System.getProperty("user.dir")).list()) {
            System.out.println(child);
        }
    }

    private static void pwd() {
        System.out.println("**** pwd ****");
        System.out.println(System.getProperty("user.dir"));
    }

    private static void cat(String filePath) {
        System.out.println("**** cat ****");
        if (!new File(filePath).isDirectory())
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

    private static void cd(String goToPath) {
        System.out.println("***** cd *****");
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

    private static void cp(String src, String dst) throws IOException { // args
        System.out.println("**** cp ****");
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

    private static void mv(String filePath, String dst) throws FileSystemException {
        System.out.println("**** mv ****");
        if (!new File(filePath).exists())
            System.out.println("mv: cannot stat \'" + filePath + "\': No such file or directory");
        else {
            File fileToMove = new File(filePath);
            boolean isMoved = fileToMove.renameTo(new File(dst));
            if (!isMoved) {
                System.out.println("Not moved !");
                //throw new FileSystemException("src/test/resources/movedFile_jdk6.txt");
            }
        }
    }

    private static void echo(String[] args) {
        System.out.println("**** echo ****");
        System.out.println(String.join(" ", args));
    }

    private static void date() {
        System.out.println("**** date ****");

    }


}
