package spring.boot.webflu.ms.cuenta.credito.app.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditAccount;

public interface TipoProductoDao extends ReactiveMongoRepository<TypeCreditAccount, String> {
	
	@Query("{'idTipo' : ?0}")
	public Mono<TypeCreditAccount> findByIdTipoCliente(String id);
	
}
