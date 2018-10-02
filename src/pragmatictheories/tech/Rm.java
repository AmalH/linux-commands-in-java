package pragmatictheories.tech;

import java.io.File;
import java.util.Scanner;

public class Rm {


    public static void main(String[] args)  {

        //rm("/.../amal");
        //rmi("../sampleFile");
        //rmr(...);
    }

    /**** rm ****/
    private static void rm(String fileOrDirectoryPath){

        File toRemove = new File(fileOrDirectoryPath);
        if(!toRemove.exists()){

            System.out.println("rm: cannot remove "+toRemove.getName()+" : No such file or directory");

        }else{

            if(toRemove.isDirectory())
                System.out.println("rm: cannot remove "+toRemove.getName()+" : Is a directory");
            else
                toRemove.delete();

        }

    }

    /**** rm -r ****/
    private static void rmr(String directoryPath){

        File directory = new File(directoryPath);
        if(!directory.exists()){

            System.out.println("rm: cannot remove "+directory.getName()+" : No such file or directory");

        }else {
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

    /**** rm -i ****/

    private static void rmi(String directoryPath){

        File toRemove= new File(directoryPath);
        Scanner reader = new Scanner(System.in);

        if(!toRemove.exists()){

            System.out.println("rm: cannot remove "+toRemove.getName()+" : No such file or directory");

        } else {

            if(toRemove.isFile() && toRemove.length()==0)
                System.out.println("rm: remove regular empty file "+toRemove.getName()+" ? y");

            if(toRemove.isFile() && toRemove.length()>0)
                System.out.println("rm: remove regular file "+toRemove.getName()+" ? y");

            if(toRemove.isDirectory())
                System.out.println("rm: remove directory "+toRemove.getName()+" ?");

            if(reader.nextLine().equals("y")){
                if((new File(directoryPath)).isDirectory())
                    rmr(directoryPath);
                if((new File(directoryPath)).isFile())
                    rm(directoryPath);
                reader.close();
            }
            else
                reader.close();
        }

    }



}
