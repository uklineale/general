$('#modal-content').on('shown.bs.modal', function () {
    var term = $( "#searchTerm" ).val();
    $.post( "/search", { query: term })
      .done(function( data ) {
        $( "#searchTerm" ).val("");
        $(".modal-body").text("");
        $(".resultsTerm").text(term);
        $("#resultsCount").text(data.length);
        term = "";
        data.forEach(function(d) {
            var jsonn = JSON.parse(d.sentiment);
            d.sentiment = jsonn;
            if(d.sentiment && d.sentiment.score){
                d.sentiment.score = d.sentiment.score * 50 + 50;
                d.sentiment.score = String(d.sentiment.score).substring(0,5);
            } else {
                d.sentiment = {
                    score: 1
                };
                d.sentiment.score = "0.00";
            }
            if(d.sentiment.score < 50 ) {
                d.arrow = "bottom";
                d.color = "red";
            } else {
                d.arrow = "top";
                d.color = "green";
            }
            var date = new Date(d.time);
            d.time = String(date).substring(0,24);
          });
        $("#searchTemplate").tmpl(data).appendTo( ".modal-body" );
      });
});

$('#openBtn').click(function () {
    $('#modal-content').modal({
        show: true
    });
});

formSubmit = function() {
    $('#modal-content').modal({
        show: true
    });
}

$('#modal-content').on('hidden.bs.modal', function () {
    $(".modal-body").text("");
})