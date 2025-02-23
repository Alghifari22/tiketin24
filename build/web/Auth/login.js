$(document).ready(function () {
        function getInputValue() {
            return {
                email: $("#email").val(),
                password: $("#password").val()
            };
        }

        $('#btnSubmit').on('click', function (event) { 
            event.preventDefault(); 

            var inputData = getInputValue();
            var page = "login";

            $.post("/tiketin24/loginCtr",
                {
                    page: page,
                    email: inputData.email,
                    password: inputData.password
                },
                function (data, status) {
                    if (!data.email || !data.password || data.email !== inputData.email || data.password !== inputData.password) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Credentials is Invalid!',
                        });
                    } else {
                        localStorage.setItem('userRole', data.level);
                        window.location.href = "http://localhost:8080/tiketin24/Dashboard/dashboard.html";
                    }
                }
            ).fail(function () {
                Swal.fire({
                    icon: 'error',
                    title: 'Connection failed',
                    text: 'Please try again.',
                });
            });
        });
    });