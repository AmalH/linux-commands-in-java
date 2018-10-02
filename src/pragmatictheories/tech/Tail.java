package pragmatictheories.tech;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tail {


    public static void main(String[] args) throws IOException {
        int linesLimit=10;
        //if(args[0]!=null)
           // linesLimit=Integer.parseInt(args[0].substring(2));
        tailFile( FileSystems.getDefault()
                .getPath("/.../...", "testFile"), linesLimit)
                .forEach(line -> System.out.println(line));
    }

    private static final List<String> tailFile(final Path source, final int limit) throws IOException {

        try (Stream<String> stream = Files.lines(source)) {
            RingBuffer buffer = new RingBuffer(limit);
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


}