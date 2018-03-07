package documents;


import org.json.JSONObject;
import storage.Database;
import users.UserCard;

public class Copy {
    private int id;
    private int documentID;
    private int level;
    private int room;
    public int checkOutTime;
    public int checkOutDay;
    public int checkOutMonth;
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

    public String getDueDate(){
        int day=checkOutDay;
        int month = checkOutMonth;
        day += checkOutTime;
        switch (month){
            case 1:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "February";
                }else return day + " " + "January";
            case 2:
                if(day > 28) {
                    day -= 28;
                    return day + " " + "March";
                }else return day + " " + "February";
            case 3:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "April";
                }else return day + " " + "March";
            case 4:
                if(day > 30) {
                    day -= 30;
                    return day + " " + "May";
                }else return day + " " + "April";
            case 5:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "June";
                }else return day + " " + "May";
            case 6:
                if(day > 30) {
                    day -= 30;
                    return day + " " + "July";
                }else return day + " " + "June";
            case 7:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "August";
                }else return day + " " + "July";
            case 8:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "September";
                }else return day + " " + "August";
            case 9:
                if(day > 30) {
                    day -= 30;
                    return day + " " + "October";
                }else return day + " " + "September";
            case 10:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "November";
                }else return day + " " + "October";
            case 11:
                if(day > 30) {
                    day -= 30;
                    return day + " " + "December";
                }else return day + " " + "November";
            default:
                if(day > 31) {
                    day -= 31;
                    return day + " " + "January";
                }else return day + " " + "December";
        }
    }
}
