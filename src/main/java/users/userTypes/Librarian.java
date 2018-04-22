package users.userTypes;

import org.json.JSONObject;

public class  Librarian extends users.userTypes.UserType {

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

    public void setPrivileges(JSONObject data){
        if(data.getBoolean("Priv3"))
            setPriv3();
        else if(data.getBoolean("Priv2"))
            setPriv2();
        else if(data.getBoolean("Priv1"))
            setPriv1();
    }

    public void serializePrivileges(JSONObject data){
        if(hasDeletePerm) {
            data.put("Priv3", true);
        }else {
            data.put("Priv3", false);
            if (hasAddPerm) {
                data.put("Priv2", true);
            } else {
                data.put("Priv2", false);
                if (hasAccessPerm && hasModifyPerm) {
                    data.put("Priv1", true);
                }else data.put("Priv1", false);
            }
        }
    }

    public void setPriv1(){
        this.hasAccessPerm = true;
        this.hasModifyPerm = true;
    }
    public void setPriv2(){
       this.setPriv1();
       this.hasAddPerm=true;
    }
    public void setPriv3(){
       this.setPriv2();
       this.hasDeletePerm=true;
    }

}
