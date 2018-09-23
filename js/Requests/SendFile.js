$('#fileForm').submit(function (e) {

    let data = new FormData();
    data.append('file', $('#file')[0].files[0]);

    console.log(
        data.get("file")
    );

    /*$.ajax({
        url: "http://" + newEngine.bankServerUrl + "/" + "handleJson",
        data: data,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function (data) {
            alert(data);
        }
    });*/
});