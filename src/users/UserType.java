package users;

public abstract class UserType {
    boolean hasCheckOverdueDocPerm;
    boolean hasEditPerm; // permissions
    boolean hasCheckOutPerm;
    boolean hasReturnPerm;
    boolean hasLongCheckOutPerm;

    public UserType() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }

}
