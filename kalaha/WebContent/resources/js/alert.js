var alert = new Vue({
	el: "#alert",
	data: {
		
	},
	methods: {
		showError(title, error) {
			console.log(title + ": " + error);
		}
	}
});