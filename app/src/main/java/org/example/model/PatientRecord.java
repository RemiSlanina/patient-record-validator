package org.example.model;

import java.util.List; 

public class PatientRecord {

    public String patientId; 
    public Integer spo2; 
    public Double temperature; 
    public Integer heartRate; 
    public String dateTimeTaken; 
    public List<Double> weights; 
    public String userId; 

    // double, int cannot be null 
    // wrappers (Double, Integer) can 

    public PatientRecord() {}

    public PatientRecord(String patientId, int spo2, 
              double temperature, int heartRate, String dateTimeTaken, 
              List<Double> weights, String userId){
                this.patientId = patientId; 
                this.spo2 = spo2; 
                this.temperature = temperature; 
                this.heartRate = heartRate; 
                this.dateTimeTaken = dateTimeTaken; 
                this.weights = weights; 
                this.userId = userId; 
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
             "patientId='" + patientId + '\'' +
             ", spo2=" + spo2 +
             ", temperature=" + temperature +
             ", heartRate=" + heartRate +
             ", dateTimeTaken='" + dateTimeTaken + '\'' +
             ", weights=" + weights +
             ", userId='" + userId + '\'' +
             '}';
    }
    
}


/*

example json 

[
  {
    "patientId": "P-1001",
    "spo2": 96,
    "temperature": "36.4",
    "heartRate": 72,
    "dateTimeTaken": "",
    "weights": [],
    "userId": null
  }
]


*/