package com.application.api.controller;

import com.application.api.entity.PersonEntity;
import com.application.api.entity.validator.PersonValidator;
import com.application.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "**", allowedHeaders = "**")
@RestController
@RequestMapping(value = "person")
public class PersonController {

    @Autowired
    private PersonRepository _personRepository;

    @Autowired
    private PersonValidator validator;

    @InitBinder
    public void validate(WebDataBinder binder){
        binder.addValidators(validator);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<PersonEntity> get() {
        return _personRepository.findAll();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<PersonEntity> getById(@PathVariable(value = "id") Long id) {
        Optional<PersonEntity> person = _personRepository.findById(id);
        return person.map(personEntity -> new ResponseEntity<>(personEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/add", method =  RequestMethod.POST)
    public PersonEntity post(@Valid @RequestBody PersonEntity person)
    {
        return _personRepository.save(person);
    }

    @RequestMapping(value = "/add/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<PersonEntity> put(@PathVariable(value = "id") long id, @Valid @RequestBody PersonEntity newPerson)
    {
        Optional<PersonEntity> oldPessoa = _personRepository.findById(id);
        if(oldPessoa.isPresent()){
            PersonEntity person = oldPessoa.get();
            person.setName(newPerson.getName());
            _personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable(value = "id") long id)
    {
        Optional<PersonEntity> pessoa = _personRepository.findById(id);
        if(pessoa.isPresent()){
            _personRepository.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
