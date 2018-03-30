package documents;

import org.json.JSONObject;
import storage.DatabaseManager;
import users.Session;
import users.UserCard;
import users.UserType;

import javax.jws.soap.SOAPBinding;
import javax.print.Doc;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class Document {

    protected static int lastID = 0;

    protected int id;
    public String title;
    protected String docType;
    public ArrayList<String> authors;
    public ArrayList<String> keywords;
    public int price;

    protected int numberOfRequests = 0;
    public ArrayList<Copy> availableCopies;
    public ArrayList<Copy> takenCopies;
    public ArrayList<Copy> bookedCopies;

    protected int checkOutTime;

    Comparator<UserCard> comparator = new UserTypeComparator();
    public PriorityQueue<UserCard> requestedBy = new PriorityQueue<UserCard>(10,comparator);

    boolean reference = false;
    int lastCopyID = 0;

    /**
     * The constructor is used for restoring object from all existing information about document
     */
    public Document(int id,
                    String title, String docType,
                    ArrayList<String> authors, ArrayList<String> keywords,
                    int price, int numberOfRequests,
                    ArrayList<Copy> availableCopies, ArrayList<Copy> takenCopies, ArrayList<Copy> bookedCopies,
                    int lastCopyID) {
        this.title = title;
        this.authors = authors;
        this.keywords = keywords;
        this.price = price;
        this.numberOfRequests = numberOfRequests;
        this.id = id;
        this.docType = docType;
        this.availableCopies = availableCopies;
        this.takenCopies = takenCopies;
        this.bookedCopies = bookedCopies;
        lastID = lastID < id?id:lastID;
        this.lastCopyID = lastCopyID;
    }

    /**
     * The constructor is used for creating object and fill copies
     */
    public Document(String title, String docType,
                    ArrayList<String> authors, ArrayList<String> keywords,
                    int price,
                    ArrayList<Copy> copies){
        this(++lastID,title, docType,authors, keywords,price,0,
                copies, new ArrayList<>(), new ArrayList<>(),1);
    }

    /**
     * The constructor is used for creating object without copies
     */
    public Document(String title, String docType,
                    ArrayList<String> authors, ArrayList<String> keywords,
                    int price){
        this(++lastID, title, docType, authors, keywords, price, 0,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),1);
    }

    /**
     * The constructor is used for creating object using information from JSON File
     * @param id of document
     * @param data is JSON object which stores information about JSON
     * @param databaseManager is the current database manager
     */
    public Document(int id, JSONObject data, DatabaseManager databaseManager){
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
        this.reference = data.getBoolean("Reference");
        this.price = data.getInt("Price");
        this.numberOfRequests = data.getInt("NumberOfRequest");
        this.availableCopies = new ArrayList<>();
        this.takenCopies = new ArrayList<>();
        this.bookedCopies = new ArrayList<>();
        int copyID;
        String[] keys = new String[0];
        JSONObject availableCopiesObj = data.getJSONObject("AvailableCopies");
        keys = availableCopiesObj.keySet().toArray(keys);
        for (int i = 0; i < availableCopiesObj.length(); i++){
            copyID = Integer.parseInt(keys[i]);
            Copy copy = new Copy(availableCopiesObj.getJSONObject(Integer.toString(copyID)), databaseManager);
            availableCopies.add(copy);
            this.lastCopyID = copyID > lastCopyID? copyID:lastCopyID;
        }
        JSONObject takenCopiesObj = data.getJSONObject("TakenCopies");
        keys = takenCopiesObj.keySet().toArray(keys);
        for (int i = 0; i < takenCopiesObj.length(); i++){
            copyID = Integer.parseInt(keys[i]);
            Copy copy = new Copy(takenCopiesObj.getJSONObject(Integer.toString(copyID)), databaseManager);
            takenCopies.add(copy);
            this.lastCopyID = copyID > lastCopyID? copyID:lastCopyID;
        }
        JSONObject bookedCopiesObj = data.getJSONObject("BookedCopies");
        keys = bookedCopiesObj.keySet().toArray(keys);
        for (int i = 0; i < bookedCopiesObj.length(); i++){
            copyID = Integer.parseInt(keys[i]);
            Copy copy = new Copy(bookedCopiesObj.getJSONObject(Integer.toString(copyID)), databaseManager);
            bookedCopies.add(copy);
            this.lastCopyID = copyID > lastCopyID? copyID:lastCopyID;
        }
    }

    public int getID(){
        return id;
    }

    public String getDocType(){
        return docType;
    }

    public void putInPQ(UserCard user, DatabaseManager databaseManager){
       this.requestedBy.add(user);
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

    public void setReference(boolean isRefernce){
        this.reference = isRefernce;
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

    public boolean takeCopy(UserCard user, Session session){
        if(availableCopies.size() > 0){
            availableCopies.get(0).checkoutBy(user);
            availableCopies.get(0).checkOutTime = this.getCheckOutTime(user.userType.isHasLongCheckOutPerm());
            availableCopies.get(0).checkOutDay = session.day;
            availableCopies.get(0).checkOutMonth = session.month;
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
        JSONObject bookedCopiesObj = new JSONObject();
        for(int i = 0; i < bookedCopies.size(); i++){
            takenCopiesObj.put(Integer.toString(bookedCopies.get(i).getID()), bookedCopies.get(i).serialize());
        }
        data.put("BookedCopies", bookedCopiesObj);
        data.put("DocumentType" , docType);
        data.put("Reference", reference);
        return data;
    }

    public static void resetID(){
        lastID = 0;
    }

    public boolean isReference(){
        return reference;
    }

    protected class UserTypeComparator implements Comparator<UserCard> {
        @Override
        public int compare(UserCard x, UserCard y) {
            if (x.userType.priority < y.userType.priority)
            {
                return -1;
            }
            if (x.userType.priority > y.userType.priority)
            {
                return 1;
            }
            return 0;
        }
    }

    public void deletePQ(){
        PriorityQueue<UserCard> newPQ = new PriorityQueue<>();
        this.requestedBy = newPQ;
    }
}