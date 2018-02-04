package users;

public abstract class UserType {
    boolean hasCheckOverdueDocPerm;
    boolean hasEditPerm; // permissions
    boolean hasCheckOutPerm;
    boolean hasReturnPerm;
    boolean hasLongCheckOutPerm;

    public boolean isHasCheckOutPerm() {
        return hasCheckOutPerm;
    }

    public void setHasCheckOutPerm(boolean hasCheckOutPerm) {
        this.hasCheckOutPerm = hasCheckOutPerm;
    }

    public boolean isHasEditPerm() {
        return hasEditPerm;
    }

    public void setHasEditPerm(boolean hasEditPerm) {
        this.hasEditPerm = hasEditPerm;
    }

    public boolean isHasLongCheckOutPerm() {
        return hasLongCheckOutPerm;
    }

    public void setHasLongCheckOutPerm(boolean hasLongCheckOutPerm) {
        this.hasLongCheckOutPerm = hasLongCheckOutPerm;
    }

    public boolean isHasReturnPerm() {
        return hasReturnPerm;
    }

    public void setHasReturnPerm(boolean hasReturnPerm) {
        this.hasReturnPerm = hasReturnPerm;
    }

    public boolean isHasCheckOverdueDocPerm() {
        return hasCheckOverdueDocPerm;
    }

    public void setHasCheckOverdueDocPerm(boolean hasCheckOverdueDocPerm) {
        this.hasCheckOverdueDocPerm = hasCheckOverdueDocPerm;
    }


    public UserType() {
        hasCheckOutPerm = true;
        hasEditPerm = false;
        hasReturnPerm = true;
        hasLongCheckOutPerm = false;
        hasCheckOverdueDocPerm = false;
    }

}
