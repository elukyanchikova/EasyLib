package users;

public class Librarian extends UserType {

   public Librarian() {
       super();
       UserType.userTypes.put(getClass().getName(), this);
       hasCheckOutPerm = true;
       hasEditPerm = true;
       hasReturnPerm = true;
       hasCheckOverdueDocPerm = true;
       hasCheckUserInfoPerm = true;
       hasUserPerm = true;
       priority = 1;
    }

}
