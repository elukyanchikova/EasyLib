package documents;


import users.UserCard;

public class Copy {
    private Document document;
    private int level;
    private int room;
    //TODO work with that variable when returning system will be started
    public int checkOutTime;
    private UserCard checkoutByUser;

    public Copy(Document document, int level, int room){
        this.document = document;
        this.level = level;
        this.room = room;
    }

    //TODO bound with user
    public void checkoutBy(UserCard user){
        this.checkoutByUser = user;
    }

    public Document getDocument() {
        return document;
    }


}
