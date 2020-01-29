package spring.boot.webflu.ms.cuenta.credito.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CreditAccount;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TypeCreditAccount;
import spring.boot.webflu.ms.cuenta.credito.app.service.ProductoService;
import spring.boot.webflu.ms.cuenta.credito.app.service.TipoProductoService;

@EnableEurekaClient
@SpringBootApplication
public class SpringBootWebfluMsCuentaCreditoApplication implements CommandLineRunner{

	@Autowired
	private ProductoService serviceCredito;
	
	@Autowired
	private TipoProductoService serviceTipoCredito;
	
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
		
		TypeCreditAccount personal = new TypeCreditAccount("1","personal");
		TypeCreditAccount empresarial = new TypeCreditAccount("2","empresarial");
		TypeCreditAccount tarjeta = new TypeCreditAccount("3","tarjeta");
		TypeCreditAccount adelantoEfectivo = new TypeCreditAccount("4","adelantoEfectivo");
		
		//
		Flux.just(personal,empresarial,tarjeta,adelantoEfectivo)
		.flatMap(serviceTipoCredito::saveTipoProducto)
		.doOnNext(c -> {
			log.info("Tipo de producto creado: " +  c.getDescripcion() + ", Id: " + c.getIdTipo());
		}).thenMany(					
				Flux.just(
						new CreditAccount("100001","47305710",personal,5000.0,2000.0,3000.0),
						new CreditAccount("100002","47305711",empresarial,5000.0,1000.0,4000.0),
						new CreditAccount("100003","47305712",tarjeta,5000.0,4000.0,1000.0),
						new CreditAccount("100004","47305713",adelantoEfectivo,5000.0,1500.0,3500.0)
						
						)					
					.flatMap(procredito -> {
						return serviceCredito.saveProducto(procredito);
					})					
				).subscribe(procredito -> log.info("Insert: " + procredito.getId() + " " + procredito.getNumero_cuenta()));
		
		
	}

}
