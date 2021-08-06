package functions.meta;

import functions.Function;
//возв в степень
public class Power implements Function {
    public double power;
    public Function function;

    public Power(Function function, double power){
        this.function = function;
        this.power=power;
    }
    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(function.getFunctionValue(x),power);
    }
}
