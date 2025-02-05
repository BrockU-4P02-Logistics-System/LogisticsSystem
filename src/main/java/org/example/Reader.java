package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public List<Location> locations;

    public Reader(String filePath) throws IOException
    {
        try
        {
            this.locations = new ArrayList<>();
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONObject geoJson = new JSONObject(content);
            JSONArray features = geoJson.getJSONArray("features");

            for(int i = 0; i < features.length(); i++)
            {
                JSONObject feature = features.getJSONObject(i);

                JSONObject geometry = feature.getJSONObject("geometry");
                String type = geometry.getString("type");

                if ("Point".equalsIgnoreCase(type))
                {
                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                    // GeoJSON standard: coordinates are in [longitude, latitude] order
                    double lon = coordinates.getDouble(0);
                    double lat = coordinates.getDouble(1);

                    // Option: retrieve an id from properties or simply use the loop index as id
                    // For this example, we will use the loop index as the id.
                    int id = i;

                    // Create a new Location object with the parsed lat, lon, and id
                    Location location = new Location(lat, lon, id);
                    this.locations.add(location);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
