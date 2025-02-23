$(document).ready(function () {
        $("#registerForm").submit(function (event) {
            event.preventDefault();

            var username = $("#username").val();
            var email = $("#email").val();
            var password = $("#password").val();
            var confirmPassword = $("#confirm-password").val();

            if (password !== confirmPassword) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Passwords do not match!',
                });
                return;
            }

            $.post("/tiketin24/loginCtr", {
                page: "register",
                username: username,
                email: email,
                password: password
            }, function (response) {
                Swal.fire({
                    icon: 'success',
                    title: 'Success!',
                    text: response,
                }).then(() => {
                    window.location.href = "login.html";
                });
            }).fail(function () {
                Swal.fire({
                    icon: 'error',
                    title: 'Registration failed',
                    text: 'Please try again.',
                });
            });
        });
    });