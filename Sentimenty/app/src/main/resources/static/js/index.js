$(function() {
    $( ".column" ).sortable({
      connectWith: ".column",
      stop: function( event, ui ) {
        var viewArray = [];
        $(".column").each(function(i){
            //console.log(i);
            viewArray[i] = [];
            $(this).children().each(function(index) {
                var x = $(this).attr("id");
                //console.log(x);
                viewArray[i].push(x);
            });
        })
        //console.log("test");
        setCookie("layout", JSON.stringify(viewArray), 365);
        //console.log(JSON.parse(getCookie("layout")));
      },
      handle: ".portlet-header",
      cancel: ".portlet-toggle",
      placeholder: "portlet-placeholder ui-corner-all"
    });

    $( ".portlet" )
      .addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
      .find( ".portlet-header" )
        .addClass( "ui-widget-header ui-corner-all" )
        .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");

    $( ".portlet-toggle" ).click(function() {
      var icon = $( this );
      icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
      icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
    });

    $("#revertSort").click(function() {
        $( this ).blur();
        var rry = [["stockModule","percentModule"],["trendsModule"],["gaugeModule","twitterModule"]];
        reorder(rry, true);
        setCookie("layout", JSON.stringify(rry), 365);
    });

    $("#anny").dblclick(function() {
        if(annyang.isListening()){
            annyang.abort();
        } else if (!annyang.isListening()) {
            annyang.start();
        }
    })
  });


function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname+"="+cvalue+"; "+expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        cname = c.substring(0,name.length);
        if (cname == name) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

$(document).ready(function() {
    var user = getCookie("username");
    var layout = getCookie("layout");
        if(user != "") {
            $.post( "/layout", { query: user })
                .done(function( data ) {
                    console.log(data);
            })
        } else if(layout != ""){
            layout = JSON.parse(layout);
            reorder(layout, false);
        }

})

function search(term) {
    $( "#searchTerm" ).val(term);
    $('#modal-content').modal({
        show: true
    });
}

function stockVoice( length ) {
    switch(length) {
        case 'one day':
            $("#oneDay").click();
            break;
        case 'one month':
            $("#oneMonth").click();
            break;
        case 'one year':
            $("#oneYear").click();
            break;
        case 'five years':
            $("#fiveYears").click();
            break;
    }
}


function reorder(layout, anim) {
    layout.forEach(function(element, index, array) {
        element.forEach(function(element){
            //console.log(element)
            if(anim){
                switch(index) {
                    case 0:
                        animateMove(element, "#colOne")
                        break;
                    case 1:
                        animateMove(element, "#colTwo" );
                        break;
                    case 2:
                        animateMove(element, "#colThree")
                        break;
                }
            } else {
                switch(index) {
                    case 0:
                        $( "#" + element ).insertBefore( "#colOne" );
                        break;
                    case 1:
                        $( "#" + element ).insertBefore( "#colTwo" );
                        break;
                    case 2:
                        $( "#" + element ).insertBefore( "#colThree" );
                        break;
                }
            }
        });
    })
}

function animateMove(element, column) {
    $("#"+element).fadeOut(function() {
       $(this).insertBefore( column ).fadeIn();
    });
}

