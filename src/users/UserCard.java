package users;

import com.sun.xml.internal.bind.v2.model.core.ID;
import documents.Book;
import documents.Document;

import java.util.ArrayList;

public class UserCard {
    String name;
    String surname;
    ID id;// card number
    UserType userType;
    int phoneNumb;
    Address addess;
    ArrayList<Book> checkedOutBooks;
    ArrayList<Document> requestedDocs;
    int fine;// штраф за передержку

    public Address getAddess() {
        return addess;
    }

    public ArrayList<Book> getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public ID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNumb() {
        return phoneNumb;
    }

    public String getSurname() {
        return surname;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ArrayList<Document> getRequestedDocs() {
        return requestedDocs;
    }

    public int getFine() {
        return fine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddess(Address addess) {
        this.addess = addess;
    }

    public void setCheckedOutBooks(ArrayList<Book> checkedOutBooks) {
        this.checkedOutBooks = checkedOutBooks;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public void setPhoneNumb(int phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setRequestedDocs(ArrayList<Document> requestedDocs) {
        this.requestedDocs = requestedDocs;
    }

    public UserCard(String name, String surname, UserType userType, int phoneNumb ){
        this.name=name;
        this.surname=surname;
        this.userType=userType;
        this.phoneNumb=phoneNumb;
        requestedDocs=null;
    }

    private class Address {
        String street;
        int numb;
        int flat;

        Address(String street, int numb, int flat) {
            this.street = street;
            this.numb = numb;
            this.flat = flat;
        }
    }
    //TODO method calculate ID
}