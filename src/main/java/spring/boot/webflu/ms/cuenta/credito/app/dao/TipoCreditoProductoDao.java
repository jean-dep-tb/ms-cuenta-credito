package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TipoCuentaCredito;

public interface TipoCreditoProductoDao extends ReactiveMongoRepository<TipoCuentaCredito, String> {
	
//	@Query("{'idTipo' : ?0}")
//	public Mono<TipoCuentaCredito> findByIdTipoCliente(String id);
	
	public Mono<TipoCuentaCredito> findByIdTipo(String id);
	
}
