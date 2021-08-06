package functions.threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable {
   private final Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }
//задаются задачи
    @Override
    public void run() {
        for (int i = 0; i < task.tasks; i++) {
            synchronized (task) {
                task.function = new Log(Math.random() * 10);
                task.leftX = Math.random() * 100;
                task.rightX = Math.random() * 100 + 100;
                task.step = Math.random();
                System.out.println("Source " + task.leftX + " " + task.rightX + " " + task.step);
            }
        }
    }
}
