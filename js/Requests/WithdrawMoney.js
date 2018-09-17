WithdrawMoney = Class.extend({

    withdrawMoney: function () {

        let login = newEngine.getCookie("login");
        let moneyAmount = document.getElementById("WithdrawMoneyAmount").value;
        let account = document.getElementById("WithdrawMoneyAccount").value;
        let password = document.getElementById("WithdrawMoneyPassword").value;

        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "moneyAmount": moneyAmount,
                "account": account,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "withdrawMoney",
            success: function (data) {
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    document.getElementById("ModalTitle").innerHTML = "Withdraw Money";
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