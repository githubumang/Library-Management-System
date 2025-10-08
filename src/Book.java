public class Book {
    private String bookId;
    private String bookName;
    private String authorName;
    private int bookQty;

    public Book(String bookId, String bookName, String authorName, int bookQty) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookQty = bookQty;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getBookQty() {
        return bookQty;
    }

}
