$('#moneyTransferForm').submit(function (e) {

    let login = newEngine.getCookie("login");
    let account = document.getElementById("moneyTransferAccountInput").value;
    let withdrawAccount = document.getElementById("moneyTransferWithdrawAccountInput").value;
    let moneyAmount = document.getElementById("moneyTransferMoneyAmountInput").value;
    let password = document.getElementById("moneyTransferPasswordInput").value;
    $.ajax({
        contentType: 'application/x-www-form-urlencoded',
        data: {
            "account": account,
            "withdrawAccount": withdrawAccount,
            "moneyAmount": moneyAmount,
            "login": login,
            "password": password
        },
        dataType: 'text',
        type: 'POST',
        url: "http://" + newEngine.bankServerUrl + "/" + "moneyTransfer",
        success: function (data) {
            let response = JSON.parse(data);
            document.getElementById("ModalTitle").innerHTML = "Money Transfer";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage;
            $('#Modal').modal('show');
            document.getElementById("moneyTransferUserInput").value = "";
            document.getElementById("moneyTransferAccountInput").value = "";
            document.getElementById("moneyTransferBankInput").value = "";
        },
        error: function (data) {
            document.getElementById("ModalTitle").innerHTML = "Money Transfer";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
            document.getElementById("moneyTransferUserInput").value = "";
            document.getElementById("moneyTransferAccountInput").value = "";
            document.getElementById("moneyTransferBankInput").value = "";
        }
    });

    e.preventDefault();
});