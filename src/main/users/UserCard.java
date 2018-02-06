package users;


import documents.Copy;
import documents.Document;

import java.util.ArrayList;

public class UserCard {
    static int lastID = 0;
    private String name;
    private String surname;
    private int id;
    private UserType userType;
    private String phoneNumb;
    private String address;

    public ArrayList<Copy> checkedOutCopies;
    public ArrayList<Document> checkedOutDocs = new ArrayList<>();

    public ArrayList<Document> requestedDocs;

    private int fine;

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> checkedOutCopies, ArrayList<Document> requestedDocs, int fine){
        this.name=name;
        this.surname=surname;
        this.userType=userType;
        this.phoneNumb=phoneNumb;
        this.address = address;
        this.checkedOutCopies = checkedOutCopies;
        this.requestedDocs = requestedDocs;
        this.fine = fine;
        this.id = lastID++;
    }

    public UserCard(String name, String surname, UserType userType, String phoneNumb, String address,
                    ArrayList<Copy> Copies, ArrayList<Document> requestedDocs){
        this(name, surname, userType, phoneNumb, address, Copies, requestedDocs, 0);
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

    public ArrayList<Copy> getCheckedOutCopies(){
        return checkedOutCopies;
    }

    public int getFine() {
        return fine;
    }

}