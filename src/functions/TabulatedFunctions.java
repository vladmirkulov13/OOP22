package functions;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public abstract class TabulatedFunctions {

    private static TabulatedFunctionFactory factory =
            new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    //получение табулированной по обычной функции на заданном отрезке
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || leftX > function.getRightDomainBorder() ||
                rightX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder() ||
                leftX >= rightX || pointsCount < 2) throw new IllegalArgumentException();
        double interval = (rightX - leftX) / (pointsCount - 1);
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + i * interval, function.getFunctionValue(leftX + i * interval));
        }
//return new ArrayTabulatedFunction(points);
        return factory.createTabulatedFunction(points);
    }

    //кол-ов точек и их поля вывести в поток
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream stream = new DataOutputStream(out);
        stream.writeInt(function.getPointsCount());
        for (int i = 0; i < function.getPointsCount(); i++) {
            FunctionPoint point = function.getPoint(i);
            stream.writeDouble(point.getCoordinateX());
            stream.writeDouble(point.getCoordinateY());
        }
        stream.close();
    }

    //считывание из потока: кол-во точек, значения каждого поля каждой точки
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream inputStream = new DataInputStream(in);
        int pointsCount = inputStream.readInt();
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(inputStream.readDouble(), inputStream.readDouble());
        }
        //return new ArrayTabulatedFunction(points);
        return factory.createTabulatedFunction(points);
    }

    //выводить кол-во точек и поля в поток out
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        PrintWriter output = new PrintWriter(out);

        output.println(function.getPointsCount());
        for (int i = 0; i < function.getPointsCount(); i++) {
            FunctionPoint point = function.getPoint(i);
            output.println(point.getCoordinateX() + " " + point.getCoordinateY());
        }
        output.close();
    }

    //должен считывать из указанного потока данные о табулированной функции,
// создавать и настраивать её объект и возвращать его из метода.
//При написании методов в первых трёх случаях необходимо воспользоваться потоками-обёртками,
// облегчающими ввод и вывод данных в требующейся форме, а в четвёртом случае – классом StreamTokenizer.
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        BufferedReader reader = new BufferedReader(in);
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(" ");
        }
        String tokens[] = builder.toString().split(" ");
        Vector<Double> doubleTokens = new Vector<>();
        for (String s : tokens) {
            switch (s) {
                case "NaN":
                    doubleTokens.add(Double.NaN);
                    break;
                case "+Infinity":
                    doubleTokens.add(Double.POSITIVE_INFINITY);
                    break;
                case "-Infinity":
                    doubleTokens.add(Double.NEGATIVE_INFINITY);
                    break;
                default:
                    try {
                        doubleTokens.add(Double.parseDouble(s));
                    } catch (NumberFormatException e) {
                        doubleTokens.add(Double.NaN);
                    }
                    break;
            }
        }
        int pointsCount = doubleTokens.get(0).intValue();
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 1; i < pointsCount * 2 + 1; i += 2) {
            points[i / 2] = new FunctionPoint();
            points[i / 2].setCoordinateX(doubleTokens.get(i));
            points[i / 2].setCoordinateY(doubleTokens.get(i + 1));
        }
        return factory.createTabulatedFunction(points);
    }

    public static void setFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount,
                                                            Class<?> c)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IllegalArgumentException {
        if (!TabulatedFunction.class.isAssignableFrom(c)) throw new IllegalArgumentException("Wrong class type!");
        return (TabulatedFunction) c.getConstructor(double.class, double.class, int.class).newInstance(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values,
                                                            Class<?> c)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!TabulatedFunction.class.isAssignableFrom(c)) throw new IllegalArgumentException("Wrong class type!");
        return (TabulatedFunction) c.getConstructor(double.class, double.class, double[].class).newInstance(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points, Class<?> c)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!TabulatedFunction.class.isAssignableFrom(c)) throw new IllegalArgumentException("Wrong class type!");
        return (TabulatedFunction) c.getConstructor(FunctionPoint[].class).newInstance(points);
    }


}
