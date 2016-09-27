$( document ).ready(function() {

  var NUM_OF_TRENDS_TO_DISPLAY = 4

  $.get("/trends", function ( trendMentionList ) {
    for (var i = 0; i < NUM_OF_TRENDS_TO_DISPLAY; i++){
        $("<button class='btn'>"+trendMentionList[0][i]+"</button>").addClass("trendButton").addClass("trend"+i).appendTo(".trend")
    }

    var showList = trendMentionList[1];
    for (var i = 0; i < trendMentionList[1].length; i++ ){//List of Trends which has List of Press
        for (var j = 0; j < trendMentionList[1][i].length; j++){//List of Press
            var apiArticle = trendMentionList[1][i][j]
            frontendArticle = apiArticle
            var parsedSentiment = JSON.parse(frontendArticle.sentiment)
            frontendArticle.sentiment = parsedSentiment
            if ( frontendArticle.sentiment && frontendArticle.sentiment.score ){
                var sentScore = frontendArticle.sentiment.score
                sentScore = 50*sentScore+50
                frontendArticle.sentiment.score = String(sentScore).substring(0,5);
            } else {
                frontendArticle.sentiment = {
                    score: 1
                };
                frontendArticle.sentiment.score = "0.00"
            }
            if (frontendArticle.sentiment.score < 50) {
                frontendArticle.arrow = "bottom"
                frontendArticle.color = "red"
            } else {
                frontendArticle.arrow = "top"
                frontendArticle.color = "green"
            }
            var date = new Date(frontendArticle.time)
            //frontendArticle.time = "" + (date.getMonth()+1) + "/" + date.getDate() + "/" + date.getFullYear()
            frontendArticle.time = String(date).substring(0,24);
            //frontendArticle.title = frontendArticle.title.substring(0,90);
            showList[i][j] = frontendArticle
            $(showList[i][j]).attr("trendNo", ""+i)
        }
    }
    for ( var i = 0; i < showList.length; i++){
        $("#clientTemplate").tmpl(showList[i]).appendTo("#mentions")
    }

    if ($(".trendButton.active").length == 0){
        $(".mention").removeClass("hide")
    }

    $(".trendButton").click(function(){
        $(".mention").addClass("hide")
        $(this).toggleClass("active")
        if (document.activeElement != document.body)
            document.activeElement.blur();
        $(".trendButton").each(function(){
            if (this.className.indexOf("active") > -1){
                //Get all class of all buttons
                var classList = this.className.split(/\s+/);
                //The third class of classList is the trend#
                var trendNo = classList[2].substring(5,6)
                //Append trend number to articles to toggle
                var articleNo = ".article"+trendNo;
                $(articleNo).toggleClass("hide")
            }
        })
        if ($(".trendButton.active").length == 0){
                $(".mention").removeClass("hide")
        }
    })

  })
});
