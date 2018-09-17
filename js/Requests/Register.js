Register = Class.extend({
    register: function () {
        let login = document
            .getElementById("registerInputLogin")
            .value;
        let password = document
            .getElementById("registerInputPassword")
            .value;
        let passwordRepeat = document
            .getElementById("registerInputPasswordRepeat")
            .value;
        if ((password.length > 49) || (password.length < 1) || (login.length > 49) || (login.length < 1)) {
            document.getElementById("registerRule").style.color = "red";
        }
        else {
            if (password === passwordRepeat) {
                $.ajax({
                    contentType: 'application/x-www-form-urlencoded',
                    data: {
                        "login": login,
                        "password": password
                    },
                    dataType: 'text',
                    type: 'POST',
                    url: "http://" + newEngine.bankServerUrl + "/" + "register",
                    success: function (data) {
                        let response = JSON.parse(data);
                        if (response.requestSuccessful === true) {
                            newEngine.setCookie("login", login);
                            window.location.href = "index.html";
                        }
                        else {
                            document.getElementById("registerError").innerHTML = "<b>" + response.responseMessage + "</b>";
                            document.getElementById("registerRule").style.color = "black";
                            document.getElementById("registerError").style.display = "inline";
                        }
                    },
                    error: function (data) {
                        document.getElementById("registerError").innerHTML = "<b>" + data.responseText + "</b>";
                        document.getElementById("registerRule").style.color = "black";
                        document.getElementById("registerError").style.display = "inline";
                    }
                })
            }
            else {
                document.getElementById("registerError").innerHTML = "<b>Passwords don't match</b>";
                document.getElementById("registerError").style.display = "inline";
            }
        }
    }
});