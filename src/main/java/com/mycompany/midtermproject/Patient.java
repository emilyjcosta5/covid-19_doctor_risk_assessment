/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.midtermproject;

import java.util.*;

/**
 *
 * @author emily
 */
public class Patient extends Person{
    // Symptoms.
    boolean cough;
    boolean fever;
    boolean tired;
    Date DOB; // birthdate
    
    public Patient(String lastName, String firstName, 
            Date shiftInHospital, Date DOB, boolean cough,
            boolean fever, boolean tired){
        // invokes base class, Person.
        super(lastName, firstName, shiftInHospital);
        this.cough = cough;
        this.fever = fever;
        this.tired = tired;
        this.DOB = DOB;
    }
    
    public Date getDOB(){
        return DOB;
    }
    
    public boolean getCough(){
        return cough;
    }
    
    public boolean getFever(){
        return fever;
    }
    
    public boolean getTired(){
        return tired;
    }

}
