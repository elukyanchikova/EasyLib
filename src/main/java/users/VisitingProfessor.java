package users;

public class VisitingProfessor extends Patron {
    public VisitingProfessor() {
        super();
        UserType.userTypes.put(getClass().getName(), this);
        hasCheckOutPerm = true;
        hasReturnPerm = true;
        hasLongCheckOutPerm = true;
        hasMultiRenewPerm = true;
    }
}
