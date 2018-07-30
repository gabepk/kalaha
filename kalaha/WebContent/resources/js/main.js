var game = new Vue({
	el: "#game",
	data: {
		gameIsActive: false
	},
	methods: {
		checkIfGameIsActive() {
			// go to back
			// check is there are variables on session
			// if yes, check is game is active
			this.gameIsActive = false;
		},
		startGame() {
			// go to back
			// empty all variables from session
			// updates front
			this.gameIsActive = true;
		}
	},
	created: function() {
		this.checkIfGameIsActive();
	}
});

