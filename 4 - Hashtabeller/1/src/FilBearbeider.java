import java.io.*;
import java.util.ArrayList;

public class FilBearbeider {

    static String[] bearbeid(String veiTilFil) {
        ArrayList<String> resultat = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(veiTilFil)));
            String line;
            while ((line = br.readLine()) != null) {
                resultat.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] kopi = new String[resultat.size()];
        return resultat.toArray(kopi);
    }
}