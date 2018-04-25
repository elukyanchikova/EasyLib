package users;

import users.userTypes.UserType;

public class Session {

    private UserType authorizedUser;

    public UserCard userCard;
    public int day;
    public int month;
    /**
     * After authorization program creates session
     * which has current authorized user
     */
    public  Session(UserType user, int day, int month){
        this.authorizedUser = user;
        if(day < 1 || day > 31) day = 1;
        if(month < 1 || month > 12) month = 12;
        this.month = month;
        int[] months = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        if(months[month-1] < day) {
            day = day - months[month-1];
            this.month++;
        }

        this.day = day;
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



    /**
     * @return user of current session
     */
    public UserType getUser(){
        return authorizedUser;
    }
}
