package com.application.api.repository;

import com.application.api.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findFirstByCpf(String cpf);
}
