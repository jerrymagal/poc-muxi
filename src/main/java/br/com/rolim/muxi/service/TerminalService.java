package br.com.rolim.muxi.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rolim.muxi.entity.Terminal;
import br.com.rolim.muxi.exception.BusinessException;
import br.com.rolim.muxi.repository.TerminalRepository;

@Service
public class TerminalService {
	
	@Autowired
	private TerminalRepository repository;
	
	public Terminal create(Terminal terminal) throws BusinessException {
		
		Optional<Terminal> optional = repository.findById(terminal.getLogic());
		
		if(optional.isPresent()) {
			throw new BusinessException("Terminal já cadastrado");
		}
		
		return repository.save(terminal);
	}
	
	public Optional<Terminal> findByLogic(Integer logic) {
		return repository.findById(logic);
	}
	
	public Terminal update(Integer logic, Terminal terminal) throws BusinessException {
		
		Optional<Terminal> optional = repository.findById(logic);
		
		if(optional.isEmpty()) {
			throw new BusinessException("Logic não cadastrado");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		
		Terminal newTerminal = modelMapper.map(terminal, Terminal.class);
		
		return repository.save(newTerminal);	
	}
}
