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
import br.com.kalaha.dto.PlayerDTO;
import br.com.kalaha.util.Constants;

@Path("/game")
public class GameService {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;
	
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
	
	// Encontrar lado oposto: 5 - chosenPit
	//
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/choosePit")
	public Response choosePit(@QueryParam("player") int playerIndex, @QueryParam("pit") int pitIndex) {
		GameDTO game = null;
		HttpSession session = null;
		
		try { // Check if there is a session already
			session = request.getSession();
			game = (GameDTO) session.getAttribute("game");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		// Check if the indexes are on the acceptable range
		if (playerIndex < 0 || playerIndex >= 2 ||
				pitIndex < 0 || pitIndex >= Constants.NUMBER_OF_PITS)
			return Response.status(Status.BAD_REQUEST).build();
		
		
		PlayerDTO player = game.getPlayers().get(playerIndex);
		
		// Empty chosen pit
		Integer totalStones = player.getPits().get(pitIndex);
		if (totalStones <= 0)
			return Response.status(Status.PRECONDITION_FAILED).build();
		
		player.getPits().set(pitIndex, 0);
		
		// Sow following pits
		int i = 1;
		int pitValue = 0;
		Integer nextPitIndex = pitIndex;
		Integer nextPlayerIndex = playerIndex;
		do {
			nextPitIndex = (pitIndex + i) % Constants.NUMBER_OF_PITS; // Does not depend on player
			
			nextPlayerIndex = (nextPitIndex == 0) ? // Check if is sowing on the 'other' side
					(nextPlayerIndex == 0 ? 1 : 0) : // If it is, check which side is the 'other' side
					nextPlayerIndex; // If it is not, nextPlayer is the same one as last iteration
			
			// Scores if current player finishes all his/hers small pits
			if (nextPitIndex == 0 && nextPlayerIndex != playerIndex) {
				pitValue = game.getPlayers().get(playerIndex).getScore();
				game.getPlayers().get(playerIndex).setScore(pitValue + 1);
				
				// Return if there are no more stones
				if (--totalStones <= 0) break;
			}
			
			// Sow stone in the next pit
			pitValue = game.getPlayers().get(nextPlayerIndex).getPits().get(nextPitIndex);
			game.getPlayers().get(nextPlayerIndex).getPits().set(nextPitIndex, pitValue + 1);
			
			i++;
		} while (--totalStones > 0);
		
		// Update game
		
		session.setAttribute("game", game);
		
		GenericEntity<GameDTO> entity = new GenericEntity<GameDTO>(game) {};
		return Response.ok().entity(entity).build();
	}
	
}
