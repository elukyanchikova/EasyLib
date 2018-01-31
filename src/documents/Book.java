package documents;

public class Book extends Document {
    String publisher;
    int edition ; //year
    boolean isBestseller;
    Book ( String publisher, int edition, boolean isBestseller){
        this.publisher=publisher;
        this.edition=edition;
        this.isBestseller=isBestseller;

    }
    Book (){
        this.publisher="NoName";
        this.edition=0;
        this.isBestseller=false;

    }
}
