package com.app.rmlprototype.rest;
import com.app.rmlprototype.entity.Patient;
import com.app.rmlprototype.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientRestController {

    @Autowired
    private PatientService patientService;

    /*public PatientRestController(PatientService patientService){
        this.patientService=patientService;
    }*/

    @GetMapping("/authenticatePatient/{username}/{password}")
    public boolean authenticateUser(@PathVariable String username, @PathVariable String password){
        return patientService.authenticateUser(username,password);
    }

    @GetMapping("/getAllPatients")
    public List<Patient> getAllDoctors(){
        return patientService.findAll();
    }

    @GetMapping("/getPatient/{theId}")
    public Patient getSinglePatient(@PathVariable int theId){
        Patient patient = patientService.findById(theId);
        if(patient==null){
            throw new RuntimeException("Doctor Not Found");
        }
        return patient;
    }

    @GetMapping("/getPatientByIdNumber/{idProofNumber}")
    public Patient getByPatientId(@PathVariable String idProofNumber){
        Patient patient = patientService.findByPatientIdProof(idProofNumber);
        if(patient==null){
            throw new RuntimeException("Doctor Not Found");
        }
        return patient;
    }

    @GetMapping("/getPatientsByIdName/{name}")
    public List<Patient> getByPatientName(@PathVariable String name){
        return patientService.findByPatientName(name);
    }

    @PostMapping("/addPatient")
    public Patient addPatient(@RequestBody Patient patient){
        patientService.save(patient);
        return patient;
    }

    @DeleteMapping("/deletePatientById/{theId}")
    public String delPatient(@PathVariable int theId){
        Patient patient = patientService.findById(theId);
        if(patient==null){
            throw new RuntimeException("Doctor Not Found");
        }
        patientService.deleteById(theId);
        return "Patient Deleted Successfully";
    }

}
