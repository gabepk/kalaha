package br.com.kalaha.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.kalaha.dto.GameDTO;
import br.com.kalaha.util.Constants;

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
	void testGetGameInSessionGameNotFound() {
		// GIVEN
		Response givenResponse = service.path("/finishGame").request().get();
		assertTrue(givenResponse.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode());
		// WHEN
		Map<String, NewCookie> cookies = givenResponse.getCookies();
		Invocation.Builder ib = service.path("/getGameInSession").request();
		for (NewCookie cookie: cookies.values())
			ib.cookie(cookie.toCookie());
		Response response = ib.get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	void testGetGameInSessionSucess() {
		// GIVEN
		Response givenResponse = service.path("/startGame").request().get();
		assertTrue(givenResponse.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode());
		// WHEN
		Map<String, NewCookie> cookies = givenResponse.getCookies();
		Invocation.Builder ib = service.path("/getGameInSession").request();
		for (NewCookie cookie: cookies.values())
			ib.cookie(cookie.toCookie());
		Response response = ib.get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode());
	}

	@Test
	void testStartGameSucess() {
		// WHEN
		Response response = service.path("/startGame").request().get();
		// THEN
		try {
			GameDTO game = response.readEntity(GameDTO.class);
			assertTrue(game != null);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should be able to read Entity GameDTO");
		}
	}

	@Test
	void testChooseEmptyPit() {
		// GIVEN
		Integer player = 0;
		Integer pit = 0;
		Response givenResponse = service.path("/startGame").request().get();
		
		// WHEN
		Map<String, NewCookie> cookies = givenResponse.getCookies();
		Invocation.Builder ib = service.path("/choosePit")
				.queryParam("player", player)
				.queryParam("pit", pit).request();
		for (NewCookie cookie: cookies.values())
			ib.cookie(cookie.toCookie());
		Response response = ib.get();
		
		if (response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
			response = ib.get(); // if pit was not empty, try again
		}
		
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.PRECONDITION_FAILED.getStatusCode());
		
	}

	@Test
	void testChoosePitOutOfRange() {
		// GIVEN
		Integer player = 0;
		Integer pit = Constants.NUMBER_OF_PITS + 10;
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
		assertTrue(response.getStatusInfo().getStatusCode() != Response.Status.BAD_REQUEST.getStatusCode());
	}
	
	@Test
	void testGetStonesFromOpponentValidPit() {
		// GIVEN
		Integer player = 0;
		Integer pit = 0;
		// WHEN
		Response response = service.path("/getStonesFromOpponent")
				.queryParam("player", player)
				.queryParam("pit", pit).request().get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() != Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	void testGetStonesFromOpponentPitOutOfRange() {
		// GIVEN
		Integer player = 0;
		Integer pit = Constants.NUMBER_OF_PITS + 10;
		// WHEN
		Response response = service.path("/getStonesFromOpponent")
				.queryParam("player", player)
				.queryParam("pit", pit).request().get();
		// THEN
		assertTrue(response.getStatusInfo().getStatusCode() == Response.Status.BAD_REQUEST.getStatusCode());
	}
	
}
