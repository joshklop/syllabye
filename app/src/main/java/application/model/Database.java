package application.model;

import com.google.gson.Gson;
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
import java.util.HashMap;

public class Database {
    private static File file;
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
        Type syllabyeType = new TypeToken<HashMap<String, Syllabus>>() {}.getType();
        setSyllabye((new Gson()).fromJson(result, syllabyeType));
    }

    public void writeSyllabye() throws IOException {
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(file.getPath()));
        // TODO Should we make the syllabye TypeToken a static member of the class with a getter?
        // It gets reused in many places...
        Type syllabyeType = new TypeToken<HashMap<String, Syllabus>>() {}.getType();
        bw.write((new Gson()).toJson(syllabye, syllabyeType).toString()); 
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
