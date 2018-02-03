package users;

public class Student extends Patron {
    public Student() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }
}
