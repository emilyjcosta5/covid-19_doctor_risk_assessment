/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.midtermproject;

import java.util.Date;

/**
 *
 * @author emily
 */
public class Doctor extends Person{
     // Medical equipment.
    boolean hasMask;
    boolean hasGloves;
    
    public Doctor(String lastName, String firstName, 
            Date shiftInHospital, boolean hasMask,
            boolean hasGloves){
        // invokes base class, Person.
        super(lastName, firstName, shiftInHospital);
        this.hasMask = hasMask;
        this.hasGloves = hasGloves;
    }
    
    public boolean gethasGloves(){
        return hasGloves;
    }
    
    public boolean gethasMask(){
        return hasMask;
    }
}
