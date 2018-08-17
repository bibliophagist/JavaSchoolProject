Login = Class.extend({

    login: function () {
        var login = document
            .getElementById("loginInputEmail")
            .value;
        var password = document
            .getElementById("loginInputPassword")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.serverProxy.bankServerUrl + "/" + "login",
            success: function (data) {
                if (data.responseText === "Success") {
                    newEngine.serverProxy.username = login;
                    document.getElementById("loginedInfo").innerHTML = "Login: <b>" + login + "</b>";
                }
                else {
                    document.getElementById("loginError").innerHTML = "<b>" + data.responseText + "</b>";
                    document.getElementById("loginError").style.display = "inline";
                    document.getElementById("loginPassword").value = "";
                }
            },
            error: function (data) {
                document.getElementById("loginError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("loginError").style.display = "inline";
                document.getElementById("loginPassword").value = "";
            }
        })

    }

});