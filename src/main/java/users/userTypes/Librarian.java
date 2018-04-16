package users.userTypes;

public class Librarian extends users.userTypes.UserType {

   public Librarian() {
       super();
       users.userTypes.UserType.userTypes.put(getClass().getName(), this);
       hasCheckOutPerm = true;
       hasEditPerm = true;
       hasReturnPerm = true;
       hasCheckOverdueDocPerm = true;
       hasCheckUserInfoPerm = true;
       hasUserPerm = true;
       priority = 1;
    }

    public void setPriv1(){
        this.hasAccessPerm=true;
        this.hasModifyPerm = true;
    }
    public void setPriv2(){
       this.setPriv1();
       this.hasAddPerm=true;
    }
    public void setPriv3(){
       this.setPriv2();
       this.hasDeletePerm=true;
    }

}
