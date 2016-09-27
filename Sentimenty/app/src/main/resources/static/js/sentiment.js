$( document ).ready(function() {
    $.get( "/sentiment", function( data ) {
      var today = data[0];
      var yesterday = data[1];
      var change = Math.abs(today-yesterday);
      $("#sentiment-today").text(String(today).substring(0,7));
      $("#sentiment-yesterday .val").text(String(change).substring(0,7));
      if(change < 0) {
        $("#sentiment-yesterday .change").addClass("glyphicon glyphicon-menu-down red");
      } else {
        $("#sentiment-yesterday .change").addClass("glyphicon glyphicon-menu-up green");
      }
    });
});