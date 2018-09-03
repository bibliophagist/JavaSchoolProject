Login = Class.extend({

    login: function () {
        let login = document
            .getElementById("loginInputLogin")
            .value;
        let password = document
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
            url: "http://" + newEngine.bankServerUrl + "/" + "login",
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {
                    newEngine.setCookie("login",login);
                    window.location.href = "index.html";
                }
                else {
                    document.getElementById("registerError").innerHTML = "<b>" + request.reqMessage + "</b>";
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