MoneyTransfer = Class.extend({

    moneyTransfer: function () {
        //TODO проверка на ввод имени другого банка
        let login =newEngine.getCookie("login");
        let user = document.getElementById("moneyTransferUserInput").value;
        let account = document.getElementById("moneyTransferAccountInput").value;
        let bank = document.getElementById("moneyTransferBankInput").value;
        let moneyAmount = document.getElementById("moneyTransferMoneyAmountInput").value;
        let password = document.getElementById("moneyTransferPasswordInput").value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "user": user,
                "account": account,
                "moneyAmount": moneyAmount,
                "login": login,
                "password": password
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