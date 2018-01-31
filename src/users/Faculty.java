package users;

public class Faculty extends Patron {
    Faculty(){
        hasCheckOutPerm=true;
        hasEditPerm=false;
        hasReturnPerm=true;
        hasLongCheckOutPerm=true;
        hasCheckOverdueDocPerm = false;}
}
