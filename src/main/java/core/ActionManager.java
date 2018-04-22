package core;

import documents.Copy;
import documents.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import storage.DatabaseManager;
import storage.Filter;
import users.Session;
import users.UserCard;
import users.userTypes.Guest;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Objects;

public class ActionManager {
    public ArrayList<ActionNote> actionNotes= new ArrayList<>();

    public void load(JSONObject data, DatabaseManager databaseManager){
        JSONArray arr = data.getJSONArray("Action");
        for(int i = 0; i < arr.length(); i++){
            actionNotes.add(new ActionNote(arr.getJSONObject(i), databaseManager));
        }
    }


    public void serialize(JSONObject data){
        JSONArray arr = new JSONArray();
        for (ActionNote actionNote : actionNotes) {
            actionNote.serialize(arr);
        }
        data.put("Actions", arr);
    }

    public String getLog(){
        ActionNote[] actions = sort();
        StringBuilder stringBuilder = new StringBuilder();
        for(ActionNote action: actions){
            stringBuilder.append(action.createNote());
        }
        return stringBuilder.toString();
    }

    private ActionNote[] sort(){
        ActionNote[] notes = new ActionNote[actionNotes.size()];
        if(actionNotes.size() > 0) {
            for (int i = 0; i < actionNotes.size(); i++) {
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if (actionNotes.get(i).month > notes[j].month) break;
                    if (actionNotes.get(i).month == notes[j].month && actionNotes.get(i).day > notes[j].day) break;
                }
                j++;
                notes[j] = actionNotes.get(i);
            }
        }
        return notes;
    }

}
