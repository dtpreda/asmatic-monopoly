package monopoly.parser;

import com.google.gson.Gson;
import monopoly.models.MonopolyBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoardLoader{
    public BoardLoader(){

    }

    public MonopolyBoard loadBoard(String path) {
        File file = new File(path);
        final Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Read all lines from the file and parse them to json
        final StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            builder.append(line);
        }
        final String jsonStr = builder.toString();
        BoardParser parser = new BoardParser();
        return parser.parse(jsonStr);
    }
}