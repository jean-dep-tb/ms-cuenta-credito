package spring.boot.webflu.ms.cuenta.credito.app.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CreditAccount;

public interface ProductoService {

	Flux<CreditAccount> findAllProducto();
	Mono<CreditAccount> findByIdProducto(String id);
	Mono<CreditAccount> saveProducto(CreditAccount clientePersonal);

	Mono<CreditAccount> consumos(Double monto, String numero_cuenta, String codigo_bancario);
	
	Mono<CreditAccount> pagos(Double monto, String numero_cuenta, String codigo_bancario);

	Mono<CreditAccount> listProdNumTarj(String numero_cuenta, String codigo_bancario);
	
	Flux<CreditAccount> findAllProductoByDniCliente(String dni);
	
	Mono<Void> deleteProducto(CreditAccount prod);
	
}
