package documents;

import org.json.JSONArray;
import org.json.JSONObject;
import storage.Database;
import users.UserCard;

import java.util.ArrayList;

public abstract class Document {

    protected static int lastID = 0;

    protected int id;
    public String title;
    protected String docType;
    public ArrayList<String> authors;
    public ArrayList<String> keywords;
    public int price;
    protected int numberOfRequests = 0;
    protected ArrayList<Copy> availableCopies;
    protected ArrayList<Copy> takenCopies;
    protected int checkOutTime;

    int lastCopyID = 0;

    /**
     * The constructor is used for restoring object from all existing information about document
     */
    public Document(int id, String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price,
                    int numberOfRequests, ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, int lastCopyID) {
        this.title = title;
        this.authors = authors;
        this.keywords = keywords;
        this.price = price;
        this.numberOfRequests = numberOfRequests;
        this.id = id;
        this.docType = docType;
        this.availableCopies = availableCopies;
        this.takenCopies = takenCopies;
        lastID = lastID < id?id:lastID;
        this.lastCopyID = lastCopyID;
    }

    /**
     * The constructor is used for creating object and fill copies
     */
    public Document(String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price,
                    ArrayList<Copy> copies){
        this(++lastID,title, docType,authors, keywords,price,0, copies, new ArrayList<>(),1);
    }

    /**
     * The constructor is used for creating object without copies
     */
    public Document(String title, String docType, ArrayList<String> authors, ArrayList<String> keywords, int price){
        this(++lastID,title, docType,authors, keywords,price,0, new ArrayList<>(), new ArrayList<>(),1);
    }

    public Document(int id, JSONObject data, Database database){
        this.id = id;
        lastID = lastID < id?id:lastID;
        this.title = data.getString("Title");
        this.authors = new ArrayList<>();
        for(int i = 0; i < data.getJSONArray("Authors").toList().size(); i++ ){
            authors.add(data.getJSONArray("Authors").getString(i));
        }
        this.keywords = new ArrayList<>();
        for(int i = 0; i < data.getJSONArray("Keywords").toList().size(); i++ ){
            keywords.add(data.getJSONArray("Keywords").getString(i));
        }
        this.price = data.getInt("Price");
        this.numberOfRequests = data.getInt("NumberOfRequest");
        this.availableCopies = new ArrayList<>();
        this.takenCopies = new ArrayList<>();
        int copyID;
        String[] keys = new String[0];
        JSONObject availableCopiesObj = data.getJSONObject("AvailableCopies");
        keys = availableCopiesObj.keySet().toArray(keys);
        for (int i = 0; i < availableCopiesObj.length(); i++){
            copyID = Integer.parseInt(keys[i]);
            Copy copy = new Copy(availableCopiesObj.getJSONObject(Integer.toString(copyID)), database);
            availableCopies.add(copy);
            this.lastCopyID = copyID > lastCopyID? copyID:lastCopyID;
        }
        JSONObject takenCopiesObj = data.getJSONObject("TakenCopies");
        keys = takenCopiesObj.keySet().toArray(keys);
        for (int i = 0; i < takenCopiesObj.length(); i++){
            copyID = Integer.parseInt(keys[i]);
            Copy copy = new Copy(takenCopiesObj.getJSONObject(Integer.toString(copyID)), database);
            takenCopies.add(copy);
            this.lastCopyID = copyID > lastCopyID? copyID:lastCopyID;
        }

    }


    public int getID(){
        return id;
    }

    public String getDocType(){
        return docType;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void increaseNumberOfRequest(){
        numberOfRequests++;
    }

    public void decreaseNumberOfRequest(){
        if(numberOfRequests > 0)
            numberOfRequests--;
    }

    public int getNumberOfAllCopies() {
        return getNumberOfAvailableCopies() + getNumberOfTakenCopies();
    }

    public int getNumberOfAvailableCopies(){
        return availableCopies.size();
    }

    public int getNumberOfTakenCopies(){
        return takenCopies.size();
    }

    public void setCopy(int level, int room){
        new Copy(this, level, room);
        lastCopyID++;
    }

    public void setCopy(Copy copy){
        if (copy.getDocumentID() == id){
            availableCopies.add(copy);
            lastCopyID++;
        }
    }

    public void removeCopy(Copy copy){
        if(availableCopies.contains(copy)) availableCopies.remove(copy);
        if(takenCopies.contains(copy)){
            copy.getCheckoutByUser().checkedOutCopies.remove(copy);
            takenCopies.remove(copy);
        }
    }

    public boolean takeCopy(UserCard user){
        if(availableCopies.size() > 0){
            availableCopies.get(0).checkoutBy(user);
            user.checkedOutCopies.add(availableCopies.get(0));
            availableCopies.get(0).checkOutTime = this.getCheckOutTime(user.userType.isHasLongCheckOutPerm());
            takenCopies.add(availableCopies.get(0));
            availableCopies.remove(0);
            return true;
        }else return false;
    }

    public void returnCopy( Copy copy){
        availableCopies.add(copy);
        takenCopies.remove(copy);
        copy.returnCopy();
    }

    public int getCheckOutTime(boolean longCheckOutPermission){
        return checkOutTime;
    }

    public JSONObject serialize(){
        JSONObject data = new JSONObject();
        data.put("Title", title);
        data.put("Authors", authors);
        data.put("Keywords", keywords);
        data.put("Price", price);
        data.put("NumberOfRequest", numberOfRequests);
        JSONObject availableCopiesObj = new JSONObject();
        for(int i = 0; i < availableCopies.size(); i++){
            availableCopiesObj.put(Integer.toString(availableCopies.get(i).getID()), availableCopies.get(i).serialize());
        }
        data.put("AvailableCopies", availableCopiesObj);
        JSONObject takenCopiesObj = new JSONObject();
        for(int i = 0; i < takenCopies.size(); i++){
            takenCopiesObj.put(Integer.toString(takenCopies.get(i).getID()), takenCopies.get(i).serialize());
        }
        data.put("TakenCopies", takenCopiesObj);
        data.put("DocumentType" , docType);
        return data;
    }
}