#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>

ESP8266WiFiMulti WiFiMulti;
void setup() {
  Serial.begin(115200);
  WiFiMulti.addAP("SSID_NAME","SSID_PASSWORD");
  pinMode(16, OUTPUT); // D0
  pinMode(5, OUTPUT); // D1
  pinMode(4, OUTPUT);// D2
  pinMode(0, OUTPUT);// D3
  pinMode(2, OUTPUT);// D4
  pinMode(14, OUTPUT);// D5
  pinMode(12, OUTPUT);// D6
  pinMode(13, OUTPUT);// D7
  
}
void loop()
{
    if((WiFiMulti.run() == WL_CONNECTED))
    {
        HTTPClient http;
        http.begin("http://192.168.0.100/project/saklar/Saklar");
        int httpCode = http.GET();
        if(httpCode > 0)
        {
            if(httpCode == HTTP_CODE_OK)
            {
              String json = http.getString();
              DynamicJsonBuffer jsonBuffer;
              JsonArray& root = jsonBuffer.parseArray(json);
              String status_saklar = root[0]["status_saklar"];
              int id_saklar = root[0]["id_saklar"];
              for(int i=0;i<root.size();i++){
                String status_saklar = root[i]["status_saklar"];
                int id_saklar = root[i]["id_saklar"];
                if(status_saklar == "hidup"){
                  if(id_saklar == 1){
                    digitalWrite(16, LOW);
                  }
                  if(id_saklar == 2){
                    digitalWrite(5, LOW);
                  }
                  if(id_saklar == 3){
                    digitalWrite(4, LOW);
                  }
                  if(id_saklar == 4){
                    digitalWrite(0, LOW);
                  }
                  if(id_saklar == 5){
                    digitalWrite(2, LOW);
                  }
                  if(id_saklar == 6){
                    digitalWrite(14, LOW);
                  }
                  if(id_saklar == 7){
                    digitalWrite(12, LOW);
                  }
                  if(id_saklar == 8){
                    digitalWrite(13, LOW);
                  }                                                                 
                }
                else {
                  if(id_saklar == 1){
                    digitalWrite(16, HIGH);
                  }
                  if(id_saklar == 2){
                    digitalWrite(5, HIGH);
                  }
                  if(id_saklar == 3){
                    digitalWrite(4, HIGH);
                  }
                  if(id_saklar == 4){
                    digitalWrite(0, HIGH);
                  }
                  if(id_saklar == 5){
                    digitalWrite(2, HIGH);
                  }
                  if(id_saklar == 6){
                    digitalWrite(14, HIGH);
                  }
                  if(id_saklar == 7){
                    digitalWrite(12, HIGH);
                  }
                  if(id_saklar == 8){
                    digitalWrite(13, HIGH);
                  } 
                }               
              }
            }

        } else {
            Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
        }
        http.end();
    }
    delay(5000);
}
