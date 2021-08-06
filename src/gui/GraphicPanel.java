/*
package gui;

import functions.Function;
import functions.TabulatedFunction;

import javax.swing.*;
import java.awt.*;

public class GraphicPanel extends JPanel {
    private int width;
    private int height;

    private Function function;
    private double xScale = 30;
    private double yScale = 30;



    private boolean drawTabulatedFunctionPoints = true;
    private int crossSize = 10;

    private Color gridColor = Color.lightGray;
    private Color axisColor = Color.BLACK;
    private Color plotColor = Color.RED;

    private double gridDistance = 1;


    public void paint(Graphics g) {
        super.paint(g);
        width = getWidth();
        height = getHeight();

        drawGrid(g);
        drawAxis(g);
        plot(g);
    }

    public void drawGrid(Graphics g) {
        g.setColor(gridColor);
        for (int x = width / 2; x < width; x += gridDistance * xScale) {  // цикл от центра до правого края
            g.drawLine(x, 0, x, height);    // вертикальная линия
        }

        for (int x = width / 2; x > 0; x -= gridDistance * xScale) {  // цикл от центра до леваого края
            g.drawLine(x, 0, x, height);   // вертикальная линия
        }

        for (int y = height / 2; y < height; y += gridDistance * yScale) {  // цикл от центра до верхнего края
            g.drawLine(0, y, width, y);    // горизонтальная линия
        }

        for (int y = height / 2; y > 0; y -= gridDistance * yScale) {  // цикл от центра до леваого края
            g.drawLine(0, y, width, y);    // горизонтальная линия
        }
    }

    private void drawAxis(Graphics g) {
        g.setColor(axisColor);
        g.drawLine(width / 2, 0, width / 2, height);
        g.drawLine(0, height / 2, width, height / 2);
    }

    private void plot(Graphics g) {
        if (function != null) {
            g.setColor(plotColor);
            for (int x = 0; x < width; x++) {
                double rX = (x - (double) width / 2) / xScale;
                if (rX > function.getLeftDomainBorder() && rX < function.getRightDomainBorder()) {
                    int y = height / 2 - (int) (function.getFunctionValue(rX) * yScale);
                    g.drawOval(x, y, 2, 2);
                }
            }
            if (function instanceof TabulatedFunction && drawTabulatedFunctionPoints) {
                TabulatedFunction tabulatedFunction = (TabulatedFunction) function;
                for (int i = 0; i < tabulatedFunction.getPointsCount(); i++) {
                    int pointX = width / 2 + (int) (tabulatedFunction.getPointX(i) * xScale);
                    int pointY = height / 2 - (int) (tabulatedFunction.getPointY(i) * yScale);
                    g.drawLine(pointX - crossSize, pointY, pointX + crossSize, pointY);
                    g.drawLine(pointX, pointY - crossSize, pointX, pointY + crossSize);
                }
            }
        }
    }


    public void setGridDistance(double gridXDistance) {
        this.gridDistance = gridXDistance;
    }

    public void setXScale(double scale) {
        this.xScale = scale;
    }

    public void setYScale(double scale) {
        this.yScale = scale;
    }


    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public void setAxisColor(Color axisColor) {
        this.axisColor = axisColor;
    }

    public void setPlotColor(Color plotColor) {
        this.plotColor = plotColor;
    }

    public void setDrawTabulatedFunctionPoints(boolean drawTabulatedFunctionPoints) {
        this.drawTabulatedFunctionPoints = drawTabulatedFunctionPoints;
    }

    public void setCrossSize(int crossSize) {
        this.crossSize = crossSize;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
*/
