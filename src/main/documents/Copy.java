package documents;


import org.json.JSONObject;
import users.UserCard;

public class Copy {
    private int id;
    private int documentID;
    private int level;
    private int room;
    //TODO work with that variable when returning system will be started
    public int checkOutTime;
    private UserCard checkoutByUser;

    public Copy(Document document, int level, int room){
        this.documentID = document.id;
        this.level = level;
        this.room = room;
        id = document.lastCopyID;
        document.setCopy(this);
    }

    //TODO bound with user
    public void checkoutBy(UserCard user){
        this.checkoutByUser = user;
    }

    public UserCard getCheckoutByUser(){
        return checkoutByUser;
    }

    public Document getDocument() {
        return Storage.getDocuments().get(documentID);
    }

    public JSONObject serialize(){
        JSONObject data = new JSONObject();
        data.put("ID", id);
        data.put("DocumentID", documentID);
        data.put("CheckedOutBy", checkoutByUser.getId());
        data.put("Level", level);
        data.put("Room", room);
        return data;
    }

    public static Copy deserialize(JSONObject data){
        return null;
    }
}
