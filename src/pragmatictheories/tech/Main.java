package pragmatictheories.tech;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        ls();
        pwd();
        cat("/home/ihavenoname/Desktop/LinuxCommandsInJava/LinuxCommandsInJava.iml");

    }

    static void ls(){
        File dir = new File(System.getProperty("user.dir"));
        String childs[] = dir.list();
        System.out.println("**** ls ****");
        for(String child: childs){
            System.out.println(child);
        }
    }

    static void pwd(){
        String pwd = System.getProperty("user.dir");
        System.out.println("**** pwd ****");
        System.out.println(pwd);
    }

    static void cat(String filePath){
        System.out.println("**** cat ****");
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader in = new BufferedReader(fileReader);
            String line;
            while((line = in.readLine())!= null){
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println(filePath+", file not found.");
        }
    }
}
