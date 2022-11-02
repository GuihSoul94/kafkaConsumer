package br.com.kafka.exemplo.kafkaconsumer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDTO {
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String phone;

}
