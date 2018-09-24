$('#fileForm').submit(function (e) {

    let login = newEngine.getCookie("login");
    let data = new FormData();
    data.append('file', $('#file')[0].files[0]);

    console.log(
        data.get("file")
    );

    $.ajax({
        url: newEngine.bankServerUrl + "/" + "handleJson",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        method: 'POST',
        type: 'POST',
        success: function (data) {
            let response = JSON.parse(data);
            $('#createAccount').modal('hide');
            document.getElementById("ModalTitle").innerHTML = "Create Account";
            document.getElementById("ModalMessage").innerHTML = response.responseMessage;
            $('#Modal').modal('show');
        },
        error: function (data) {
            let response = JSON.parse(data);
            document.getElementById("ModalTitle").innerHTML = "Create Account";
            document.getElementById("ModalMessage").innerHTML = data;
            $('#Modal').modal('show');
        }
    });
});