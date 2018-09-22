CreateAccount = Class.extend({
    createAccount: function () {
        let login = newEngine.getCookie("login");
        let account = document.getElementById("CreateAccountName").value;
        let accountType = document.getElementById("CreateAccountType").value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "account": account,
                "accountType": accountType
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "createAccount",
            success: function (data) {
                let response = JSON.parse(data);
                if (response.requestSuccessful === true) {
                    $('#createAccount').modal('hide');
                    document.getElementById("ModalTitle").innerHTML = "Create Account";
                    document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                    $('#Modal').modal('show');
                    document.getElementById("CreateAccountName").value = "";
                    document.getElementById("CreateAccountType").value = "";
                }
                else {
                    document.getElementById("moneyTransferError").innerHTML = "<b>" + response.responseMessage + "</b>";
                    document.getElementById("moneyTransferError").style.display = "inline";
                    document.getElementById("CreateAccountName").value = "";
                    document.getElementById("CreateAccountType").value = "";
                }
            },
            error: function (data) {
                document.getElementById("moneyTransferError").innerHTML = "<b>" + data.responseText + "</b>";
                document.getElementById("moneyTransferError").style.display = "inline";
                document.getElementById("CreateAccountName").value = "";
                document.getElementById("CreateAccountType").value = "";
            }
        })

    }
});