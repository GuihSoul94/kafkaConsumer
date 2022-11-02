package br.com.kafka.exemplo.kafkaconsumer.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.kafka.exemplo.kafkaconsumer.entity.Contact;

@Repository
@Component
public interface IContact extends JpaRepository<Contact, Integer> {

}
