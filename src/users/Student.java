package users;

public class Student extends Patron {
    Student() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }
}
