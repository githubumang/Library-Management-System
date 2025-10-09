import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StudentService {

    public void registerStudent(ArrayList<Student> students) {
        JDialog dialog = new JDialog((Frame)null, "Register Student", true);
        dialog.setSize(600, 500);
        dialog.setLayout(new GridLayout(3, 3));

        JTextField studentName = new JTextField();
        JLabel nameError = new JLabel("");
        JTextField registerNumber = new JTextField();
        JLabel registerError = new JLabel("");

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        nameError.setForeground(Color.RED);
        registerError.setForeground(Color.RED);

        dialog.add(new JLabel("Enter Student name: "));
        dialog.add(studentName);
        dialog.add(nameError);

        dialog.add(new JLabel("Enter Registeration number"));
        dialog.add(registerNumber);
        dialog.add(registerError);

        dialog.add(submitButton);
        dialog.add(cancelButton);

        submitButton.addActionListener(_ -> {
            Boolean isAnyError = false;

            String name = studentName.getText();
            String registerNum = registerNumber.getText();

            if(name.isEmpty()) {
                isAnyError = true;
                nameError.setText("*Can not be empty");
            } else {
                nameError.setText("");
            }

            if(registerNum.isEmpty()) {
                isAnyError = true;
                registerError.setText("*Can not be empty");
            } else if(!registerNum.matches("\\d+")) {
                isAnyError = true;
                registerError.setText("*Should contain digit only");
            } else {
                registerError.setText("");
            }

            if(isAnyError == false) {
                students.add(new Student(name, registerNum));
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(_ -> {
            dialog.dispose();
        });

        dialog.setVisible(true);

    }
}
