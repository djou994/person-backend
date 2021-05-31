package com.application.api.entity.validator;

import com.application.api.entity.PersonEntity;
import com.application.api.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator {

    final
    PersonRepository repository;

    private PersonValidator(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PersonEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonEntity person = (PersonEntity) target;
        if(person.getCpf() == null) {
            errors.rejectValue("cpf", "O CPF deve ser informado");
        } else {
            verifyCpf(errors, person);
            verifyDuplication(errors, person);
        }
        if(!verifyEmail(person.getEmail(), errors)){
            errors.rejectValue("email", "O e-mail é inválido");
        }
    }

    private void verifyCpf(Errors errors, PersonEntity person) {
        if (!CpfValidator.isCPF(person.getCpf())) {
            errors.rejectValue("cpf", "CPF inválido");
        }
    }

    private void verifyDuplication(Errors errors, PersonEntity person) {
        boolean isDuplicated = repository.findFirstByCpf(person.getCpf()) != null;
        if (isDuplicated){
            errors.rejectValue("cpf", "Já existe cadastro para este CPF");
        }
    }

    public static boolean verifyEmail(String email, Errors errors) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        } else {
            errors.rejectValue("email", "O e-mail deve ser informado");
        }
        return isEmailIdValid;
    }
}
