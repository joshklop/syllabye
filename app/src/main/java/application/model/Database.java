package application.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;

public class Database {
    private static File file;
    private static Type syllabyeType = new TypeToken<HashMap<String, Syllabus>>() {}.getType();
    private static Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalTime.class, new LocalTimeConverter())
        .setPrettyPrinting()
        .create();
    private HashMap<String, Syllabus> syllabye;

    public Database(URL resource) throws FileNotFoundException {
        setFile(resource);
        resetSyllabye();
    }

    public void add(Syllabus s) {
        syllabye.put(s.getCourseSubject() + s.getCourseNumber() + s.getSemester() + s.getYear(), s);
    }

    public void readSyllabye() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(getFile().getPath()));
        String result = br.readLine();
        br.close();
        setSyllabye(gson.fromJson(result, syllabyeType));
    }

    public void writeSyllabye() throws IOException {
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(file.getPath()));
        bw.write(gson.toJson(syllabye, syllabyeType).toString());
        bw.close();
    }

    public static void setFile(URL resource) throws FileNotFoundException {
        File f = new File(resource.getPath());
        if (!f.isFile())
            throw new FileNotFoundException();
        file = f;
    }

    public static File getFile() {
        return file;
    }

    public HashMap<String, Syllabus> getSyllabye() {
        return syllabye;
    }

    public void setSyllabye(HashMap<String, Syllabus> syllabye) {
        this.syllabye = syllabye;
    }

    public void resetSyllabye() {
        setSyllabye(new HashMap<String, Syllabus>());
    }
}
