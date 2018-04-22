package users.userTypes;


import org.json.JSONObject;

public class Librarian extends users.userTypes.UserType {

    public Librarian() {
        super();
        users.userTypes.UserType.userTypes.put(getClass().getName(), this);
        hasCheckOutPerm = true;
        hasEditPerm = true;
        hasReturnPerm = true;
        hasCheckOverdueDocPerm = true;
        hasCheckUserInfoPerm = true;
        hasUserPerm = true;
        priority = 1;
    }

    public void setPrivileges(JSONObject data) {
        if (data.getBoolean("Priv3"))
            setPriv3();
        else if (data.getBoolean("Priv2"))
            setPriv2();
        else if (data.getBoolean("Priv1"))
            setPriv1();
        else setDefault();
    }

    public void serializePrivileges(JSONObject data) {
        if (hasDeletePerm) {
            data.put("Priv3", true);
        } else {
            data.put("Priv3", false);
            if (hasAddPerm) {
                data.put("Priv2", true);
            } else {
                data.put("Priv2", false);
                if (hasAccessPerm && hasModifyPerm) {
                    data.put("Priv1", true);
                } else data.put("Priv1", false);
            }
        }
    }

    /**
     * method for setting accessPermission and ModifyPermission to the librarian
     */
    public void setDefault(){
        this.hasAddPerm = false;
        this.hasAccessPerm = false;
        this.hasModifyPerm = false;
        this.hasDeletePerm = false;
    }

    public void setPriv1() {
        this.hasAddPerm = false;
        this.hasAccessPerm = true;
        this.hasModifyPerm = true;
        this.hasDeletePerm = false;
    }

    /**
     * method for setting accessPermission, ModifyPermission, addPermission to the librarian
     */
    public void setPriv2() {
        this.hasAccessPerm = true;
        this.hasModifyPerm = true;
        this.hasAddPerm = true;
        this.hasDeletePerm = false;
    }

    /**
     * method for setting accessPermission, ModifyPermission, addPermission, deletePermission to the librarian
     */
    public void setPriv3() {
        this.hasAccessPerm = true;
        this.hasModifyPerm = true;
        this.hasAddPerm = true;
        this.hasDeletePerm = true;
    }

    /**
     * method for resetting accessPermission, ModifyPermission, addPermission, deletePermission to the librarian
     */
    public void resetPriv1() {
        this.resetPriv2();
        this.hasAccessPerm = false;
        this.hasModifyPerm = false;
    }

    /**
     * method for resetting addPermission, deletePermission to the librarian
     */
    public void resetPriv2() {
        this.resetPriv3();
        this.hasAddPerm = false;
    }

    /**
     * method for resetting deletePermission to the librarian
     */
    public void resetPriv3() {
        this.hasDeletePerm = false;
    }

}
