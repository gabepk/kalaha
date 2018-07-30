package br.com.kalaha.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.kalaha.dto.GameDTO;
import br.com.kalaha.dto.PlayerDTO;

@Path("/game")
public class GameService {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;
	
	@GET
	@Path("/startGame")
	public Response startGame() {
		try {
			// Check if there is a session already
			HttpSession session = request.getSession();
			GameDTO game = new GameDTO();
			game.setPits(6);
			game.setStones(6);
			game.setWinner(null);
			PlayerDTO[] players = new PlayerDTO[2];
			game.setPlayers(players);
			
			// Set new empty game
			session.setAttribute("game", game);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/choosePit")
	public Response choosePit(@QueryParam("player") Integer playerIndex, @QueryParam("pit") Integer pitIndex) {
		try {
			// Check if there is a session already
			HttpSession session = request.getSession();
			GameDTO game = (GameDTO) session.getAttribute("game");
			PlayerDTO player = game.getPlayer(playerIndex);
			player.setChosenPit(pitIndex);
			
			// Updates one step
			
			
			
			// Update game
			session.setAttribute("game", game);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
