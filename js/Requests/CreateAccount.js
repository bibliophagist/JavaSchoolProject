$('#createAccountForm').submit(function (e) {

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
            $('#createAccount').modal('hide');
            document.getElementById("ModalTitle").innerHTML = "Create Account";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage;
            $('#Modal').modal('show');
            document.getElementById("CreateAccountName").value = "";
            document.getElementById("CreateAccountType").value = "";
        },
        error: function (data) {
            let response = JSON.parse(data);
            document.getElementById("ModalTitle").innerHTML = "Create Account";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
            document.getElementById("CreateAccountName").value = "";
            document.getElementById("CreateAccountType").value = "";
        }
    });

    e.preventDefault();
});