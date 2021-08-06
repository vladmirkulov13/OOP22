package functions;

import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {
    private static class FunctionNode {
        public FunctionPoint point;
        public FunctionNode next;
        public FunctionNode prev;

        FunctionNode() {
            this.point = null;
        }

        public FunctionNode(FunctionPoint point) {
            this.point = point;
        }

    }


    private FunctionNode head;
    private int pointsCount;

    public LinkedListTabulatedFunction() {
        head = new FunctionNode();
        head.next = head;
        head.prev = head;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX >= rightX || pointsCount < 2) throw new IllegalArgumentException();

        head = new FunctionNode();
        head.prev = head;
        head.next = head;

        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            addNodeToTail(new FunctionPoint(leftX + interval * i, 0));
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (leftX >= rightX || values.length < 2) throw new IllegalArgumentException();

        head = new FunctionNode();
        head.prev = head;
        head.next = head;

        double interval = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            addNodeToTail(new FunctionPoint(leftX + interval * i, values[i]));
        }
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException {
        if (points.length < 2) throw new IllegalArgumentException();
        head = new FunctionNode();
        head.prev = head;
        head.next = head;
        addNodeToTail(points[0]);
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].getCoordinateX() >= points[i].getCoordinateX()) {
                throw new IllegalArgumentException();
            } else {
                addNodeToTail(points[i]);
            }
        }
    }

    public FunctionNode getNodeByIndex(int index) {
        FunctionNode node = head.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public double getLeftDomainBorder() {
        return head.next.point.getCoordinateX();
    }

    public double getRightDomainBorder() {
        return head.prev.point.getCoordinateX();
    }

    public double getFunctionValue(double x) {
        FunctionNode node = head.next;
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;
        while (node != head) {
            if (x == node.point.getCoordinateX()) return node.point.getCoordinateY();
            else if (x > node.point.getCoordinateX() && node.next != head && x < node.next.point.getCoordinateX()) {
                return node.point.getCoordinateY() + (node.next.point.getCoordinateY() - node.point.getCoordinateY())
                        / (node.next.point.getCoordinateX() - node.point.getCoordinateX())
                        * (x - node.point.getCoordinateX());
            }
            node = node.next;
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= pointsCount) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= pointsCount) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).point.getCoordinateX();
    }

    public void setPointX(double pointX, int index) throws FunctionPointIndexOutOfBoundsException,
            InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) throw new FunctionPointIndexOutOfBoundsException();
        FunctionNode node = getNodeByIndex(index);
        if (node.prev == head) {
            if (pointX >= node.next.point.getCoordinateX()) {
                throw new InappropriateFunctionPointException();
            }
        } else if (node.next == head) {
            if (pointX <= node.prev.point.getCoordinateX()) {
                throw new InappropriateFunctionPointException();
            }
        } else {
            if (pointX >= node.next.point.getCoordinateX() || pointX <= node.prev.point.getCoordinateX()) {
                throw new InappropriateFunctionPointException();
            }
        }
        node.point.setCoordinateX(pointX);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= pointsCount) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).point.getCoordinateY();
    }

    public void setPointY(double pointY, int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= pointsCount) throw new FunctionPointIndexOutOfBoundsException();
        getNodeByIndex(index).point.setCoordinateY(pointY);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode newNode = new FunctionNode(point);
        if (point.getCoordinateX() < getLeftDomainBorder()) {
            head.next.prev = newNode;
            newNode.next = head.next;
            head.next = newNode;
            newNode.prev = head;
        } else if (point.getCoordinateX() > getRightDomainBorder()) {
            head.prev.next = newNode;
            newNode.prev = head.prev;
            head.prev = newNode;
            newNode.next = head;
        } else {
            FunctionNode node = head.next;
            boolean added = false;
            while (node != head && !added) {
                if (node.point.getCoordinateX() == point.getCoordinateX()) {
                    throw new InappropriateFunctionPointException();
                } else if (point.getCoordinateX() > node.point.getCoordinateX() &&
                        point.getCoordinateX() < node.next.point.getCoordinateX()) {
                    node.next.prev = newNode;
                    newNode.next = node.next;
                    node.next = newNode;
                    newNode.prev = node;
                    added = true;
                } else {
                    node = node.next;
                }

            }
        }
    }

    public void setPoint(FunctionPoint point, int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount) throw new FunctionPointIndexOutOfBoundsException();
        if (index == 0) {
            if (point.getCoordinateX() < head.next.next.point.getCoordinateX()) {
                head.next.point = point;
            } else throw new InappropriateFunctionPointException();
        } else if (index == pointsCount - 1) {
            if (point.getCoordinateX() > head.prev.prev.point.getCoordinateX()) {
                head.next.point = point;
            } else throw new InappropriateFunctionPointException();
        } else {
            FunctionNode node = addNodeByIndex(index);
            if (point.getCoordinateX() < node.next.point.getCoordinateX() && point.getCoordinateX() > node.prev.point.getCoordinateX()) {
                node.point = point;
            } else throw new InappropriateFunctionPointException();
        }
    }

    public FunctionNode addNodeToTail(FunctionPoint point) {
        FunctionNode newNode = new FunctionNode(point);

        head.prev.next = newNode;
        newNode.prev = head.prev;
        head.prev = newNode;
        newNode.next = head;
        pointsCount++;
        return newNode;
    }

    public FunctionNode addNodeByIndex(int index) {
        FunctionNode node = head;
        FunctionNode newNode = new FunctionNode();
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.next.prev = newNode;
        newNode.next = newNode;
        node.next = newNode;
        newNode.prev = node;
        pointsCount++;
        return newNode;
    }

    public FunctionNode deleteNodeByIndex(int index) {
        FunctionNode node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        FunctionNode deletedNode = node.next;
        node.next = deletedNode.next;
        node.next.prev = node;

        return deletedNode;
    }

    public void deletePoint(int index) {
        if (index < 0 || index >= getPointsCount()) throw new FunctionPointIndexOutOfBoundsException();
        if (pointsCount <= 2) throw new IllegalStateException();
        deleteNodeByIndex(index);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{" + head.next.point);
        FunctionNode node = head.next.next;
        while (node != head) {
            str.append(", ");
            str.append(node.point);
            node = node.next;
        }
        str.append("}");
        return str.toString();
    }

    @Override
    public int size() {
        return pointsCount;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        FunctionNode n = this.head.next;
        while (n != head) {
            if (n.point.equals(o))
                return true;
            n = n.next;
        }
        return false;
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            FunctionNode currentNode = head.next;

            @Override
            public boolean hasNext() {
                return currentNode != head;
            }

            @Override
            public FunctionPoint next() {
                if (hasNext()) {
                    currentNode = currentNode.next;
                    return currentNode.prev.point;
                } else throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[pointsCount];
        FunctionNode node = head.next;
        for (int i = 0; i < pointsCount; i++) {
            array[i] = node.point;
            node = node.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return (T[]) toArray();
    }

    @Override
    public boolean add(FunctionPoint functionPoint) {
        try {
            addNodeToTail(functionPoint);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean remove(Object o) {
        FunctionNode n = head.next;
        while (n != head) {
            if (n.point.equals(o)) {
                n.prev.next = n;
                n.next.prev = n;
                return true;
            }
            n = n.next;
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
        for (Object o : collection) {
            if (!add((FunctionPoint) o)) return false;
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
        FunctionNode n = this.head.next;
        while (n != head) {
            if (!collection.contains(n.point)) {
                if (!remove(n.point)) {
                    return false;
                }
            }
            n = n.next;
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
            if (pointsCount != that.getPointsCount()) {
                return false;
            } else {
                FunctionNode node = head.next;
                for (int i = 0; i < pointsCount; i++) {
                    if (!node.point.equals(that.getPoint(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = pointsCount*37;
        FunctionNode node = head.next;
        while (node != head) {
            hash ^= node.hashCode();
            node = node.next;
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction();
        function.pointsCount = pointsCount;
        FunctionNode node = this.head.next;
        while (node != head) {
            function.addNodeToTail((FunctionPoint) node.point.clone());
            node = node.next;
        }
        return function;
    }

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }
    }
}
