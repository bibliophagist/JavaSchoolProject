DeleteAccount = Class.extend({
    deleteAccount: function () {
        var account = document
            .getElementById("accountInput")
            .value;
        var password = document
            .getElementById("passwordInput")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "account": account,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.serverProxy.bankServerUrl + "/" + "deleteAccount",
            //TODO rework
            success: function (data) {
                if (data.responseText === "Success") {
                    document.getElementById("ModalTitle").innerHTML="Delete Account";
                    document.getElementById("ModalMessage").innerHTML = data.responseText;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
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