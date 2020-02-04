package spring.boot.webflu.ms.cuenta.credito.app.service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CuentaCredito;

public interface ProductoCreditoService {

	Flux<CuentaCredito> findAllProducto();
	Mono<CuentaCredito> findByIdProducto(String id);
	Mono<CuentaCredito> saveProductoCredito(CuentaCredito clientePersonal); //saveProducto
	Mono<CuentaCredito> consumosCredito(Double monto, String numero_cuenta, String codigo_bancario);//consumos
	Mono<CuentaCredito> pagosCredito(Double monto, String numero_cuenta, String codigo_bancario);//pagos
	Mono<CuentaCredito> productosCredito(String numero_cuenta, String codigo_bancario);//listaProductosCredito - listProdNumTarj
	Flux<CuentaCredito> productoCreditoCliente(String dni); //listarProductoCreditoCliente
	Mono<Void> delete(CuentaCredito prod); //deleteProducto
	public Flux<CuentaCredito> cuentaSinConsumo(String dni);
}
