package storage;

import org.json.JSONObject;

import java.io.*;

public class DataManager {


    public final int ID_USERCARD = 0;
    public final int ID_DOCUMENT = 1;

    /**
     * DATA LOADING AND SAVING
     * */
    private JSONObject jsonData;
    private UserCardManager userCardManager;
    private DocumentsManager documentsManager;

    public void start(){
        userCardManager = new UserCardManager(this);
        documentsManager = new DocumentsManager();
    }

    public void modify(int managerID){
        switch (managerID){
            case ID_USERCARD: break;
            case ID_DOCUMENT: break;
            default: break;
        }
    }

    public void save(){
        /*try {
            JSONObject root = new JSONObject();
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File("library.json")));
            pw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }*/
        jsonData.put(userCardManager.ID, userCardManager.getData());
    }

    public void update(){

    }

    private void load(){
        File file = new File("library.json");
        if(file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file)));
                StringBuilder stringBuilder = new StringBuilder();
                reader.lines().forEach(stringBuilder::append);
                this.jsonData = new JSONObject(stringBuilder.toString());
                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    JSONObject get(String key){
        return jsonData.optJSONObject(key);
    }


}
