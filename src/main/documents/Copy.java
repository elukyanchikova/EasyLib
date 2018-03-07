package documents;


import org.json.JSONObject;
import storage.Database;
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

    public Copy(JSONObject data, Database database){
        this.id = data.getInt("ID");
        this.documentID = data.getInt("DocumentID");
        this.level = data.getInt("Level");
        this.room = data.getInt("Room");
        if(data.get("CheckedOutBy")!= JSONObject.NULL) {
            this.checkoutByUser = database.getUserCard(data.getInt("CheckedOutBy"));
            checkoutByUser.checkedOutCopies.add(this);
        }

    }

    //TODO bound with user
    public void checkoutBy(UserCard user){
        this.checkoutByUser = user;
    }

    public UserCard getCheckoutByUser(){
        return checkoutByUser;
    }

    public int getDocumentID() {
        return documentID;
    }

    public JSONObject serialize(){
        JSONObject data = new JSONObject();
        data.put("ID", id);
        data.put("DocumentID", documentID);
        if(checkoutByUser != null) {
            data.put("CheckedOutBy", checkoutByUser.getId());
        }else data.put("CheckedOutBy", JSONObject.NULL);
        data.put("Level", level);
        data.put("Room", room);
        return data;
    }

    public int getID(){
        return id;
    }

    public void returnCopy(){
        checkoutByUser = null;
    }
}
