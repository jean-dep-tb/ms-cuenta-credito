package spring.boot.webflu.ms.cuenta.credito.app.documents;


import java.util.Date;

import javax.validation.constraints.NotEmpty;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection ="ProductoCredito")
public class CreditAccount {
	

	@Id
	@NotEmpty
	private String id;
	@NotEmpty
	private String numero_cuenta;
	@NotEmpty
	private String dni;
	
	@NotEmpty
	private TypeCreditAccount tipoProducto;
	
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
	
	public CreditAccount() {
	
	}

	public CreditAccount(String numero_cuenta,String dni,TypeCreditAccount tipoProducto,
			Double credito,Double saldo, Double consumo) {
		this.numero_cuenta = numero_cuenta;
		this.dni = dni;
		this.tipoProducto = tipoProducto;
		this.credito = credito;
		this.saldo = saldo;
		this.consumo = consumo;
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










