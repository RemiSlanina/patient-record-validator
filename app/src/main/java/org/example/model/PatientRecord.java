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

double, int cannot be null 
wrappers (Double, Integer) can 

json 

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