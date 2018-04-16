package users.userTypes;

import documents.Book;

public class Admin extends UserType{

    public Admin(){
            UserType.userTypes.put(getClass().getName(), this);
            hasLongCheckOutPerm=true;
        hasCheckOutPerm = true;
       // hasEditPerm = true;
        hasReturnPerm = true;
        hasCheckOverdueDocPerm = true;
        hasCheckUserInfoPerm = true;
        hasUserPerm = true;
//Does it necessary?
        hasAddPerm = true;
        hasModifyPerm = true;
        hasDeletePerm = true;
        hasAccessPerm = true;

        hasEditingLibrarianPerm = true;
        priority = 1;
    }

}
