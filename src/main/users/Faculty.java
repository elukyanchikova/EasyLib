package users;

public class Faculty extends Patron {
    public Faculty(){
        hasCheckOutPerm=true;
        hasReturnPerm=true;
        hasLongCheckOutPerm=true;
    }
}
