import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.Translation;
import com.graphhopper.util.TranslationMap;

import java.util.Locale;

public class OntarioRouting {
    public static void main(String[] args) {
        // Path to the OSM file and GraphHopper data directory
        String osmFile = "C:\\Users\\whizk\\Documents\\LogisticsSystem\\data\\ontario-latest.osm.pbf";
        String graphHopperDir = "C:\\Users\\whizk\\Documents\\LogisticsSystem\\graphHopper";

        // Initialize GraphHopper
        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile(osmFile); // Set the path to the .pbf file
        hopper.setGraphHopperLocation(graphHopperDir); // Directory for graph data

        // Add profile (modern GraphHopper API)
        hopper.setProfiles(
                new Profile("car").setVehicle("car").setWeighting("fastest")
        );

        // Import OSM data (this step preprocesses the file and builds the graph)
        System.out.println("Preprocessing the Ontario OSM data...");
        hopper.importOrLoad();
        System.out.println("Preprocessing completed!");

        // Define a routing request
        double startLat = 43.65107, startLon = -79.347015; // Toronto (example)
        double endLat = 45.421532, endLon = -75.697189;   // Ottawa (example)
        GHRequest request = new GHRequest(startLat, startLon, endLat, endLon)
                .setProfile("car");

        GHResponse response = hopper.route(request);

        if (response.hasErrors()) {
            System.err.println("Errors: " + response.getErrors());
            return;
        }

        // Display routing details
        InstructionList instructions = response.getBest().getInstructions();
        TranslationMap translationMap = hopper.getTranslationMap();
        Translation tr = translationMap.getWithFallBack(Locale.CANADA); // Use "en" for English

        // Update the loop
        instructions.forEach(instruction -> System.out.println(instruction.getTurnDescription(tr)));

        System.out.println("Distance: " + response.getBest().getDistance() + " meters");
        System.out.println("Time: " + response.getBest().getTime() / 1000 + " seconds");
    }
}
