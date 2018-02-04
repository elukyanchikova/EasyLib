package users;

public class Guest extends UserType {
    /**
     * UserType which do not have any permission
     */
    public Guest(){
        hasCheckOutPerm = false;
        hasEditPerm = false;
        hasReturnPerm = false;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }
}
