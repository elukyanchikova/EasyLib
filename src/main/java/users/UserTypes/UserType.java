package users.UserTypes;

import java.util.HashMap;

public abstract class UserType {
    //Permissions of the user
    boolean hasCheckOverdueDocPerm = false;
    boolean hasEditPerm = false;
    boolean hasCheckOutPerm = false;
    boolean hasReturnPerm = false;
    boolean hasLongCheckOutPerm = false;
    boolean hasCheckUserInfoPerm = false;
    boolean hasMultiRenewPerm = false;
    boolean hasLowerCheckOut = false;

    boolean hasUserPerm = false;

    public  int priority = -1;

    public static void load(){
        UserType.userTypes.put("Faculty", new Faculty());
        UserType.userTypes.put("Student", new Student());
        UserType.userTypes.put("Librarian", new Librarian());
        UserType.userTypes.put("Guest", new Guest());
        UserType.userTypes.put("Professor", new Professor());
        UserType.userTypes.put("VisitingProfessor", new VisitingProfessor());
        UserType.userTypes.put("TA", new TA());
        UserType.userTypes.put("Instructor", new Instructor());
        UserType.userTypes.put("users.UserTypes.Professor", new Professor());
        UserType.userTypes.put("users.UserTypes.VisitingProfessor", new VisitingProfessor());
        UserType.userTypes.put("users.UserTypes.TA", new TA());
        UserType.userTypes.put("users.UserTypes.Instructor", new Instructor());
        UserType.userTypes.put("users.UserTypes.Faculty", new Faculty());
        UserType.userTypes.put("users.UserTypes.Student", new Student());
        UserType.userTypes.put("users.UserTypes.Librarian", new Librarian());
        UserType.userTypes.put("users.UserTypes.Guest", new Guest());
    }

    public boolean isHasCheckOutPerm() {
        return hasCheckOutPerm;
    }

    public boolean isHasEditPerm() {
        return hasEditPerm;
    }

    public boolean isHasLongCheckOutPerm() {
        return hasLongCheckOutPerm;
    }

    public boolean isHasReturnPerm() {
        return hasReturnPerm;
    }

    public boolean isHasCheckOverdueDocPerm() {
        return hasCheckOverdueDocPerm;
    }

    public boolean isHasCheckUserInfoPerm(){
        return hasCheckUserInfoPerm;
    }

    public boolean isHasMultiRenewPerm(){ return hasMultiRenewPerm;}

    public boolean isHasUserPerm() { return  hasUserPerm; }

    public boolean isHasLowerCheckOut(){ return hasLowerCheckOut;}

    public static HashMap<String, UserType> userTypes = new HashMap<>();

}