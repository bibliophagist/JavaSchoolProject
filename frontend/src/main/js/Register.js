Register = Class.extend({
    login: "",
    password: "",

    register: function () {
        var login = document
            .getElementById("registerLoginInput")
            .value;
        var password = document
            .getElementById("registerPasswordInput")
            .value;
        var passwordRepeat = document
            .getElementById("registerPasswordRepeatInput")
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
                            document.getElementById("registerModal").style.display = "none";
                            document.getElementById('body').style.overflow = 'auto';
                            document.getElementById("loginButton").style.display = "none";
                            document.getElementById("registerButton").style.display = "none";
                            document.getElementById("loginedInfo").innerHTML = "Login: <b>" + login + "</b>";
                            document.getElementById("signOut").style.display = "block";
                            document.getElementById("loginedInfo").style.display = "inline";
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