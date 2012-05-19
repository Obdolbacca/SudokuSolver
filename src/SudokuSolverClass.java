import com.sun.tools.javac.util.Convert;

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
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;
    private JTextArea textArea9;
    private int[] field = new int[81];

    private boolean checkHLine (int lineNum) {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        for (int i = 0; i < 9; i ++) {
            if (field[lineNum * 9 + i] == 0) continue;
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
            if (field[9 * i + lineNum] == 0) continue;
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
            if (field[getAbsPosBySquare(squareNum, i)] == 0) continue;
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
        boolean isLucky;
        int num = 1;
        isLucky = checkHLine(num) & checkVLine(num) & checkSquare(num);
        if (!isLucky) return false;

        for (int i = 0; i < 81; i ++) {
            if (field[i] == 0) return false;
        }
        return true;
    }

    private void sudokuSolutionShow () {
        ArrayList<ArrayList<Integer>> squares = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 9; i ++) {
            squares.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < 81; i ++) {
            int squareNum = getSquareByAbsPos(i);
            squares.get(squareNum).add(field[i]);
        }
        for (int i = 0; i < 9; i ++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 9; j ++) {
                if (j == 3 || j == 6) {
                    sb.append('\n');
                }
                int val = squares.get(i).get(j);
                sb.append(val == 0 ? "*" : val);
                sb.append(' ');
            }
            if (i == 0) {
                textArea1.setText(sb.toString());
                continue;
            }
            if (i == 1) {
                textArea2.setText(sb.toString());
                continue;
            }
            if (i == 2) {
                textArea3.setText(sb.toString());
                continue;
            }
            if (i == 3) {
                textArea4.setText(sb.toString());
                continue;
            }
            if (i == 4) {
                textArea5.setText(sb.toString());
                continue;
            }
            if (i == 5) {
                textArea6.setText(sb.toString());
                continue;
            }
            if (i == 6) {
                textArea7.setText(sb.toString());
                continue;
            }
            if (i == 7) {
                textArea8.setText(sb.toString());
                continue;
            }
            if (i == 8) {
                textArea9.setText(sb.toString());
            }
        }
    }

    private void fillField () throws IOException {
        File inp = new File(textField1.getText());
        FileReader inpStream = null;
        try {
            inpStream = new FileReader(inp);
            int i = 0;
            int buf;
            char[] tmp = new char[1];
            while ((buf = inpStream.read()) >= 0) {
                if (buf != '\n' && i < 81) {
                    if (buf != '*') {
                        tmp[0] = (char)buf;
                        field[i ++] = Integer.parseInt(new String(tmp));
                    } else {
                        field[i ++] = 0;
                    }
                }
            }
        } finally {
            if (inpStream != null) {
                inpStream.close();
            }

        }
    }

    public SudokuSolverClass() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(selFileBut);

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

        this.setResizable(false);

        selFileBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (file.isDirectory()) return true;
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
                try{
                    fillField();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(getRootPane(), "Somewhat went wrong", "Error", JOptionPane.ERROR_MESSAGE);
                }
                sudokuSolutionShow();
                getRootPane().setDefaultButton(buttonOK);
            }
        });
    }

    private void onOK() {
        if (!(textField1.getText().equals(""))) {
            if (!sudokuSolve()) {
                JOptionPane.showMessageDialog(getRootPane(), "Seems like this is not solvable...", "Can not solve", JOptionPane.WARNING_MESSAGE);
            }
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
