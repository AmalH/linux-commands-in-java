package pragmatictheories.tech;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.FileSystems;
import java.text.DecimalFormat;

public class Time {

    public static void run(String commandName) throws IOException {

        double startSystemTime = getSystemTime(), startUserTime = getUserTime(), startCpuTime = getCpuTime();

        switch (commandName){
            case "ls":
                TestCommands.ls();
                break;
            case "pwd":
                TestCommands.pwd();
                break;
            case "cat":
                TestCommands.cat("");
                break;
            case "cd":
                TestCommands.cd("");
                break;
            case "cp":
                TestCommands.cp("","");
                break;
            case "mv":
                TestCommands.mv("","");
                break;
            case "rm":
                TestCommands.rm("");
                break;
            case "rmr":
                TestCommands.rmr("");
                break;
            case "tail":
                TestCommands.tail(FileSystems.getDefault().getPath(""))
                        .forEach(line -> System.out.println(line));
                break;
            case "date -u":
                TestCommands.date(new String[]{"-u"});
                break;
            default:
                System.out.println("Please test only the commands implemented in TestCommands.java !");
        }

        System.out.println(
                "\n real " + formatTime(Double.parseDouble(new DecimalFormat("#0.000").format(getCpuTime() - startCpuTime)))
                        + "\n user " + formatTime(Double.parseDouble(new DecimalFormat("#0.000").format(getUserTime() - startUserTime)))
                        + "\n sys  " + formatTime(Double.parseDouble(new DecimalFormat("#0.000").format(getSystemTime() - startSystemTime))));
    }


    private static double getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
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
