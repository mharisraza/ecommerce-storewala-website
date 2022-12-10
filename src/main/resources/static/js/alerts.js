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

	if (status == "cp-not-match") {
		swal({
			title: "Error!",
			text: "Both Passwords should be match.",
			icon: "error",
			button: "TRY AGAIN"
		});
	}

	if (status == "went-wrong") {
		swal({
			title: "Error!",
			text: "Something Went Wrong! Please try later.",
			icon: "error",
			button: "OKAY"
		});

	}

	if (status == "registered-success") {
		swal({
			title: "Success!",
			text: "Registered Success, login now!",
			icon: "success",
			button: "Login now"
		}).then(function() {
			window.location = "login";
		});
	}

	if (status == "bad-credentials") {
		swal({
			title: "Bad Credentials!",
			text: "Email Address or password is wrong!",
			icon: "error",
			button: "OKAY"
		});
	}

	if (status == "user-disabled") {
		swal({
			title: "Account Suspended!",
			text: "Admin has suspended your account.",
			icon: "error",
			button: "Contact Us"
		}).then(function() {

			window.location = "/contact";

		});

	}

	if (status == "logout-success") {
		swal({
			title: "Logout Successfully!",
			icon: "success",
			button: "Thanks!"
		});
	}

	if (status == "suspended") {
		swal({
			title: "User suspended successfully!",
			icon: "success",
			button: "OKAY!"
		});
	}

	if (status == "unsuspended") {
		swal({
			title: "User unsuspended successfully",
			icon: "success",
			button: "OKAY!"
		});
	}

	if (status == "user-deleted") {

		swal({
			title: "User Deleted Successfully!",
			icon: "success",
			button: "OKAY!"
		});

	}

	if (status == "title-null") {
		swal({
			title: "Error",
			icon: "error",
			text: "Category title is required",
			button: "OKAY!"
		});
	}

	if (status == "category-already-exist") {
		swal({
			title: "Error",
			icon: "error",
			text: "Category with this name already exist.",
			button: "TRY AGAIN"

		});
	}
	
	if(status == "category-added") {
		swal({
			title: "Success",
			icon: "success",
			text: "Category added successfully.",
			button: "OKAY",
		});
	}
	
	if(status == "product-has-errors") {
		swal({
			title: "Error",
			text:"Unable to add product, open product view to see errors.",
			icon:"error",
			button: "OKAY",
		});
	}
	
	if(status == "category-not-selected") {
		swal({
			
			title: "Error",
			text:"Product category were not selected.",
			icon:"error",
			button: "OKAY",
			
		});
	}
	
	if(status == "product-added") {
		swal({
			title: "Success",
			text: "Product added successfully",
			icon:"success",
			button: "OKAY",
		});
	}
	
	if(status == "images-exceed") {
		swal({
			title : "Error",
			text: "You cannot upload more than 5 product images.",
			icon : "error",
			button : "OKAY",
		})
	}
	
	if(status == "user-not-exist") {
		swal({
			
			title : "Error",
			text : "User with provided email address doesn't exist.",
			icon : "error",
			button : "OKAY",
			
		});
	}
	
	if(status == "user-not-suspend") {
		swal({
			title : "User is not Banned.",
			text : "User with provided email addres is not banned.",
			icon : "info",
			button : "OKAY",
			
		});
	}
	
	if(status == "message-send-successfully") {
		swal({
			title : "Success",
			text : "Message send successfully.",
			icon : "success",
			button : "THANKS!",
		});
	}
	
	if(status == "not-login") {
		swal({
			title : "Error",
			text : "Please login or signup to continue.",
			icon : "error",
			button : "OKAY!"
		})
	}
	
	if(status == "seller-not-allow") {
		swal({
			title : "Error",
			text: "Seller isn't allowed to make an order.",
			icon : "error",
			button : "OKAY",
		});
	}
	
	if(status == "admin-not-allow") {
		swal({
			title : "Error",
			text: "Admin isn't allowed to make an order.",
			icon : "error",
			button : "OKAY",
		});
	}
	
	if(status == "commented-successfully") {
		showToast("Review added successfully");
	}
	
	if(status == "ordered-successfully") {
		showToast("Ordered successfully. Please look at the order status, Thanks!");
		localStorage.removeItem("cart");
	}
	
	if(status == "already-ordered") {
		showToast("The orderYou have already placed an order for this item. Please wait for it to be processed.");
	}

});

function deleteUser(id) {

	swal({
		title: "Are you sure?",
		text: "Do you want to delete this user?",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {

				window.location = "action?user=delete&user_id=" + id;

			} else {

			}
		});

}

function categoryViewOrAdd() {

	swal({
		title: "Categories",
		text: "What do you want to do? Add or View Categories",
		icon: "info",
		buttons: ["View", "Add"],
		dangerMode: true,
	}).then((addCategory) => {
		if (addCategory) {
			$('#category_add').modal('toggle');
		} else {
			$('#category_view').modal('toggle');
		}
	});

}

function showToast(content) {

	$("#snackbar").addClass("show");
	$("#snackbar").html(content);

	// After 3 seconds, remove the show class from DIV
	setTimeout(() => {
		$("#snackbar").removeClass("show");
	}, 3000);
}
