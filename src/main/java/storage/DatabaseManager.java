package storage;

import core.ActionManager;
import core.ActionNote;
import documents.*;
import org.json.JSONObject;
import users.userTypes.Admin;
import users.userTypes.Librarian;
import users.UserCard;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {

    public ActionManager actionManager;
    /**
     * Cached JSON Object that connected with .json file
     */
    private JSONObject jsonData;
    private JSONObject userCardData;
    private JSONObject documentsData;

    public void createAdmin() {
        this.saveUserCard(new UserCard(0, "Admin", "Admin", new Admin(), "None", "None"));
        this.update();
    }

    /**
     * Loaded in database manager objects with access in runtime
     */
    private HashMap<Integer, UserCard> userCards = new HashMap<>();
    private HashMap<Integer, Document> documents = new HashMap<>();

    /**
     * Name of JSON file(database) which manager works with
     */
    private String fileDataName;

    /**
     * Create database manager which work with database(JSON file)
     *
     * @param fileDataName is the name of JSON file(database) which manager works with
     */
    public DatabaseManager(String fileDataName) {
        this.fileDataName = fileDataName;
        load();
    }

    /**
     * Load and parse JSON file which manager works with
     */
    public void load() {
        File file = new File(fileDataName + ".json");
        try {
            if (file.exists()) {

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
                actionManager = new ActionManager();
                actionManager.load(jsonData, this);

                this.saveUserCard(new UserCard(0, "Admin", "Admin", new Admin(), "None", "None"));
                this.update();


            } else {
                if (!file.createNewFile()) {
                    throw new IOException("File is not created");
                }
                this.jsonData = new JSONObject();
                this.userCardData = new JSONObject();
                this.documentsData = new JSONObject();
                this.actionManager = new ActionManager();
                saveUserCard(new UserCard(0, "Admin", "Admin", new Admin(), "None", "None"));
                update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            this.jsonData = new JSONObject();
            this.userCardData = new JSONObject();
            this.documentsData = new JSONObject();
            this.actionManager = new ActionManager();
            saveUserCard(new UserCard(0, "Admin", "Admin", new Admin(), "None", "None"));
            update();
        }
    }

    public void resetDatabase() {
        this.actionManager = new ActionManager();
        this.userCardData = new JSONObject();
        this.documentsData = new JSONObject();
        Document.resetID();
        UserCard.resetID();
        update();
        load();
    }

    public void update() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File(fileDataName + ".json")));
            jsonData.put("UserCard", userCardData);
            jsonData.put("Document", documentsData);
            actionManager.serialize(jsonData);
            pw.write(jsonData.toString());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadDocuments() {
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

    protected void loadUserCards() {
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

    /**
     * This method save new state in database
     * Use it to add or edit user card
     *
     * @param userCard that should be saved
     */
    public void saveUserCard(UserCard userCard) {
        //Update data of userCard
        userCardData.put(Integer.toString(userCard.getId()), userCard.serialize());
        update();
        loadUserCards();
        loadDocuments();
    }

    public void removeUserCard(UserCard userCard) {
        userCardData.remove(Integer.toString(userCard.getId()));
        update();
        loadUserCards();
    }

    public void saveDocuments(Document document) {
        //Update data of userCard
        documentsData.put(Integer.toString(document.getID()), document.serialize());
        update();
        loadDocuments();
    }

    public void removeDocuments(Document document) {
        documentsData.remove(Integer.toString(document.getID()));
        update();
        loadDocuments();
    }

    public UserCard getUserCard(int id) {
        return userCards.get(id);
    }

    public Document getDocuments(int id) {
        return documents.get(id);
    }

    public Integer[] getUserCardsID() {
        return userCards.keySet().toArray(new Integer[0]);
    }

    public Integer[] getDocumentsID() {
        return documents.keySet().toArray(new Integer[0]);
    }

    public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> result = new ArrayList<>();
        Integer[] keys = new Integer[0];
        keys = documents.keySet().toArray(keys);
        for (Integer key : keys) {
            result.add(getDocuments(key));
        }
        return result;
    }

    public ArrayList<UserCard> getAllUsers() {
        ArrayList<UserCard> result = new ArrayList<>();
        Integer[] keys = new Integer[0];
        keys = userCards.keySet().toArray(keys);
        for (Integer key : keys) {
            result.add(getUserCard(key));
        }
        return result;
    }

    public ArrayList<Document> filterDocument(Filter filter) {
        ArrayList<Document> matchDocuments = new ArrayList<>();
        Integer[] keys = getDocumentsID();
        for (int i = 0; i < keys.length; i++) {
            if (filter.filter(documents.get(keys[i]))) {
                matchDocuments.add(documents.get(keys[i]));
            }
        }
        return matchDocuments;
    }


    public void rebalanceForReferenceType(Document doc) {
        DatabaseManager databaseManager = this;
        if (Book.class.isAssignableFrom(doc.getClass())) {
            if (doc.getNumberOfAvailableCopies() == 1) {
                doc.isReference = true;
            }
        } else if (AVMaterial.class.isAssignableFrom(doc.getClass())) {
            if (doc.getNumberOfAvailableCopies() == 0) {
                doc.isReference = true;
            }
        } else if (JournalArticle.class.isAssignableFrom(doc.getClass())) {
            if (doc.getNumberOfAvailableCopies() == 1) {
                doc.isReference = true;
            }
        }
        databaseManager.saveDocuments(doc);
    }
}
