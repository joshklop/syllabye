package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public void readSyllabye() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getPath()));
        HashMap<String, Syllabus> result = (HashMap<String, Syllabus>)ois.readObject(); // down-casting object
        setSyllabye(result);
    }

    public void writeSyllabye() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getPath()));
        oos.writeObject(syllabye);
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
