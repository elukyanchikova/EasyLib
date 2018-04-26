package documents;


import org.json.JSONObject;
import storage.DatabaseManager;
import users.Session;
import users.UserCard;

public class Copy {
    private int id;
    private int documentID;
    private int level;
    private int room;
    public int checkOutTime =0 ;
    public int checkOutDay = 0;
    public int checkOutMonth = 0;
    public boolean hasRenewed = false;
    private UserCard checkoutByUser;

    public Copy(Document document, int level, int room){
        this.documentID = document.id;
        this.level = level;
        this.room = room;
        id = document.lastCopyID;
    }

    public Copy(JSONObject data, DatabaseManager databaseManager){
        this.id = data.getInt("ID");
        this.documentID = data.getInt("DocumentID");
        this.level = data.getInt("Level");
        this.room = data.getInt("Room");
        if(data.get("CheckedOutBy")!= JSONObject.NULL) {
            this.checkoutByUser = databaseManager.getUserCard(data.getInt("CheckedOutBy"));
            boolean flag = true;
            for(int i = 0; i < checkoutByUser.checkedOutCopies.size(); i++) {
                if(checkoutByUser.checkedOutCopies.get(i).getDocumentID() == documentID) flag = false;
            }
            if(flag) databaseManager.getUserCard(checkoutByUser.getId()).checkedOutCopies.add(this);

        }
        this.checkOutDay = data.getInt("CheckedOutDay");
        this.checkOutMonth = data.getInt("CheckedOutMonth");
        this.checkOutTime = data.getInt("CheckedOutTime");
        this.hasRenewed = data.getBoolean("Renewed");
    }


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
        data.put("CheckedOutDay", checkOutDay);
        data.put("CheckedOutMonth", checkOutMonth);
        data.put("CheckedOutTime",checkOutTime);
        data.put("Renewed", hasRenewed);
        return data;
    }

    public int getID(){
        return id;
    }

    public void returnCopy(){
        hasRenewed = false;
        checkoutByUser = null;
    }

    public int getOverdue(Session session){
        int days;
        int[] m = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        if(checkOutMonth > session.month) return 0;
        days = session.day - checkOutDay;
        if(checkOutMonth < session.month){
            for(int i = checkOutMonth; i < session.month; i++) {
                days += m[i-1];
            }
        }else if(checkOutDay==session.day) return 0;
        return days<(checkOutTime)? 0: (days-checkOutTime);
    }

    public String getDueDate(){
        int day=checkOutDay;
        int month = checkOutMonth;
        day += checkOutTime;
        return getStringFromData(day, month);
    }

    public String getCheckedOutDate(){
        return getStringFromData(checkOutDay, checkOutMonth);
    }

    private String getStringFromData(int day, int month){
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
