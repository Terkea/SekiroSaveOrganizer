package app.model;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SavefileJSON {

    private static final String JSONFILEPATH = "savefiles.json";

    public void addSavefile(Savefile sf){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSONFILEPATH);
        try {
            List<Savefile> tests = mapper.readValue(file, new TypeReference<List<Savefile>>(){});
            tests.add(sf);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Savefile> getAllSavefiles(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSONFILEPATH);
        try {
            List<Savefile> serverfiles = mapper.readValue(file, new TypeReference<ArrayList<Savefile>>(){});
            return FXCollections.observableList(serverfiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Savefile getSavefile(String name){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSONFILEPATH);
        try {
            List<Savefile> serverfiles = mapper.readValue(file, new TypeReference<List<Savefile>>(){});
            for (Savefile entry : serverfiles){
                if (entry.getName().equals(name)){
                    Savefile sf = new Savefile();
                    sf.setName(entry.getName());
                    sf.setDate(entry.getDate());
                    return sf;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean savefileUnique(String name){
        Savefile test = getSavefile(name);
        if (test == null){
            return true;
        }
        return false;
    }

    public static void deleteSavefile(String name) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(JSONFILEPATH));

        Iterator<JsonNode> nodes = jsonNode.elements();
        while (nodes.hasNext()) {
            if (nodes.next().get("name").textValue().equals(name)) {
                nodes.remove();
            }
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSONFILEPATH), jsonNode);

    }
}
