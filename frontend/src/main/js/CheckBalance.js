ChekBalance = Class.extend({

    checkBalance: function () {
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": "",
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.serverProxy.bankServerUrl + "/" + "checkBalance",
            success: function (data) {
                if (data.responseText === "Success") {

                }
                else {
                    document.getElementById("checkBalanceError").innerHTML = "<b>" + data.responseText + "</b>";
                    document.getElementById("checkBalanceError").style.display = "inline";
                }
            },
            error: function (data) {
                document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("moneyTransferError").style.display = "inline";
            }
        })

    }

});