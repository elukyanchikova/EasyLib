package users;

public class Librian extends User {

    Librian() {
        hasCheckOutPerm = true;
        hasEditPerm = true;
        hasReturnPerm = true;
        hasLongCheckOutPerm = true;
        hasCheckOverdueDocPerm = true;

    }

}
