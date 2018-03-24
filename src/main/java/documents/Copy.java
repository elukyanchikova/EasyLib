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
    private UserCard checkoutByUser;

    public Copy(Document document, int level, int room){
        this.documentID = document.id;
        this.level = level;
        this.room = room;
        id = document.lastCopyID;
        document.setCopy(this);
    }

    public Copy(JSONObject data, DatabaseManager databaseManager){
        this.id = data.getInt("ID");
        this.documentID = data.getInt("DocumentID");
        this.level = data.getInt("Level");
        this.room = data.getInt("Room");
        if(data.get("CheckedOutBy")!= JSONObject.NULL) {
            this.checkoutByUser = databaseManager.getUserCard(data.getInt("CheckedOutBy"));
            if(!checkoutByUser.checkedOutCopies.contains(this)) {
                checkoutByUser.checkedOutCopies.add(this);
            }
        }
        this.checkOutDay = data.getInt("CheckedOutDay");
        this.checkOutMonth = data.getInt("CheckedOutMonth");
        this.checkOutTime = data.getInt("CheckedOutTime");

    }

    //TODO bound with user
    public void checkoutBy(UserCard user){
        this.checkoutByUser = user;
    }

    public void setFine(UserCard user,Session currentSession,DatabaseManager databaseManager){
        int fine = 0;

        for (int i = 1; i <= user.checkedOutCopies.size(); i++) {

   /* The overdue fine is a hundred rubles per item per day, but cannot be
    higher than the value of the overdue item.*/
            while(fine<=databaseManager.getAllDocuments().get(i).price){
            fine = fine + (user.checkedOutCopies.get(i).getOverdue(currentSession) * 100) ;
            user.fine = fine;}
        }
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
        return data;
    }

    public int getID(){
        return id;
    }

    public void returnCopy(){
        checkoutByUser = null;
    }

    public int getOverdue(Session session){
        int days = 0;
        int[] m = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        if(checkOutMonth > session.month) return -1;
        days = session.day - checkOutDay;
        if(checkOutMonth < session.month){
            int[] months = new int[session.month - checkOutMonth];
            for(int i = checkOutMonth; i < session.month; i++) {
                days += m[i-1];
            }
        }
        return days<(checkOutTime)? -1: (days-checkOutTime);
    }

    public String getDueDate(){
        int day=checkOutDay;
        int month = checkOutMonth;
        day += checkOutTime-1;
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
