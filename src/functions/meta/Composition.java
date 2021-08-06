package functions.meta;

import functions.Function;
//слияние двух функций
public class Composition implements Function {
    private Function function;
    private Function argument;

    public Composition(Function function, Function argument) {
        this.function = function;
        this.argument = argument;
    }

    @Override
    public double getLeftDomainBorder() {
        return argument.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return argument.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(argument.getFunctionValue(x));
    }
}
