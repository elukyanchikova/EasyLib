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
    public void setModifyPerm(Librarian libraian, Boolean perm){
        libraian.hasModifyPerm = perm;
    }
    public void setAccessPerm(Librarian librarian, Boolean perm){
        librarian.hasAccessPerm = perm;
    }
    public void setDeletePerm(Librarian librarian, Boolean perm){
        librarian.hasDeletePerm = perm;
    }
    public void setAddPerm(Librarian librarian, boolean perm){
        librarian.hasAddPerm = perm;
    }
}
