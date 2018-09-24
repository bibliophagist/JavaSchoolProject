$('#depositMoneyForm').submit(function (e) {

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
        url: "https://" + newEngine.bankServerUrl + "/" + "depositMoney",
        success: function (data) {
            let response = JSON.parse(data);
            document.getElementById("ModalTitle").innerHTML = "Deposit Money";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage;
            $('#Modal').modal('show');
        },
        error: function (data) {
            document.getElementById("ModalTitle").innerHTML = "Deposit Money";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
        }
    });

    e.preventDefault();
});