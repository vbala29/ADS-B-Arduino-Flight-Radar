//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

//Switzerland = https://opensky-network.org/api/states/all?lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226
//NJ = https://opensky-network.org/api/states/all?lamin=39.065456&lomin=-75.448057&lamax=41.386476&lomax=-73.657286
//

public class adsBWriter{
    private static File adsBData = new File("/Users/vikrambala/Documents/Java Projects/FlightRadarRaw.txt");
    // unordered
    public static File adsBDataOutput = new File("/Users/vikrambala/Documents/Java Projects/FlightRadarOutput.txt");
    public static File adsBDataOutputOrdered =  new File("/Users/vikrambala/Documents/Java Projects/FlightRadarOrderedOutput.txt");

    public static void fileWriter() throws IOException {
        FileWriter adsBDataWriter = new FileWriter(adsBData);

        adsBData.createNewFile();

        try {
            URL url = new URL("https://opensky-network.org/api/states/all?lamin=39.065456&lomin=-75.448057&lamax=41.386476&lomax=-73.657286");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
                adsBDataWriter.write(line);
                adsBDataWriter.flush();
            }

            System.out.println("Wrote to FlightRadar.txt successfully");
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }

    }

}

