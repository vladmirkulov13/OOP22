package functions.threads;

import functions.basic.Log;

import functions.threads.Semaphore;

public class Generator extends Thread {
    private final Task task;
    private Semaphore semaphore;
//также задаются задачи но с помощью семафоры
    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                semaphore.beginWrite();
                task.function = new Log(Math.random() * 10);
                task.leftX = Math.random() * 100;
                task.rightX = Math.random() * 100 + 100;
                task.step = Math.random();
                System.out.println("Source " + task.leftX + " " + task.rightX + " " + task.step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.endWrite();
        }
    }
}
