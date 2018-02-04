package users;

public abstract class Patron extends UserType {
    public Patron() {
        hasCheckOutPerm = true;
        hasReturnPerm = true;
    }

}
