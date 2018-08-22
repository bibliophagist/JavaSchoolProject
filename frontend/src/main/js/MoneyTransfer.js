MoneyTransfer = Class.extend({

    moneyTransfer: function () {
        var account = document
            .getElementById("accountInput")
            .value;
        var moneyAmount = document
            .getElementById("moneyAmountInput")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "account": account,
                "moneyAmount": moneyAmount,
                "login": "",
                "password": ""
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.serverProxy.bankServerUrl + "/" + "moneyTransfer",
            success: function (data) {
                if (data.responseText === "Success") {

                }
                else {
                    document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                    document.getElementById("moneyTransferError").style.display = "inline";
                    document.getElementById("accountInput").value = "";
                    document.getElementById("moneyAmount").value="";
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