CheckBalance = Class.extend({

    checkBalance: function () {
        let login = document
            .getElementById("accountInput")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": "",
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "checkBalance",
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {
                    document.getElementById("ModalTitle").innerHTML="Current balance";
                    document.getElementById("ModalMessage").innerHTML = data.responseText;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("checkBalanceError").innerHTML = "<b>" + request.reqMessage + "</b>";
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