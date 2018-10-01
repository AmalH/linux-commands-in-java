package pragmatictheories.tech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ping {


    public static void main(String[] args) {

        try {
            new PingResult((Runtime.getRuntime().exec("ping google.com")).getErrorStream()).start();
            new PingResult((Runtime.getRuntime().exec("ping google.com")).getInputStream()).start();
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

}
