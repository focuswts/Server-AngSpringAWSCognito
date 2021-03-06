package com.spring.angular.controller;

import com.spring.angular.entity.Person;
import com.spring.angular.repository.PersonRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;


    @RequestMapping(value = "/persons",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    @RequestMapping(value = "/persons/{id}",method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("Person not found for this id :: " + personId));

        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/persons/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Person createPerson(@Valid @RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/persons/update/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long personId, @Valid @RequestBody Person personDetails) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("Person not found for this id :: " + personId));

        person.setId(personDetails.getId());
        person.setName(personDetails.getName());
        person.setCpf(personDetails.getCpf());
        person.setEmail(personDetails.getEmail());

        final Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);

    }

    @DeleteMapping("/persons/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personId)
            throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));

        System.out.println("ID: " + person.getId());
        personRepository.delete(person);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
