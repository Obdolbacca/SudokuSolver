import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class SudokuSolverClass extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JButton selFileBut;

    public SudokuSolverClass() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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
                if (fc.showDialog(selFileBut, "") == JFileChooser.APPROVE_OPTION) {
                    textField1.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private void onOK() {
        File inp = new File(textField1.getText());

        try {

        } catch (Exception e) {

        } finally {

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
