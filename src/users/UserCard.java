package users;

import documents.Book;

import java.util.ArrayList;

public class UserCard {
    User person;
    int phoneNumb;
    Address addess;
    ArrayList<Book> checkedOutBooks;
    int fine;// штраф за передержку



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


}
