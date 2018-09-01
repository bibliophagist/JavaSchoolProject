MoneyTransfer = Class.extend({

    moneyTransfer: function () {
        let account = document
            .getElementById("accountInput")
            .value;
        let moneyAmount = document
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
            url: "http://" + newEngine.bankServerUrl + "/" + "moneyTransfer",
            //TODO rework
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {

                }
                else {
                    document.getElementById("registerError").innerHTML = "<b>" + request.reqMessage + "</b>";
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