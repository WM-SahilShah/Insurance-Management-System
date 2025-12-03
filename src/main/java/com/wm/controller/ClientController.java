package com.wm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wm.dto.ClientDto;
import com.wm.service.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients/")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> updateClient(@Valid @RequestBody ClientDto client, @PathVariable("id") Integer id){
		return new ResponseEntity<ClientDto>(clientService.updateClientInfo(client, id),HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClient( @PathVariable("id") Integer id){
	return new ResponseEntity<String>(clientService.deleteClient(id),HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Integer id){
	return new ResponseEntity<ClientDto>(clientService.findById(id),HttpStatus.OK);
	}

	@GetMapping("/email/")
	public ResponseEntity<ClientDto> getClientByEmail(@RequestParam("email") String email){
	return new ResponseEntity<ClientDto>(clientService.findByEmail(email),HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<ClientDto>> getAllClient(){
	return new ResponseEntity<List<ClientDto>>(clientService.findAllClient(),HttpStatus.OK);
	}

}
