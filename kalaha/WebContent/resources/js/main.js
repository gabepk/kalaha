var game = new Vue({
	el: "#game",
	data: {
		gameIsActive: false,
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
				vm.game = response.data;
				
				// check if there are new moves
				
			}).catch(function (error) {
				alert.showError("Error", error);
			});
		}
	},
	created: function() {
		this.checkIfGameIsActive();
	}
});

