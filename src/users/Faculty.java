package users;

public class Faculty extends Patron {
    public Faculty(){
        hasCheckOutPerm=true;
        hasEditPerm=false;
        hasReturnPerm=true;
        hasLongCheckOutPerm=true;
        hasCheckOverdueDocPerm = false;}
}
