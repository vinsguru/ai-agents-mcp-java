import java.io.BufferedReader;
import java.io.FileReader;

public class FileLoader {

    public static String readFile(String path) {
        String data = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;

            while ((line = br.readLine()) != null) {
                data = data + line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}