Login = Class.extend({

    login: function (){
        var login = document
            .getElementById("loginInput")
            .value;
        var password = document
            .getElementById("loginPassword")
            .value;
        newEngine.serverProxy.playerName=login;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://"+ newEngine.serverProxy.bankServerUrl + "/" + "login",
            success: function () {
                    document.getElementById("loginModal").style.display = "none";
                    document.getElementById('body').style.overflow='auto';
                    document.getElementById("loginButton").style.display = "none";
                    document.getElementById("registerButton").style.display = "none";
                    newEngine.serverProxy.playerName = login;
                    document.getElementById("loginedInfo").innerHTML = "Login: <b>" + login + "</b>";
                    document.getElementById("signOut").style.display = "block";
                    document.getElementById("loginedInfo").style.display = "inline";
            },
            error: function (data) {
                document.getElementById("loginError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("loginError").style.display = "inline";
                document.getElementById("loginPassword").value = "";
            }
        })

	}

});