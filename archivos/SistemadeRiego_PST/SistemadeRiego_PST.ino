#include <SimpleDHT.h>
// Librería  <SimpleDHT.h> del sensor de temperatura y humedad relativa
#include <SPI.h>
#include <ESP8266WiFi.h>

//Librería <SPI.h> del motor DC compatible con la minibomba de agua DC
#define humidity_sensor_pin A0
//Se define variable del sensor de humedad en el suelo en el pin A0
//Defino variable del sensor de fotoresistencia LDR en el pin D5
// for DHT11, 
//      VCC: 5V or 3V
//      GND: GND
//      DATA: 2
int pinDHT11 = 2;
// Se declara la variable pinDHT11 y lo asocio al pin 2
int water_pump_pin = 14;
//Se declara la variable mini bomba de agua y lo asocio al pin 14
int water_pump_speed = 255;
int bandera=0;
//Velocidad de la minibomba de agua oscila entre 100 como mínimo y 255 como máximo. Yo he //elegido 255 pero ustedes pueden elegir la que estimen conveniente. A más velocidad, mayor //bombeo de agua
const char* ssid = "Lab-Telematica";   //se coloca el nombre de la red a conectarse
const char* password = "l4bt3l3m4tic@"; //se coloca la contraseña de la red a conectarse
const char* host = "192.168.2.173";  //se coloca la direccion IP del servidor (raspberry)
float tiempo_inicial=0;  //se declara variable para iniciar contador de encendido de bomba
float tiempo_final=0;    //se declara variable para finalizar contador de encendido de bomba
float duracion_encendido=0;   //se declara variable para almacenar tiempo total de encendido de bomba
const float caudal_bomba=1.57;   //Caudal de la bomba en L/min
static char outstr[15];
String mac_adress="";   //se declara variable para almacenar la direccion mac del dispositivo

SimpleDHT11 dht11;

void setup() {
  Serial.begin(9600);
  pinMode(water_pump_pin,OUTPUT);
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password); //inicia el proceso de conexion con la red wifi
  while (WiFi.status() != WL_CONNECTED) {
  delay(500);
  Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP()); //se obtiene la direccion IP de la conexion 

  mac_adress= WiFi.macAddress();  //se obtiene la direccion mac del modulo wifi
  Serial.println(mac_adress);
}

void loop() {
  // start working…
// Mide la temperatura y humedad relativa y muestra resultado
  Serial.println("***********");
  Serial.println("Sample DHT11…");
  
  // read with raw sample data.
  byte temperature = 0;
  byte humidity_in_air = 0;
  byte data[40] = {0};
  if (dht11.read(pinDHT11, &temperature, &humidity_in_air, data)) {  //se verifica que los sensores esten funcionando correctamente
    Serial.print("Read DHT11 failed");
  }
  
  Serial.print("Sample RAW Bits: ");
  for (int i = 0; i < 40; i++) {
    Serial.print((int)data[i]);
    if (i > 0 && ((i + 1) % 4) == 0) {
      Serial.print(' ');
    }
  }
  Serial.println("");
  
  Serial.print("Sample OK: ");
  Serial.print("Temperature: ");Serial.print((int)temperature); Serial.print(" *C, ");
  Serial.print("Relative humidity in air: ");Serial.print((int)humidity_in_air); Serial.println(" %");
  int ground_humidity_value = map(analogRead(humidity_sensor_pin), 300, 1023, 100, 0);  // Mide la humedad en el suelo en % y muestra el resultado

  Serial.print("Ground humidity: ");
  Serial.print(ground_humidity_value);
  Serial.println("%");
  if( ground_humidity_value <= 50 && temperature < 30) {  //se comprueba que el valor de humedad del suelo y de temperatura sean los adecuados para encender la bomba
  bandera+=1;
  if (bandera==1){
    tiempo_inicial=millis();  //se inicia el contador
    }
  digitalWrite(water_pump_pin, HIGH);  //se enciende la bomba para empezar el riego 
  Serial.println("Irrigate");
}
 else{
 digitalWrite(water_pump_pin, LOW);  //se apaga la bomba para acabar el riego 
 Serial.println("Do not irrigate");

  if (bandera>0){ 
  bandera=0;
  tiempo_final=millis();  //se termina el contador
  duracion_encendido=(tiempo_final-tiempo_inicial)*60*caudal_bomba/1000;  //se calcula el consumo de agua total
  String duracion_final = String(duracion_encendido); 
  Serial.print("connecting to ");
  Serial.println(host);

  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;  //se declara el puerto por default
  if (!client.connect(host, httpPort)) {  
  Serial.println("connection failed");
  return;
  }
 // We now create a URL for the request
 String url = "/dht11.php";   //se llama a la pagina de conexion con el servidor 
 String duracion = "?duracion_riego=";  //se declara la variable que se desea envia al servidor
 String dispositivo= "&dispositivo=";  

 Serial.print("Requesting URL: ");
 Serial.println(url);
 client.print(String("GET ") + url + duracion + duracion_final + dispositivo + mac_adress+ " HTTP/1.1\r\n" +
 "Host: " + host + "\r\n" + "Connection: close\r\n\r\n");  //se envia el requerimiento al servidor para subir los datos a la base de datos en phpMyadmin
 unsigned long timeout = millis();
 while (client.available() == 0) {
 if (millis() - timeout > 5000) {
 Serial.println(">>> Client Timeout !");
 client.stop();
 return;
 }
 }
 while (client.available()) {
  String line = client.readStringUntil('\r');
  Serial.print(line);
  }

 Serial.println();
 Serial.println("closing connection"); 
 }  
}

// Ejecuta el código cada 2 segundos
 delay(2000);
}
