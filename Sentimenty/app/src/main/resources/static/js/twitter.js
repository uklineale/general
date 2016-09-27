$(document).ready(function(){
    $.get("/twitter", function (twitterList){
        for(var i=0; i < twitterList.length; i++){
            var newDate = new Date(twitterList[i].created);
            twitterList[i].created = String(newDate).substring(0,24);
            $("#twitterTemplate").tmpl(twitterList[i]).appendTo(".tweetedAbout");
        }
    })
})