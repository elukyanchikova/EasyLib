package documents;

public class Book extends Document {
    Publisher publisher;
    int edition; //year
    boolean isBestseller;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher){this.publisher=publisher;}

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public boolean isBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(boolean isBestseller) {
        isBestseller = isBestseller;
    }

    public Book(Publisher publisher, int edition, boolean isBestseller) {

        this.publisher = publisher;
        this.edition = edition;
        this.isBestseller = isBestseller;
        docType="Book";

    }

    public Book() {
        this.publisher = null;
        this.edition = 0;
        this.isBestseller = false;
        docType="Book";


    }

    private class Publisher {
        String name;
        String surname;

        protected Publisher(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }
    }
}
