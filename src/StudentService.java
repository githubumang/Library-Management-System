import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    public void checkOutBook(ArrayList<Student> students, ArrayList<Book> books) {
        int[] ind = {-1, -1};
        JDialog dialog = new JDialog((Frame) null, "Rent/Check Out Book", true);

        dialog.setSize(600, 400);

        dialog.setLayout(new BorderLayout(10, 10));

        //Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 3));

        JTextField bookId = new JTextField();
        JLabel bookIdError = new JLabel("");
        bookId.setPreferredSize(new Dimension(300, 30));
        JTextField registerNum = new JTextField();
        JLabel registerNumError = new JLabel("");
        registerNum.setPreferredSize(new Dimension(300, 30));
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Enter Book id:"));
        searchPanel.add(bookId);
        searchPanel.add(bookIdError);
        searchPanel.add(new JLabel("Enter Register number:"));
        searchPanel.add(registerNum);
        searchPanel.add(registerNumError);
        searchPanel.add(searchButton);

        //Info of book and student
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 2));

        JLabel bookName = new JLabel("Book Name : ");
        JLabel studentName = new JLabel("Student Name: ");
        JLabel bookQuantity = new JLabel("Book Quantity: ");
        JLabel bookAuthor = new JLabel("Book Author: ");

        infoPanel.add(bookName);
        infoPanel.add(studentName);
        infoPanel.add(bookQuantity);
        infoPanel.add(bookAuthor);


        JButton checkOutBook = new JButton("Check Out");
        checkOutBook.setEnabled(false);

        dialog.add(searchPanel, BorderLayout.NORTH);
        dialog.add(infoPanel, BorderLayout.CENTER);
        dialog.add(checkOutBook, BorderLayout.SOUTH);


        searchButton.addActionListener(_ -> {
            Boolean isBookIdFound = false;
            Boolean isRegisterNumFound = false;
            checkOutBook.setEnabled(true);
            ind[0] = -1;
            ind[1] = -1;
            
            for(Book book:books) {
                ind[0]++;
                if(book.getBookId().equals(bookId.getText())) {
                    isBookIdFound = true;
                    bookName.setText("Book Name : "+book.getBookName());
                    bookQuantity.setText("Book Quantity: "+book.getBookQty());
                    bookAuthor.setText("Book Author: "+book.getAuthorName());
                    if(book.getBookQty() == 0) {
                        checkOutBook.setEnabled(false);
                    }
                    break;
                }
            }

            for(Student student:students) {
                ind[1]++;
                if(student.getRegisterNumber().equals(registerNum.getText())) {
                    isRegisterNumFound = true;
                    studentName.setText("Student Name: "+student.getName());
                    break;
                }
            }

            if(isBookIdFound == false) {
                bookName.setText("Book Name : ");
                bookQuantity.setText("Book Quantity: ");
                bookAuthor.setText("Book Author: ");
                bookIdError.setText("*This Book Id doesn't exist");
                checkOutBook.setEnabled(false);
            }

            if(isRegisterNumFound == false) {
                studentName.setText("Student Name : ");
                registerNumError.setText("*This Register number doesn't exist");
                checkOutBook.setEnabled(false);
            }

        });

        checkOutBook.addActionListener(_ -> {
            Book book = books.get(ind[0]);

            JOptionPane.showMessageDialog(
                dialog,                        //parent component
                "The book '"+book.getBookName()+"' is assigned to "+students.get(ind[1]).getName(),  // message
                "Success",                   // title
                JOptionPane.INFORMATION_MESSAGE // type of message
            );
            
            book.setQuantity(book.getBookQty()-1);
            bookQuantity.setText("Book Quantity: "+book.getBookQty());
            if(book.getBookQty() == 0) {
                checkOutBook.setEnabled(false);
            }
            
        });

        dialog.setVisible(true);
    }

    public void checkInBook(ArrayList<Student> students, ArrayList<Book> books) {
        int[] ind = {-1, -1};
        JDialog dialog = new JDialog((Frame) null, "Rent/Check Out Book", true);

        dialog.setSize(600, 400);

        dialog.setLayout(new BorderLayout(10, 10));

        //Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 3));

        JTextField bookId = new JTextField();
        JLabel bookIdError = new JLabel("");
        bookId.setPreferredSize(new Dimension(300, 30));
        JTextField registerNum = new JTextField();
        JLabel registerNumError = new JLabel("");
        registerNum.setPreferredSize(new Dimension(300, 30));
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Enter Book id:"));
        searchPanel.add(bookId);
        searchPanel.add(bookIdError);
        searchPanel.add(new JLabel("Enter Register number:"));
        searchPanel.add(registerNum);
        searchPanel.add(registerNumError);
        searchPanel.add(searchButton);

        //Info of book and student
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 2));

        JLabel bookName = new JLabel("Book Name : ");
        JLabel studentName = new JLabel("Student Name: ");
        JLabel bookQuantity = new JLabel("Book Quantity: ");
        JLabel bookAuthor = new JLabel("Book Author: ");

        infoPanel.add(bookName);
        infoPanel.add(studentName);
        infoPanel.add(bookQuantity);
        infoPanel.add(bookAuthor);


        JButton checkInBook = new JButton("Check In");
        checkInBook.setEnabled(false);

        dialog.add(searchPanel, BorderLayout.NORTH);
        dialog.add(infoPanel, BorderLayout.CENTER);
        dialog.add(checkInBook, BorderLayout.SOUTH);


        searchButton.addActionListener(_ -> {
            Boolean isBookIdFound = false;
            Boolean isRegisterNumFound = false;
            checkInBook.setEnabled(true);
            ind[0] = -1;
            ind[1] = -1;
            
            for(Book book:books) {
                ind[0]++;
                if(book.getBookId().equals(bookId.getText())) {
                    isBookIdFound = true;
                    bookName.setText("Book Name : "+book.getBookName());
                    bookQuantity.setText("Book Quantity: "+book.getBookQty());
                    bookAuthor.setText("Book Author: "+book.getAuthorName());
                    break;
                }
            }

            for(Student student:students) {
                ind[1]++;
                if(student.getRegisterNumber().equals(registerNum.getText())) {
                    isRegisterNumFound = true;
                    studentName.setText("Student Name: "+student.getName());
                    break;
                }
            }

            if(isBookIdFound == false) {
                bookName.setText("Book Name : ");
                bookQuantity.setText("Book Quantity: ");
                bookAuthor.setText("Book Author: ");
                bookIdError.setText("*This Book Id doesn't exist");
                checkInBook.setEnabled(false);
            }

            if(isRegisterNumFound == false) {
                studentName.setText("Student Name : ");
                registerNumError.setText("*This Register number doesn't exist");
                checkInBook.setEnabled(false);
            }

        });

        checkInBook.addActionListener(_ -> {
            Book book = books.get(ind[0]);

            JOptionPane.showMessageDialog(
                dialog,                        //parent component
                "The book '"+book.getBookName()+"' is returned by "+students.get(ind[1]).getName(),  // message
                "Success",                   // title
                JOptionPane.INFORMATION_MESSAGE // type of message
            );
            
            book.setQuantity(book.getBookQty()+1);
            bookQuantity.setText("Book Quantity: "+book.getBookQty());
            
        });

        dialog.setVisible(true);
    }
}
