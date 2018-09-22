SendFile = Class.extend({

    sendFile: function () {
        let login = newEngine.getCookie("login");
        //TODO read file?

        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "checkBalance",
            success: function (data) {
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    document.getElementById("ModalTitle").innerHTML = "Handle xml";
                    document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("checkBalanceError").innerHTML = "<b>" + response.responseMessage + "</b>";
                    document.getElementById("checkBalanceError").style.display = "inline";
                }
            },
            error: function (data) {
                document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("moneyTransferError").style.display = "inline";
            }
        });

        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "checkBalance",
            success: function (data) {
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    document.getElementById("ModalTitle").innerHTML = "Handle json";
                    document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("checkBalanceError").innerHTML = "<b>" + response.responseMessage + "</b>";
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