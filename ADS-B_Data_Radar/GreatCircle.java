//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

package com.vikrambala;

public class GreatCircle {
    private static final double MY_LAT = //User-Defined;
    private static final double MY_LON = //User-Defined;
    private static final double r = 3598.8;

    public static double greatCircle(double lat2, double lon2){
        // distance between latitudes and longitudes 
        double dLat = Math.toRadians(lat2 - MY_LAT);
        double dLon = Math.toRadians(lon2 - MY_LON);

        // convert to radians
        double lat1 = Math.toRadians(MY_LAT);
        lat2 = Math.toRadians(lat2);

        // apply formulae 
        double a = Math.pow((Math.sin(dLat / 2)), 2) +
                (Math.pow((Math.sin(dLon / 2)), 2) * Math.cos(lat1) * Math.cos(lat2));
        double rad = 3958.8; //miles
        double c = 2 * Math.asin(Math.sqrt(a));
        return (rad * c);

    }

  

}
