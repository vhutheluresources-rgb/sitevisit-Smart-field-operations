// Function to toggle password visibility
function passwordVisibility() {
	var passwordField = document.getElementById('password');
	var confirm_passwordField = document.getElementById('confirm_password');
	var passwordCheckbox = document.getElementById('show-password');
	if (passwordField && passwordCheckbox) {
		passwordField.type = passwordCheckbox.checked ? 'text' : 'password';
	}
	
	if(confirm_passwordField && passwordCheckbox){
		confirm_passwordField.type = passwordCheckbox.checked ? 'text' : 'password';
	}
}

// Function to hide the error message after 2 seconds
setTimeout(function() {
	var errorMessage = document.getElementById('error-message');
	if (errorMessage) {
		errorMessage.style.display = 'none';
	}
}, 2000);

//form validating
function checkForm() {
	if (!checkPasswordMatch()) {
		return false;
	}

	if (!checkIdInput()) {
		return false;
	}

	if (!checkPhoneNumber()) {
		return false;
	}

	return true;
}

//Password validation
function checkPasswordMatch() {
	var password = document.getElementById('password').value;
	var confirmPassword = document.getElementById('confirm_password').value;
	var password_match_message = document.getElementById('password_match_message');

	// Check if passwords match
	if (password !== confirmPassword) {
		password_match_message.innerText = 'Passwords do not match!';
		password_match_message.style.display = 'block';
		setTimeout(function() {
			password_match_message.style.display = 'none';
		}, 2000);
		return false;
	}

	// Check if password meets complexity requirements
	var hasSpecialChar = /[^a-zA-Z0-9]/.test(password); // At least one special character
	var hasNumericDigit = /\d/.test(password); // At least one numeric digit
	var hasLowerCase = /[a-z]/.test(password); // At least one lowercase letter
	var hasUpperCase = /[A-Z]/.test(password); // At least one uppercase letter

	if (!hasSpecialChar || !hasNumericDigit || !hasLowerCase || !hasUpperCase) {
		password_match_message.innerText = 'Password must contain at least one special character, one numeric digit, one lowercase letter, and one uppercase letter!';
		password_match_message.style.display = 'block';
		setTimeout(function() {
			password_match_message.style.display = 'none';
		}, 5000); // Longer display time for complex password requirement
		return false;
	}

	return true;
}

//id validation
function checkIdInput() {
	var idInput = document.getElementById('idNumber').value;
	var id_validation_message = document.getElementById("id_validation_message");

	// Check if input is 13 digits long
	if (idInput.length !== 13) {
		id_validation_message.innerText = "ID number must be 13 digits long";
		id_validation_message.style.display = 'block';
		setTimeout(function() {
			id_validation_message.style.display = 'none';
		}, 2000); // Hide the message after 2 seconds
		return false;
	}

	// Check if input consists of numbers only
	if (!/^\d+$/.test(idInput)) {
		id_validation_message.innerText = "ID number must contain only numeric digits";
		id_validation_message.style.display = 'block';
		setTimeout(function() {
			id_validation_message.style.display = 'none';
		}, 2000); // Hide the message after 2 seconds
		return false;
	}

	// Extract the first 6 digits representing the date of birth
	var dobPart = idInput.substring(0, 6);
	var year = dobPart.substring(0, 2);
	var month = dobPart.substring(2, 4);
	var day = dobPart.substring(4, 6);

	// Adjust year for 1900s or 2000s
	var currentYear = new Date().getFullYear();
	var fullYear = (currentYear % 100) >= parseInt(year) ? '20' + year : '19' + year;

	// Check if the first 6 digits form a valid date of birth
	var dateOfBirth = new Date(fullYear, month - 1, day);
	if (dateOfBirth.getFullYear() != fullYear || dateOfBirth.getMonth() != (month - 1) || dateOfBirth.getDate() != day) {
		id_validation_message.innerText = "Invalid date of birth in ID number";
		id_validation_message.style.display = 'block';
		setTimeout(function() {
			id_validation_message.style.display = 'none';
		}, 2000); // Hide the message after 2 seconds
		return false;
	}

	// Checksum validation using Luhn algorithm


	id_validation_message.innerText = ""; // Clear any previous error message
	id_validation_message.style.display = 'none'; // Hide the error message
	return true;
}

function luhnCheck(id) {
	var sum = 0;
	var shouldDouble = false;
	for (var i = id.length - 1; i >= 0; i--) {
		var digit = parseInt(id.charAt(i));
		if (shouldDouble) {
			digit *= 2;
			if (digit > 9) {
				digit -= 9;
			}
		}
		sum += digit;
		shouldDouble = !shouldDouble;
	}
	return (sum % 10) == 0;
}

// Phone number validation
function checkPhoneNumber() {
	var phoneNumber = document.getElementById('phoneNumber').value;
	var phone_validation_message = document.getElementById("phone_validation_message");

	// Check if input consists of exactly 10 digits
	if (!/^\d{10}$/.test(phoneNumber)) {
		phone_validation_message.innerText = "Phone number must be 10 digits long and contain only numbers!";
		phone_validation_message.style.display = 'block';
		setTimeout(function() {
			phone_validation_message.style.display = 'none';
		}, 2000); // Hide the message after 2 seconds
		return false;
	}

	phone_validation_message.innerText = ""; // Clear any previous error message
	phone_validation_message.style.display = 'none'; // Hide the error message
	return true;
}

//
// Get the password input field
var passwordInput = document.getElementById('password');

// Add event listener for input event
passwordInput.addEventListener('input', function() {
	var password = passwordInput.value;

	// Check if password contains at least one special character
	var hasSpecialChar = /[^a-zA-Z0-9]/.test(password); // At least one special character
	updateStatus(hasSpecialChar, 'special_character');

	// Check if password contains at least one numeric digit
	var hasNumericDigit = /\d/.test(password); // At least one numeric digit
	updateStatus(hasNumericDigit, 'number');

	// Check if password contains at least one lowercase letter
	var hasLowerCase = /[a-z]/.test(password); // At least one lowercase letter
	updateStatus(hasLowerCase, 'lowercase');

	// Check if password contains at least one uppercase letter
	var hasUpperCase = /[A-Z]/.test(password); // At least one uppercase letter
	updateStatus(hasUpperCase, 'uppercase');
});

// Update color of each requirement text
function updateStatus(hasRequirement, elementId) {
	var element = document.getElementById(elementId);

	if (hasRequirement) {
		element.style.color = 'green'; // Set color to green if requirement is met
	} else {
		element.style.color = 'red'; // Set color to red if requirement is not met
	}
}
// Function to hide the error message after 2 seconds if it's visible
setTimeout(function() {
	var errorMessage = document.querySelector('.container p');
	if (errorMessage && errorMessage.style.display !== 'none') {
		errorMessage.style.display = 'none';
	}
}, 2000);

