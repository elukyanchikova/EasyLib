package users;

import users.Guest;

public class Session {

    private UserType authorizedUser;
    //TODO: uaerID
    /**
     * After authorization program creates session
     * which has current authorized user
     */
    public  Session(UserType user){
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

    public UserType getUser(){
        return authorizedUser;
    }
}
