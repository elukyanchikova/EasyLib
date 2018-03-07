package users;

public class Session {

    private UserType authorizedUser;

    //TODO: change userCard access.
    public UserCard userCard;
    public int day;
    public int month;
    /**
     * After authorization program creates session
     * which has current authorized user
     */
    public  Session(UserType user, int day, int month){
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

    //TODO: get UserCard instead UserType

    /**
     * @return user of current session
     */
    public UserType getUser(){
        return authorizedUser;
    }
}
