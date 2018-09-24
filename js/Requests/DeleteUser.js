$('#deleteUserForm').submit(function (e) {
    let login = newEngine.getCookie("login");
    let password = document.getElementById("deleteUserPassword").value;
    $.ajax({
        contentType: 'application/x-www-form-urlencoded',
        data: {
            "login": login,
            "password": password
        },
        dataType: 'text',
        type: 'POST',
        url: "https://" + newEngine.bankServerUrl + "/" + "deleteUser",
        success: function (data) {
            let response = JSON.parse(data);
            if (response.requestSuccessful === true) {
                document.getElementById("ModalTitle").innerHTML = "Delete User";
                document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                $('#Modal').modal('show');
                setTimeout(newEngine.unload(), 4000);
            }
            else {
                document.getElementById("ModalTitle").innerHTML = "Delete User";
                document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                $('#Modal').modal('show');
            }
        },
        error: function (data) {
            document.getElementById("ModalTitle").innerHTML = "Delete User";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
        }
    });

    e.preventDefault();
});