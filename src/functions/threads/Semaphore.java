package functions.threads;

public class Semaphore {
    private boolean canWrite = true;

    public synchronized void beginRead() throws InterruptedException {
        while (canWrite) {
            wait();
            // освобождает монитор и переводит
            // вызывающий поток в состояние ожидания до тех пор,
            // пока другой поток не вызовет метод notify()
        }
    }

    public synchronized void endRead() {
        canWrite = true;
        //возобновляет работу всех потоков, у которых ранее был вызван метод wait()
        notifyAll();
    }

    public synchronized void beginWrite() throws InterruptedException {
        while (!canWrite) {
            wait();
        }
    }

    public synchronized void endWrite() {
        canWrite = false;
        notifyAll();
    }

}
