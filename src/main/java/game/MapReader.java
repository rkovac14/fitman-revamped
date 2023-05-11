package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapReader {
    BufferedReader reader;
    private int rows;
    private int cols;

    /**
     * Read a map file
     * @param fileLocation Map file location
     */
    public MapReader(String fileLocation){
        try {
            this.reader = new BufferedReader(new FileReader(fileLocation));
            String[] dimensions = reader.readLine().split(" ");
            this.rows = Integer.parseInt(dimensions[0]);
            this.cols = Integer.parseInt(dimensions[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interpret a map file
     * @param cfg configuration to read
     */
    public void ReadMap(MazeConfigure cfg) {
        cfg.startReading(this.rows, this.cols);
        for(int i = 0; i < this.rows; i++){
            try {
                String line = reader.readLine();
                cfg.processLine(line);

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
