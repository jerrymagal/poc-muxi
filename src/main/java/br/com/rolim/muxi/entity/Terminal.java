package br.com.rolim.muxi.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Terminal implements BaseConverter<Terminal>, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@NotNull(message = "Logic required")
	private Integer logic;
	
	@NotEmpty(message = "Serial required")
	private String serial;
	
	@NotEmpty(message = "Model required")
	private String model;

	@Min(value = 0, message = "Minimum is 0")
	private Integer sam;
	
	private String ptid;
	
	private Integer plat;
	
	@NotEmpty(message = "Version required")
	private String version;
	
	private Integer mxr;
	
	private Integer mxf;
	
	private String VERFM;

	@Override
	public Terminal convert(String[] fields) {
		
		return Terminal.builder()
			.logic(fields[0].isEmpty() ? null : Integer.valueOf(fields[0]))
			.serial(fields[1].isEmpty() ? null : fields[1])
			.model(fields[2].isEmpty() ? null : fields[2])
			.sam(fields[3].isEmpty() ? null : Integer.valueOf(fields[3]))
			.ptid(fields[4].isEmpty() ? null : fields[4])
			.plat(fields[5].isEmpty() ? null : Integer.valueOf(fields[5]))
			.version(fields[6].isEmpty() ? null : fields[6])
			.mxr(fields[7].isEmpty() ? null : Integer.valueOf(fields[7]))
			.mxf(fields[8].isEmpty() ? null : Integer.valueOf(fields[8]))
			.VERFM(fields[9].isEmpty() ? null : fields[9]).build();
	}
}
