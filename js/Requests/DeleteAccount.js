$('#deleteAccountForm').submit(function (e) {

    let login = newEngine.getCookie("login");
    let account = document.getElementById("deleteAccountName").value;
    let password = document.getElementById("deleteAccountPassword").value;
    $.ajax({
        contentType: 'application/x-www-form-urlencoded',
        data: {
            "login": login,
            "account": account,
            "password": password
        },
        dataType: 'text',
        type: 'POST',
        url: newEngine.bankServerUrl + "/" + "deleteAccount",
        success: function (data) {
            let response = JSON.parse(data);
            newEngine.setCookie("accounts", response.accListString);
            $('#deleteAccount').modal('hide');
            document.getElementById("ModalTitle").innerHTML = "Delete Account";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage;
            $('#Modal').modal('show');

        },
        error: function (data) {
            $('#deleteAccount').modal('hide');
            document.getElementById("ModalTitle").innerHTML = "Delete Account";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
        }
    });

    e.preventDefault();
});