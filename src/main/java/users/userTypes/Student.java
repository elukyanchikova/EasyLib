package users.userTypes;

public class Student extends Patron {

    public Student(){
        super();
        UserType.userTypes.put(getClass().getName(), this);
        priority =6;
    }
}
