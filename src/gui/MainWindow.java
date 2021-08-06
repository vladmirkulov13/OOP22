package gui;

import functions.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MainWindow extends JFrame {

    FunctionParametersWindow functionDialog = new FunctionParametersWindow();
    TabulatedFunctionDocument functionDocument;

    JFileChooser fileChooser = new JFileChooser();
    FunctionClassLoader functionLoader;
    FunctionTableModel tableModel;
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    JMenuItem newFileItem = new JMenuItem("New");
    JMenuItem openFileItem = new JMenuItem("Open");
    JMenuItem saveFileItem = new JMenuItem("Save");
    JMenuItem saveFileAsItem = new JMenuItem("Save as");
    JMenuItem exportItem = new JMenuItem("Export txt");
    JMenuItem exitItem = new JMenuItem("Exit");

    JMenu tabulateMenu = new JMenu("Tabulate");
    JMenuItem tabulateItem = new JMenuItem("Tabulate");


    JScrollPane scrollPane = new JScrollPane();
    JTable functionTable = new JTable();

    JTextField pointXField = new JTextField();
    JTextField pointYField = new JTextField();

    JLabel pointXLabel = new JLabel("Point X");
    JLabel pointYLabel = new JLabel("Point Y");

    JButton addPointButton = new JButton("Add");
    JButton deletePointButton = new JButton("Delete");


    public MainWindow() {
        functionDocument = new TabulatedFunctionDocument();
        functionDocument.newFunction(0, 10, 11);
        tableModel = new FunctionTableModel(functionDocument, this);
        functionTable.setModel(tableModel);

        scrollPane.add(functionTable);
        JPanel pointEditPanel = initBottomPanel();
        this.setLayout(new BorderLayout());
        initMenu();
        this.getContentPane().add(new JScrollPane(functionTable));
        this.add(pointEditPanel, BorderLayout.SOUTH);
        this.pack();


        this.setResizable(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                closeWindow();
            }
        });

        this.setVisible(true);
    }

    private void initMenu() {
        fileMenu.add(newFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.add(saveFileAsItem);
        fileMenu.add(openFileItem);
        fileMenu.add(exportItem);
        fileMenu.add(exitItem);


        tabulateMenu.add(tabulateItem);


        menuBar.add(fileMenu);
        menuBar.add(tabulateMenu);
        this.add(menuBar, BorderLayout.NORTH);

        newFileItem.addActionListener(ActionEvent -> {
            if (functionDialog.showDialog() == FunctionParametersWindow.OK) {
                functionDocument.newFunction(
                        functionDialog.getLeftDomainBorder(),
                        functionDialog.getRightDomainBorder(),
                        functionDialog.getPointsCount());
                tableModel.setFunction(functionDocument);
                functionTable.revalidate();
                functionTable.repaint();
            }
        });

            int pointIndex = functionTable.getRowCount() - 2;
            try {
                functionDocument.deletePoint(pointIndex);
                functionTable.revalidate();
                functionTable.repaint();
            } catch (Exception ex) {
                showMessage("Can not delete point!");
            }

saveFileAsItem.addActionListener(actionEvent -> {
if(assignFunctionFileName()){
try {
saveFile();
showMessage("File Saved!");
} catch (IOException e) {
showMessage("Can not save file!");
}
}
});


        saveFileItem.addActionListener(actionEvent -> {
            if (!functionDocument.isFileNameAssigned()) {
                assignFunctionFileName();
            }
            try {
                saveFile();
                showMessage("File saved!");
            } catch (IOException e) {
                showMessage("Can not save file!");
            }
        });

        openFileItem.addActionListener(actionEvent -> {
            openFile();
        });
        exportItem.addActionListener(actionEvent -> {
            exportTxt();
        });

        exitItem.addActionListener(actionEvent -> closeWindow());

        tabulateItem.addActionListener(actionEvent -> {
            loadClass();

        });

    }

    private JPanel initBottomPanel() {
        JPanel pointEditPanel = new JPanel();
        pointEditPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        pointEditPanel.add(pointXLabel, constraints);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 1;
        constraints.ipadx = 250;
        pointEditPanel.add(pointXField, constraints);

        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.ipadx = 0;
        pointEditPanel.add(addPointButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        pointEditPanel.add(pointYLabel, constraints);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 1;
        constraints.ipadx = 250;
        pointEditPanel.add(pointYField, constraints);

        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.ipadx = 0;
        pointEditPanel.add(deletePointButton);

        addPointButton.addActionListener(actionEvent -> {
            FunctionPoint newPoint = new FunctionPoint();
            try {
                newPoint.setCoordinateY(Double.parseDouble(pointYField.getText()));
                newPoint.setCoordinateX(Double.parseDouble(pointXField.getText()));
                functionDocument.addPoint(newPoint);
            } catch (Exception ex) {
                showMessage("Can not add point!");
            }

            functionTable.revalidate();
            functionTable.repaint();
        });

        deletePointButton.addActionListener(actionEvent -> {
            int pointIndex = functionTable.getSelectedRow();
            try {
                functionDocument.deletePoint(pointIndex);
                functionTable.revalidate();
                functionTable.repaint();
            } catch (Exception ex) {
                showMessage("Can not delete point!");
            }
        });

        return pointEditPanel;
    }


    private void closeWindow() {
        if (functionDocument.isModified()) {
            int result = JOptionPane.showConfirmDialog(MainWindow.this,
                    "File not saved, really exit?", "Confirm exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                MainWindow.this.dispose();
            }
        }
    }

    private void saveFile() throws IOException {
        functionDocument.saveFunction();
    }
private boolean assignFunctionFileName() {
fileChooser.setDialogTitle("Save File");
fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
functionDocument.setFileName(fileChooser.getSelectedFile().getAbsolutePath());
return true;
}
else return false;
}
   /* private void assignFunctionFileName() {
        fileChooser.setDialogTitle("Save File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
            functionDocument.setFileName(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }*/

    private void openFile() {
        fileChooser.setDialogTitle("Open File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
            try {
                functionDocument.loadFunction(fileChooser.getSelectedFile().getAbsolutePath());
            } catch (IOException ex) {
                showMessage("Can not read file!");
            } finally {
                tableModel.setFunction(functionDocument);
                functionTable.revalidate();
                functionTable.repaint();
            }
        }
    }

    private void exportTxt() {
        fileChooser.setDialogTitle("Export file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
            try {
                TabulatedFunctions.writeTabulatedFunction(functionDocument.getFunction(),
                        new FileWriter(fileChooser.getSelectedFile().getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadClass() {
        fileChooser.setDialogTitle("Load Class");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
            try {
                functionLoader = new FunctionClassLoader(ClassLoader.getSystemClassLoader());
                Class<?> functionClass = functionLoader.findClass(fileChooser.getSelectedFile().getAbsolutePath());
                Object loadedFunction = (functionClass.getConstructor().newInstance());
                if(loadedFunction instanceof Function) {
                    if (functionDialog.showDialog() == FunctionParametersWindow.OK) {
                        TabulatedFunction function = TabulatedFunctions.tabulate((Function) loadedFunction
                                , functionDialog.getLeftDomainBorder()
                                , functionDialog.getRightDomainBorder(), functionDialog.getPointsCount());
                        functionDocument.setFunction(function);
                        tableModel.setFunction(functionDocument);
                    }
                    tableModel.setFunction(functionDocument);
                }else showMessage("Class is not instance of interface Function!");
            } catch (ClassNotFoundException e) {
                showMessage("Can not open class file!");
            } catch (IllegalAccessException e) {
                showMessage("Can not access constructor!");
            } catch (NoSuchMethodException e) {
                showMessage("Can not find constructor!");
            } catch (InvocationTargetException | InstantiationException e) {
                showMessage("Can not create class Instance!");
            }
        }
        functionTable.revalidate();
        functionTable.repaint();
    }
    private void showMessage(String message){
        JOptionPane.showMessageDialog(MainWindow.this,message);
    }
}

