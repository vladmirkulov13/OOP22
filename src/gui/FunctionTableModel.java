package gui;

import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import functions.TabulatedFunctionDocument;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class FunctionTableModel implements TableModel {
    private TabulatedFunction function;
    private Component parent;

    private Set<TableModelListener> listenerSet = new HashSet<TableModelListener>();

    public FunctionTableModel(TabulatedFunctionDocument function, Component parent) {
        super();
        this.function = function.getFunction();
        this.parent = parent;
    }

    public int getRowCount() {
        return function.getPointsCount();
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "X";
            case 1:
                return "Y";
        }
        return "";
    }

    public Class getColumnClass(int index) {
        return Double.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return function.getPointX(rowIndex);
        } else {
            return function.getPointY(rowIndex);
        }
    }

    public void setValueAt(Object object, int rowIndex, int columnIndex) {
        try {
            if (columnIndex == 0) {
                function.setPointX((Double) object, rowIndex);
            } else {
                function.setPointY((Double) object, rowIndex);
            }

        } catch (InappropriateFunctionPointException e) {
            JOptionPane.showMessageDialog(parent, "Inappropriate value!");
        }
    }

    public void setFunction(TabulatedFunction function){
        this.function=function;
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
        listenerSet.add(tableModelListener);
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {
        listenerSet.remove(tableModelListener);
    }
}
