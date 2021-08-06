package functions.threads;

import functions.Functions;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;
    //также решаются задачи но с помощью семафоры
    public Integrator(Task task, Semaphore semaphore) {
        this.semaphore = semaphore;
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.tasks; i++) {

            try {
                semaphore.beginRead();
                System.out.println("Result " + task.leftX + " " + task.rightX + " " + task.step + " "
                        + Functions.integral(task.function, task.leftX, task.rightX, task.step) + "\n");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            semaphore.endRead();
        }
    }
}
