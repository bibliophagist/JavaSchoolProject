Engine = Class.extend({

    bankServerUrl: "https://teamseven-server.azurewebsites.net:8080/",

    load: function () {
        if ((this.getCookie("login")===undefined) || (this.getCookie("login")===null)) {
            window.location.href = "index_welcomePage.html";
        }
        newEngine.setCookie("login","login");
        document.getElementById("loginInfo").innerHTML="Login: " + newEngine.getCookie("login");
    },

    unload: function () {
        this.setCookie("login", "", {
            expires: -1
        });
        window.location.reload();
    },

    setCookie: function (name, value, options) {
        options = options || {};

        let expires = options.expires;

        if (typeof expires === "number" && expires) {
            let d = new Date();
            d.setTime(d.getTime() + expires * 1000);
            expires = options.expires = d;
        }
        if (expires && expires.toUTCString) {
            options.expires = expires.toUTCString();
        }

        value = encodeURIComponent(value);

        let updatedCookie = name + "=" + value;

        for (let propName in options) {
            updatedCookie += "; " + propName;
            let propValue = options[propName];
            if (propValue !== true) {
                updatedCookie += "=" + propValue;
            }
        }

        document.cookie = updatedCookie;
    },

    getCookie: function (name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }

});

newEngine = new Engine();
