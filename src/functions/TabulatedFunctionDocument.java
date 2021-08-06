package functions;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

public class TabulatedFunctionDocument implements TabulatedFunction {
    private TabulatedFunction function;
    private String fileName;
    private boolean modified = true;
    private boolean fileNameAssigned = false;

    public void newFunction(double leftX, double rightX, int pointsCount) {
        function = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        fileNameAssigned=false;
        modified = true;
    }

    public void saveFunction() throws IOException {
        if(fileNameAssigned) {
            TabulatedFunctions.outputTabulatedFunction(function, new FileOutputStream(fileName));
            modified = true;
        }
    }

    public void saveFunctionAs(String newFileName) throws IOException {
        fileName = newFileName;
        fileNameAssigned = true;
        modified = false;
        saveFunction();
    }


    public void loadFunction(String newFileName) throws IOException {
        fileName = newFileName;
        function = TabulatedFunctions.inputTabulatedFunction(new FileInputStream(fileName));
        modified = false;
        fileNameAssigned=true;
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        this.function = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        modified = true;
        fileNameAssigned=false;
    }

    public TabulatedFunction getFunction() {
        return function;
    }

    public void setFileName(String fileName){
        this.fileName=fileName;
        fileNameAssigned=true;
    }

    public void setFunction(TabulatedFunction function){
        this.function=function;
        modified=true;
        fileNameAssigned=false;
    }

    @Override
    public int getPointsCount() {
        return function.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        return function.getPoint(index);
    }

    @Override
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        return function.getPointX(index);
    }

    @Override
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        return function.getPointY(index);
    }

    @Override
    public void setPointX(double pointX, int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        function.setPointX(pointX, index);
        modified = true;
    }

    @Override
    public void setPointY(double pointY, int index) throws FunctionPointIndexOutOfBoundsException {
        function.setPointY(pointY, index);
        modified = true;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        function.addPoint(point);
        modified = true;
    }

    public void deletePoint(int index) {
        function.deletePoint(index);
        modified = true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
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
        return function.getFunctionValue(x);
    }

    public boolean isFileNameAssigned() {
        return fileNameAssigned;
    }

    public boolean isModified() {
        return modified;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return function.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return function.contains(o);
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return function.iterator();
    }

    @Override
    public Object[] toArray() {
        return function.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return function.toArray(ts);
    }

    @Override
    public boolean add(FunctionPoint point) {
        return function.add(point);
    }

    @Override
    public boolean remove(Object o) {
        return function.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return function.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends FunctionPoint> collection) {
        return function.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return function.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return function.retainAll(collection);
    }

    @Override
    public void clear() {
        function.clear();
    }
}
