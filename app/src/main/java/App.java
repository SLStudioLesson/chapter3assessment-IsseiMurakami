import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.recipeapp.datahandler.CSVDataHandler;
import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.datahandler.JSONDataHandler;

public class App {

    public static void main(String[] args) {

        DataHandler dataHandler;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Choose the file format:");
            System.out.println("1. CSV");
            System.out.println("2. JSON");
            System.out.print("Select (1/2): ");
            String choice = reader.readLine();

            if ("1".equals(choice)) {
                dataHandler = new CSVDataHandler();
            } else if ("2".equals(choice)) {
                dataHandler = new JSONDataHandler();
            } else {
                dataHandler = new CSVDataHandler();
            }

            System.out.println("\nCurrent mode: " + dataHandler.getMode() + "\n");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}