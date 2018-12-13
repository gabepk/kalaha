package br.com.kalaha.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.kalaha.dto.GameDTO;
import br.com.kalaha.util.Constants;
import br.com.kalaha.util.GameUtil;

@Path("/game")
public class GameService {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getGameInSession")
	public Response getGameInSession() {
		try {
			HttpSession session = request.getSession();
			GameDTO game = (GameDTO) session.getAttribute("game");
			
			// Game was active, but its over
			if(game == null || game.getWinner() != null) {
				session.setAttribute("game", null);
				return Response.status(Status.NOT_FOUND).build();
			}
			
			GenericEntity<GameDTO> entity = new GenericEntity<GameDTO>(game) {};
			return Response.ok().entity(entity).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/startGame")
	public Response startGame() {
		try {
			// Create a new game
			GameDTO game = new GameDTO();
			HttpSession session = request.getSession();
			session.setAttribute("game", game);
			
			GenericEntity<GameDTO> entity = new GenericEntity<GameDTO>(game) {};
			return Response.ok().entity(entity).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/choosePit")
	public Response choosePit(@QueryParam("player") int playerIndex, @QueryParam("pit") int pitIndex) {
		// Check if the indexes are on the acceptable range
		if (playerIndex < 0 || playerIndex >= 2 ||
				pitIndex < 0 || pitIndex >= Constants.NUMBER_OF_PITS)
			return Response.status(Status.BAD_REQUEST).build();
		
		GameDTO game = null;
		HttpSession session = null;
		
		try { // Check if there is a session already
			session = request.getSession();
			game = (GameDTO) session.getAttribute("game");
			
			if (game == null)
				return Response.status(Status.NOT_FOUND).build();
			
			// Check if pit is empty
			int totalStones = game.getPlayers().get(playerIndex).getPits().get(pitIndex);
			if (totalStones <= 0)
				return Response.status(Status.PRECONDITION_FAILED).build();
			
			GameUtil.playOneStep(game, totalStones, playerIndex, pitIndex);
			
			// Check if game is over
			GameUtil.checkEndOfGame(game);
			
			// Update game
			session.setAttribute("game", game);
			
			GenericEntity<GameDTO> entity = new GenericEntity<GameDTO>(game) {};
			return Response.ok().entity(entity).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getStonesFromOpponent")
	public Response getStonesFromOpponent(@QueryParam("player") int playerIndex, @QueryParam("pit") int pitIndex) {
		// Check if the indexes are on the acceptable range
		if (playerIndex < 0 || playerIndex >= 2 ||
				pitIndex < 0 || pitIndex >= Constants.NUMBER_OF_PITS)
			return Response.status(Status.BAD_REQUEST).build();
		
		GameDTO game = null;
		HttpSession session = null;
		
		try { // Check if there is a session already
			session = request.getSession();
			game = (GameDTO) session.getAttribute("game");
			
			if (game == null)
				return Response.status(Status.NOT_FOUND).build();
			
			GameUtil.getStones(game, playerIndex, pitIndex);
			
			// Check if game is over
			GameUtil.checkEndOfGame(game);
			
			GenericEntity<GameDTO> entity = new GenericEntity<GameDTO>(game) {};
			return Response.ok().entity(entity).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/setNextPlayer")
	public Response setNextPlayer(@QueryParam("nextPlayer") Integer nextPlayer) {
		try {
			HttpSession session = request.getSession();
			GameDTO game = (GameDTO) session.getAttribute("game");
			game.setNextPlayer(nextPlayer);
			
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/finishGame")
	public Response finishGame() {
		try {
			HttpSession session = request.getSession();
			session.setAttribute("game", null);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
