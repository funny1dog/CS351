
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWorker implements Runnable {
    private final String source;
    private final String dest;

    public FileWorker(String source) {
        this.source = source;
        dest = "src/lecture21/backup.txt";
    }

    @Override
    public void run() {
        Path sourceFile = Paths.get(source);
        Path destFile = Paths.get(dest);

        try (BufferedReader reader = Files.newBufferedReader(sourceFile);
                BufferedWriter writer = Files.newBufferedWriter(destFile)) {
            reader.lines().forEach(line -> {
                try {
                    writer.write(line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Done backing up");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        FileWorker fileWorker = new FileWorker();
//        fileWorker.run();
//        ScheduledExecutorService executor =
//                Executors.newScheduledThreadPool(1);
//
//        executor.scheduleAtFixedRate(
//                new FileWorker(source),
//                10,
//                10,
//                TimeUnit.SECONDS);
    }
}
