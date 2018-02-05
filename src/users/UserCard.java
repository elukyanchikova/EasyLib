package users;

import documents.Copy;
import documents.Document;

import java.util.ArrayList;

public class UserCard {
    static int lastID = 0;
    String name;
    String surname;
    int id;
    UserType userType;
    String phoneNumb;
    String address;

    public ArrayList<Copy> checkedOutDocs;

    public ArrayList<Document> requestedDocs;

    int fine;

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutDocs, ArrayList<Document> requestedDocs, int fine){
        this.name=name;
        this.surname=surname;
        this.userType=userType;
        this.phoneNumb=phoneNumb;
        this.address = address;
        this.checkedOutDocs = checkedOutDocs;
        this.requestedDocs = requestedDocs;
        this.fine = fine;
        this.id = lastID++;
    }

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutDocs, ArrayList<Document> requestedDocs){
        this(name, surname, userType, phoneNumb, address, checkedOutDocs, requestedDocs, 0);
    }
    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address){
        this(name, surname, userType, phoneNumb, address, new ArrayList<Copy>(), new ArrayList<Document>(), 0);
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

    public ArrayList<Copy> getCheckedOutDocs(){
        return checkedOutDocs;
    }

    public int getFine() {
        return fine;
    }

}