package functions.meta;

import functions.Function;
//сумма двух значений функций
public class Sum implements Function {
    private Function function1;
    private Function function2;

    public Sum(Function function1, Function function2){
        this.function1=function1;
        this.function2=function2;
    }
    @Override
    public double getLeftDomainBorder() {
        return Double.max(function1.getLeftDomainBorder(), function2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Double.min(function1.getRightDomainBorder(), function2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return function1.getFunctionValue(x) + function2.getFunctionValue(x);
    }
}
