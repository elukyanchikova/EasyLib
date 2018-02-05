package documents;

import users.UserCard;

public class Copy {
    private Document documentType;
    private int level;
    private int room;
    private UserCard checkoutByUser;

    public Copy(Document documentType, int level, int room){
        this.documentType = documentType;
        this.level = level;
        this.room = room;
    }

    //TODO bound with user
    public void checkoutBy(UserCard user){
        this.checkoutByUser = user;
    }
}
