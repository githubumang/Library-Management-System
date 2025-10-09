import java.awt.BorderLayout;
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

public class BooksService {

    public void addNewBook (ArrayList<Book> books) {
        JDialog dialog = new JDialog((Frame) null, "Add New Book", true); // true = modal
        dialog.setSize(600, 500);
        dialog.setLayout(new GridLayout(5, 3));

        JTextField bookId = new JTextField();
        JLabel idError = new JLabel("");
        JTextField bookName = new JTextField();
        JLabel nameError = new JLabel("");
        JTextField authorName = new JTextField();
        JLabel authorError = new JLabel("");
        JTextField bookQty = new JTextField();
        JLabel qtyError = new JLabel("");

        idError.setForeground(Color.RED);
        nameError.setForeground(Color.RED);
        authorError.setForeground(Color.RED);
        qtyError.setForeground(Color.RED);

        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");

        dialog.add(new JLabel("Book Id:"));
        dialog.add(bookId);
        dialog.add(idError);
        dialog.add(new JLabel("Book Name:"));
        dialog.add(bookName);
        dialog.add(nameError);
        dialog.add(new JLabel("Author Name:"));
        dialog.add(authorName);
        dialog.add(authorError);
        dialog.add(new JLabel("Quantity:"));
        dialog.add(bookQty);
        dialog.add(qtyError);
        dialog.add(submit);
        dialog.add(cancel);


        submit.addActionListener(_ -> {
            Boolean isAnyError = false;

            String id = bookId.getText().trim();
            String name = bookName.getText().trim();
            String author = authorName.getText().trim();
            String qty = bookQty.getText().trim();

            if(id.isEmpty()) {
                isAnyError = true;
                idError.setText("*Id can not be empty");
            } else {
                idError.setText("");
            }

            if(name.isEmpty()) {
                isAnyError = true;
                nameError.setText("*BookName can not be empty");
            } else {
                nameError.setText("");
            }

            if(author.isEmpty()) {
                isAnyError = true;
                authorError.setText("*Author Name can not be empty");
            } else {
                authorError.setText("");
            }

            if(qty.isEmpty()) {
                isAnyError = true;
                qtyError.setText("*Quantity can not be empty");
            } else if(!qty.matches("\\d+")) {
                isAnyError = true;
                qtyError.setText("*Quantity should be number");
            } else {
                qtyError.setText("");
            }


            if(isAnyError == false) {
                books.add(new Book(id, name, author, Integer.parseInt(qty)));
                dialog.dispose();
            }
        });

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        dialog.setVisible(true); 
    }

    public void showBooks (ArrayList<Book> books) {
        JDialog dialog = new JDialog((Frame) null, "Show All Books", true);

        dialog.setSize(600, 500);

        String[] columns = {"Book Id", "Book Name", "Author", "Quantity"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for(Book book:books) {
            Object[] row = {book.getBookId(), book.getBookName(), book.getAuthorName(), book.getBookQty()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }
}
