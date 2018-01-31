import users.Guest;

public class Session {

    Guest authorizedUser;

    public  Session(Guest user){
        this.authorizedUser = user;
    }

    public boolean endSession(){
       if(authorizedUser != null){
            authorizedUser = null;
            return true;
        }else return false;
    }
}
