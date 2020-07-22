# Description:
ADS-B_Data_Radar contains a Java program retrieves ADS-B data for a bounded longitude/latitude box (set to NJ/NYC area, KEWR and KJFK airports are the only Class Bravo's in the box). The program deciphers the data and prints out the aircraft in order of closest distance to a defined latitude/longitude point (this must be manually entered by the user, and is presumably the point that the user is at) within the box. Information such as altitude, track (heading), ground speed, and current position are printed to the console. 

The program writes thisinformation  to three files: FlightRadarRaw.txt (the raw information from the OpenSkyNetwork ADS-B provider), 
FlightRadarOutput.txt (includes the radar's distance ordered and formatted output that is written to the console),
and FlightRadarOrderedOutput.txt (which is the distance ordered data for planes formatted such that it can easily be written and 
deciphered by the processing program. This text file is necessary for output to be written to the Arduino).
   

# Usage:
The txt files will be created automatically by the program, and will be overwritten if a file of the same path already exists. The OpenSkyNetwork 
is the provider of this ADS-B data. Please read their terms of use before using their data for any purposes: https://opensky-network.org/about/terms-of-use 
Credit: The OpenSky Network, http://www.opensky-network.org


