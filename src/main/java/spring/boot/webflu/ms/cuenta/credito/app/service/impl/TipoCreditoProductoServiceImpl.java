package spring.boot.webflu.ms.cuenta.credito.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.dao.TipoCreditoProductoDao;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TipoCuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.service.TipoCreditoProductoService;

@Service
public class TipoCreditoProductoServiceImpl implements TipoCreditoProductoService{

	
	@Autowired
	public TipoCreditoProductoDao  tipoProductoDao;
	
	@Override
	public Flux<TipoCuentaCredito> findAllTipoproducto()
	{
	return tipoProductoDao.findAll();
	
	}
	@Override
	public Mono<TipoCuentaCredito> findByIdTipoProducto(String id)
	{
		//return tipoProductoDao.findById(id);
		return tipoProductoDao.findByIdTipo(id);
	
	}
	
	@Override
	public Mono<TipoCuentaCredito> saveTipoProducto(TipoCuentaCredito tipoCliente)
	{
		return tipoProductoDao.save(tipoCliente);
	}
	
	@Override
	public Mono<Void> deleteTipo(TipoCuentaCredito tipoProducto) {
		return tipoProductoDao.delete(tipoProducto);
	}
	
}
