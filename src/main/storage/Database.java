package storage;

import documents.Copy;
import documents.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import users.UserCard;
import users.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private JSONObject jsonData;
    private JSONObject userCardData;

    HashMap<Integer,UserCard> userCards = new HashMap<>();
    HashMap<Integer,Document> documents = new HashMap<>();

    public void load(){
        File file = new File("library.json");
        try {
            if(file.exists()) {

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file)));
                    StringBuilder stringBuilder = new StringBuilder();
                    reader.lines().forEach(stringBuilder::append);
                    this.jsonData = new JSONObject(stringBuilder.toString());
                    this.userCardData = jsonData.getJSONObject("UserCard");
                    reader.close();
                 loadUserCards();

            }else {
                file.createNewFile();
                this.jsonData = new JSONObject();
                this.userCardData = new JSONObject();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method save new state in database
     * Use it to add or edit user card
     * @param userCard that should be saved
     */
    public void saveUserCard(UserCard userCard){
        //Update data of userCard
        userCardData.put(Integer.toString(userCard.getId()), userCard.serialize());
        update();
    }

    private void update(){
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File("library.json")));
            jsonData.put("UserCard", userCardData);
            pw.write(jsonData.toString());
            pw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUserCard(UserCard userCard){
        userCardData.remove(Integer.toString(userCard.getId()));
        userCards.remove(userCard.getId());
        update();
    }

    private void loadUserCards(){
        int id;
        String[] keys = new String[0];
        keys = userCardData.keySet().toArray(keys);
        for (int i = 0; i < keys.length; i++){
            id = Integer.parseInt(keys[i]);
            JSONObject data =  userCardData.getJSONObject(Integer.toString(id));
            UserCard userCard = new UserCard(id, data);
            userCards.put(id, userCard);
        }
    }

    public UserCard getUserCard(int id){
        return userCards.get(id);
    }

}
