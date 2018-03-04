package users;

import java.util.HashMap;

public abstract class UserType {
    //Permissions of the user
    boolean hasCheckOverdueDocPerm = false;
    boolean hasEditPerm = false;
    boolean hasCheckOutPerm = false;
    boolean hasReturnPerm = false;
    boolean hasLongCheckOutPerm = false;

    public boolean isHasCheckOutPerm() {
        return hasCheckOutPerm;
    }

    public boolean isHasEditPerm() {
        return hasEditPerm;
    }

    public boolean isHasLongCheckOutPerm() {
        return hasLongCheckOutPerm;
    }

    public boolean isHasReturnPerm() {
        return hasReturnPerm;
    }

    public boolean isHasCheckOverdueDocPerm() {
        return hasCheckOverdueDocPerm;
    }

    public static HashMap<String, UserType> userTypes = new HashMap<>();

}
