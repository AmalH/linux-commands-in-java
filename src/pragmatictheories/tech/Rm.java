package pragmatictheories.tech;

import java.io.File;

public class Rm {


    public static void main(String[] args)  {

        //rm("/home/ihavenoname/Desktop/amal");
        rmr("/home/ihavenoname/Desktop/LinuxCommandsInJava/bn");
    }

    /**** rm ****/
    static void rm(String fileOrDirectoryPath){

        File toRemove = new File(fileOrDirectoryPath);
        if(!toRemove.exists()){

            System.out.println("rm: cannot remove "+toRemove.getName()+" : No such file or directory");

        }else{

            if(toRemove.isDirectory())
                System.out.println("rm: cannot remove "+toRemove.getName()+" : Is a directory");
            else
                toRemove.delete();

        }
        System.exit(0);

    }

    /**** rm -r ****/
    static void rmr(String directoryPath){

        File directory = new File(directoryPath);
        if(!directory.exists()){

            System.out.println("rm: cannot remove "+directory.getName()+" : No such file or directory");

        }else {
            if (directory.list().length == 0)
                directory.delete();
            else {
                for (String temp : directory.list()) {
                    rm(directoryPath+"/"+temp);
                }
                if (directory.list().length == 0) {
                    directory.delete();
                }
            }
        }
    }


    // force deletion

    // recursive deletion

    // removing multiple files

    // removing directories

    // interactive deletion

}
