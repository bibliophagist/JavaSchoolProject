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
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    document.getElementById("ModalTitle").innerHTML = "Delete Account";
                    document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("moneyTransferError").innerHTML = "<b>" + response.responseMessage + "</b>";
                    document.getElementById("moneyTransferError").style.display = "inline";
                }
            },
            error: function (data) {
                document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("moneyTransferError").style.display = "inline";
            }
        })

    }
});