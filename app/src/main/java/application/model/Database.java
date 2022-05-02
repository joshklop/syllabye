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

/**
 * Used to keep the database, file, and the syllabye HashMap
 * @author
 */
public class Database {
    private static File file;
    private HashMap<String, Syllabus> syllabye;

    /**
     * Sets the file and calls resetSyllabye
     * @param resource used to set the file of the specific URL given by resource
     * @throws FileNotFoundException
     */
    public Database(URL resource) throws FileNotFoundException {
        setFile(resource);
        resetSyllabye();
    }

    /**
     * Adds Syllabus s to the HashMap syllabye
     * @param s is added along with its key into the HashMap syllabye
     */
    public void add(Syllabus s) {
        syllabye.put(Database.computeKey(s), s);
    }
    
    /**
     * Removes the specific key from the HashMap syllabye
     * @param key is removed from the HashMap syllabye
     */
    public void delete(String key) {
    	syllabye.remove(key);
    }

    /**
     * Reads the the file and pulls a resulting HashMap and calls setSyllabye with result
     * @throws FileNotFoundException if there is an exception of the matching type	
     * @throws IOException if there is an exception of the matching type
     * @throws ClassNotFoundException if there is an exception of the matching type
     */
    public void readSyllabye() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getPath()));
        HashMap<String, Syllabus> result = (HashMap<String, Syllabus>)ois.readObject();
        setSyllabye(result);
    }
    
    /**
     * Writes the given object syllabye to oos
     * @throws IOException if there is an exception of the matching type
     */
    public void writeSyllabye() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getPath()));
        oos.writeObject(syllabye);
    }
    
    /**
     * Sets the parameter file to a given file parameter f if f is a file
     * @param resource is used to set up the parameter f
     * @throws FileNotFoundException if there is an exception of the matching type
     */
    public static void setFile(URL resource) throws FileNotFoundException {
        File f = new File(resource.getPath());
        if (!f.isFile())
            throw new FileNotFoundException();
        file = f;
    }

    /**
     * Returns the current file
     * @return file
     */
    public static File getFile() {
        return file;
    }

    /**
     * Returns the current HashMap of syllabye
     * @return syllabye
     */
    public HashMap<String, Syllabus> getSyllabye() {
        return syllabye;
    }

    /**
     * Sets the given parameter syllabye to the syllabye in Database
     * @param syllabye is used to set the Database syllabye to the parameter syllabye
     */
    public void setSyllabye(HashMap<String, Syllabus> syllabye) {
        this.syllabye = syllabye;
    }
    
    /**
     * Sets a syllabye with a new HasMap
     */
    public void resetSyllabye() {
        setSyllabye(new HashMap<String, Syllabus>());
    }

    /**
     * Returns the key of the given Syllabus
     * @param s is used to get the key of the given syllabus
     * @return the key of the given syllabus
     */
    public static String computeKey(Syllabus s) {
        return s.getCourseSubject() + s.getCourseNumber() + s.getSemester() + s.getYear() + s.isRecitation();
    }
}
