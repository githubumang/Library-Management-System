import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
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
                Boolean isIdExist = false;
                for(Book book:books) {
                    if(id.equals(book.getBookId())) {
                        isIdExist = true;
                        break;
                    }
                }

                if(isIdExist == true) {
                    isAnyError = true;
                    idError.setText("*This id is already exist");
                }
                else {
                    idError.setText("");
                }
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

    public void searchBook (ArrayList<Book> books) {
        
        JDialog dialog = new JDialog((Frame) null, "Search Book", null);

        dialog.setSize(600, 500);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        // Label
        JLabel searchLabel = new JLabel("Enter book id/name, author name to search:");
        topPanel.add(searchLabel, BorderLayout.WEST);

        // Search box
        JTextField searchBox = new JTextField();
        searchBox.setPreferredSize(new Dimension(300, 30)); // Make it wider
        topPanel.add(searchBox, BorderLayout.CENTER);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        topPanel.add(searchButton, BorderLayout.EAST);

        // Add top panel to dialog
        dialog.add(topPanel, BorderLayout.NORTH);
        

        String[] columns = {"Book Id", "Book Name", "Author", "Quantity"};

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);


        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        dialog.add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(_ -> {
            tableModel.setRowCount(0);
            String searchWord = searchBox.getText();
            if(!searchWord.isEmpty()) {
                for(Book book:books) {
                    if(book.getBookId().toLowerCase().contains(searchWord.toLowerCase()) || book.getBookName().toLowerCase().contains(searchWord.toLowerCase()) || book.getAuthorName().toLowerCase().contains(searchWord.toLowerCase())) {
                        Object[] row = {book.getBookId(), book.getBookName(), book.getAuthorName(), book.getBookQty()};
                        tableModel.addRow(row);
                    }
                }
            }
        });
        

        dialog.setVisible(true);
    }

    public void updateQty(ArrayList<Book> books) {
        int[] ind = {0};
        JDialog dialog = new JDialog((Frame)null, "Add/Remove New Books", true);
        
        dialog.setSize(600, 500);
        dialog.setLayout(new BorderLayout(10, 10));

        //Search Panel

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 3));

        JTextField bookId = new JTextField();
        JLabel idError = new JLabel("");
        bookId.setPreferredSize(new Dimension(300, 30));
        JButton searchBookBtn = new JButton("Search");

        searchPanel.add(new JLabel("Enter Book id:"));
        searchPanel.add(bookId);
        searchPanel.add(idError);
        searchPanel.add(searchBookBtn);

        //Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 2));

        JLabel bookName = new JLabel("Book Name : ");
        JLabel bookQuantity = new JLabel("Book Quantity: ");
        JLabel bookAuthor = new JLabel("Book Author: ");

        infoPanel.add(bookName);
        infoPanel.add(bookQuantity);
        infoPanel.add(bookAuthor);
        
        dialog.add(searchPanel, BorderLayout.NORTH);
        dialog.add(infoPanel, BorderLayout.CENTER);

        //ChangeInfo Panel
        JPanel changeInfo = new JPanel();
        changeInfo.setLayout(new GridLayout(3, 2));
        JRadioButton addBook = new JRadioButton("Add Book");
        JRadioButton removeBook = new JRadioButton("Remove Book");
        JTextField quantity = new JTextField();
        JLabel quantityError = new JLabel();
        JButton changeQtyBtn = new JButton("Change Quantity");
        changeQtyBtn.setEnabled(false);

        quantity.setPreferredSize(new Dimension(300, 30));

        ButtonGroup group = new ButtonGroup();
        group.add(addBook);
        group.add(removeBook);

        changeInfo.add(addBook);
        changeInfo.add(removeBook);
        changeInfo.add(quantity);
        changeInfo.add(quantityError);
        changeInfo.add(changeQtyBtn);
        

        dialog.add(changeInfo, BorderLayout.SOUTH);

        searchBookBtn.addActionListener(_ -> {
            ind[0] = 0;
            Boolean isBookFound = false;
            for(Book book:books) {
                if(book.getBookId().equals(bookId.getText())) {
                    isBookFound = true;
                    bookAuthor.setText("Book Author: " + book.getAuthorName());
                    bookName.setText("Book Name : " + book.getBookName());
                    bookQuantity.setText("Book Quantity: " + book.getBookQty());
                    break;
                }
                ind[0]++;
            }

            if(!isBookFound) {
                changeQtyBtn.setEnabled(false);
                bookAuthor.setText("Book Author: ");
                    bookName.setText("Book Name : ");
                    bookQuantity.setText("Book Quantity: ");
            }
            else {
                changeQtyBtn.setEnabled(true);
            }
        });

        changeQtyBtn.addActionListener(_ -> {
            String selectedOpt = "";
            quantityError.setText("");
            if(addBook.isSelected()) {
                selectedOpt = "added";
            }
            else if(removeBook.isSelected()) {
                selectedOpt = "removed";
            }
            else {
                quantityError.setText("*Please select add/remove option");
            }

            if(!selectedOpt.isEmpty()) {
                String quantityCount = quantity.getText();
                if(quantityCount.isEmpty()) {
                    quantityError.setText("*Please enter a valid number");
                }
                else if(!quantityCount.matches("\\d+")) {
                    quantityError.setText("*Please enter a valid number");
                }
                else if((selectedOpt.equals("removed") == true) && (Integer.parseInt(quantityCount.trim()) > books.get(ind[0]).getBookQty())) {
                    quantityError.setText("*Please enter number less than book quantity.");
                }
                else {
                    int newQuantity = 0;
                    int oldQuantity = books.get(ind[0]).getBookQty();

                    if(selectedOpt.equals("added")) {
                        newQuantity = oldQuantity + Integer.parseInt(quantityCount);
                    }
                    else {
                        newQuantity = oldQuantity - Integer.parseInt(quantityCount);
                    }

                    books.get(ind[0]).setQuantity(newQuantity);
                    bookQuantity.setText("Book Quantity: " + newQuantity);

                    JOptionPane.showMessageDialog(
                        dialog,
                        quantityCount+" books has been "+selectedOpt+" to "+books.get(ind[0]).getBookName()+". The new Quantity is "+newQuantity+".",
                        "Success Message",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    
                }
            }
        });
        
        

        dialog.setVisible(true);
    }
}
