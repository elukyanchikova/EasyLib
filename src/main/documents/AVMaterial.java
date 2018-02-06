package documents;

import java.util.ArrayList;

public class AVMaterial extends Document{

    public AVMaterial(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                      int numberOfCopies, int numberOfRequests){
        super(title, "AV material", authors, keywords, price, numberOfCopies,numberOfRequests);
        this.checkOutTime = 14;
    }

    public AVMaterial(String title, ArrayList<String> authors, ArrayList<String> keywords, int price,
                      int numberOfCopies){
        this(title, authors, keywords, price, numberOfCopies,0);
    }
}