SignOut = Class.extend({

    signOut: function () {
        document.getElementById("loginButton").style.display = "block";
        document.getElementById("registerButton").style.display = "block";
        document.getElementById("signOut").style.display = "none";
        document.getElementById("loginedInfo").style.display = "none";
        document.getElementById("historyButton").style.display = "none";
        document.getElementById("game").style.display = "none";
    }

});
