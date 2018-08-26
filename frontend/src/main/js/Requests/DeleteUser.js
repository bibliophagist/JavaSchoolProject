DeleteUser = Class.extend({
    deleteUser: function () {
        let login = document
            .getElementById("loginInput")
            .value;
        let password = document
            .getElementById("passwordInput")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "deleteUser",
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {
                    document.getElementById("ModalTitle").innerHTML="Delete User";
                    document.getElementById("ModalMessage").innerHTML = request.reqMessage;
                    $('#Modal').modal('show');
                    setTimeout(window.location.href = "index.html", 4000);
                }
                else {
                    document.getElementById("registerError").innerHTML = "<b>" + request.reqMessage + "</b>";
                    document.getElementById("moneyTransferError").style.display = "inline";
                    document.getElementById("accountInput").value = "";
                    document.getElementById("moneyAmount").value = "";
                }
            },
            error: function (data) {
                document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("moneyTransferError").style.display = "inline";
                document.getElementById("moneyAmount").value = "";
            }
        })

    }
});