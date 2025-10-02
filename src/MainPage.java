import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPage {
    JFrame frame;

    JButton[] buttons = new JButton[8];
    Runnable[] actions = new Runnable[8];
    
    ArrayList<Book> books = new ArrayList<Book>();
    BooksService booksService;

    public MainPage() {
        System.out.println("MainPage called");
        frame = new JFrame();

        booksService = new BooksService();

        buttons[0] = new JButton("Add a New Book");
        buttons[1] = new JButton("Upgrade Quantity of a Book");
        buttons[2] = new JButton("Search a Book");
        buttons[3] = new JButton("Show All Books");
        buttons[4] = new JButton("Register Student");
        buttons[5] = new JButton("Show All Registered Students");
        buttons[6] = new JButton("Check Out Book");
        buttons[7] = new JButton("Check-In Book");

        actions[0] = () -> books.add(booksService.addNewBook(frame));
        actions[1] = () -> booksService.addNewBook(frame);
        actions[2] = () -> booksService.addNewBook(frame);
        actions[3] = () -> booksService.addNewBook(frame);
        actions[4] = () -> booksService.addNewBook(frame);
        actions[5] = () -> booksService.addNewBook(frame);
        actions[6] = () -> booksService.addNewBook(frame);
        actions[7] = () -> booksService.addNewBook(frame);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void showMainPageOptions() {
        frame.setLayout(new GridLayout(4, 2));

        for(int idx = 0; idx<8; idx++) {
            final int ind = idx;
            frame.add(buttons[idx]);
            buttons[idx].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println(buttons[ind].getText()+" is clicked");
                    actions[ind].run();
                }
            });
        }
        
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

}
