import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
                Boolean isIdExist = false;
                for(Student student:students) {
                    if(student.getRegisterNumber().equals(registerNum)) {
                        isAnyError = true;
                        isIdExist = true;
                        break;
                    }
                }

                if(isIdExist) {
                    registerError.setText("*This id already exist");
                } else {
                    registerError.setText("");
                }
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

    public void showAllStudent (ArrayList<Student>students) {
        JDialog dialog = new JDialog((Frame) null, "Show All Register Student", true);

        dialog.setSize(600, 500);

        String[] columns = {"Register Number", "Name"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        
        for(Student student:students) {
            Object[] row = {student.getRegisterNumber(), student.getName()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        dialog.setVisible(true);
    }
}
