CreateAccount = Class.extend({
    createAccount: function () {
        let login = document
            .getElementById("accountInput")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "createAccount",
            success: function (data) {
                let request = JSON.parse(data);
                console.log(request);
                if (request.success === true) {
                    document.getElementById("ModalTitle").innerHTML="Create Account";
                    document.getElementById("ModalMessage").innerHTML = data.responseText;
                    $('#Modal').modal('show');
                }
                else {
                    document.getElementById("moneyTransferError").innerHTML = "<b>" + request.reqMessage + "</b>";
                    document.getElementById("moneyTransferError").style.display = "inline";
                    document.getElementById("accountInput").value = "";
                    document.getElementById("moneyAmount").value = "";
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