$( document ).ready(function() {
    $.get( "/percentSentiment", function( data ) {
      var array = data.split('\n');
      var today = array[1].split(',')[1] ;
      var yesterday = array[2].split(',')[1];
      $("#good").text(String(today).substring(0,4) + '%');
      $("#bad").text(String(yesterday).substring(0,4) + '%');
    });
});