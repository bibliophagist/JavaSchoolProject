MoneyTransfer = Class.extend({

    moneyTransfer: function () {
        //если ничего не введено - это undefined
        let login =newEngine.getCookie("login");
        //если ничего не введено - это пустая строка ""!
        let user = document.getElementById("moneyTransferUserInput").value;
        let account = document.getElementById("moneyTransferAccountInput").value;
        let bank = document.getElementById("moneyTransferBankInput").value;
        let moneyAmount = document.getElementById("moneyTransferMoneyAmountInput").value;
        let password = document.getElementById("moneyTransferPasswordInput").value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "bank": bank,
                "user": user,
                "account": account,
                "moneyAmount": moneyAmount,
                "login": login,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "moneyTransfer",
            success: function (data) {
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    document.getElementById("ModalTitle").innerHTML = "Money Transfer";
                    document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                    $('#Modal').modal('show');
                    document.getElementById("moneyTransferUserInput").value="";
                    document.getElementById("moneyTransferAccountInput").value="";
                    document.getElementById("moneyTransferBankInput").value="";
                }
                else {
                    document.getElementById("registerError").innerHTML = "<b>" + response.responseMessage + "</b>";
                    document.getElementById("moneyTransferError").style.display = "inline";
                    document.getElementById("moneyTransferUserInput").value="";
                    document.getElementById("moneyTransferAccountInput").value="";
                    document.getElementById("moneyTransferBankInput").value="";
                }
            },
            error: function (data) {
                document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("moneyTransferError").style.display = "inline";
                document.getElementById("moneyTransferUserInput").value="";
                document.getElementById("moneyTransferAccountInput").value="";
                document.getElementById("moneyTransferBankInput").value="";
            }
        })
    }

});