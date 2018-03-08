package users;

import documents.Copy;
import documents.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import storage.Database;

import java.util.ArrayList;

public class UserCard {
    private static int lastID = 0;

    private int id;
    public String name;
    public String surname;
    public UserType userType;
    public String phoneNumb;
    public String address;
    public int libraryID;

    public ArrayList<Copy> checkedOutCopies;
    public ArrayList<Document> requestedDocs;
    public int fine;

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs, int libraryID){
        this(++lastID, name,surname, userType, phoneNumb, address, checkedOutCopies, requestedDocs, libraryID);
    }

    public UserCard(int id, String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs, int libraryID){
        this.name=name;
        this.surname=surname;
        this.userType=userType;
        this.phoneNumb=phoneNumb;
        this.address = address;
        this.checkedOutCopies = checkedOutCopies;
        this.requestedDocs = requestedDocs;
        this.id = id;
        lastID = lastID < id?id:lastID;
    }

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address, int libraryID){
        this(name, surname, userType, phoneNumb, address, new ArrayList<>(), new ArrayList<>(), libraryID);
    }

    public UserCard(int id,String name, String surname, UserType userType, String phoneNumb, String address, int libraryID){
        this(id, name, surname, userType, phoneNumb, address, new ArrayList<>(), new ArrayList<>(),libraryID);
    }

    //public UserCard(int id, JSONObject data, Database database){
    public UserCard(int id, JSONObject data){
        this.id = id;
        lastID = lastID < id?id:lastID;
        this.name = data.getString("Name");
        this.surname = data.getString("Surname");
        this.userType = UserType.userTypes.get(data.getString("UserType"));
        this.phoneNumb = data.getString("PhoneNumber");
        this.address = data.getString("Address");
        JSONArray requestedDocsObj = data.getJSONArray("RequestedDocs");
        for(int i = 0; i < requestedDocsObj.toList().size(); i++){
            //database.getDocument(requestedBooksObj.get(i))
        }
        requestedDocs = new ArrayList<>();
        checkedOutCopies = new ArrayList<>();
        this.libraryID = data.getInt("LibraryID");
    }

    public int getId() {
        return id;
    }

    public JSONObject serialize(){
        JSONObject data = new JSONObject();

        data.put("Name", name);
        data.put("Surname", surname);
        data.put("UserType", userType.getClass().getName());
        data.put("PhoneNumber", phoneNumb);
        data.put("Address", address);
        JSONArray requestedBooksObj = new JSONArray();
        for(int i = 0; i < requestedDocs.size(); i++){
            requestedBooksObj.put(requestedDocs.get(i).getID());
        }
        data.put("RequestedDocs", requestedBooksObj);
        data.put("LibraryID", libraryID);
        return data;
    }

    public static void resetID(){
        lastID = 0;
    }
}