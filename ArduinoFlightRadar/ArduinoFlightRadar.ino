//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

#include <Stepper.h>
#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
#include <math.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

const int STEPS = 2038; // the number of steps in one revolution of motor (28BYJ-48)
const int STEPPER = 2038;
const double oneStepDegree = 0.1766437684;
const double oneAltStep = 5.095; //Corresponds to 100 feet on altimeter
const double MYLAT = //USER DEFINED
const double MYLON = //USER DEFINED

Stepper altimeter(STEPS, 8, 9, 10, 11);
Stepper headingIndicator(STEPPER, 3, 4, 5, 6); 


String myString;

String callsign;
String distance;
int altitude;
int velocity; 
int track;
int recentStepHeading = 0; 
int recentAlt = 0;

double latitude;
double longitude;
double headingLook; 

 void setup() {
    pinMode(7, OUTPUT);
  pinMode(12, INPUT);
  pinMode(13, OUTPUT);
//  pinMode(2, INPUT);
  
 lcd.begin();
  Serial.begin(9600);
  headingIndicator.setSpeed(10); 
  altimeter.setSpeed(10);
  lcd.print("Vikram's FLight        " );
  lcd.setCursor(0, 1);
  lcd.print("Radar v1.0             ");
  delay(1500); 
  lcd.setCursor(0,0); 
  lcd.print("Wait for green      ");
  lcd.setCursor(0,1);
  lcd.print("LED. Then press.             "); 
 }

 void loop() {
  digitalWrite(7, HIGH);
  delay(100);
  digitalWrite(7, LOW); 
  delay(100);
  digitalWrite(7, HIGH);
  delay(100);
  digitalWrite(7, LOW); 
//
//  if(digitalRead(2) == HIGH){
//  altimeter.step(-1 * recentAlt);
//  headingIndicator.step(-1 * recentStepHeading);
//}

while(Serial.available()){
  digitalWrite(7, LOW);
    myString = Serial.readString();
    digitalWrite(13, HIGH);
    delay(100);
    digitalWrite(13, LOW);
    delay(100);
     digitalWrite(13, HIGH);
    delay(100);
    digitalWrite(13, LOW);
    delay(100); 
    digitalWrite(13, HIGH);
    delay(100);
      digitalWrite(13, LOW);
} 

if(digitalRead(12) == HIGH){
      digitalWrite(7, LOW);
      lcd.clear();
      int ind1 = myString.indexOf(',');
      callsign = myString.substring(0, ind1);
      int ind2 = myString.indexOf(',', ind1+1);
      distance = myString.substring(ind1 + 1, ind2);
      int ind3 = myString.indexOf(',', ind2+1);
      altitude = myString.substring(ind2+1, ind3).toInt();
      int ind4 = myString.indexOf(',', ind3+1);
      velocity = myString.substring(ind3+1, ind4).toInt();
      int ind5 = myString.indexOf('*', ind4+1);
      track = myString.substring(ind4+1, ind5).toInt(); 
      int ind6 = myString.indexOf(',', ind5+2); 
      latitude = myString.substring(ind5+2, ind6).toDouble();
      int ind7 = myString.indexOf(',',ind6+1);
      longitude = myString.substring(ind6+1, ind7).toDouble(); 

      double latDif = latitude - MYLAT;
      latDif = abs(latDif); 

      double lonDif = longitude - MYLON;
      lonDif = abs(lonDif);
      
      headingLook = atan(latDif/lonDif); 
       headingLook = ((headingLook * 180) / 3.14);
    
     lcd.print("Callsgn: " + callsign); 
      lcd.setCursor(0, 1); 
     lcd.print("Retriving...                         ");

      //-------- ALTIMETER   
      if(recentAlt != 0){
     altimeter.step(-recentAlt);
     recentAlt = 0; 
     }
     int stepsAlt = (int) ((altitude / 100) * oneAltStep); 
     altimeter.step(stepsAlt);
     recentAlt = stepsAlt; 

//---------  HEADING 
     if(recentStepHeading != 0){
     headingIndicator.step(-1 * recentStepHeading);
     recentStepHeading = 0;
      }
     int stepsHeading = (int) (track / oneStepDegree); 
     headingIndicator.step(stepsHeading);
     recentStepHeading = stepsHeading; 

    



//---------
    int count = 0;
     while(count < 2){
     lcd.setCursor(0, 1); 
     lcd.print("Dist: " + distance + " mi       ");
     delay(1000);
     lcd.setCursor(0, 1); 
     lcd.print("Alt: "); lcd.print(abs(altitude)); lcd.print(" ft          ");
     delay(1000);
     lcd.setCursor(0, 1); 
     lcd.print("Velo: "); lcd.print(abs(velocity)); lcd.print(" kt          ");
     delay(1000);
     lcd.setCursor(0, 1); 
     lcd.print("Track: "); lcd.print(abs(track)); lcd.print(" deg               ");
     delay(1000);
     count ++;
     }
     
     lcd.setCursor(0, 1); 
     lcd.print("D: " + distance + " L: " + headingLook + "             ");
    }

    
  

 }
