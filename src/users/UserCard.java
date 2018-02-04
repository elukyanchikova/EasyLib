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

    public UserCard(String name, String surname,UserType userType, int phoneNumb ){
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