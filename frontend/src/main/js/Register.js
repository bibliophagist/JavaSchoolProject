Register = Class.extend({
    login: "",
    password: "",

    register: function () {
        var login = document
            .getElementById("registerInputEmail")
            .value;
        var password = document
            .getElementById("registerInputPassword")
            .value;
        var passwordRepeat = document
            .getElementById("registerInputPasswordRepeat")
            .value;
        var passport = document
            .getElementById("registerInputPassport")
            .value;
        if ((password.length > 49) || (password.length < 6) || (login.length > 49) || (login.length < 6)) {
            document.getElementById("registerRule").style.color = "red";
            document.getElementById("registerPasswordInput").value = "";
            document.getElementById("registerPasswordRepeatInput").value = "";
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
                    url: "http://" + newEngine.serverProxy.bankServerUrl + "/" + "register",
                    success: function (data) {
                        if (data.responseText === "Success") {
                            document.getElementById("loginedInfo").innerHTML = "Login: <b>" + login + "</b>";
                        }
                        else {
                            document.getElementById("registerError").innerHTML = "<b>" + data.responseText + "</b>";
                            document.getElementById("registerRule").style.color = "black";
                            document.getElementById("registerError").style.display = "inline";
                            document.getElementById("registerPasswordInput").value = "";
                            document.getElementById("registerPasswordRepeatInput").value = "";
                        }
                    },
                    error: function (data) {
                        document.getElementById("registerError").innerHTML = "<b>" + data.responseText + "</b>";
                        document.getElementById("registerRule").style.color = "black";
                        document.getElementById("registerError").style.display = "inline";
                        document.getElementById("registerPasswordInput").value = "";
                        document.getElementById("registerPasswordRepeatInput").value = "";
                    }
                })
            }
            else {
                document.getElementById("registerError").innerHTML = "<b>Passwords don't match</b>";
                document.getElementById("registerError").style.display = "inline";
                document.getElementById("registerPasswordInput").value = "";
                document.getElementById("registerPasswordRepeatInput").value = "";
            }
        }
    }
});