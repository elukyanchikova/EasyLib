import users.Guest;

public class Session {

    Guest authorizedUser;

    /**
     * After authorization program creates session
     * which has current authorized user
     */
    public  Session(Guest user){
        this.authorizedUser = user;
    }

    /**
     * If user want to log out, then session will reset authorized user
     */
    public boolean endSession(){
       if(authorizedUser != null){
            authorizedUser = null;
            return true;
        }else return false;
    }
}
