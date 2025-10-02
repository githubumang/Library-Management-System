import javax.swing.JButton;
import javax.swing.JFrame;

public class BooksService {


    public Book addNewBook (JFrame frame) {
        System.out.println("Work is done");
        frame.getContentPane().removeAll();
        frame.add(new JButton("newContest"));
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
        return new Book(0, null, null, 0, 0);
    }
}
