/*
 * Author: Emily Costa
 * This project takes an input file containing patients at a hospital
 * and outputs a file containing the patients that potentially have
 * COVID-19. The functions will rate the patients on their likelihood 
 * of having the virus, and the doctors that potentially came
 * in contact with them.
 */

package com.mycompany.midtermproject;

import java.io.*;
import java.util.*;
import java.time.*;
import java.text.*;

/**
 *
 * @author federico manzo
 */
public class Exposed {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws ParseException, IOException{
        // For user interface and creating objects only.
        
        ArrayList<Doctor> doctors;
        ArrayList<Patient> patients;
                
        System.out.println("Hello, User. \n"
                + "This program will output a file identifying "
                + "COVID-19 risk ratings of doctors at your "
                + "hospital based on patient and medical "
                + "supply data. \n"
                + "The system already has the data for the "
                + "doctors and their supplies. Please enter "
                + "the patient data.");
        
        // Create ArrayLists of Persons.
        patients = createPatientObjects();
        doctors = createDoctorObjects();
        
        // Compute and set virus ratings.
        patients = setPatientVirusRatings(patients);
        doctors = setDoctorVirusRatings(doctors, patients);
        
        // Save doctors in output file.
        toFile(doctors);
    }
    
    public static ArrayList<Doctor> createDoctorObjects() throws FileNotFoundException, IOException, ParseException{
        // Creates an ArrayList of Doctor objects from input file.
        
        ArrayList<Doctor> doctors = new ArrayList<>();
        Doctor inDoctor;
        String fname, lname;
        Date inHospital;
        boolean mask, gloves;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dir = System.getProperty("user.dir");
        BufferedReader csvReader = new BufferedReader(new FileReader(dir + "/src/main/java/com/mycompany/midtermproject/doctors.csv"));
        String row;
        Scanner in;
        
        while ((row = csvReader.readLine()) != null) {
            // Set up scanner to read csv file.
            in = new Scanner(row);
            in.useDelimiter(",");
            
            //  Extract doctor information from csv.
            // Format: firstName,lastName, inHospitalDate, hasMask, hasGloves.
            fname = in.next();
            lname = in.next();
            inHospital = sdf.parse(in.next());
            // System.out.println(inHospital);
            mask = in.nextBoolean();
            gloves = in.nextBoolean();
            
            inDoctor = new Doctor(lname, fname, inHospital, mask, gloves);
            doctors.add(inDoctor);
        }
        return doctors;
    }
    
    public static ArrayList<Patient> createPatientObjects() throws ParseException{
        // Creates an ArrayList of Doctor objects from input stream.
        
        Scanner in = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        ArrayList<Patient> patients = new ArrayList<>();
        Patient inPatient;
        String fname, lname;
        Date dob, inHospital;
        boolean cough, fever, tired;
                
        
        // Loops for every patient the user wants to input.
        boolean cont = true;
        while(cont){
            // Prompt user for patient data, until "done".
            System.out.println("Patient first name:");
            fname = in.next();
            
            System.out.println("Patient last name:");
            lname = in.next();
            
            System.out.println("Patient date of birth (yyyy/mm/dd):");
            dob = sdf.parse(in.next());
            
            System.out.println("Day patient was in hospital (yyyy/mm/dd):");
            inHospital = sdf.parse(in.next());
            
            System.out.println("Symptoms of patient (cough, fever, tired):");
            System.out.println("Cough (true/false)?:");
            cough = in.nextBoolean();
            System.out.println("Fever (true/false)?:");
            fever = in.nextBoolean();
            System.out.println("Tired (true/false)?:");
            tired = in.nextBoolean();
            
            // Create Patient object.
            inPatient = new Patient(lname, fname, inHospital, dob,
                                                        cough, fever, tired);
            // Add to ArrayList.
            patients.add(inPatient);
            
            // Check is user wants to continue adding patients.
            System.out.println("Do you want to enter another patient?(yes/no):");
            if(in.next().equalsIgnoreCase("no")) cont = false;
        }   
        in.close();
        return patients;
    }
    
    public static ArrayList<Patient> setPatientVirusRatings(ArrayList<Patient> patients){
        int rating;
        for(int i=0; i<patients.size(); i++){
            rating = computePatientVirusRating(patients.get(i));
            patients.get(i).setvirusRating(rating);
        }
        return patients;
    }
    
    public static ArrayList<Doctor> setDoctorVirusRatings(
                                             ArrayList<Doctor> doctors, ArrayList<Patient> patients){
        int rating;
        for(int i=0; i<doctors.size(); i++){
            rating = computeDoctorVirusRating(doctors.get(i), patients);
            doctors.get(i).setvirusRating(rating);
            // System.out.println(doctors.get(i).getvirusRating());
        }
        return doctors;
    }
        
    public static int computePatientVirusRating(Patient patient){
        // Computes likelihood of patient having COVID-19.
        
        // Get necessary patient info from object.
        Date DOB = patient.getDOB();
        boolean cough = patient.getCough();
        boolean fever = patient.getFever();
        boolean tired = patient.getTired();
        
         // Set base rating to 0.
        int virusRating = 0;
        
        // Compute the age in years.
        Calendar c = Calendar.getInstance();
        c.setTime(DOB);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate dob = LocalDate.of(year, month, date);
        LocalDate now = LocalDate.now();
        Period age = Period.between(dob, now);
        int yrsAge = age.getYears();
        int old = 65;
        int veryOld = 80;
        
        // Higher risk with symptoms.
        if(cough) virusRating += 1;
        if(fever) virusRating += 1;
        if(tired) virusRating += 1;
        
        // Higher risk if older.
        if(yrsAge > old) virusRating += 1;
        if(yrsAge > veryOld) virusRating += 1;
        
        return virusRating;
    }
    
    public static int computeDoctorVirusRating(Doctor doctor, ArrayList<Patient> patients){
        // Computes likelihood of patient having COVID-19.
        int virusRating = 0;
        
        // Get necessary info from Doctor object.
        boolean hasMask = doctor.gethasMask();
        boolean hasGloves = doctor.gethasGloves();
        
        // Higher risk with less medical equipment.
        if(!hasMask) virusRating += 1;
        if(!hasGloves) virusRating +=1;
        
        // Higher risk if in hospital with patient that potentially has COVID-19.
        for(int i=0; i<patients.size(); i++){
            if(patients.get(i).getshiftInHospital().compareTo(doctor.getshiftInHospital())==0){
                virusRating += patients.get(i).getvirusRating();
            }
        }

        return virusRating;
    }
    
    public static void toFile(ArrayList<Doctor> doctors) throws IOException{
        String dir = System.getProperty("user.dir");
        FileWriter w = new FileWriter(dir + "/src/main/java/com/mycompany/midtermproject/riskOfDoctors.csv");
        w.append("Doctor Name,COVID-19 Risk Rating\n");
        for(int i=0;i<doctors.size();i++){
            w.append(doctors.get(i).toString() + "," + doctors.get(i).getvirusRating() + "\n");
        }
        w.flush();
        w.close();
    }
}