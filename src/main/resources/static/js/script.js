document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("loginForm");

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const message = document.getElementById("message");

        message.textContent = "";

        try {
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
                localStorage.setItem("fullName", data.fullName);
                localStorage.setItem("role", data.role);

                window.location.href = "/dashboard";
            } else {
                message.textContent = data.message;
            }

        } catch (error) {
            message.textContent = "Server error. Try again.";
        }
    });

});