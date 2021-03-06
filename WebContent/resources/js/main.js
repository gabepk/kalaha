var game = new Vue({
	el: "#game",
	data: {
		gameIsActive: false,
		game: {
			pits: 0,
			stones: 0,
			players: [],
			winner: null,
			nextPlayer: null
		},
		winnerVal: null,
		winnerStr: '',
		nextPlayerVal: null,
		nextPlayerStr: ''
	},
	watch: {
		nextPlayerVal: function(val) {
			if (val == null) return;
			const vm = this;
			axios.get("/rest/game/setNextPlayer?nextPlayer=" + val)
					.then(response => {
				vm.nextPlayerStr = (val == 0) ? "Player 1" : "Player 2";
				
				// Block other player
				var opponentIndex = (val == 0) ? 1 : 0;
				var playerPits = document.getElementsByClassName("pit-player" + (val+1));
				var opponentPits = document.getElementsByClassName("pit-player" + (opponentIndex+1));
				for(var i=0; i<playerPits.length; i++) {
					playerPits[i].style.pointerEvents = "auto";
					playerPits[i].style.backgroundColor = "#fff";
					opponentPits[i].style.pointerEvents = "none";
					opponentPits[i].style.backgroundColor = "#ddd";
				}
				
				document.getElementsByClassName("big-pit-player"+(val+1))[0].style.backgroundColor = "#fff";
				document.getElementsByClassName("big-pit-player"+(opponentIndex+1))[0].style.backgroundColor = "#ddd";
				
			}).catch(function (error) {
				alert.showError("Error", error);
				vm.nextPlayerStr = null;
			});
		},
		winnerVal: function(val) {
			this.winnerStr = (val == 0) ? "Player 1 wins!" :
				((val == 1) ? "Player 2 wins!" : "It's a match");
			
			this.finishGame(this);
		}
	},
	methods: {
		getGameInSession() {
			const vm = this;
			axios.get("/rest/game/getGameInSession").then(response => {
				if (response.data) {
					vm.game = response.data;
					alert.showMsg("Continuing previous game");
					vm.buildGame(vm);
				} else {
					this.gameIsActive = false;
				}
			}).catch(function (error) {});
		},
		startGame() {
			const vm = this;
			axios.get("/rest/game/startGame").then(response => {
				vm.game = response.data;
				vm.buildGame(vm);
			}).catch(function (error) {
				alert.showError("Server Error", error);
			});
		},
		buildGame(vm) {
			vm.nextPlayerStr = (vm.game.nextPlayer == 0) ? "Player 1" : "Player 2";
			vm.winnerStr = "";
			vm.gameIsActive = true;
			
			// Block other player when interface is ready
			Vue.nextTick(function() {
				var opponentIndex = (vm.game.nextPlayer == 0) ? 1 : 0;
				var playerPits = document.getElementsByClassName("pit-player" + (vm.game.nextPlayer + 1));
				var opponentPits = document.getElementsByClassName("pit-player" + (opponentIndex + 1));
				for(var i=0; i<playerPits.length; i++) {
					playerPits[i].style.pointerEvents = "auto";
					playerPits[i].style.backgroundColor = "#fff";
					opponentPits[i].style.pointerEvents = "none";
					opponentPits[i].style.backgroundColor = "#ddd";
				}

				document.getElementsByClassName("big-pit-player"+(vm.game.nextPlayer+1))[0].style.backgroundColor = "#fff";
				document.getElementsByClassName("big-pit-player"+(opponentIndex+1))[0].style.backgroundColor = "#ddd";
			});
		},
		choosePit(playerIndex, pitIndex) {
			const vm = this;
			axios.get("/rest/game/choosePit?player=" + playerIndex + "&pit=" + pitIndex)
					.then(response => {
				vm.game = response.data;
				
				if (vm.game.winner != null) {
					vm.winnerVal = vm.game.winner;
					return;
				}
				
				if (response.data.lastStoneOnPlayersSmallEmptyPit == true) {
					alert.showMsg("Captured opponent's stones");
					vm.getStonesFromOpponent(playerIndex, response.data.lastPit);
					return;
				} 
				// Do next steps until there are no more automatic steps
				else if (response.data.lastStoneOnPlayersBigPit == true) {
					vm.nextPlayerVal = playerIndex;
					return;
				}
				
				vm.nextPlayerVal = playerIndex == 0 ? 1 : 0;
				
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		},
		getStonesFromOpponent(playerIndex, pit) {
			const vm = this;
			axios.get("/rest/game/getStonesFromOpponent?player=" + playerIndex + "&pit=" + pit)
					.then(response => {
				vm.game = response.data;

				if (vm.game.winner != null) {
					vm.winnerVal = vm.game.winner;
					return;
				}
				
				vm.nextPlayerVal = playerIndex;
				
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		},
		finishGame(vm) {
			vm.nextPlayerVal = null;
			vm.nextPlayerStr = "";
			
			// Block all players
			var playerPits = document.getElementsByClassName("pit-player1");
			var opponentPits = document.getElementsByClassName("pit-player2");
			for(var i=0; i<playerPits.length; i++) {
				playerPits[i].style.pointerEvents = "none";
				playerPits[i].style.backgroundColor = "#ddd";
				opponentPits[i].style.pointerEvents = "none";
				opponentPits[i].style.backgroundColor = "#ddd";
			}
			
			document.getElementsByClassName("big-pit-player1")[0].style.backgroundColor = "#ddd";
			document.getElementsByClassName("big-pit-player2")[0].style.backgroundColor = "#ddd";
		},
	},
	created: function() {
		this.getGameInSession();
	}
});

