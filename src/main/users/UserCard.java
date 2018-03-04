package users;


import documents.Copy;
import documents.Document;

import java.util.ArrayList;

public class UserCard {
    private static int lastID = 0;

    public int id;
    public String name;
    public String surname;
    public UserType userType;
    public String phoneNumb;
    public String address;

    public ArrayList<Copy> checkedOutCopies;
    public ArrayList<Document> requestedDocs;
    public int fine;

    //TODO: remove this
    public ArrayList<Document> checkedOutDocs = new ArrayList<>();


    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs){
        this(++lastID, name,surname, userType, phoneNumb, address, checkedOutCopies, requestedDocs);
    }

    public UserCard(int id, String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs){
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

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address){
        this(name, surname, userType, phoneNumb, address, new ArrayList<>(), new ArrayList<>());
    }

    public UserCard(int id,String name, String surname, UserType userType, String phoneNumb, String address){
        this(id, name, surname, userType, phoneNumb, address, new ArrayList<>(), new ArrayList<>());
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public String getSurname() {
        return surname;
    }

    public UserType getUserType() {
        return userType;
    }

    public ArrayList<Document> getRequestedDocs() {
        return requestedDocs;
    }

    public ArrayList<Copy> getCheckedOutCopies(){
        return checkedOutCopies;
    }

    public int getFine() {
        return fine;
    }

}