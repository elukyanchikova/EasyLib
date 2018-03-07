package storage;

import documents.*;
import org.json.JSONObject;
import users.UserCard;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private JSONObject jsonData;
    private JSONObject userCardData;
    private JSONObject documentsData;

    HashMap<Integer,UserCard> userCards = new HashMap<>();
    HashMap<Integer,Document> documents = new HashMap<>();

    private String fileDataName;
    public Database(String fileDataName){
        this.fileDataName  = fileDataName;
        load();
    }

    public void load(){
        File file = new File(fileDataName +".json");
        try {
            if(file.exists()) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file)));
                StringBuilder stringBuilder = new StringBuilder();
                reader.lines().forEach(stringBuilder::append);
                this.jsonData = new JSONObject(stringBuilder.toString());
                this.userCardData = jsonData.getJSONObject("UserCard");
                this.documentsData = jsonData.getJSONObject("Document");
                reader.close();
                loadUserCards();
                loadDocuments();

            }else {
                if(!file.createNewFile()){
                    throw new IOException("File is not created");
                }
                this.jsonData = new JSONObject();
                this.userCardData = new JSONObject();
                this.documentsData = new JSONObject();
                update();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void update(){
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File(fileDataName + ".json")));
            jsonData.put("UserCard", userCardData);
            jsonData.put("Document", documentsData);
            pw.write(jsonData.toString());
            pw.close();
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
        loadUserCards();
    }

    public void removeUserCard(UserCard userCard){
        userCardData.remove(Integer.toString(userCard.getId()));
        update();
        loadUserCards();
    }

    private void loadUserCards(){
        userCards.clear();
        int id;
        String[] keys = new String[0];
        keys = userCardData.keySet().toArray(keys);
        for (String key : keys) {
            id = Integer.parseInt(key);
            JSONObject data = userCardData.getJSONObject(Integer.toString(id));
            UserCard userCard = new UserCard(id, data);
            userCards.put(id, userCard);
        }
    }

    public UserCard getUserCard(int id){
        return userCards.get(id);
    }

    public void resetDatabase(){
        this.userCardData = new JSONObject();
        this.documentsData = new JSONObject();
        update();
        load();
    }

    public Integer[] getUsercardsID(){
        return userCards.keySet().toArray(new Integer[0]);
    }


    public void saveDocuments(Document document){
        //Update data of userCard
        documentsData.put(Integer.toString(document.getID()), document.serialize());
        update();
        loadDocuments();
    }

    public void removeDocuments(Document document){
        documentsData.remove(Integer.toString(document.getID()));
        update();
        loadDocuments();
    }

    private void loadDocuments(){
        documents.clear();
        int id;
        String[] keys = new String[0];
        keys = documentsData.keySet().toArray(keys);
        for (String key : keys) {
            id = Integer.parseInt(key);
            JSONObject data = documentsData.getJSONObject(Integer.toString(id));
            Document document = null;
            switch (data.getString("DocumentType")) {
                case "Book":
                    document = new Book(id, data, this);
                    break;
                case "Journal Article":
                    document = new JournalArticle(id, data, this);
                    break;
                case "AV material":
                    document = new AVMaterial(id, data, this);
                    break;
            }
            documents.put(id, document);
        }
    }

    public Document getDocuments(int id){
        return documents.get(id);
    }

    public Integer[] getDocumentsID(){
        return documents.keySet().toArray(new Integer[0]);
    }

    public ArrayList<Document> getAllDocuments(){
        ArrayList<Document> result = new ArrayList<>();
        Integer[] keys = new Integer[0];
        keys = documents.keySet().toArray(keys);
        for (Integer key : keys) {
            result.add(getDocuments(key));
        }
        return result;
    }

    public ArrayList<UserCard> getAllUsers(){
        ArrayList<UserCard> result = new ArrayList<>();
        Integer[] keys = new Integer[0];
        keys = userCards.keySet().toArray(keys);
        for (Integer key : keys) {
            result.add(getUserCard(key));
        }
        return result;
    }
}
