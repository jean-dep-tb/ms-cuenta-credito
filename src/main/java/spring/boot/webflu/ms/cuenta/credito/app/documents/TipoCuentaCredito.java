package spring.boot.webflu.ms.cuenta.credito.app.documents;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection ="tipoProducto")
public class TipoCuentaCredito {

	@NotEmpty
	private String idTipo;
	@NotEmpty
	private String descripcion;
	
	public TipoCuentaCredito() {

	}

	public TipoCuentaCredito(String idTipo, String descripcion) {
		this.idTipo = idTipo;
		this.descripcion = descripcion;
	}
	
	
	
	
}
