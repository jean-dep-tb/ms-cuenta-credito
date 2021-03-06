package spring.boot.webflu.ms.cuenta.credito.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.dao.ProductoDao;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CreditAccount;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	public ProductoDao productoDao;
	
	@Override
	public Flux<CreditAccount> findAllProducto()
	{
	return productoDao.findAll();
	}
	
	@Override
	public Mono<CreditAccount> findByIdProducto(String id)
	{
	return productoDao.findById(id);
	}
	
	@Override
	public Mono<CreditAccount> saveProducto(CreditAccount clientePersonal)
	{
	return productoDao.save(clientePersonal);
	}
	
	@Override
	public Mono<Void> deleteProducto(CreditAccount prod) {
		// TODO Auto-generated method stub
		return productoDao.delete(prod);
	}
	
	@Override
	public Mono<CreditAccount> consumos(Double monto, String numTarjeta, String codigo_bancario) {
		//verifica si existe el numero de cuenta
		return productoDao.viewNumTarjeta(numTarjeta, codigo_bancario).flatMap(c -> {
			
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
	public Mono<CreditAccount> pagos(Double monto, String numTarjeta, String codigo_bancario) {
		return productoDao.viewNumTarjeta(numTarjeta, codigo_bancario).flatMap(c -> {
			
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
	public Mono<CreditAccount> listProdNumTarj(String num, String codigo_bancario) {
		return productoDao.viewNumTarjeta(num, codigo_bancario);
	}
	
	@Override
	public Flux<CreditAccount> findAllProductoByDniCliente(String dni) {
		return productoDao.viewDniCliente(dni);
	}

}
