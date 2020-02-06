package spring.boot.webflu.ms.cuenta.credito.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.dao.ProductoCreditoDao;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductoCreditoService;

@Service
public class ProductoCreditoServiceImpl implements ProductoCreditoService {
	
	@Autowired
	public ProductoCreditoDao productoDao;
	
	@Override
	public Flux<CuentaCredito> findAllProducto()
	{
	return productoDao.findAll();
	}
	
	@Override
	public Mono<CuentaCredito> findByIdProducto(String id)
	{
	return productoDao.findById(id);
	}
	
	@Override
	public Mono<CuentaCredito> saveProductoCredito(CuentaCredito clientePersonal)
	{
	return productoDao.save(clientePersonal);
	}
	
	@Override
	public Mono<Void> delete(CuentaCredito prod) {
		// TODO Auto-generated method stub
		return productoDao.delete(prod);
	}
	
	@Override
	public Mono<CuentaCredito> consumosCredito(Double monto, String numTarjeta, String codigo_bancario) {
		//verifica si existe el numero de cuenta
		return productoDao.findByNumeroCuentaAndCodigoBanco(numTarjeta, codigo_bancario).flatMap(c -> {
			
			System.out.println("Objeto credito -->>>" + c.toString());
			System.out.println("Monto -->>>>" + monto);
			
			if (monto < c.getSaldo()) {
				c.setSaldo((c.getSaldo() - monto));
				c.setConsumo(c.getConsumo() + monto);
				return productoDao.save(c);
			}
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para realizar"
			+ " el consumo, tiene un saldo de: "+c.getSaldo() ));
		});
	}

	@Override
	public Mono<CuentaCredito> pagosCredito(Double monto, String numTarjeta, String codigo_bancario) {
		return productoDao.findByNumeroCuentaAndCodigoBanco(numTarjeta, codigo_bancario).flatMap(c -> {
			
			if (c.getConsumo() == 0) {
				return Mono.error(new InterruptedException("SIN DEUDA"));
			
			}else {
				
				//ACTUALIZANDO EL SALDO Y EL CONSUMO
				
				c.setSaldo((c.getSaldo() + monto));
				c.setConsumo(c.getConsumo() - monto);
				return productoDao.save(c);
			}
			
		});
	}
		
	@Override
	public Mono<CuentaCredito> productosCredito(String num, String codigo_bancario) {
		return productoDao.findByNumeroCuentaAndCodigoBanco(num, codigo_bancario);
	}
	
	@Override
	public Flux<CuentaCredito> productoCreditoCliente(String dni) {
		return productoDao.findByDni(dni);
	}
	
	public Flux<CuentaCredito> cuentaSinConsumo(String dni){
		return productoDao.verDeudaCredito(dni);
	}

}
