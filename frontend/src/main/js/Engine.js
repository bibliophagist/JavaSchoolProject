Engine = Class.extend({

    serverProxy: null,

    load: function() {
        if (!newInputEngine.bindings.length) {
            newInputEngine.setup();
        }

        this.serverProxy = new ServerProxy();
        newEngine.serverProxy.connectToBankServer();
    },
    

    restart: function() {
        newInputEngine.removeAllListeners();
        newEngine.setup();
        this.serverProxy = new ServerProxy();
    },

});

newEngine = new Engine();