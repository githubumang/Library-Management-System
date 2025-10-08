//Library Management System using Java

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.showMainPageOptions();
        });
        
    }
}
