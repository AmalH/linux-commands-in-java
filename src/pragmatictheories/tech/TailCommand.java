package pragmatictheories.tech;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;

public class TailCommand implements Runnable {

    private boolean debug = false;

    private int runEveryNseconds;
    private long lastKnownPosition = 0;
    private boolean shouldIRun = true;
    private File fileToRead;
    private static int readLinesCounter = 0;


    public static void main(String argv[]) {

        Executors.newFixedThreadPool(4)
                .execute(new TailCommand("/home/ihavenoname/Desktop/LinuxCommandsInJava/testFile", 2000));

    }

    public TailCommand(String myFile, int myInterval) {
        fileToRead = new File(myFile);
        this.runEveryNseconds = myInterval;
    }

    public void stopRunning() {
        shouldIRun = false;
    }

    public void run() {
        try {
            while (shouldIRun) {
                Thread.sleep(runEveryNseconds);
                if (fileToRead.length()> lastKnownPosition) {

                    // Reading and writing file
                    RandomAccessFile readWriteFileAccess = new RandomAccessFile(fileToRead, "rw");
                    readWriteFileAccess.seek(lastKnownPosition);
                    String fileLine;
                    while ((fileLine = readWriteFileAccess.readLine()) != null&&readLinesCounter<10) {
                        System.out.println(fileLine);
                        readLinesCounter++;
                    }
                    lastKnownPosition = readWriteFileAccess.getFilePointer();
                    readWriteFileAccess.close();
                    System.out.println(lastKnownPosition);
                } else {
                    if (debug)
                        System.out.println("Couldn't found new line after line # " + readLinesCounter);
                }
            }
        } catch (Exception e) {
            stopRunning();
        }
        if (debug)
            System.out.println("Exit the program...");
    }

}
