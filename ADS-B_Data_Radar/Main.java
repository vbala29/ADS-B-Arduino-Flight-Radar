//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static LinkedList<Airplane> orderedAircraft;
    static SearchTree airplaneTree;

    public static void main(String[] args) throws IOException {
        while(true) {
            adsBWriter.fileWriter();
            List<Airplane> unorderedAircraft = gatherInfo();
            orderAircraft(unorderedAircraft);
            printOrderedAircraft();
            try {
                Thread.sleep(30000);
            } catch(InterruptedException e){
                System.out.println("Interrupted due to " + e.getMessage());
            }
        }

    }

    public static List<Airplane> gatherInfo() throws IOException { //Also WRITES TO OUTPUTFILE with latest data
        int charCount;
        charCount = adsBReader.goToFirstAC();
        List<Airplane> airplanesFound = new LinkedList<>();
        adsBWriter.adsBDataOutput.createNewFile();

        int count = 0;
        while(true) {

            Airplane plane = new Airplane();
            charCount = adsBReader.readCallSign(charCount, plane);
            charCount = adsBReader.readCountry(charCount, plane);
            charCount = adsBReader.readPosition(charCount, plane);
            charCount = adsBReader.readBarometricAltitude(charCount, plane);
            charCount = adsBReader.readVelocity(charCount, plane);
            charCount = adsBReader.readTrack(charCount, plane);
            charCount = adsBReader.findNextAircraft(charCount);
            airplanesFound.add(plane);
            count ++;
            if(charCount == 0){
                break;
            }
        }


        FileWriter adsBDataWriter = new FileWriter(adsBWriter.adsBDataOutput);
        adsBDataWriter.write("Most Recent ADS-B data: \n");
        adsBDataWriter.flush();

        String output;
        for(int i = 0; i < airplanesFound.size(); i++){
            output = airplanesFound.get(i).unorderedAirplaneInfo();
            adsBDataWriter.write(output);
            adsBDataWriter.flush();
        }

        adsBDataWriter.write("---------------------------------\n");
        adsBDataWriter.flush();

        return airplanesFound;
    }

    public static List<Airplane> orderAircraft(List<Airplane> unorderedAircraft){ //ALSO ASSIGNS DISTANCES AND CREATES BINARY TREE
        airplaneTree = new SearchTree();
        for(Airplane airplane :  unorderedAircraft){
            airplane.setDistanceFromMe(GreatCircle.greatCircle(airplane.getLatitude(), airplane.getLongitude()));
        }


        for(Airplane airplane : unorderedAircraft){
            airplaneTree.addItem(airplane);
        }

        orderedAircraft = airplaneTree.traverse(airplaneTree.getRoot());
        return orderedAircraft;
    }

    public static void printOrderedAircraft() throws IOException {
      adsBWriter.adsBDataOutputOrdered.createNewFile();
      FileWriter adsBDataWriter = new FileWriter(adsBWriter.adsBDataOutputOrdered);
      String output;
      for(int i = 0; i <  orderedAircraft.size(); i++){
            output = orderedAircraft.get(i).printOrderedAircraft(i + 1); // also prints to console as well as returning the info
            adsBDataWriter.write(output);
            adsBDataWriter.flush();
        }

        adsBDataWriter.write("---------------------------------\n");
        adsBDataWriter.flush();



    }

}
