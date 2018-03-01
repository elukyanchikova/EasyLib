package storage;

import org.json.JSONObject;
import users.UserCard;

import java.util.ArrayList;

class UserCardManager {

    final String ID = "user_card";
    ArrayList<UserCard> userCards = new ArrayList<>();

    JSONObject data;
    UserCardManager(DataManager dataManager){
        data = dataManager.get(ID);
    }

    JSONObject getData(){
        return data;
    }

    void modify(int objectID){

    }
}
