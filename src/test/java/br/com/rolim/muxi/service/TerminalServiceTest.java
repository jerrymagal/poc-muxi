package br.com.rolim.muxi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rolim.muxi.entity.Terminal;
import br.com.rolim.muxi.exception.BusinessException;
import br.com.rolim.muxi.repository.TerminalRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TerminalServiceTest {

	@InjectMocks
	private TerminalService service;

	@Mock
	private TerminalRepository repository;

	private Terminal terminal;

	@BeforeAll
	public void setup() {
		terminal = Terminal.builder().logic(44332511).serial("22569").model("PWWIN").sam(7).ptid("F04A2E4088B").plat(4)
				.version("8.00b3").mxr(0).mxf(16777216).VERFM("PWWIN").build();
	}

	@Test
	void create() throws BusinessException {

		when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		when(repository.save(Mockito.any())).thenReturn(terminal);

		Terminal terminalSalvo = service.create(terminal);

		assertEquals(terminalSalvo.getLogic(), 44332511);
	}
	
	@Test
	void createExistsException() throws BusinessException {

		when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(terminal));
		
		assertThrows(BusinessException.class, () -> {
			service.create(terminal);
		});
	}
	
	@Test
	void findByLogic() {
		
		when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(terminal));
		
		Integer logic = 44332511;
		
		Optional<Terminal> optional = service.findByLogic(logic);
		
		assertEquals(optional.get().getLogic(), logic);
	}
	
	@Test
	void update() throws BusinessException {
		Integer logic = 44332511;
		
		Terminal terminal = Terminal.builder().logic(logic).serial("222555").model("PWWIN").sam(18).ptid("F04A2E4088B").plat(4)
				.version("8.00b3").mxr(0).mxf(16777216).VERFM("PWWIN").build();

		when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(terminal));
		when(repository.save(Mockito.any())).thenReturn(terminal);
		
		Terminal terminal2 = service.update(logic, terminal);
		
		assertEquals(terminal2.getSerial(), "222555");
		
	}
	
	@Test
	void updateLogicNotExistsException() throws BusinessException {
		when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		
		assertThrows(BusinessException.class, () -> {
			service.update(44332511, terminal);
		});
		
	}

}
