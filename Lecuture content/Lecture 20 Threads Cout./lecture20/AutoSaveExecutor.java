
public class AutoSaveExecutor implements Runnable {
    private final int saveInterval;

    public AutoSaveExecutor(int saveInterval) {
        this.saveInterval = saveInterval;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(saveInterval);
                FileWorker fileWorker =
                        new FileWorker("src/lecture21/editing.txt");
                Thread fwt = new Thread(fileWorker);
                fwt.start();

                fwt.join();

                System.out.println("Write completed at " + System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
