package functions;

import functions.Function;
import functions.meta.*;


public abstract class Functions {
    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power) {
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    }

    public static double integral(Function function, double leftX, double rightX, double step) throws IllegalArgumentException {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()
                || leftX > rightX)
            throw new IllegalArgumentException();
        double integral = 0;
        double x = leftX;
        while (x < rightX) {
            if (x + step < rightX) {
                integral += (function.getFunctionValue(x) + function.getFunctionValue(x + step)) / 2 * step;
            } else {
                integral += (function.getFunctionValue(x) + function.getFunctionValue(rightX)) / 2 * (rightX - x);
            }
            x += step;
        }
        return integral;
    }
}
