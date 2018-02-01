package users;

import com.sun.xml.internal.bind.v2.model.core.ID;

public abstract class UserType {
    boolean hasCheckOverdueDocPerm;
    boolean hasEditPerm; // permissions
    boolean hasCheckOutPerm;
    boolean hasReturnPerm;
    boolean hasLongCheckOutPerm;

    UserType() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }



    }

}
