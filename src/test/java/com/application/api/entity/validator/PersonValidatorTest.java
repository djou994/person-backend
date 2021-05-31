package com.application.api.entity.validator;

import com.application.api.repository.PersonRepository;

import com.application.api.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class PersonValidatorTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonValidator validator;
    private PersonEntity entity;
    public Errors errors;

    @BeforeEach
    void setUp() {
        entity = new PersonEntity();
        errors = new BeanPropertyBindingResult(entity, "cpf");
    }

    @Test
    void validateIsValid() {
        entity.setCpf("00000000000");
        validator.validate(entity, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    void validateIsInvalid() {
        entity.setCpf("30603224067");
        entity.setEmail("teste@teste.com");
        validator.validate(entity, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void cpfIsNull() {
        entity.setCpf(null);
        validator.validate(entity, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    void emailIsValid() {
        entity.setCpf("30603224067");
        entity.setEmail("teste@teste.com");
        validator.validate(entity, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void emailIsInvalid() {
        entity.setCpf("30603224067");
        entity.setEmail("teste");
        validator.validate(entity, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    void emailIsNull() {
        entity.setCpf("30603224067");
        entity.setEmail(null);
        validator.validate(entity, errors);
        assertTrue(errors.hasErrors());
    }
}