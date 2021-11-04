
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoSaveDemo {
    public static void main(String[] args) {
//        AutoSaveExecutor ase = new AutoSaveExecutor(10_000);
//        Thread aset = new Thread(ase);
//        aset.start();

        ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new FileWorker(
                "src/lecture21/editing.txt"),
                10,
                10,
                TimeUnit.SECONDS);
    }
}
