$(document).ready(function() {
	$('[rel="tooltip"]').tooltip({ trigger: "hover" });

	tinymce.init({
		selector: '#productdescription'
	});



});



$(function() {
	$("#fileupload")
		.change(
			function() {
				if (typeof (FileReader) != "undefined") {
					var dvPreview = $("#dvPreview");
					dvPreview.html("");
					var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
					$($(this)[0].files)
						.each(
							function() {
								var file = $(this);
								if (regex.test(file[0].name
									.toLowerCase())) {
									var reader = new FileReader();
									reader.onload = function(
										e) {
										var img = $("<img />");
										img
											.attr(
												"style",
												"height:100px;width: 100px");
										img
											.attr(
												"src",
												e.target.result);
										dvPreview
											.append(img);
									}
									reader
										.readAsDataURL(file[0]);
								} else {
									alert(file[0].name
										+ " is not a valid image file.");
									dvPreview.html("");
									return false;
								}
							});
				} else {
					alert("This browser does not support HTML5 FileReader.");
				}
			});
});


function countChar(val) {
	var len = val.value.length;
	if (len >= 500) {
		val.value = val.value.substring(0, 500);
	} else {
		$('#charNum').text(500 - len);
	}
};




// adding to cart.

function addToCart(pId, pName, pPrice) {



	let cart = localStorage.getItem("cart");
	if (cart == null) {
		let products = [];
		let product = { productId: pId, productName: pName, productPrice: pPrice, productQuantity: 1 }
		products.push(product);
		localStorage.setItem("cart", JSON.stringify(products));
	}
	else {

		let pcart = JSON.parse(cart);

		let oldProduct = pcart.find((item) => item.productId == pId)

		if (oldProduct) {

			oldProduct.productQuantity = oldProduct.productQuantity + 1;
			pcart.map((item) => {

				if (item.productId == oldProduct.productId) {
					item.productQuantity = oldProduct.productQuantity;
				}
			})

			localStorage.setItem("cart", JSON.stringify(pcart));
			console.log("Product quantity is increased");
			showToast("Item quantity is increased");


		}
		else {
			let product = { productId: pId, productName: pName, productPrice: pPrice, productQuantity: 1 }
			pcart.push(product);
			localStorage.setItem("cart", JSON.stringify(pcart));
			console.log("Another Product is added");
			showToast("Item is added to the cart");
		}

	}


	updateCart();
}

function updateCart() {

	let cartString = localStorage.getItem("cart");
	let cart = JSON.parse(cartString);
	if (cart == null || cart.length == 0) {
		console.log("Cart is empty!!");
		$(".cart-items").html("0");
		$(".cart-body").html(`<h1  class="text-center">Sorry, Cart doesn't have any items </h1>`);
		$(".checkout-btn").attr('disabled', true);
	}
	else {
		cart.map((item) => {
			console.log(cart);
			$(".cart-items").html(`${cart.length}`);
			let table = `
			<div class="table-responsive">
          <table class='table table-striped table-hover'>
          <thead class='thead-light'>
            <tr>
            <th>Item Name</th>
            <th>Item Quantity</th>
            <th>Item Price</th>
            <th>Total Price</th>
            <th>Action</th>
              </tr>
          </thead> `;

			let formatCurrency = Intl.NumberFormat('en-PK');

			let totalPrice = 0;
			cart.map((item) => {

				let productTotalPrice = item.productQuantity * item.productPrice;

				table += `
                   <tr>
                   
                   <td>${item.productName}
                   <input type='hidden' name='product_ids' value='${item.productId}'>
                   </td>
                   <td><i onclick='incQuantity(${item.productId}, ${item.productQuantity})' class='fa fa-circle-plus text-success'></i> ${item.productQuantity} <i onclick='decQuantity(${item.productId}, ${item.productQuantity})' class='fa fa-circle-minus text-danger'></i>
                   <input type='hidden' name='product_quantities' value='${item.productQuantity}'>
                   </td>
                   <td>Rs.${formatCurrency.format(item.productPrice)}/-</td>
                   <td>Rs.${formatCurrency.format(productTotalPrice)}/-</td>
                   <td><button onclick='deleteItemFromCart(${item.productId})' class='btn btn-danger btn-sm'>Remove</button></td>
                   </tr>`

				totalPrice += item.productPrice * item.productQuantity;



			})





			table = table + `<tr><td class='colspan='5' class='text-center font-weight-bold'>Total Price: Rs.${formatCurrency.format(totalPrice)}/-
             <input type='hidden' name='total_price' value='${totalPrice}'>
             </td></tr></table></div>`
			$(".cart-body").html(table);
			$(".checkout-btn").attr('disabled', false);

		})
	}
}


