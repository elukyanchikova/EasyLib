package users.userTypes;

public class Admin extends UserType{

    public Admin(){
            UserType.userTypes.put(getClass().getName(), this);
            hasLongCheckOutPerm=true;

    }
}
