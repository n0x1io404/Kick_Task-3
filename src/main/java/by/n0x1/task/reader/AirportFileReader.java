package by.n0x1.task.reader;

import by.n0x1.task.entity.Plane;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AirportFileReader {
    public static int[] readAirportConfig(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        String[] config = lines.get(0).split(",");
        return new int[]{Integer.parseInt(config[0].strip()), Integer.parseInt(config[1].trim())};
    }

    public static List<Plane> readPlanes(String filePath) throws Exception {
        List<Plane> planes = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");
            int id = Integer.parseInt(data[0].trim());
            int arriving = Integer.parseInt(data[1].trim());
            int departing = Integer.parseInt(data[2].trim());
            planes.add(new Plane(id, arriving, departing));
        }
        return planes;
    }
}