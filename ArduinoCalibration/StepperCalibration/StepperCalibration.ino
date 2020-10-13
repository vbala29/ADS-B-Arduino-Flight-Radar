//Developed by VIKRAM BALA, 2020. Contact: vikrambala2002@gmail.com. 
//Please contact if you wish to use for commercial purposes, and provide credit in any public usage.
//Credit can be provided with the following: "Vikram Bala, https://github.com/vbala29"

#include <Stepper.h>
#include <math.h>

const int STEPS = 2038;
Stepper altimeter(STEPS, 8, 9, 10, 11);
Stepper headingIndicator(STEPS, 3, 4, 5, 6); 
int count = 0;
String myString;
int headingSteps;
int altimeterSteps;

void setup() {
  headingIndicator.setSpeed(10); 
  altimeter.setSpeed(10);
  Serial.begin(9600);


}

void loop() {
  
    
while((Serial.available()) && (count >= 0)) {
    myString = Serial.readString();

    count++;
}

int ind1 = myString.indexOf(',');
headingSteps = myString.substring(0, ind1).toInt();
int ind2 = myString.indexOf(',', ind1+1);
altimeterSteps = myString.substring(ind1 + 1, ind2).toInt(); 

altimeter.step((int) (-1 * altimeterSteps / 100 * 5.095)); 
headingIndicator.step((int) (-1 * headingSteps / 0.1766437684)); 
altimeterSteps = 0;
headingSteps = 0; 
myString = '0'; 

}