function deleteItemFromCart(pId) {

	let cart = JSON.parse(localStorage.getItem("cart"));
	let newCart = cart.filter((item) => item.productId != pId)
	localStorage.setItem("cart", JSON.stringify(newCart));
	updateCart();
	showToast("Item is removed from the cart");
}

function deleteAllProductsFromCart() {

	let cart = JSON.parse(localStorage.getItem("cart"));
	let newCart = cart.filter;
	localStorage.setIem("cart", JSON.stringify(newCart));
	updateCart();

}

function incQuantity(pId, pQuantity) {

	let pcart = JSON.parse(localStorage.getItem("cart"));

	let oldProduct = pcart.find((item) => item.productId == pId)

	if (oldProduct) {

		oldProduct.productQuantity = oldProduct.productQuantity + 1;
		pcart.map((item) => {

			if (item.productId == oldProduct.productId) {
				item.productQuantity = oldProduct.productQuantity;
			}
		})

		localStorage.setItem("cart", JSON.stringify(pcart));
		console.log("Product quantity is increased");
		showToast("Item quantity is increased");

		updateCart();

	}
}

function decQuantity(pId, pQuantity) {

	let pcart = JSON.parse(localStorage.getItem("cart"));

	let oldProduct = pcart.find((item) => item.productId == pId)

	if (oldProduct) {
		
		if(oldProduct.productQuantity == 1) {
			showToast("Item quantity cannot decrease anymore.");
			return;
		}

		oldProduct.productQuantity = oldProduct.productQuantity - 1;
		pcart.map((item) => {

			if (item.productId == oldProduct.productId) {
				item.productQuantity = oldProduct.productQuantity;
			}
		})

		localStorage.setItem("cart", JSON.stringify(pcart));
		console.log("Product quantity is increased");
		showToast("Item quantity is decreased.");

		updateCart();

	}
}


$(document).ready(function() {

	updateCart();

})

function showToast(content) {

	$("#snackbar").addClass("show");
	$("#snackbar").html(content);

	// After 3 seconds, remove the show class from DIV
	setTimeout(() => {
		$("#snackbar").removeClass("show");
	}, 3000);
}

function checkOut() {

	window.location = "/checkout";

}



  function showPreview(event){
  if(event.target.files.length > 0){
    var src = URL.createObjectURL(event.target.files[0]);
    var preview = document.getElementById("file-ip-1-preview");
    preview.src = src;
    preview.style.display = "";
  }
}


function toggleReviewInput() {
  var reviewInput = document.getElementById("reviewInput");
  var addReviewButton = document.getElementById("addReviewButton");
  var cancelButton = document.getElementById("cancelButton");
  var reviewForm = document.getElementById("reviewForm");
  if (reviewInput.style.display === "none") {
    reviewInput.style.display = "block";
    cancelButton.style.display = "block";
    // Change the button text to "Post Review" when the textarea is visible
    addReviewButton.innerText = "Post Review";
  } else {
    reviewInput.style.display = "none";
    cancelButton.style.display = "none";
    // Change the button text back to "Add Review" when the textarea is hidden
    addReviewButton.innerText = "Add Review";
    // Submit the form when the user clicks the "Post Review" button
  }

  
  // Check if the reviewForm element exists and has a submit event
  if (reviewForm && reviewForm.addEventListener && reviewForm.dispatchEvent) {
    // Add a submit event listener to the reviewForm element
    reviewForm.addEventListener('submit', (event) => {
      // Use the event object to prevent the default form submission behavior
      event.preventDefault();

      // Submit the form manually (if you want to)
      reviewForm.submit();
    });

    // Manually trigger the submit event on the reviewForm element when the "Post Review" button is clicked
    addReviewButton.addEventListener('click', (event) => {
      reviewForm.dispatchEvent(new Event('submit'));
    });
  }

  return false;
}