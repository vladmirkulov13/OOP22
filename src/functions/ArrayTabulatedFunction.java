package functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.System.arraycopy;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable {

    private FunctionPoint[] points;
    private int pointsCount;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX >= rightX || pointsCount < 2) throw new IllegalArgumentException();
        this.pointsCount=pointsCount;
        points = new FunctionPoint[pointsCount];
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + interval * i, 0);
        }
    }
    {

    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        this(leftX,rightX,values.length);
        if (leftX >= rightX || values.length < 2) throw new IllegalArgumentException();
        for (int i = 0; i < values.length; i++) {
            points[i].setCoordinateY(values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] points) {
        this.pointsCount=points.length;
        if (points.length < 2) throw new IllegalArgumentException();
        for (int i = 1; i < points.length; i++) {
            if (points[i].getCoordinateX() <= points[i - 1].getCoordinateX()) {
                throw new IllegalArgumentException();
            }
        }
        FunctionPoint[] points1=new FunctionPoint[pointsCount*2];
        this.points = points1;
        arraycopy(points,0,this.points,0,pointsCount);
    }


    public double getLeftDomainBorder() {
        return points[0].getCoordinateX();
    }

    public double getRightDomainBorder() {
        return points[pointsCount-1].getCoordinateX();
    }

    public double getFunctionValue(double x) {
        for (int i = 0; i < pointsCount - 1; i++) {
            if (x == points[i].getCoordinateX()) {
                return points[i].getCoordinateY();
            } else if (x == points[i + 1].getCoordinateX()) {
                return points[i + 1].getCoordinateY();
            } else if (x > points[i].getCoordinateX() && x < points[i + 1].getCoordinateX()) {
                return points[i].getCoordinateY() + (points[i + 1].getCoordinateY() - points[i].getCoordinateY())
                        / (points[i + 1].getCoordinateX() - points[i].getCoordinateX())
                        * (x - points[i].getCoordinateX());
            }
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException();
        return points[index];
    }

    public void setPoint(FunctionPoint point, int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index > getPointsCount() - 1) throw new FunctionPointIndexOutOfBoundsException();

        if (index == 0) {
            if (point.getCoordinateX() < points[index + 1].getCoordinateX()) {
                points[index] = point;
            }
        } else if (index == pointsCount - 1) {
            if (point.getCoordinateX() > points[index - 1].getCoordinateX()) {
                points[index] = point;
            }
        } else {
            if (point.getCoordinateX() > points[index + 1].getCoordinateX() && point.getCoordinateX() > points[index - 1].getCoordinateX()) {
                points[index] = point;
            } else throw new InappropriateFunctionPointException();
        }
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= getPointsCount()) throw new FunctionPointIndexOutOfBoundsException();
        return points[index].getCoordinateX();
    }

    public void setPointX(double pointX, int index) throws FunctionPointIndexOutOfBoundsException,
            InappropriateFunctionPointException {
        if (index < 0 || index >= getPointsCount()) throw new FunctionPointIndexOutOfBoundsException();
        if (index == 0) {
            if (pointX >= points[index + 1].getCoordinateX()) {
                throw new InappropriateFunctionPointException();
            }
        } else if (index == pointsCount - 1) {
            if (pointX <= points[index - 1].getCoordinateX()) {
                throw new InappropriateFunctionPointException();
            }
        } else {
            if (pointX >= points[index + 1].getCoordinateX() || pointX <= points[index - 1].getCoordinateX()) {
                throw new InappropriateFunctionPointException();
            }
        }

        points[index].setCoordinateX(pointX);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= getPointsCount()) throw new FunctionPointIndexOutOfBoundsException();
        return points[index].getCoordinateY();
    }

    public void setPointY(double pointY, int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= getPointsCount()) throw new FunctionPointIndexOutOfBoundsException();
        points[index].setCoordinateY(pointY);
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        if (index < 0 || index >= getPointsCount()) throw new FunctionPointIndexOutOfBoundsException();
        if (pointsCount <= 2) throw new IllegalStateException();
        FunctionPoint[] points1 = new FunctionPoint[points.length -1 ];
        if(index==0)
        System.arraycopy(points,1,points1,0,points.length-1);
        else {
            System.arraycopy(points, 0, points1, 0, index );
            System.arraycopy(points,index+1,points1,index,pointsCount - 1 - index);
        }
        points = points1;
        pointsCount--;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if(pointsCount==points.length){
            FunctionPoint[] newPoints=new FunctionPoint[pointsCount*2];
            arraycopy(points,0,newPoints,0,pointsCount);
            points=newPoints;
        }
        if (point.getCoordinateX() < getLeftDomainBorder()) {
            arraycopy(points,0,points,1,pointsCount);
            points[0]=point;
        } else if (point.getCoordinateX() > getRightDomainBorder()) {
            points[pointsCount]=point;
        } else {
            for (int i = 0; i < pointsCount - 1; i++) {
                if (point.getCoordinateX() > points[i].getCoordinateX()
                        && point.getCoordinateX() < points[i + 1].getCoordinateX()) {

                    arraycopy(points,i+1,points,i+2,pointsCount-i-1);
                    points[i+1]=point;
                    break;
                } else if (points[i].getCoordinateX() == point.getCoordinateX()
                        || points[i + 1].getCoordinateX() == point.getCoordinateX())
                    throw new InappropriateFunctionPointException();
            }
        }
        pointsCount++;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{" + points[0]);
        for (int i = 1; i < pointsCount; i++) str.append("," + points[i]);
        str.append("}");
        return str.toString();
    }

    @Override
    public int size() {
        return points.length;
    }

    @Override
    public boolean isEmpty() {
        return points.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (FunctionPoint i : points) {
            if (i.equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<>() {
            int currentIndex = -1;

            @Override
            public boolean hasNext() {
                return currentIndex < size() - 1;
            }

            @Override
            public FunctionPoint next() {
                if (hasNext()) {
                    return points[++currentIndex];
                } else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

    @Override
    public Object[] toArray() {
        return points;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return (T[]) points;
    }

    @Override
    public boolean add(FunctionPoint functionPoint) {
        try {
            addPoint(functionPoint);
            return true;
        } catch (InappropriateFunctionPointException e) {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < points.length; i++) {
            if (points[i].equals(o)) {
                deletePoint(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends FunctionPoint> collection) {
        for (FunctionPoint p : collection) {
            if (!add(p)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (size() - collection.size() < 2) return false;
        for (Object o : collection) {
            if (!remove(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        for (FunctionPoint p : points) {
            if (!collection.contains(p)) {
                if (!remove(p)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TabulatedFunction)) return false;
        else {
            TabulatedFunction that = (TabulatedFunction) o;
            if (that.getPointsCount() != points.length) {
                return false;
            } else {
                for (int i = 0; i < points.length; i++) {
                    if (!this.points[i].equals(that.getPoint(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {

        return Arrays.hashCode(points);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint[] newPoints = new FunctionPoint[getPointsCount()];
        for (int i = 0; i < getPointsCount(); i++) {
            newPoints[i] = (FunctionPoint) points[i].clone();
        }
        return new ArrayTabulatedFunction(newPoints);
    }

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new ArrayTabulatedFunction(leftX,rightX,pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new ArrayTabulatedFunction(leftX,rightX,values);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new ArrayTabulatedFunction(points);
        }
    }

}
