package spring.boot.webflu.ms.cuenta.credito.app.documents;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection ="ProductoCredito")
public class CuentaCredito {
	

	@Id
	@NotEmpty
	private String id;
	@NotEmpty
	private String numero_cuenta;
	@NotEmpty
	private String dni;
	
	@NotEmpty
	private TipoCuentaCredito tipoProducto;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha_afiliacion;
	
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fecha_caducidad;
	@NotEmpty
	private Double credito;
	@NotEmpty
	private Double saldo;
	@NotEmpty
	private Double consumo;
	@NotEmpty
	private String usuario;
	@NotEmpty
	private String clave;
	
	@NotEmpty
	private String codigo_bancario;
	
	public CuentaCredito() {
	
	}

	public CuentaCredito(String numero_cuenta,String dni,TipoCuentaCredito tipoProducto,
			Double credito,Double saldo, Double consumo,String codigo_bancario) {
		this.numero_cuenta = numero_cuenta;
		this.dni = dni;
		this.tipoProducto = tipoProducto;
		this.credito = credito;
		this.saldo = saldo;
		this.consumo = consumo;
		this.codigo_bancario = codigo_bancario;
	}
	
	//private tipoProducto tipoCliente;
	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
//	public Date fecha_afiliacion() {
//		return fecha_afiliacion;
//	}
//	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
//	public Date fecha_caducidad() {
//		return fecha_caducidad;
//	}
	
}










