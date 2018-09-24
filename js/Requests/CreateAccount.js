$('#createAccountForm').submit(function (e) {

    let login = newEngine.getCookie("login");
    let account = document.getElementById("CreateAccountName").value;
    account = "70" + account;
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
        url: newEngine.bankServerUrl + "/" + "createAccount",
        success: function (data) {
            let response = JSON.parse(data);
            newEngine.setCookie("accounts", response.accListString);
            $('#createAccount').modal('hide');
            document.getElementById("ModalTitle").innerHTML = "Create Account";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage + ". <br>  Reload for new list of accounts";
            $('#Modal').modal('show');
        },
        error: function (data) {
            let response = JSON.parse(data);
            document.getElementById("ModalTitle").innerHTML = "Create Account";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
        }
    });

    e.preventDefault();
});