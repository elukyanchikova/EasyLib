package users;

public class Librian extends UserType {

   public  Librian() {
        hasCheckOutPerm = true;
        hasEditPerm = true;
        hasReturnPerm = true;
        hasLongCheckOutPerm = true;
        hasCheckOverdueDocPerm = true;

    }

}
