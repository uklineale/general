$( document ).ready(function() {
    $.get( "/mentions", function( data ) {
      var today = data[0];
      var offset = 0;
      offset = data[1];
      $("#mentionsToday").text(today);
      if (offset == 0){
              $("#offset").text('today');
            } else if (offset == 1){
              $("#offset").text('yesterday');
            } else{
              $("#offset").text(String(offset) + ' days ago');
            }
    });
});

//current sentiment