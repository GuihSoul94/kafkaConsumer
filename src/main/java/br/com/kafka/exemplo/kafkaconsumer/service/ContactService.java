package br.com.kafka.exemplo.kafkaconsumer.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.kafka.exemplo.kafkaconsumer.entity.Contact;
import br.com.kafka.exemplo.kafkaconsumer.infrastructure.IContact;

@Service
public class ContactService {
	
	@Autowired
	private IContact contactRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void insertContact(Contact contact) {
		try {
			contactRepository.save(contact);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void updateContact(Contact contact) {
		try {
			contactRepository.save(contact);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteContact(Integer id) {
		try {
			contactRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public List<Contact> listContact() {
		try {
			return contactRepository.findAll();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}

}
