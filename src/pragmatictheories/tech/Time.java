package pragmatictheories.tech;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.FileSystems;
import java.text.DecimalFormat;

public class Time {

    public static void execute(String commandToExecute) throws IOException {

        double startSystemTime = getSystemTime(), startUserTime = getUserTime(), startCpuTime = getCpuTime();

        switch (commandToExecute){
            case "ls":
                TestCommands.ls();
                break;
            case "pwd":
                TestCommands.pwd();
                break;
            case "cat":
                TestCommands.cat("/home/noname/Desktop/testFile");
                break;
            case "cd":
                TestCommands.cd("/home/noname/Desktop/testDirectory");
                break;
            case "cp":
                TestCommands.cp("/home/noname/Desktop/testFile1","/home/noname/Desktop/testFile2");
                break;
            case "mv":
                TestCommands.mv("/home/noname/Desktop/testFile","/home/noname/Desktop/testDirectory");
                break;
            case "rm":
                TestCommands.rm("/home/noname/Desktop/testFile");
                break;
            case "rmr":
                TestCommands.rmr("/home/noname/Desktop/testDirectory");
                break;
            case "tail":
                TestCommands.tail(FileSystems.getDefault().getPath("/home/noname/Desktop/testFile"))
                        .forEach(line -> System.out.println(line));
                break;
            case "date -u":
                TestCommands.date(new String[]{"-u"});
                break;
            default:
                System.out.println("You can only test the commands implemented in TestCommands.java !");
        }

        System.out.println(
                "\n real " + formatTime(Double.parseDouble(new DecimalFormat("#0.000").format(getCpuTime() - startCpuTime)))
                        + "\n user " + formatTime(Double.parseDouble(new DecimalFormat("#0.000").format(getUserTime() - startUserTime)))
                        + "\n sys  " + formatTime(Double.parseDouble(new DecimalFormat("#0.000").format(getSystemTime() - startSystemTime))));
    }


    private static double getCpuTime() {
        ThreadMXBean bean =
                ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                (bean.getCurrentThreadCpuTime() / 1000000000.0) : 0L;
    }

    private static double getUserTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                (bean.getCurrentThreadUserTime() / 1000000000.0) : 0L;
    }

    private static double getSystemTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                ((bean.getCurrentThreadCpuTime() - bean.getCurrentThreadUserTime()) / 1000000000.0) : 0L;
    }


    private static String formatTime(Double time) {
        return time.intValue() / 60
                + "m" +
                new DecimalFormat("#0.000").format(time - time.intValue())
                + "s";

    }


}
