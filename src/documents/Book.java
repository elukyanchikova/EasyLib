package documents;

public class Book extends Document {
    Publisher publisher;
    int edition; //year
    boolean isBestseller;

    Book(Publisher publisher, int edition, boolean isBestseller) {
        this.publisher = publisher;
        this.edition = edition;
        this.isBestseller = isBestseller;

    }

    public Book() {
        this.publisher = null;
        this.edition = 0;
        this.isBestseller = false;

    }

    private class Publisher {
        String name;
        String surname;

        Publisher(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }
    }
}
