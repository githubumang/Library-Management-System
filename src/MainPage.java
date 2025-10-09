import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPage {
    JFrame frame;

    JButton[] buttons = new JButton[8];
    Runnable[] actions = new Runnable[8];
    
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Student> students = new ArrayList<Student>();
    
    BooksService booksService;
    StudentService studentService;

    public MainPage() {
        frame = new JFrame();
        books.add(new Book("001", "Alpha", "XYZ", 10));
        books.add(new Book("002", "Beta", "ABC", 10));
        books.add(new Book("003", "Gamma", "PQR", 10));

        students.add(new Student("Mohan", "123"));
        students.add(new Student("Mohan", "123"));
        students.add(new Student("Mohan", "123"));

        booksService = new BooksService();
        studentService = new StudentService();

        buttons[0] = new JButton("Add a New Book");
        buttons[1] = new JButton("Update Quantity of a Book");
        buttons[2] = new JButton("Search a Book");
        buttons[3] = new JButton("Show All Books");
        buttons[4] = new JButton("Register Student");
        buttons[5] = new JButton("Show All Registered Students");
        buttons[6] = new JButton("Check Out Book");
        buttons[7] = new JButton("Check-In Book");

        actions[0] = () -> booksService.addNewBook(books);
        actions[1] = () -> temFrame();
        actions[2] = () -> temFrame();
        actions[3] = () -> booksService.showBooks(books);
        actions[4] = () -> studentService.registerStudent(students);
        actions[5] = () -> temFrame();
        actions[6] = () -> temFrame();
        actions[7] = () -> temFrame();

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void showMainPageOptions() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        for(int idx = 0; idx<8; idx++) {
            final int ind = idx;
            panel.add(buttons[idx]);
            buttons[idx].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actions[ind].run();
                }
            });
        }
        
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void temFrame() {
        
    }

}
