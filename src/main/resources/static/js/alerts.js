$(document).ready(function() {

	var status = document.getElementById("status").value;

	if (status == "email-exist") {
		swal({
			title: "Error!",
			text: "This email address is already exist.",
			icon: "error",
			button: "TRY AGAIN"
		});
	}

	if (status == "role-not-select") {
		swal({
			title: "Error!",
			text: "Please select your role.",
			icon: "error",
			button: "TRY AGAIN"
		});
	}

	if (status == "cp-empty") {
		swal({
			title: "Error!",
			text: "Confirm Password is required.",
			icon: "error",
			button: "TRY AGAIN"
		});
	}
	
	if(status == "cp-not-match") {
		swal({
			title: "Error!",
			text: "Both Passwords should be match.",
			icon: "error",
			button: "TRY AGAIN"
		});
	}
	
	if(status == "went-wrong") {
		swal({
			title: "Error!",
			text: "Something Went Wrong! Please try later.",
			icon: "error",
			button: "OKAY"
		});
		
	}
	
	if(status == "registered-success") {
		swal({
			title: "Success!",
			text: "Registered Success, login now!",
			icon: "success",
			button: "Login now"
		}).then(function() {
			window.location="login";
		});
	}
	
	if(status == "bad-credentials") {
		swal({
			title: "Bad Credentials!",
			text: "Email Address or password is wrong!",
			icon: "error",
			button: "OKAY"
		});
	}
	
	if(status == "user-disabled") {
		swal({
			title: "Account Suspended!",
			text: "Admin has suspended your account.",
			icon: "error",
			button: "Contact Us"
		}).then(function() {
			
			window.location="/contact";
			
		});
		
	}
	
	if(status == "logout-success") {
		swal({
			title: "Logout Successfully!",
			icon: "success",
			button: "Thanks!"
		});
	}

});