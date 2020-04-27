package com.mycompany.midtermproject;

import java.util.*;

/**
 * Person class.
 * To be extended in Doctor and Patient classes.
 */
public class Person {
    String lastName;
    String firstName;
    Date shiftInHospital;
    int virusRating;
    
    public Person(String lastName, String firstName, 
            Date shiftInHospital){
        this.firstName = firstName;
        this.lastName = lastName;
        this.shiftInHospital = shiftInHospital;
    }
    
    public void setvirusRating(int virusRating){
        this.virusRating = virusRating;
    }
    
    public Date getshiftInHospital(){
        return shiftInHospital;
    }
    
    public String getfirstName(){
        return firstName;
    }
    
    public String getlastName(){
        return lastName;
    }
    
    public int getvirusRating(){
        return virusRating;
    }
}
