# ADS-B Arduino Flight Radar 
Overview: Contains files to retrieve automatic dependent surveillance broadcast data from airplanes within a bounded lat/long region, decipher, organize, and write the data to an Arduino, and display the information on a LCD, physical altimeter and heading indicator. **The arduino/processing programs are currently configured to take the information of the aircraft closest to a defined lat/long point, and display that on the radar.**

# ADS-B Data Supplier 
The OpenSkyNetwork is the provider of this ADS-B data. Please read their terms of use before using their data for any purposes: https://opensky-network.org/about/terms-of-use 
Credit: The OpenSky Network, http://www.opensky-network.org

# Components: 
**ADS-B Data Radar**

ADS-B_Data_Radar (ADS-B-Arduino-Flight-Radar/ADS-B_Data_Radar/) contains a Java program that retrieves ADS-B data for a bounded longitude/latitude box (set to NJ/NYC area to maximize Class Bravo Airport traffic). The program deciphers the data and prints out the aircraft in order of closest distance to a defined latitude/longitude point (this must be manually entered by the user in the GreatCircle class and Arduino Flight Radar file, and is presumably the point that the user is at) within the box. Information such as altitude, track (heading), ground speed, and current position are printed to the console. 

The program writes thisinformation  to three files: FlightRadarRaw.txt (the raw information from the OpenSkyNetwork ADS-B provider), 
FlightRadarOutput.txt (includes the radar's distance ordered and formatted output that is written to the console),
and FlightRadarOrderedOutput.txt (which is the distance ordered data for planes formatted such that it can easily be written and 
deciphered by the processing program. This text file is necessary for output to be written to the Arduino).
   
**Serial Port Writer (Processing 3)**

The processing program (ADS-B-Arduino-Flight-Radar/SerialWriter/) reads the top line from the FlightRadarOrderedOutput.txt file (which is being 
updated at a set interval by the java program). This data is then printed to the serial port the Arduino is connected to (this port must be manually entered), and after 5 seconds a switch is flipped so that data is read from the text file again. 

**Arduino (Arduino/C: Controls LCD, 2 Status LEDs, a Button, and 2 Stepper Motors)**

The Arduino program automatically reads data from the serial port when it is avaliable, and calculates/stores the following values: callsign, distance from the defined center lat/long point, altitude, velocity, track, which direction the user should look to find the plane in the sky (in degrees), latitude/longitude of the plane on the radar. When a button connected to pin 12 is pressed, these values are displayed on an LCD. In addition, two stepper motors, one functioning as an altimeter, the other a heading indicator, display the data of the closest plane. A status LED connected to pin 13 blinks when data is read from the serial port, while a status LED connected to pin 7 is lit when no data is being read from the serial port, and the Arduino is not in the processing of displaying data on the radar (i.e. the motors are not turning and the LCD has cycled through all values to display). 

**Motor Calibration (Used to quickly repotisition the altimeter and heading indicator motors to 0 ft/ 0º)**

In the serial monitor, enter the positions of the heading indicator followed by the altimeter, seperated by commas and in their respective units. The Arduino will convert these values to steps and reset the motors to their 0 value positions. 

# Images of Example Hardware
*Flight Radar in its neutral position, displaying no data.*
![IMG_9087](https://user-images.githubusercontent.com/56012430/88239488-8f319f80-cc52-11ea-8cc5-9f7195471c63.jpg)

*Flight radar cycling through data on the LCD, motors have turned to reflect the data of the aircraft (N938SA). (Note that the altitude is 1300 ft, so the altimeter has only turned a few degrees to the right)*
![IMG_9096](https://user-images.githubusercontent.com/56012430/88239554-b5efd600-cc52-11ea-8bdb-d709a1814c7b.jpg)

*Flight radar has finished cycling and now shows the distance from the user to the aircraft ("D"), as well as which direction to look ("L") to find the aircraft (assuming the center lat/long point the user defined is their position). Notice that the green LED connected to pin 13 is lit, indicating data is being read from the serial port by the Arduino.*
![IMG_9093](https://user-images.githubusercontent.com/56012430/88239559-bb4d2080-cc52-11ea-9d6d-a9b35b7e00ba.JPG)



