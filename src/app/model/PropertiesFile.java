package app.model;

import java.io.*;
import java.util.Properties;

public class PropertiesFile {

    public static Properties prop = new Properties();

    public void saveProperty(String name, String value){
        try {
            prop.setProperty(name, value);
            InputStream file = getClass().getResourceAsStream("config.terkea");
            prop.store(new FileOutputStream("config.terkea"), null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePropety(String name){
        try {
            prop.remove(name);
            InputStream file = getClass().getResourceAsStream("config.terkea");
            prop.store(new FileOutputStream("config.terkea"), null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProp(String name){
        try {
            prop.load(new FileInputStream("config.terkea"));
            String value = prop.getProperty(name);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
