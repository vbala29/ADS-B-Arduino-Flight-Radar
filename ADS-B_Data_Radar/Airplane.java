//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala;

import com.vikrambala.binarytree.OrderedItem;

public class Airplane extends OrderedItem {
    private String callSign;
    private String country;
    private float latitude;
    private float longitude;
    private int barometricAlt;
    private int velocity;
    private int track;
    private boolean onGround;
    private double distanceFromMe;


    public Airplane(){

    }

    public Airplane(String callSign, String country, float latitude, float longitude, float barometricAlt, float velocity, float track, boolean onGround) {
        this.callSign = callSign;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.barometricAlt = (int) Math.round(barometricAlt * 3.28084);
        this.velocity = (int) Math.round(velocity * 1.94384);
        this.track = (int) Math.round(track);
        this.onGround = onGround;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setBarometricAlt(float barometricAlt) {
        this.barometricAlt = (int) Math.round(barometricAlt * 3.28084);
    }

    public void setVelocity(float velocity) {
        this.velocity = (int) Math.round(velocity * 1.94384);
    }

    public void setTrack(float track) {
        this.track = (int) Math.round(track);
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setDistanceFromMe(double distanceFromMe) {
        this.distanceFromMe = distanceFromMe;
    }




    public String unorderedAirplaneInfo() { //both returns output for the file, and also prints to console.
        String output = (onGround) ? "Callsign = " + callSign + ", Country = " + country + ", Position = " + latitude + "º (lat), " + longitude + "º (lon),"
                + " Altitude = Airplane is on the ground" + ", Velocity = " + velocity + " knots (ground speed),"
                + " Track = " + track + "º\n" : "Callsign = " + callSign + ", Country = " + country + ", Position = " + latitude + "º (lat), " + longitude + "º (lon),"
                + " Barometric Altitude = " + barometricAlt + " feet, Velocity = " + velocity + " knots (ground speed),"
                + " Track = " + track + "º \n";
        return output;

    }

    public String printOrderedAircraft(int i){
        if (onGround) {
            System.out.println(i + ". Callsign = " + callSign + ", Distance From Me = " + distanceFromMe + " miles, Country = " + country + ", Position = " + latitude + "º (lat), " + longitude + "º (lon),"
                    + " Altitude = Airplane is on the ground" + ", Velocity = " + velocity + " knots (ground speed),"
                    + " Track = " + track + "º");
        } else {
            System.out.println(i + ". Callsign = " + callSign + ", Distance From Me = " + distanceFromMe + " miles, Country = " + country + ", Position = " + latitude + "º (lat), " + longitude + "º (lon),"
                    + " Barometric Altitude = " + barometricAlt + " feet, Velocity = " + velocity + " knots (ground speed),"
                    + " Track = " + track + "º");

        }
        String output = (onGround) ? callSign + ',' + roundDistance(distanceFromMe, 3) + "," + 0 + "," + velocity + "," + track +  "*, " + latitude + ","
                + longitude + "," + country + "\n" : callSign + "," + roundDistance(distanceFromMe, 3) + "," + barometricAlt + "," + velocity + "," + track + "*," + latitude + ","
                + longitude + "," + country + "\n";
        return output;
    }

    public static double roundDistance(double value, double places){
        double scale = Math.pow(10, places);
        return (Math.round(value * scale)  / scale);


    }


    public double getDistanceFromMe() {
        return distanceFromMe;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCallSign() {
        return callSign;
    }

    private int comparePlanes(Airplane plane){
        if(plane.getDistanceFromMe() > this.distanceFromMe){
            return -1;
        } if (plane.getDistanceFromMe() == this.distanceFromMe){
            return 0;
        } else return 1;
    }
//------------------ BST ALGO -------------

    public Airplane next() {
        return this.rightLink;
    }


    public Airplane setNext(Airplane plane) {
        this.rightLink = plane;
        return this.rightLink;
    }


    public Airplane previous() {
        return this.leftLink;
    }


    public Airplane setPrevious(Airplane plane) {
        this.leftLink = plane;
        return this.leftLink;
    }


    public int compareTo(Airplane plane) {
        return (plane != null) ? comparePlanes(plane) :  -2;
    }
}
