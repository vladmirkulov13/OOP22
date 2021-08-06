import functions.*;
import functions.basic.*;
import functions.meta.Power;
import functions.threads.*;
import gui.MainWindow;

import java.io.*;


public class Main {
    public static void secondLabTest() throws InappropriateFunctionPointException {
        double values[] = {0, 1, 4, 9, 16, 25};
        TabulatedFunction function = new ArrayTabulatedFunction(0, 5, values);
        TabulatedFunction function2 = new ArrayTabulatedFunction(0, 5, 5);
        for (int i = 0; i < 5; i++) {
            function2.setPointY(Math.sin(i), i);
        }
        FunctionPoint fp = new FunctionPoint(3, 4);
        System.out.println(function2);
        function2.deletePoint(3);
        //System.out.println(function);
        System.out.println(function2);
        function2.addPoint(fp);
        System.out.println(function2);
        System.out.println(function.getFunctionValue(0));
        System.out.println(function.getFunctionValue(1.5));
        System.out.println(function2.getFunctionValue(5));

    }

    public static void thirdLabTest() {
        double values[] = {0, 1, 4, 9, 16, 25};
        TabulatedFunction function = new ArrayTabulatedFunction(0, 5, values);
        System.out.println(function);
        try {
            function.addPoint(new FunctionPoint(0.5, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(function);
        function.deletePoint(2);
        System.out.println(function);
        function = new LinkedListTabulatedFunction(0, 5, 5);
        try {
            function.addPoint(new FunctionPoint(0.7, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(function);
       /* try {
            function.deletePoint(0);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static void fourthLabTest() {
        //синус косинус
        Function sin = new Sin();
        Function cos = new Cos();
        System.out.println("These are Sin and Cos as Functions: ");
        for (double x = 0; x < 2 * Math.PI; x += 0.1) {
            System.out.print("Sin(" + x + ") = " + sin.getFunctionValue(x) + " ");
            System.out.println("Cos(" + x + ") = " + cos.getFunctionValue(x));
        }
//синус косинус табулированные
        Function tabSin = TabulatedFunctions.tabulate(sin, 0, 2 * Math.PI, 10);
        Function tabCos = TabulatedFunctions.tabulate(cos, 0, 2 * Math.PI, 10);
        System.out.println(" ");
        System.out.println("These are Sin and Cos as TabulatedFunctions: ");
        System.out.println(" ");
        for (double x = 0; x < 2 * Math.PI; x += 0.1) {
            System.out.print("Sin(" + x + ") = " + tabSin.getFunctionValue(x) + " ");
            System.out.println("Cos(" + x + ") = " + tabCos.getFunctionValue(x));
        }
        System.out.println(" ");
        System.out.println(" ");
        //табулированная экпонента
        TabulatedFunction tabExp = TabulatedFunctions.tabulate(new Exp(), 0, 2, 3);
        System.out.println("Tabulated EXP from 0 to 2:");
        System.out.println(tabExp);
        TabulatedFunction tabExp1;
        //запись в файл экспоненты а также чтение ее обратно из файла
        try {
            TabulatedFunctions.writeTabulatedFunction(tabExp, new FileWriter("exp"));
            tabExp1 = TabulatedFunctions.readTabulatedFunction(new FileReader("exp"));
            System.out.println("New tabulated EXP from file from old EXP");
            System.out.println(tabExp1);
        } catch (IOException e) {
            e.printStackTrace();
        }
//табулированный логарифм, основание пять
        TabulatedFunction tabLog = TabulatedFunctions.tabulate(new Log(5), 0, 10, 11);
        TabulatedFunction tabLog1 = null;
        System.out.println("Tabulated Log, base 5");
        System.out.println(tabLog);
        //запись в файл логарифма а также чтение его обратно из файла
        try {
            TabulatedFunctions.writeTabulatedFunction(tabLog, new FileWriter("log"));
            tabLog1 = TabulatedFunctions.readTabulatedFunction(new FileReader("log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("New tabulated Log from file from old Log");
        System.out.println(tabLog1);
        //проверка композиции с двумя экспонентами (не лучший пример)))
        Function f = Functions.composition(new Exp(), new Exp());
        TabulatedFunction tf = TabulatedFunctions.tabulate(f, 0, 10, 11);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("newfile"));
            out.writeObject(tf);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("newfile"));
            TabulatedFunction tf_from_newfile = (TabulatedFunction) in.readObject();
            System.out.println("Tabulated composition function (exp,exp) from file:");
            TabulatedFunctions.writeTabulatedFunction(tf_from_newfile, new OutputStreamWriter(System.out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void fifthLabTest() {


        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(1, 5, 5);
        TabulatedFunction listFunction = new LinkedListTabulatedFunction(1, 5, 5);
        TabulatedFunction anotherFunction = new ArrayTabulatedFunction(1, 2, 2);
        System.out.println("ToString:");
        System.out.println(arrayFunction);
        System.out.println(listFunction);
        System.out.println("Equals:");
        System.out.println(arrayFunction.equals(listFunction));
        System.out.println(arrayFunction.equals(anotherFunction));

        System.out.println("HashCode:");
        System.out.println(arrayFunction.hashCode());
        System.out.println(listFunction.hashCode());
        System.out.println(anotherFunction.hashCode());

        try {
            TabulatedFunction arrayClone = (TabulatedFunction) arrayFunction.clone();
            System.out.println("Clone of array:");
            System.out.println(arrayClone);
            arrayFunction.setPointY(1, 2);
            System.out.println(arrayClone);
            System.out.println("List clone:");
            TabulatedFunction listClone = (TabulatedFunction) listFunction.clone();
            System.out.println(listClone);
            listFunction.setPointY(1, 2);
            System.out.println(listClone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    static void sixthLabTest() {
        MainWindow window = new MainWindow();
    }
    static void sevenLabTest(){
        Exp e = new Exp();
        Double  f = Functions.integral(e, 0, 1 ,0.1);
        System.out.println(f);
    }



    static void eighthLabTest() {
        System.out.println("Iterable implementation test");
        TabulatedFunction tf = new ArrayTabulatedFunction(0, 2, 3);
        for (FunctionPoint p : tf) {
            System.out.println(p);
        }
        System.out.println();
        tf = new LinkedListTabulatedFunction(0, 2, 3);
        for (FunctionPoint p : tf) {
            System.out.println(p);
        }

        System.out.println("\nFactories test");
        Function cos = new Cos();
        tf = TabulatedFunctions.tabulate(new Cos(), 0, Math.PI, 11);
        System.out.println(tf.getClass() + "\n" + tf);
        TabulatedFunctions.setFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(new Cos(), 0, Math.PI, 11);
        System.out.println(tf.getClass() + "\n" + tf);

        System.out.println("\nReflection test");
        try {
            tf = TabulatedFunctions.createTabulatedFunction(1.0, 5.0, 5, ArrayTabulatedFunction.class);
            System.out.println(tf.getClass() + "\n" + tf);

            tf = TabulatedFunctions.createTabulatedFunction(0.0, 1.0, new double[]{0, 1}, LinkedListTabulatedFunction.class);
            System.out.println(tf.getClass() + "\n" + tf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void nonThread() {
        Task task = new Task();
        task.tasks = 100;
        for (int i = 0; i < task.tasks; i++) {
            task.function = new Log(Math.random() * 10);
            task.leftX = Math.random() * 100;
            task.rightX = Math.random() * 100 + 100;
            task.step = Math.random();
            System.out.print("Source " + task.leftX + " " + task.rightX + " " + task.step);
            System.out.println("Result " + task.leftX + " " + task.rightX + " " + task.step + " "
                    + Functions.integral(task.function, task.leftX, task.rightX, task.step));
            System.out.println();
        }
    }

    public static void simpleThreads() {
        Task task = new Task();
        task.tasks = 100;
        SimpleGenerator generator = new SimpleGenerator(task);
        Thread generatorThread = new Thread(generator);
        SimpleIntegrator integrator = new SimpleIntegrator(task);
        Thread integratorThread = new Thread(integrator);
        generatorThread.start();
        integratorThread.start();
        generator.run();
        integrator.run();
    }

    public static void complicatedThreads() {
        Semaphore semaphore = new Semaphore();
        Task task = new Task();
        task.tasks = 100;
        Integrator integrator = new Integrator(task, semaphore);
        Generator generator = new Generator(task, semaphore);
        generator.start();
        integrator.start();
    }


    public static void main(String[] args) throws InterruptedException, InappropriateFunctionPointException {

        //secondLabTest();
        //thirdLabTest();
        //fourthLabTest();
        //fifthLabTest();
        sixthLabTest();
        //sevenLabTest();
        //eighthLabTest();


    }
}
