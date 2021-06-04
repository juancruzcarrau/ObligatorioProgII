import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Read {

    public static void main(String args[]) {
        String path = "../../dataset/";
        String line = "";

        try {
            BufferedReader br = new BufferedReader( new FileReader("dataset/IMDb movies.csv"));
//            int iteration = 0;
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
//                if (iteration == 1) {
//                    for (int i = 0; i < values.length; i++) {
//                        System.out.println(values[i]);;
//                    }
//                    break;
//                }
//                iteration++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



}
