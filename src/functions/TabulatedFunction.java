package functions;

import java.util.Collection;

public interface TabulatedFunction extends Function, Cloneable, Collection<FunctionPoint> {

    int getPointsCount();

    FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException;

    double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;

    double getPointY(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPointX(double pointX, int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    void setPointY(double pointY, int index) throws FunctionPointIndexOutOfBoundsException;

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    void deletePoint(int pointIndex) throws FunctionPointIndexOutOfBoundsException, IllegalStateException;

    Object clone() throws CloneNotSupportedException;
}
