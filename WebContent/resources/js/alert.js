var alert = new Vue({
	el: "#alert",
	data: {
		
	},
	methods: {
		showError(msg, error) {
			console.log(error);
			
			var alertMsg = document.getElementById("alert-msg");
			var alertContainer = document.getElementById("alert");
			alertMsg.innerText = msg;
			alertContainer.classList.add("alert-error");
			alertContainer.style.display = "block";
			setTimeout(function(){
				alertMsg.innerText = "";
				alertContainer.style.display = "none";
				alertContainer.classList.remove("alert-error");
			}, 5000);
		},
		showMsg(msg) {
			var alertMsg = document.getElementById("alert-msg");
			var alertContainer = document.getElementById("alert");
			alertMsg.innerText = msg;
			alertContainer.classList.add("alert-msg");
			alertContainer.style.display = "block";
			setTimeout(function(){
				alertMsg.innerText = "";
				alertContainer.style.display = "none";
				alertContainer.classList.add("alert-msg");
			}, 5000);
		}
	}
});