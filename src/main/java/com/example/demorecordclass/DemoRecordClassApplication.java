package com.example.demorecordclass;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SpringBootApplication
public class DemoRecordClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRecordClassApplication.class, args);
    }

}

@RestController
@RequestMapping("/person")
record PersonController(PersonService personService) {

    @PostMapping("/create")
    public ResponseEntity<String> save(@RequestBody PersonCreateDto dto) {
        personService.save(dto);
        return ResponseEntity.ok("Created successfully!");
    }
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class PersonCreateDto {
    private String firstName;
    private String lastName;
}

interface PersonRepository extends JpaRepository<Person, Long> {
}

@Service
record PersonService(PersonRepository testRepository) {
    public void save(PersonCreateDto dto) {
        Person person = Person.builder().firstName(dto.getFirstName()).lastName(dto.getLastName()).build();
        testRepository.save(person);
    }
}

