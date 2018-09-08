DepositMoney = Class.extend({

    depositMoney: function () {

        let login = newEngine.getCookie("login");
        let moneyAmount = document.getElementById("DepositMoneyAmount").value;
        let account = document.getElementById("DepositMoneyAccount").value;
        let password = document.getElementById("DepositMoneyPassword").value;

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
            url: "http://" + newEngine.bankServerUrl + "/" + "depositMoney",
            success: function (data) {
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    document.getElementById("ModalTitle").innerHTML = "Deposit Money";
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