DeleteAccount = Class.extend({
    deleteAccount: function () {
        let login = newEngine.getCookie("login");
        let account = document.getElementById("deleteAccountName").value;
        let password = document.getElementById("deleteAccountPassword").value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "account": account,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "deleteAccount",
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {
                    document.getElementById("ModalTitle").innerHTML = "Delete Account";
                    document.getElementById("ModalMessage").innerHTML = request.reqMessage;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("moneyTransferError").innerHTML = "<b>" + request.reqMessage + "</b>";
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