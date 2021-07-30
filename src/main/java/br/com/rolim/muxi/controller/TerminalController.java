package br.com.rolim.muxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rolim.muxi.entity.Terminal;
import br.com.rolim.muxi.exception.BusinessException;
import br.com.rolim.muxi.service.TerminalService;
import br.com.rolim.muxi.validation.ValidJson;

import static br.com.rolim.muxi.validation.SchemaLocations.TERMINAL;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/terminal")
public class TerminalController {
	
	@Autowired
	private TerminalService service;
	
	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@ValidJson(TERMINAL) Terminal terminal) throws BusinessException {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(terminal));
	}
	
	@GetMapping("/{logic}")
	public ResponseEntity<?> findByLogic(@PathVariable Integer logic) {
		
		Optional<Terminal> optional = service.findByLogic(logic);
		
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PutMapping(value = "/{logic}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable Integer logic, @Valid @RequestBody Terminal terminal) throws BusinessException {
		return ResponseEntity.ok(service.update(logic, terminal));
	}
}
