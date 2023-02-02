#include <SPI.h>
#include <MFRC522.h>
#include <ESP8266WiFi.h>

const char* ssid = "WIFI2";                // hotspot ssid
const char* password = "123456789";        // hotspot password
IPAddress javaServerIP(192, 168, 137, 1);  // IP address of the Java listener
int port = 9090;
WiFiClient client;

constexpr uint8_t RST_PIN = D3;  // Configurable, see typical pin layout above
constexpr uint8_t SS_PIN = D4;   // Configurable, see typical pin layout above
byte ledR = D8;
byte ledG = D9;
byte ledB = D10;
MFRC522 rfid(SS_PIN, RST_PIN);  // Instance of the class
MFRC522::MIFARE_Key key;
String tag;
String oldTag;

void setup() {
  Serial.begin(9600);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
  }
  client.setTimeout(100);
  SPI.begin();      // Init SPI bus
  rfid.PCD_Init();  // Init MFRC522
  pinMode(ledR, OUTPUT);
  pinMode(ledG, OUTPUT);
  pinMode(ledB, OUTPUT);
  digitalWrite(ledR, HIGH);  //wyłączamy czerowny kolor
  digitalWrite(ledG, HIGH);  //wyłączamy zielony kolor
  digitalWrite(ledB, HIGH);  //wyłączamy neiebieski kolor
}
void loop() {
  if (!rfid.PICC_IsNewCardPresent()) {
    return;
  }
  if (rfid.PICC_ReadCardSerial()) {
    for (byte i = 0; i < 4; i++) {
      tag += rfid.uid.uidByte[i];
    }
    if (oldTag != tag) {
      oldTag = tag;
      digitalWrite(ledG, LOW);  //wyłączamy zielony kolor
      delay(500);
      digitalWrite(ledG, HIGH);  //wyłączamy zielony kolor
    } else {
      digitalWrite(ledR, LOW);  //wyłączamy czerowny kolor
      delay(500);
      digitalWrite(ledR, HIGH);  //wyłączamy czerowny kolor
    }
    rfid.PICC_HaltA();
    rfid.PCD_StopCrypto1();
    Serial.println(tag);
    if (client.connect(javaServerIP, port)) {
      if (client.connected()) {
        digitalWrite(ledB, LOW);  //wyłączamy niebieski kolor
        delay(200);
        digitalWrite(ledB, HIGH);  //wyłączamy niebieski kolor
        client.print(tag);
        client.stop();
      }
    }
    tag = "";
  }
}
