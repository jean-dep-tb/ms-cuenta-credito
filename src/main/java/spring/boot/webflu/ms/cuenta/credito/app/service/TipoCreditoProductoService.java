package spring.boot.webflu.ms.cuenta.credito.app.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TipoCuentaCredito;

public interface TipoCreditoProductoService {
	
	Flux<TipoCuentaCredito> findAllTipoproducto();
	Mono<TipoCuentaCredito> findByIdTipoProducto(String id);
	Mono<TipoCuentaCredito> saveTipoProducto(TipoCuentaCredito tipoProducto);
	Mono<Void> deleteTipo(TipoCuentaCredito tipoProducto);
	
}
