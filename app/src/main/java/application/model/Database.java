package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Database {
    private static File file;
    private static HashMap<String, Syllabus> syllabye;

    public Database(String resourceName) throws FileNotFoundException {
        setFile(resourceName);
        setSyllabye(new HashMap<String, Syllabus>());
    }

    public void add(Syllabus s) {
        syllabye.put(s.getCourseSubject() + s.getCourseNumber() + s.getSemester() + s.getYear(), s);
    }

    public void read() {
        // TODO
    }

    public void write() {
        BufferedWriter bw = null;
        try { 
            bw = new BufferedWriter(new FileWriter(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Type syllabyeType = new TypeToken<HashMap<String, Syllabus>>() {}.getType();
        try {
            bw.write((new Gson()).toJson(syllabye, syllabyeType).toString()); 
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void setFile(String resourceName) throws FileNotFoundException {
        File f = new File(resourceName);
        if (!f.isFile())
            throw new FileNotFoundException();
        file = f;
    }

    public static File getFile() {
        return file;
    }

    public static HashMap<String, Syllabus> getSyllabye() {
        return syllabye;
    }

    public static void setSyllabye(HashMap<String, Syllabus> syllabye) {
        Database.syllabye = syllabye;
    }
}
