package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CreditAccount;


public interface ProductoDao extends ReactiveMongoRepository<CreditAccount, String> {

	
	/*@Query("{ 'numero_cuenta' : ?0 }")
	Flux<Producto> viewNumTarjeta(String numero_cuenta);*/
	
	@Query("{ 'numero_cuenta' : ?0 , 'codigo_bancario': ?1}")
	Mono<CreditAccount> viewNumTarjeta(String numero_cuenta, String codigo_bancario);
	
	@Query("{ 'dni' : ?0 }")
	Flux<CreditAccount> viewDniCliente(String dni);
	
}
