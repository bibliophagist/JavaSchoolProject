CheckBalance = Class.extend({

    checkBalance: function () {
        let login = newEngine.getCookie("login");
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
            },
            dataType: 'text',
            type: 'POST',
            url: "http://" + newEngine.bankServerUrl + "/" + "checkBalance",
            success: function (data) {
                let response = JSON.parse(data);
                document.getElementById("ModalTitle").innerHTML = "Current balance";
                document.getElementById("ModalMessage").innerHTML = response.responseMessage;
                $('#Modal').modal('show');

            },
            error: function (data) {
                document.getElementById("ModalTitle").innerHTML = "ERROR";
                document.getElementById("ModalMessage").innerHTML = data;
                $('#Modal').modal('show');
            }
        })

    }

});