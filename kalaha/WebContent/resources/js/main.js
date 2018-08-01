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
		winner: '',
		nextPlayerVal: 0,
		nextPlayerStr: ''
	},
	watch: {
		nextPlayerVal: function(val) {
			const vm = this;
			axios.get("/kalaha/rest/game/setNextPlayer?nextPlayer=" + val)
					.then(response => {
				vm.nextPlayerStr = (val == 0) ? "Player 1" : "Player 2";
			}).catch(function (error) {
				alert.showError("Error", error);
				vm.nextPlayerStr = null;
			});
		},
		winner: function(val) {
			
			
			this.gameIsActive = false;
			winner = (val == 0) ? "Player 1!" :
				((val == 1) ? "Player 2!" : "It's a match");
		}
	},
	methods: {
		getGameInSession() {
			const vm = this;
			axios.get("/kalaha/rest/game/getGameInSession").then(response => {
				if (response.data) {
					vm.game = response.data;
					this.gameIsActive = true;
				} else {
					this.gameIsActive = false;
				}
			}).catch(function (error) {
				alert.showError("Error", error);
			});
			this.gameIsActive = false;
		},
		startGame() {
			const vm = this;
			axios.get("/kalaha/rest/game/startGame?nextPlayer=" + this.nextPlayerVal).then(response => {
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
				vm.game = response.data;
				
				if (vm.game.winner) {
					winner = vm.game.winner;
					return;
				}
				
				if (response.data.lastStoneOnPlayersSmallEmptyPit == true) {
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
			axios.get("/kalaha/rest/game/getStonesFromOpponent?player=" + playerIndex + "&pit=" + pit)
					.then(response => {
				vm.game = response.data;

				if (game.winner) {
					winner = vm.game.winner;
					return;
				}
				
				vm.nextPlayerVal = playerIndex;
				
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		}
	},
	created: function() {
		this.getGameInSession();
	}
});

