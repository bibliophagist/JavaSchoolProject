CheckBalance = Class.extend({

    checkBalance: function () {
        let login = newEngine.getCookie("login");
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "checkBalance",
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {
                    document.getElementById("ModalTitle").innerHTML = "Current balance";
                    document.getElementById("ModalMessage").innerHTML = request.reqMessage;
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