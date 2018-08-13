ServerProxy = Class.extend({

    bankServerUrl: "localhost:8080",
    socket: null,

    connectToBankServer: function () {
        var self = this;

        this.socket = new WebSocket("ws://" + this.bankServerUrl + "/connect");

        this.socket.onopen = function () {
            console.log("Connection established.");
        };

        this.socket.onclose = function (event) {
            if (event.wasClean) {
                console.log('closed');
            } else {
                console.log('alert close');
            }
            console.log('Code: ' + event.code + ' cause: ' + event.reason);
            window.location.reload(true);
        };

        this.socket.onmessage = function (event) {
            var msg = JSON.parse(event.data);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function (error) {
            console.log("Error " + error.message);
        };
    }

});