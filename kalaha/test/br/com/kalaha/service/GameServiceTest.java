package br.com.kalaha.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GameServiceTest {

	private static WebTarget service;
	private static final String URL = "http://localhost:8080/kalaha/rest/game";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		service = client.target(UriBuilder.fromUri(URL).build());
	}

	@Test
	void testStartGame() {
		// WHEN
		Response response = service.path("/startGame").request().get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode());
	}

	@Test
	void testChooseEmptyPit() {
		// GIVEN
		Integer player = 0;
		Integer pit = 0;
		// WHEN
		Response response = service.path("/choosePit")
				.queryParam("player", player)
				.queryParam("pit", pit).request().get();
		
		// if pit was not empty, try again
		if (response.getStatusInfo().getStatusCode() == 
				Response.Status.OK.getStatusCode()) {
			response = service.path("/choosePit")
					.queryParam("player", player)
					.queryParam("pit", pit).request().get();
		}
		
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.PRECONDITION_FAILED.getStatusCode());
		
	}

	@Test
	void testChooseInvalidRangePit() {
		// GIVEN
		Integer player = 0;
		Integer pit = -1;
		// WHEN
		Response response = service.path("/choosePit")
				.queryParam("player", player)
				.queryParam("pit", pit).request().get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	void testChooseValidPit() {
		// GIVEN
		Integer player = 0;
		Integer pit = 0;
		// WHEN
		Response response = service.path("/choosePit")
				.queryParam("player", player)
				.queryParam("pit", pit).request().get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode());
	}

}
