package users;

import org.json.JSONObject;
import storage.DatabaseManager;

public class Notification {

    public static final int NO_NOTIFICATION = 0;
    public static final int GET_COPY_NOTIFICATION = 1;
    public static final int OUTDATNDING_REQUEST_NOTIFICATION_FOR_CHECKED_OUT_US = 2;
    public static final int ACCEPT_NOTIFICATION = 3;
    public static final int REQECT_NOTIFICATION = 4;
    public static final int OUTDATNDING_REQUEST_NOTIFICATION_FOR_PQ=5;


    public int id = 0;
    public int docID;

    public Notification(int notificationID, int documentID){
        this.id = notificationID;
        this.docID = documentID;
    }

    public JSONObject serialize(){
        JSONObject data = new JSONObject();
        data.put("NotificationID", id);
        data.put("DocumentID", docID);
        return data;
    }

    public Notification(JSONObject data){
        this.id = data.getInt("NotificationID");
        this.docID = data.getInt("DocumentID");
    }

    public String getMessage(DatabaseManager databaseManager){
        switch (id){
            case 1: return "\"" + databaseManager.getDocuments(docID).title + "\" is available now!";
            case 2: return "Sorry, \"" + databaseManager.getDocuments(docID).title + "\" is not available now. Please, return the copy, if you have it, immediately!";
            case 3: return "You have checked out \"" + databaseManager.getDocuments(docID).title + "\".";
            case 4: return "Your request for checking out \"" + databaseManager.getDocuments(docID).title + "\" has rejected!";
            case 5: return "Sorry, \"" + databaseManager.getDocuments(docID).title + "\" is not available now. You were deleted from a waiting list.";
            default: return "";
        }
    }
}
