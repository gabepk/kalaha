var game = new Vue({
	el: "#game",
	data: {
		gameIsActive: false,
		nextTurn: 0,
		game: {
			pits: 0,
			stones: 0,
			players: [],
			winner: null
		}
	},
	methods: {
		checkIfGameIsActive() {
			// go to back
			// check is there are variables on session
			// if yes, check is game is active
			this.gameIsActive = false;
		},
		startGame() {
			const vm = this;
			axios.get("/kalaha/rest/game/startGame").then(response => {
				vm.game = response.data;
				this.gameIsActive = true;
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		},
		choosePit(playerIndex, pitIndex) {
			const vm = this;
			axios.get("/kalaha/rest/game/choosePit?player=" + playerIndex + "&pit=" + pitIndex)
					.then(response => {
				vm.game = response.data.game;
				
				if (vm.game.winner) {
					// finish game
					return;
				}
				
				if (response.data.lastStoneOnPlayersSmallEmptyPit == true) {
					vm.getStonesFromOpponent(playerIndex, response.data.lastPit);
					return;
				} 
				// Do next steps until there are no more automatic steps
				else if (response.data.lastStoneOnPlayersBigPit == true) {
					vm.nextTurn = playerIndex;
					return;
				}
				
				vm.nextTurn = playerIndex == 0 ? 1 : 0;
				
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		},
		getStonesFromOpponent(playerIndex, pit) {
			const vm = this;
			axios.get("/kalaha/rest/game/getStonesFromOpponent?player=" + playerIndex + "&pit=" + pit)
					.then(response => {
				vm.game = response.data;

				if (game.winner) {
					// finish game
					return;
				}
				
				vm.nextTurn = playerIndex;
				
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		}
	},
	created: function() {
		this.checkIfGameIsActive();
	}
});

