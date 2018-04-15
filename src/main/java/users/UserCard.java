package users;

import documents.Copy;
import documents.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import storage.DatabaseManager;
import users.userTypes.UserType;

import java.util.ArrayList;

public class UserCard implements Comparable<UserCard> {
    private static int lastID = 0;

    private int id;
    public String name;
    public String surname;
    public UserType userType;
    public String phoneNumb;
    public String address;

    public ArrayList<Copy> checkedOutCopies;
    public ArrayList<Document> requestedDocs;
    public ArrayList<Notification> notifications = new ArrayList<>();
    //public int fine;

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs) {
        this(++lastID, name, surname, userType, phoneNumb, address, checkedOutCopies, requestedDocs);
    }

    public UserCard(int id, String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs) {
        this.name = name;
        this.surname = surname;
        this.userType = userType;
        this.phoneNumb = phoneNumb;
        this.address = address;
        this.checkedOutCopies = checkedOutCopies;
        this.requestedDocs = requestedDocs;
        this.id = id;
        lastID = lastID < id ? id : lastID;
    }

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address) {
        this(name, surname, userType, phoneNumb, address, new ArrayList<>(), new ArrayList<>());
    }

    public UserCard(int id, String name, String surname, UserType userType, String phoneNumb, String address) {
        this(id, name, surname, userType, phoneNumb, address, new ArrayList<>(), new ArrayList<>());
    }

    //public UserCard(int id, JSONObject data, DatabaseManager database){
    public UserCard(int id, JSONObject data) {
        this.id = id;
        lastID = lastID < id ? id : lastID;
        this.name = data.getString("Name");
        this.surname = data.getString("Surname");
        this.userType = UserType.userTypes.get(data.getString("UserType"));
        this.phoneNumb = data.getString("PhoneNumber");
        this.address = data.getString("Address");
        JSONArray requestedDocsObj = data.getJSONArray("RequestedDocs");
        for (int i = 0; i < requestedDocsObj.toList().size(); i++) {
            //database.getDocument(requestedBooksObj.get(i))
        }
        JSONArray notificationsObj = data.getJSONArray("Notifications");
        for (int i = 0; i < notificationsObj.toList().size(); i++) {
            notifications.add(new Notification(notificationsObj.getJSONObject(i)));
        }
        requestedDocs = new ArrayList<>();
        checkedOutCopies = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public JSONObject serialize() {
        JSONObject data = new JSONObject();

        data.put("Name", name);
        data.put("Surname", surname);
        data.put("UserType", userType.getClass().getName());
        data.put("PhoneNumber", phoneNumb);
        data.put("Address", address);
        JSONArray requestedBooksObj = new JSONArray();
        for (int i = 0; i < requestedDocs.size(); i++) {
            requestedBooksObj.put(requestedDocs.get(i).getID());
        }
        data.put("RequestedDocs", requestedBooksObj);
        JSONArray notificationsObj = new JSONArray();
        for (int i = 0; i < notifications.size(); i++) {
            notificationsObj.put(notifications.get(i).serialize());
        }
        data.put("Notifications", notificationsObj);
        return data;
    }

    public int getFine(UserCard user, Session currentSession, DatabaseManager databaseManager) {
        int fine = 0;
        int priceOfTheBook ;
        int possibleFine ;
        for (int i = 0; i < user.checkedOutCopies.size(); i++) {
            priceOfTheBook = databaseManager.getDocuments(user.checkedOutCopies.get(i).getDocumentID()).price;
            possibleFine = user.checkedOutCopies.get(i).getOverdue(currentSession) * 100;
            if (priceOfTheBook < possibleFine) {
                fine = fine + priceOfTheBook;
            } else {
                fine = fine + possibleFine;
            }
        }
        return fine;
    }

    public static void resetID() {
        lastID = 0;
    }

    @Override
    public int compareTo(UserCard o) {
        return Integer.compare(userType.priority, o.userType.priority);
    }


}