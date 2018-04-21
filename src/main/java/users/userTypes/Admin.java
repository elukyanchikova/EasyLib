package users.userTypes;

import documents.Book;

public class Admin extends UserType{

    public Admin(){
            UserType.userTypes.put(getClass().getName(), this);
            hasLongCheckOutPerm=true;
        hasCheckOutPerm = true;
        hasReturnPerm = true;
        hasCheckOverdueDocPerm = true;
        hasCheckUserInfoPerm = true;
        hasUserPerm = true;
        hasEditingLibrarianPerm = true;
        priority = 1;
    }

}
