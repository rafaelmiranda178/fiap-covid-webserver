package br.com.fiap.billing.api.person.web

import br.com.fiap.billing.api.core.exception.ResourceNotFoundException
import br.com.fiap.billing.api.person.Person
import br.com.fiap.billing.api.person.PersonService
import br.com.fiap.billing.api.person.PersonSummary
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/help-covid/persons")
class PersonRestService(private val service: PersonService) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable id: Long): Person {
        val entity = service.getById(id)

        if (!entity.active) {
            throw ResourceNotFoundException("Resource with id $id was not found.")
        }

        return entity;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<Person> {
        return service.findAll()
                .filter { it -> it.active }
    }


    @GetMapping("/summary")
    @ResponseStatus(HttpStatus.OK)
    fun findAllSummary(): List<PersonSummary> {
        return service.findAllSummary()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody person: Person): Person {
        return service.create(person)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun put(@PathVariable id: Long,
            @RequestBody person: Person): Person {
        return service.update(id, person)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }

}