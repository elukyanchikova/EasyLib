package storage;

import org.json.JSONObject;

import java.io.*;

public class DataLoader {

    private JSONObject jsonData;

    public void start(){

    }

    public void save(){
        try {
            JSONObject root = new JSONObject();
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File("library.json")));
            pw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
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

    public JSONObject get(String key){
        return null;
    }
}
