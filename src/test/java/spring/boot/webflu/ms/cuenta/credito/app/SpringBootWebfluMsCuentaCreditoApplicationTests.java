package spring.boot.webflu.ms.cuenta.credito.app;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.cuenta.credito.app.documents.CuentaCredito;
import spring.boot.webflu.ms.cuenta.credito.app.documents.TipoCuentaCredito;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWebfluMsCuentaCreditoApplicationTests {	
	
	@Autowired
	private WebTestClient client;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void listarCuentaCredito() {
		client.get().uri("/api/ProductoCredito")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk() 
		.expectHeader().contentType(MediaType.APPLICATION_JSON) //.hasSize(2);
		.expectBodyList(CuentaCredito.class).consumeWith(response -> {
			
			List<CuentaCredito> cuentaCredito = response.getResponseBody();
			
			cuentaCredito.forEach(p -> {
				System.out.println(p.getNumeroCuenta());
			});
			
			Assertions.assertThat(cuentaCredito.size() > 0).isTrue();
		});
	}
	
	@Test
	void crearCuentaBanco() {
		
		TipoCuentaCredito tpCredito = new TipoCuentaCredito();
		tpCredito.setIdTipo("1");
		tpCredito.setDescripcion("personal");
		
		CuentaCredito ctCredito = new CuentaCredito();		
		ctCredito.setNumeroCuenta("900001");
		ctCredito.setDni("47305710");
		ctCredito.setTipoProducto(tpCredito);
		ctCredito.setFecha_afiliacion("2020-02-03");
		ctCredito.setFecha_caducidad("2020-02-03");
		ctCredito.setSaldo(9.0);
		ctCredito.setUsuario("jean");
		ctCredito.setClave("123");
		ctCredito.setCodigoBanco("bcp");
		
		client.post()
		.uri("/api/ProductoCredito/guardarCredito")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(ctCredito), CuentaCredito.class)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody(CuentaCredito.class)
		.consumeWith(response -> {
			CuentaCredito b = response.getResponseBody();
			Assertions.assertThat(b.getDni()).isNotEmpty().isEqualTo("47305710");
			Assertions.assertThat(b.getNumeroCuenta()).isNotEmpty().isEqualTo("900001");
			Assertions.assertThat(b.getTipoProducto().getDescripcion()).isNotEmpty().isEqualTo("personal");
			Assertions.assertThat(b.getFecha_afiliacion()).isNotEmpty().isEqualTo("2020-02-03");
			Assertions.assertThat(b.getFecha_caducidad()).isNotEmpty().isEqualTo("2020-02-03");
			Assertions.assertThat(b.getSaldo()).isEqualTo(9.0);
			Assertions.assertThat(b.getUsuario()).isNotEmpty().isEqualTo("jean");
			Assertions.assertThat(b.getClave()).isNotEmpty().isEqualTo("123");
			Assertions.assertThat(b.getCodigoBanco()).isEqualTo("bcp");
		});
	}
	
	

}
