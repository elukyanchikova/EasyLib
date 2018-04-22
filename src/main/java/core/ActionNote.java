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

    UserCard userCard;
    public UserCard targetUser;
    public Document targetDocument;
    int day;
    int month;
    int actionID;

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
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case EDIT_USER_ACTION_ID:
                stringBuilder.append("edited ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
                stringBuilder.append(targetUser.name + " " + targetUser.surname);
                stringBuilder.append("(ID:" + targetUser.getId() + ") ");
                break;
            case DELETE_USER_ACTION_ID:
                stringBuilder.append("deleted ");
                stringBuilder.append(targetUser.userType.getClass().getName().replace("users.userTypes", "") + " ");
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
            default:
                stringBuilder.append("has invalid action.");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
