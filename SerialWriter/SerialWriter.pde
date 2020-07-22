import processing.serial.*;
import java.io.*;
int mySwitch=0;
int counter=0;
String [] subtext;
Serial myPort;
String text;
int count = 0;


void setup(){
 //When mySwitch=1, the program is setup to read the text file.
 //This is turned off when mySwitch = 0
 mySwitch=1;
 
 myPort = new Serial(this, "/dev/tty.usbmodem142301", 9600);
}

void draw() {
 while (mySwitch>0){
 readData("/Users/vikrambala/Documents/Java Projects/FlightRadarOrderedOutput.txt");
 mySwitch=0;
 }
   myPort.write(text);
   System.out.println(text);

   delay(5000);
   mySwitch=1;
  
}



void readData(String myFileName){
 
 File file=new File(myFileName);
 BufferedReader br=null;
 
 try{
 br=new BufferedReader(new FileReader(file));
 
 // reads first line of the flightRADAR file 
 text = br.readLine();

 }catch(FileNotFoundException e){
 e.printStackTrace();
 }catch(IOException e){
 e.printStackTrace();
 }finally{
 try {
 if (br != null){
 br.close();
 }
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
}
