document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const errorContainer = document.getElementById("error-container");
    const errorMessage = document.getElementById("error-message");
    const loginButton = document.querySelector(".btn-login");
    const btnText = document.querySelector(".btn-text");
    const btnLoader = document.querySelector(".btn-loader");

    function showError(message) {
        errorMessage.textContent = message;
        errorContainer.style.display = "flex";
    }

    function clearError() {
        errorMessage.textContent = "";
        errorContainer.style.display = "none";
    }

    function setLoading(isLoading) {
        loginButton.disabled = isLoading;
        loginButton.setAttribute("aria-busy", isLoading ? "true" : "false");

        if (btnText) {
            btnText.style.display = isLoading ? "none" : "inline";
        }

        if (btnLoader) {
            btnLoader.style.display = isLoading ? "flex" : "none";
        }
    }

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();
        clearError();

        const username = emailInput.value.trim();
        const password = passwordInput.value.trim();

        if (!username) {
            showError("Please enter your username.");
            return;
        }

        if (!password) {
            showError("Please enter your password.");
            return;
        }

        try {
            setLoading(true);

            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            const data = await response.json();

            if (data.success) {
                localStorage.setItem("isLoggedIn", "true");
                localStorage.setItem("fullName", data.fullName || "");
                localStorage.setItem("role", data.role || "");
                localStorage.setItem("username", username);

                window.location.href = "/dashboard.html";
            } else {
                showError(data.message || "Invalid username or password.");
            }
        } catch (error) {
            showError("Unable to connect to the server. Please try again.");
        } finally {
            setLoading(false);
        }
    });
});

function togglePassword() {
    const passwordInput = document.getElementById("password");
    const toggleIcon = document.getElementById("toggleIcon");
    const toggleButton = document.querySelector(".toggle-password");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        toggleIcon.classList.remove("fa-eye");
        toggleIcon.classList.add("fa-eye-slash");
        toggleButton.setAttribute("aria-pressed", "true");
    } else {
        passwordInput.type = "password";
        toggleIcon.classList.remove("fa-eye-slash");
        toggleIcon.classList.add("fa-eye");
        toggleButton.setAttribute("aria-pressed", "false");
    }
}

function showMessage(message) {
    alert(message);
}