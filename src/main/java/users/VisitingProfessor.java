package users;

public class VisitingProfessor extends Patron {
    public VisitingProfessor() {
        super();
        UserType.userTypes.put(getClass().getName(), this);
        hasMultiRenewPerm = true;
        priority = 3;
    }
}
