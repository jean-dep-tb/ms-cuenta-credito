package spring.boot.webflu.ms.cuenta.credito.app.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditAccount;

public interface TipoProductoService {
	
	Flux<TypeCreditAccount> findAllTipoproducto();
	Mono<TypeCreditAccount> findByIdTipoProducto(String id);
	Mono<TypeCreditAccount> saveTipoProducto(TypeCreditAccount tipoProducto);
	Mono<Void> deleteTipo(TypeCreditAccount tipoProducto);
	
}
