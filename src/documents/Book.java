package documents;

import java.util.ArrayList;

public class Book extends Document {
    protected  String publisher;
    protected int year;
    protected boolean isBestseller;

    public Book(String title, ArrayList<Person> authors, ArrayList<String> keywords, int price,
                int numberOfCopies, int numberOfRequests, String publisher, int year, boolean isBestseller) {

        super(title, "Book", authors, keywords, price, numberOfCopies, numberOfRequests);
        this.publisher = publisher;
        this.year = year;
        this.isBestseller = isBestseller;
    }

    public Book(String title, ArrayList<Person> authors, ArrayList<String> keywords, int price,
                int numberOfCopies, String publisher, int year, boolean isBestseller) {

        this(title, authors, keywords, price, numberOfCopies, 0, publisher, year, isBestseller);
    }


    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public boolean isBestseller() {
        return isBestseller;
    }



}
