$('#loginForm').submit(function (e) {

    let login = document.getElementById("loginInputLogin").value;
    let password = document.getElementById("loginInputPassword").value;
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
            let response = JSON.parse(data);
            if (response.requestSuccessful === true) {
                newEngine.setCookie("login", login);
                newEngine.setCookie("accounts", response.accListString);
                window.location.href = "index.html";
            }
            else {
                document.getElementById("registerError").innerHTML = "<b>" + response.responseMessage + "</b>";
                document.getElementById("loginError").style.display = "inline";
            }
        },
        error: function (data) {
            document.getElementById("loginError").innerHTML = "<b>" + data.responseText + "</b>";
            document.getElementById("loginError").style.display = "inline";
        }
    });
    e.preventDefault();
});
