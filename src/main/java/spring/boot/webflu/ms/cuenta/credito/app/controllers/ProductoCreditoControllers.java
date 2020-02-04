package spring.boot.webflu.ms.cuenta.credito.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TipoCuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.dto.CuentaCreditoDto;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductoCreditoService;
import spring.boot.webflu.ms.cuenta.credito.app.service.TipoCreditoProductoService;

@RequestMapping("/api/ProductoCredito")
@RestController
public class ProductoCreditoControllers {

	@Autowired
	private ProductoCreditoService productoService;
	
	@Autowired
	private TipoCreditoProductoService tipoProductoService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<CuentaCredito>>> findAll() 
	{
		return Mono.just(
			ResponseEntity
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(productoService.findAllProducto())
			
			);
	}
		
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CuentaCredito>> viewId(@PathVariable String id){
		return productoService.findByIdProducto(id).map(p-> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());	
	}
	
	
	@PutMapping
	public Mono<CuentaCredito> updateProducto(@RequestBody CuentaCredito producto)
	{
		System.out.println(producto.toString());
		return productoService.saveProductoCredito(producto);
	}	
		
	@PostMapping
	public Mono<CuentaCredito> registrarProductoCredito(@RequestBody CuentaCredito pro){
		//BUSCA SI EL TIPO DE CREDITO EXISTE
		Mono<TipoCuentaCredito> tipo= tipoProductoService.findByIdTipoProducto(pro.getTipoProducto().getIdTipo());
			return tipo
					.defaultIfEmpty(new TipoCuentaCredito())
					.flatMap(c -> {
						if (c.getIdTipo() ==null) {
							return Mono.error(new InterruptedException("NO EXISTE ESTE TIPO"));
						}
							return Mono.just(c);
					})
					.flatMap(t->{						
						pro.setTipoProducto(t);
						return productoService.saveProductoCredito(pro);
					});
	}
	
	// GUARDA CUENTA PRODUCTO BANCO ---> 
	@PostMapping("/guardarCredito")
	public Mono<CuentaCredito> guardarProBanco(@RequestBody CuentaCredito cuentaCredito) {
		return productoService.saveProductoCredito(cuentaCredito);
	}
	
	//CUENTAS SIN DEUDA
	@GetMapping("/dniSinDeuda/{dni}")
	public Flux<CuentaCredito> cuentasSinDeuda(@PathVariable String dni) {
			Flux<CuentaCredito> credito = productoService.cuentaSinConsumo(dni);
			return credito;

	}
	
	@GetMapping("/dni/{dni}")
	public Flux<CuentaCredito> productosCreditoCliente(@PathVariable String dni) {
		Flux<CuentaCredito> credito = productoService.productoCreditoCliente(dni);
		return credito;
	}
	
	//RETIRO DE CREDITO - SERVICIO CONSUMIDO DESDE MS-OP-CREDITO
	@PutMapping("/consumo/{monto}/{numero_cuenta}/{codigo_bancario}")
	public Mono<CuentaCredito> retiroCredito(@PathVariable Double monto,@PathVariable String numero_cuenta,@PathVariable String codigo_bancario) {
			return productoService.consumosCredito(monto, numero_cuenta,codigo_bancario);
	}
	
	//REALIZO UN DEPOSITO CREDITO - CONSUMIDO DESDE MS-OP-CREDITO
	@PutMapping("/pago/{numero_cuenta}/{monto}/{codigo_bancario}")
	public Mono<CuentaCredito> despositoCredito(@PathVariable Double monto,@PathVariable String numero_cuenta,@PathVariable String codigo_bancario) {		
			return productoService.pagosCredito(monto, numero_cuenta,codigo_bancario);
	}
	
	//MUESTRA LA CUENTA BANCARIA CON EL NUMERO DE CUENTA
	@GetMapping("/numero_cuenta/{numero_cuenta}/{codigo_bancario}")
	public Mono<CuentaCredito> cuentaBancariaCredito(@PathVariable String numero_cuenta, @PathVariable String codigo_bancario) {
		Mono<CuentaCredito> credito = productoService.productosCredito(numero_cuenta, codigo_bancario);
		return credito;
	}
	
	//MUESTRA LOS SALDOS - POR NUMERO DE CUENTA
	@GetMapping("/saldoDisponible/{numero_cuenta}/{codigo_bancario}")
	public Mono<CuentaCreditoDto> saldosCredito(@PathVariable String numero_cuenta, @PathVariable String codigo_bancario) {

		Mono<CuentaCredito> operacion = productoService.productosCredito(numero_cuenta, codigo_bancario);

		return operacion.flatMap(c -> {

			CuentaCreditoDto cd = new CuentaCreditoDto();
			
				cd.setDni(c.getDni());
				cd.setNumero_cuenta(c.getNumero_cuenta());
				cd.setSaldo(c.getSaldo());
		
				cd.setConsumo(c.getConsumo());
				cd.setCredito(c.getCredito());
			
			return Mono.just(cd);
		});

	}
	
}
