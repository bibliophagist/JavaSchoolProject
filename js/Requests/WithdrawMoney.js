$('#withdrawForm').submit(function (e) {

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
        url: "https://" + newEngine.bankServerUrl + "/" + "withdrawMoney",
        success: function (data) {
            let response = JSON.parse(data);
            document.getElementById("ModalTitle").innerHTML = "Withdraw Money";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage;
            $('#Modal').modal('show');
        },
        error: function (data) {
            document.getElementById("ModalTitle").innerHTML = "Withdraw Money";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
        }
    });
    e.preventDefault();
});