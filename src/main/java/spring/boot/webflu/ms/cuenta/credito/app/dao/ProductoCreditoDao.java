package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CuentaCredito;


public interface ProductoCreditoDao extends ReactiveMongoRepository<CuentaCredito, String> {

	
	/*@Query("{ 'numero_cuenta' : ?0 }")
	Flux<Producto> viewNumTarjeta(String numero_cuenta);*/
	
//	@Query("{ 'numero_cuenta' : ?0 , 'codigo_bancario': ?1}")
//	Mono<CuentaCredito> viewCuentaBanco(String numero_cuenta, String codigo_bancario);
	
	Mono<CuentaCredito> findByNumeroCuentaAndCodigoBanco(String numero_cuenta, String codigo_bancario);
	
//	Mono<CuentaCredito> findByNumero_cuentaAndCodigo_bancario(String numero_cuenta, String codigo_bancario);
	
//	@Query("{ 'dni' : ?0 }")
//	Flux<CuentaCredito> viewDniCliente(String dni);
	
	Flux<CuentaCredito> findByDni(String dni);
	
	@Query("{ 'dni' : ?0 , $where : 'this.consumo > 0'}  }")
	Flux<CuentaCredito> verDeudaCredito(String dni);
	
}
