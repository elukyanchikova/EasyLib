package core;

import documents.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import storage.DatabaseManager;
import users.UserCard;

public class ActionNote {

    public static final int ADD_USER_ACTION_ID = 1;
    public static final int EDIT_USER_ACTION_ID = 2;
    public static final int DELETE_USER_ACTION_ID = 3;
    public static final int ADD_DOCUMENT_ACTION_ID = 4;
    public static final int EDIT_DOCUMENT_ACTION_ID = 5;
    public static final int DELETE_DOCUMENT_ACTION_ID = 6;
    public static final int OUTSTANDING_REQUEST_ACTION_ID = 7;
    public static final int REQUEST_DOCUMENT_ACTION_ID = 8;
    public static final int BOOK_DOCUMENT_ACTION_ID = 9;
    public static final int CHECK_OUT_DOCUMENT_ACTION_ID = 10;
    public static final int RENEW_DOCUMENT_ACTION_ID = 11;
    public static final int RETURN_DOCUMENT_ACTION_ID = 12;
    public static final int ACCEPT_REQUEST_ACTION_ID = 13;
    public static final int REJECT_REQUEST_ACTION_ID = 14;
    public static final int ADD_COPY_ACTION_ID = 15;
    public static final int REMOVE_WAITING_LIST_ACTION_ID = 16;
    public static final int NOTIFY_TO_RETURN_ACTION_ID = 17;
    public static final int NOTIFY_REMOVED_FROM_WAITING_LIST_ACTION_ID = 18;

    UserCard userCard;
    public UserCard targetUser;
    public Document targetDocument;
    int day;
    int month;
    int actionID;
    boolean ok;

    public ActionNote(UserCard userCard, int day, int month, int id){
        this.actionID = id;
        this.day = day;
        this.month = month;
        this.userCard = userCard;
    }

    public ActionNote(UserCard userCard, int day, int month, int id, UserCard targetUser){
        this(userCard, day, month, id);
        this.targetUser = targetUser;
    }

    public ActionNote(UserCard userCard, int day, int month, int id, Document targetDocument){
        this(userCard, day, month, id);
        this.targetDocument = targetDocument;
    }

    public ActionNote(UserCard userCard, int day, int month, int id, UserCard targetUser, Document targetDocument){
        this(userCard, day, month, id);
        this.targetUser = targetUser;
        this.targetDocument = targetDocument;
    }

    public ActionNote(UserCard userCard, int day, int month, int id, UserCard targetUser, Document targetDocument, boolean ok) {
        this(userCard, day, month, id, targetUser, targetDocument);
        this.ok = ok;
    }

    public ActionNote(JSONObject action, DatabaseManager databaseManager){
        this.actionID = action.getInt("ActionID");
        this.day = action.getInt("Day");
        this.month = action.getInt("Month");
        this.userCard = databaseManager.getUserCard(action.getInt("UserCardAction"));
        this.targetUser = databaseManager.getUserCard(action.getInt("TargetUser"));
        this.targetDocument = databaseManager.getDocuments(action.getInt("TargetDocument"));
    }

    public JSONObject serialize(){
        JSONObject action = new JSONObject();
        action.put("UserCardAction", userCard.getId());
        action.put("Day", day);
        action.put("Month", month);
        action.put("ActionID", actionID);
        if(targetUser != null)
            action.put("TargetUser", targetUser.getId());
        else  action.put("TargetUser", 0);
        if(targetDocument != null)
            action.put("TargetDocument", targetDocument.getID());
        else  action.put("TargetDocument", 0);
        return action;
    }

    public String createNote(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if(day < 10) stringBuilder.append("0");
        stringBuilder.append(day);
        stringBuilder.append("/");
        if(month < 10) stringBuilder.append("0");
        stringBuilder.append(month + "]");
        stringBuilder.append(userCard.userType.getClass().getName().replace("users.userTypes.", "") + " ");
        stringBuilder.append(userCard.name + " " + userCard.surname);
        stringBuilder.append( "(ID:" + userCard.getId() + ") ");
        switch (actionID) {
            case ADD_USER_ACTION_ID:
                stringBuilder.append("added ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes.", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case EDIT_USER_ACTION_ID:
                stringBuilder.append("edited ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes.", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case DELETE_USER_ACTION_ID:
                stringBuilder.append("deleted ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes.", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case ADD_DOCUMENT_ACTION_ID:
                stringBuilder.append("added ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case EDIT_DOCUMENT_ACTION_ID:
                stringBuilder.append("edited ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case DELETE_DOCUMENT_ACTION_ID:
                stringBuilder.append("deleted ");
               stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case OUTSTANDING_REQUEST_ACTION_ID:
                stringBuilder.append("made an outstanding request on ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                if(!ok) stringBuilder.append("access was denied");
                break;
            case REQUEST_DOCUMENT_ACTION_ID:
                stringBuilder.append("requested ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case BOOK_DOCUMENT_ACTION_ID:
                stringBuilder.append("booked ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case CHECK_OUT_DOCUMENT_ACTION_ID:
                stringBuilder.append("checked out ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case RENEW_DOCUMENT_ACTION_ID:
                stringBuilder.append("renewed ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case RETURN_DOCUMENT_ACTION_ID:
                stringBuilder.append("returned ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case ACCEPT_REQUEST_ACTION_ID:
                stringBuilder.append("accept request on ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                stringBuilder.append("made by ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case REJECT_REQUEST_ACTION_ID:
                stringBuilder.append("reject request on ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                stringBuilder.append("made by ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case ADD_COPY_ACTION_ID:
                stringBuilder.append("add copy of ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                stringBuilder.append("made by: ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case REMOVE_WAITING_LIST_ACTION_ID:
                stringBuilder.append("removed waiting list of ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                break;
            case NOTIFY_TO_RETURN_ACTION_ID:
                stringBuilder.append("notified to return ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                stringBuilder.append("taken by: ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case NOTIFY_REMOVED_FROM_WAITING_LIST_ACTION_ID:
                stringBuilder.append("notified doc not available ");
                stringBuilder.append(targetDocument.getDocType() + " ");
                stringBuilder.append(targetDocument.title);
                stringBuilder.append("(ID:" + targetDocument.getID() + ") ");
                stringBuilder.append("and that the user was removed from wait list: ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            default:
                stringBuilder.append("has invalid action.");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
