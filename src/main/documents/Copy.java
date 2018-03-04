package documents;


import users.UserCard;

public class Copy {
    private int documentID;
    private int level;
    private int room;
    //TODO work with that variable when returning system will be started
    public int checkOutTime;
    private int checkoutByUserID;

    public Copy(Document document, int level, int room){
        this.documentID = document.id;
        this.level = level;
        this.room = room;
    }

    //TODO bound with user
    public void checkoutBy(UserCard user){
        this.checkoutByUserID = user.id;
    }

    public Document getDocument() {
        return Storage.getDocuments().get(documentID);
    }


}
