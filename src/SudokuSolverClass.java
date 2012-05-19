import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SudokuSolverClass extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JButton selFileBut;
    private int[] field = new int[81];

    private boolean checkHLine (int lineNum) {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        for (int i = 0; i < 9; i ++) {
            if (lst.contains(field[lineNum * 9 + i])) {
                return false;
            } else {
                lst.add(field[lineNum * 9 + i]);
            }
        }
        return true;
    }

    private boolean checkVLine (int lineNum) {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        for (int i = 0; i < 9; i ++) {
            if (lst.contains(field[9 * i + lineNum])) {
                return false;
            } else {
                lst.add(field[9 * i + lineNum]);
            }
        }
        return true;
    }

    private boolean checkSquare (int pos) {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        int squareNum = getSquareByAbsPos(pos);
        for (int i = 0; i < 9; i ++) {
            if (lst.contains(field[getAbsPosBySquare(squareNum, i)])) {
                return false;
            } else {
                lst.add(field[getAbsPosBySquare(squareNum, i)]);
            }
        }
        return true;
    }

    private int getAbsPosBySquare (int squareNum, int pos) {
        int retVal = 0;
        int auxY = ((squareNum / 3) * 3);
        int auxX = ((squareNum % 3) * 3);
        int position = (pos / 3) * 9 + (pos % 3);
        retVal = auxX + (auxY * 9) + position;
        return retVal;
    }

    private int getSquareByAbsPos (int pos) {
        int retVal = 0;
        int auxX = (pos % 9) / 3;
        int auxY = (pos / 9) / 3;
        retVal = auxY * 3 + auxX;
        return retVal;
    }

    private boolean sudokuSolve () {
        boolean isUnlucky;
        int num = 1;
        isUnlucky = checkHLine(num) & checkVLine(num) & checkSquare(num);
        if (!isUnlucky) return false;

        return true;
    }

    private void sudokuSolutionShow () {
        StringBuilder sb = new StringBuilder();
    }

    private void fillField () throws IOException {
        File inp = new File(textField1.getText());
        FileReader inpStream = null;
        try {
            inpStream = new FileReader(inp);
        } finally {
            if (inpStream != null) {
                inpStream.close();
            }

        }
    }

    public SudokuSolverClass() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        selFileBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        String[] filenameParts = file.getName().split("\\.");
                        if (filenameParts[filenameParts.length - 1].toLowerCase().equals("sud")) return true;
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "Sudoku files";
                    }
                });
                if (fc.showDialog(selFileBut, "") == JFileChooser.APPROVE_OPTION) {
                    textField1.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private void onOK() {
        if (!(textField1.getText().equals(""))) {
            try{
                fillField();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Somewhat went wrong", "Error", JOptionPane.ERROR_MESSAGE);
            }
            sudokuSolve();
            sudokuSolutionShow();

        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void sscMain() {
        SudokuSolverClass dialog = new SudokuSolverClass();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
