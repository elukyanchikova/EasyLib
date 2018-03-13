package users;

public class Student extends Patron {

    public Student(){
        super();
        UserType.userTypes.put(getClass().getName(), this);
    }
}
