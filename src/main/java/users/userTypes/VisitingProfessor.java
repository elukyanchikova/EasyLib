package users.userTypes;

public class VisitingProfessor extends Patron {
    public VisitingProfessor() {
        super();
        UserType.userTypes.put(getClass().getName(), this);
        hasMultiRenewPerm = true;
        hasLowerCheckOut = true;
        priority = 3;
    }
}
