//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala;

import java.io.*;
import java.util.ArrayList;


public class adsBReader {
    private static File adsBData = new File("/Users/vikrambala/Documents/Java Projects/FlightRadarRaw.txt");


    public static int goToFirstAC() throws IOException {
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);
        boolean found = false;
        int i ;
        char c;
        int charCount = 0;
        while(!found) { //goes to first bracket then skips 11 to be after quotation mark before callsign
            i = br.read();
            c = (char) i;
            if (c == '[') {
                br.skip(11);
                charCount += 11;
                found = true;
            }
            charCount ++;
        }

        return charCount;

    }

    public static int findNextAircraft(int charCount) throws IOException {
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);
        br.skip(charCount);

        int i;
        char c;
        int bracketCount = 0;
        while(true){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == '['){ //SKIPS TO AFTER Quotation marks before callsign.
                br.skip(10);
                charCount += 10;
                break;
            }
            if(c == ']'){
                bracketCount ++;
            }
            if(bracketCount == 2){
                charCount = 0;
                break;
            }
        }

        return charCount;
    }

    public static int readCallSign(int charCount, Airplane currentAirplane) throws IOException {
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);

        int i;
        char c;
        ArrayList<Character> callSignArrayList = new ArrayList<>();

        boolean read = false;
        br.skip(charCount);
        while(!read){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == ' ' | c == '\"'){
                read = true;
            } else{
                callSignArrayList.add(Character.valueOf(c));
            }
        }
        StringBuilder builder = new StringBuilder();
        String callSign;

        for(int j = 0; j < (callSignArrayList.size()); j++){
            builder.append(callSignArrayList.get(j));
        }
        callSign = builder.toString();

        currentAirplane.setCallSign(callSign);
        return charCount;
    }

    public static int readCountry(int charCount, Airplane currentAirplane) throws IOException {
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);

        br.skip(charCount);
        int k;
        char d = 0;
        int count = 0;
        char previous = 0;
        // gets from end of callsign +  1 space, to beginning of country
        while(true){
            if (count > 0){
                previous = d;
            }
            k = br.read();
            d = (char) k;
            charCount++;
            if(d == '\"' && previous == ','){
                break;
            }
            count ++;
        }

        int i;
        char c;
        ArrayList<Character> countryArrayList = new ArrayList<>();
        boolean read = false;
        //reads country to array
        while(!read){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == '\"'){
                read = true;
            } else{
                countryArrayList.add(Character.valueOf(c));
            }
        }


        StringBuilder builder = new StringBuilder();
        String country;

        for(int j = 0; j < (countryArrayList.size()); j++){
            builder.append(countryArrayList.get(j));
        }

        country = builder.toString();

        currentAirplane.setCountry(country);
        return charCount;
    }

    public static int readPosition(int charCount, Airplane currentAirplane) throws IOException {
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);

        int i;
        char c;
        ArrayList<Character> positionArrayList = new ArrayList<>();

        boolean read = false;
        br.skip(charCount + 23);
        charCount += 23;
        //gets from end of country and after its last quotation mark, past ICAO-24 bit country address and unix timestamp, to beginning of position
        int commaCount = 0;
        while(!read){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == ','){
                positionArrayList.add(Character.valueOf(c));
                commaCount ++;
            } else{
                positionArrayList.add(Character.valueOf(c));
            }
            if(commaCount == 2){
                read = true;
            }

        }
        StringBuilder latitudeBuilder = new StringBuilder();
        StringBuilder longitudeBuilder = new StringBuilder();
        String latitudeString;
        String longitudeString;
        int arrayPosition = 0;
        for(int j = 0; j < positionArrayList.size(); j++){
            if(positionArrayList.get(j).compareTo(',') == 0){
                arrayPosition = j;
                break;
            }
            longitudeBuilder.append(positionArrayList.get(j));
        }
        for(int y = arrayPosition + 1; y < positionArrayList.size(); y++){
            latitudeBuilder.append(positionArrayList.get(y));
        }

        latitudeString = latitudeBuilder.toString().replaceAll(",",""); //Gets rid of comma after latitude
        longitudeString = longitudeBuilder.toString();

        float latitude = Float.parseFloat(latitudeString);
        float longitude = Float.parseFloat(longitudeString);

        currentAirplane.setLatitude(latitude);
        currentAirplane.setLongitude(longitude);
        return charCount;
    }

    public static int readBarometricAltitude(int charCount, Airplane currentAirplane) throws IOException{
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);

        br.skip(charCount); // gets to after comma after the latitude (second position number in ads-B readout string)

        int i;
        char c;
        ArrayList<Character> altitudeArrayList = new ArrayList<>();
        boolean read = false;
        //reads altitude to array;
        while(!read){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == ','){
                read = true;
            } else{
                altitudeArrayList.add(Character.valueOf(c));
            }
        }


        StringBuilder builder = new StringBuilder();
        String altitudeString;

        for(int j = 0; j < (altitudeArrayList.size()); j++){
            builder.append(altitudeArrayList.get(j));
        }

        altitudeString = builder.toString();
        if(altitudeString.equals("null") || Double.parseDouble(altitudeString) <= 0){
            currentAirplane.setOnGround(true);
        } else {
            float barometricAltitude = Float.parseFloat(altitudeString);
            currentAirplane.setBarometricAlt(barometricAltitude);
        }

        return charCount;
    }

    public static int readVelocity(int charCount, Airplane currentAirplane) throws IOException{
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);

        br.skip(charCount); // gets to after comma after the baroAltitude;
        int k;
        char d;
        // gets to past comma after onGround Boolean
        while(true){
            k = br.read();
            d = (char) k;
            charCount++;
            if(d == ','){
                break;
            }
        }
        
        int i;
        char c;
        ArrayList<Character> velocityArrayList = new ArrayList<>();
        boolean read = false;
        //reads velocity to array;
        while(!read){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == ','){
                read = true;
            } else{
                velocityArrayList.add(Character.valueOf(c));
            }
        }


        StringBuilder builder = new StringBuilder();
        String velocityString;

        for(int j = 0; j < (velocityArrayList.size()); j++){
            builder.append(velocityArrayList.get(j));
        }

        velocityString = builder.toString();
        float velocity = Float.parseFloat(velocityString);
        currentAirplane.setVelocity(velocity);
        return charCount;
    }

    public static int readTrack(int charCount, Airplane currentAirplane) throws IOException{
        FileReader adsBReader = new FileReader(adsBData);
        BufferedReader br = new BufferedReader(adsBReader);

        br.skip(charCount); // gets to after comma after the velocity;

        int i;
        char c;
        ArrayList<Character> trackArrayList = new ArrayList<>();
        boolean read = false;
        //reads track to array;
        while(!read){
            i = br.read();
            c = (char) i;
            charCount ++;
            if(c == ','){
                read = true;
            } else{
                trackArrayList.add(Character.valueOf(c));
            }
        }


        StringBuilder builder = new StringBuilder();
        String trackString;

        for(int j = 0; j < (trackArrayList.size()); j++){
            builder.append(trackArrayList.get(j));
        }

        trackString = builder.toString();
        float track = Float.parseFloat(trackString);
        currentAirplane.setTrack(track);
        return charCount;
    }


}
