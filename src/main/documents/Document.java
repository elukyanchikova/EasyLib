package documents;

import users.UserCard;

import java.util.ArrayList;

public abstract class Document {

    protected static int lastID = 0;

    protected int id;
    public String title;
    public String docType;
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


    public int getID(){
        return id;
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
        setCopy(new Copy(this, level, room));
    }

    public void setCopy(Copy copy){
        if (copy.getDocument() == this){
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

    public int getCheckOutTime(boolean longCheckOutPermission){
        return checkOutTime;
    }
}