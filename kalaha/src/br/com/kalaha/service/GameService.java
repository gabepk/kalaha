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
	
	// Encontrar lado oposto: 5 - chosenPit
	//
	
	@GET
	@Path("/choosePit")
	public Response choosePit(@QueryParam("player") Integer playerIndex, @QueryParam("pit") Integer pitIndex) {
		try {
			// Check if there is a session already
			HttpSession session = request.getSession();
			GameDTO game = (GameDTO) session.getAttribute("game");
			PlayerDTO player = game.getPlayers()[playerIndex];
			
			// Empty chosen pit
			Integer totalStones = player.getPits()[pitIndex];
			if (totalStones <= 0)
				return Response.status(Status.PRECONDITION_FAILED).build();
			player.getPits()[pitIndex] = 0;
			
			// Sow following pits
			int i = 1;
			Integer nextPitIndex;
			Integer nextPlayerIndex = playerIndex;
			do {
				nextPitIndex = (pitIndex + i) % 6; // Does not depend on player
				
				nextPlayerIndex = ((pitIndex + i) / 6) >= 1 ? // Check if is sowing on the 'other' side
						(nextPlayerIndex == 0 ? 1 : 0) : // If it is, check which side is the 'other' side
						nextPlayerIndex; // If it is not, nextPlayer is the same one as last iteration
				
				// Scores if current player finishes all his/hers small pits
				if (nextPitIndex == 0 && nextPlayerIndex != playerIndex) {
					game.getPlayers()[playerIndex].setScore(
							game.getPlayers()[playerIndex].getScore() + 1);
					
					// Return if there are no more stones
					if (--totalStones <= 0) break;
				}
				
				// Sow stone in the next pit
				game.getPlayers()[nextPlayerIndex].getPits()[nextPitIndex]++;
				
			} while (totalStones-- > 0);
			
			// Update game
			session.setAttribute("game", game);
			
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
