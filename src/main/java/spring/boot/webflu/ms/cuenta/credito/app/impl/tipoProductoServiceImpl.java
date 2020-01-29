package spring.boot.webflu.ms.cuenta.credito.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.dao.TipoProductoDao;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditAccount;
import spring.boot.webflu.ms.cuenta.credito.app.service.TipoProductoService;

@Service
public class tipoProductoServiceImpl implements TipoProductoService{

	
	@Autowired
	public TipoProductoDao  tipoProductoDao;
	
	@Override
	public Flux<TypeCreditAccount> findAllTipoproducto()
	{
	return tipoProductoDao.findAll();
	
	}
	@Override
	public Mono<TypeCreditAccount> findByIdTipoProducto(String id)
	{
		//return tipoProductoDao.findById(id);
		return tipoProductoDao.findByIdTipoCliente(id);
	
	}
	
	@Override
	public Mono<TypeCreditAccount> saveTipoProducto(TypeCreditAccount tipoCliente)
	{
		return tipoProductoDao.save(tipoCliente);
	}
	
	@Override
	public Mono<Void> deleteTipo(TypeCreditAccount tipoProducto) {
		return tipoProductoDao.delete(tipoProducto);
	}
	
}
