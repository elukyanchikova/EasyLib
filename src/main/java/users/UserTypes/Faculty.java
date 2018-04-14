package users.UserTypes;

public class Faculty extends Patron {
    public Faculty(){
        super();
        UserType.userTypes.put(getClass().getName(), this);
        hasLongCheckOutPerm=true;
    }
}
