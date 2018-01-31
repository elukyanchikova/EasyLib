package users;

public abstract class User {
    Id person;
    boolean hasCheckOverdueDocPerm;
    boolean hasEditPerm; // permissions
    boolean hasCheckOutPerm;
    boolean hasReturnPerm;
    boolean hasLongCheckOutPerm;

    User() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }

    private class Id {
        String name;
        String surname;

        Id(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

    }

}
