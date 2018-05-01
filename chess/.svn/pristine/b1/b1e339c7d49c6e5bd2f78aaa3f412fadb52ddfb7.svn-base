import java.io.*;
import java.util.ArrayList;

/**
 * Created by Denver on 2/10/2016.
 */
public class FileLoader {

    ArrayList<String> commands = new ArrayList<>();
    public void LoadFile(String fileName){
        try (InputStream in = new FileInputStream(fileName))
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
               commands.add(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        int s = 2+4;
    }
}
