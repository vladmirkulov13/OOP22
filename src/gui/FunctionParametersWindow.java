package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Flow;

public class FunctionParametersWindow extends JDialog {
    public static final int OK = 111;
    public static final int CANCEL = 222;

    private static final int TEXT_FIELD_WIDTH = 250;
    private static final int SPINNER_WIDTH = 100;

    private int windowStatus;
    private JFrame mainFrame = new JFrame();
    private JLabel leftBorderLabel = new JLabel("Left domain border");
    private JTextField leftBorderField = new JTextField();
    private JLabel rightBorderLabel = new JLabel("Right domain border");
    private JTextField rightBorderField = new JTextField();
    private JLabel pointsCountLabel = new JLabel("Points count");
    private JSpinner pointsCountSpinner = new JSpinner();
    private JButton cancelButton = new JButton("Cancel");
    private JButton okButton = new JButton("OK");

    public FunctionParametersWindow() {

        //window properties
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        //spinner properties
        SpinnerNumberModel pointsCountModel = new SpinnerNumberModel();
        pointsCountModel.setMinimum(2);
        pointsCountModel.setStepSize(1);
        pointsCountModel.setValue(2);
        pointsCountSpinner.setModel(pointsCountModel);

        //components layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //components adding
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy = 0;
        constraints.gridx = 0;
        mainPanel.add(leftBorderLabel, constraints);
        constraints.gridx = 1;

        constraints.ipadx = TEXT_FIELD_WIDTH;
        mainPanel.add(leftBorderField, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.ipadx = 0;
        mainPanel.add(rightBorderLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = TEXT_FIELD_WIDTH;
        mainPanel.add(rightBorderField, constraints);

        constraints.ipadx = 0;
        constraints.gridy = 2;
        constraints.gridx = 0;
        mainPanel.add(pointsCountLabel, constraints);

        constraints.gridx = 1;
        constraints.ipadx = SPINNER_WIDTH;
        mainPanel.add(pointsCountSpinner, constraints);

        constraints.ipadx = 0;
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        mainPanel.add(cancelButton, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        mainPanel.add(okButton, constraints);

        this.add(mainPanel);
        this.pack();

        //action listeners
        okButton.addActionListener(actionEvent -> {
            try {
                double leftDomainBorder = Double.parseDouble(leftBorderField.getText());
                double rightDomainBorder = Double.parseDouble(rightBorderField.getText());
                windowStatus = OK;
                this.setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inappropriate number format!");
            }
        });

        cancelButton.addActionListener(actionEvent -> {
            windowStatus = CANCEL;
            this.setVisible(false);
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowStatus = CANCEL;
            }
        });
    }

    public int showDialog() {
        setVisible(true);
        return windowStatus;
    }

    public double getLeftDomainBorder() {
        return Double.parseDouble(leftBorderField.getText());
    }

    public double getRightDomainBorder() {
        return Double.parseDouble(rightBorderField.getText());
    }

    public int getPointsCount() {
        return (int) pointsCountSpinner.getValue();

    }
}