package spring.boot.webflu.ms.cuenta.credito.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TipoCuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductoCreditoService;
import spring.boot.webflu.ms.cuenta.credito.app.service.TipoCreditoProductoService;

@SpringBootApplication
public class SpringBootWebfluMsCuentaCreditoApplication implements CommandLineRunner{

	@Autowired
	private ProductoCreditoService serviceCredito;
	
	@Autowired
	private TipoCreditoProductoService serviceTipoCredito;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluMsCuentaCreditoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluMsCuentaCreditoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("ProductoCredito").subscribe();
		mongoTemplate.dropCollection("tipoProducto").subscribe();
		
		TipoCuentaCredito personal = new TipoCuentaCredito("1","personal");
		TipoCuentaCredito empresarial = new TipoCuentaCredito("2","empresarial");
		TipoCuentaCredito tarjeta = new TipoCuentaCredito("3","tarjeta");
		TipoCuentaCredito adelantoEfectivo = new TipoCuentaCredito("4","adelantoEfectivo");
		
		//
		Flux.just(personal,empresarial,tarjeta,adelantoEfectivo)
		.flatMap(serviceTipoCredito::saveTipoProducto)
		.doOnNext(c -> {
			log.info("Tipo de producto creado: " +  c.getDescripcion() + ", Id: " + c.getIdTipo());
		}).thenMany(					
				Flux.just(
						new CuentaCredito("100001","47305710",personal,5000.0,5000.0,0.0,"bcp"),
						new CuentaCredito("100002","47305711",empresarial,2000.0,2000.0,.0,"bcp"),
						new CuentaCredito("100003","47305712",tarjeta,5000.0,4000.0,1000.0,"bcp"),
						new CuentaCredito("100004","47305713",adelantoEfectivo,6000.0,1500.0,3500.0,"bbva"),
						new CuentaCredito("100005","47305714",personal,3000.0,2000.0,1000.0,"bcp")
						
						)					
					.flatMap(procredito -> {
						return serviceCredito.saveProductoCredito(procredito);
					})					
				).subscribe(procredito -> log.info("Insert: " + procredito.getId() + " " + procredito.getNumeroCuenta()));
		
		
	}

}
