Engine = Class.extend({

    serverProxy: null,

    load: function() {
        this.serverProxy = new ServerProxy();
        //newEngine.serverProxy.connectToBankServer();
    },

    getLogin: function () {
        return newEngine.serverProxy.username;
    },

    restart: function() {
        newEngine.serverProxy.connectToBankServer();
    },

});

newEngine = new Engine();