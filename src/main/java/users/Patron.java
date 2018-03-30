package users;

public abstract class Patron extends UserType {
    public Patron() {
        hasUserPerm = true;
        hasReturnPerm = true;
    }

}
