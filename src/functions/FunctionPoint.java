package functions;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable {
    private double coordinateX;
    private double coordinateY;

    public FunctionPoint(double x, double y) {
        coordinateX = x;
        coordinateY = y;
    }

    public FunctionPoint(FunctionPoint point) {
        this.coordinateX = point.coordinateX;
        this.coordinateY = point.coordinateY;
    }

    public FunctionPoint() {
        this.coordinateX = 0;
        this.coordinateY = 0;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Override
    public String toString() {
        return "(" + coordinateX +
                ", " + coordinateY +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionPoint)) return false;
        FunctionPoint that = (FunctionPoint) o;
        return Double.compare(that.coordinateX, coordinateX) == 0 &&
                Double.compare(that.coordinateY, coordinateY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateX, coordinateY);
    }

    public Object clone() throws CloneNotSupportedException {
        return new FunctionPoint(coordinateX, coordinateY);
    }
}
