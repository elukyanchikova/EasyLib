package users;

public class Patron extends User {
    Patron() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }

}
