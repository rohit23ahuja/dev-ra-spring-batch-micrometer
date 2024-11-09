package dev.ra.springbatch.micrometer.internal;

public class CricketService {

    public void serviceMethod(String message){
        System.out.println(String.format("Parameter received %s", message));
    }
}
