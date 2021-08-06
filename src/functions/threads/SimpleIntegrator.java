package functions.threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }
//решаются задачи
    @Override
    public void run() {
        for (int i = 0; i < task.tasks; i++) {
            synchronized (task) {
                while (task.function == null) {
                }
                System.out.println("Result " + task.leftX + " " + task.rightX + " " + task.step + " "
                        + Functions.integral(task.function, task.leftX, task.rightX, task.step) + "\n");
            }
        }
    }
}
