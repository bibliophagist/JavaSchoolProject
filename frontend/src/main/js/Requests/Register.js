Register = Class.extend({
    login: "",
    password: "",

    register: function () {
        let login = document
            .getElementById("registerInputEmail")
            .value;
        let password = document
            .getElementById("registerInputPassword")
            .value;
        let passwordRepeat = document
            .getElementById("registerInputPasswordRepeat")
            .value;
       /* let passport = document
            .getElementById("registerInputPassport")
            .value;*/
        if ((password.length > 49) || (password.length < 1) || (login.length > 49) || (login.length < 1)) {
            document.getElementById("registerRule").style.color = "red";
            document.getElementById("registerInputEmail").value="";
            document.getElementById("registerInputPassword").value = "";
            document.getElementById("registerInputPasswordRepeat").value = "";
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
                        let request = JSON.parse(data);
                        console.log(request);
                        if (request.success === true) {
                            newEngine.setCookie("login",login);
                            window.location.href = "index.html";
                        }
                        else {
                            document.getElementById("registerError").innerHTML = "<b>" + request.reqMessage + "</b>";
                            document.getElementById("registerRule").style.color = "black";
                            document.getElementById("registerError").style.display = "inline";
                            document.getElementById("registerInputEmail").value="";
                            document.getElementById("registerInputPassword").value = "";
                            document.getElementById("registerInputPasswordRepeat").value = "";
                        }
                    },
                    error: function (data) {
                        document.getElementById("registerError").innerHTML = "<b>" + data.responseText + "</b>";
                        document.getElementById("registerRule").style.color = "black";
                        document.getElementById("registerError").style.display = "inline";
                        document.getElementById("registerInputEmail").value="";
                        document.getElementById("registerInputPassword").value = "";
                        document.getElementById("registerInputPasswordRepeat").value = "";
                    }
                })
            }
            else {
                document.getElementById("registerError").innerHTML = "<b>Passwords don't match</b>";
                document.getElementById("registerError").style.display = "inline";
                document.getElementById("registerInputEmail").value="";
                document.getElementById("registerInputPassword").value = "";
                document.getElementById("registerInputPasswordRepeat").value = "";
            }
        }
    }
});