$(document).ready(function(){
    $("table tbody tr td:first-child").click(function(){
        var switching= true;
        
        do {
            switching = false;
            
            for (var c = 2; c <= $(this).parent().children().length - 1; c++) {
                var CurrValue = $(this).parent().children("td:nth-child(" + c + ")").text();
                var  NextValue = $(this).parent().children("td:nth-child(" + (c + 1) + ")").text();    
                if ($.isNumeric(CurrValue))
                    CurrValue = parseInt (CurrValue);
                if ($.isNumeric(NextValue))
                    NextValue = parseInt (NextValue);
                if (!$(this).hasClass("ascendent") && CurrValue > NextValue) {
                    for (var r = 1; r <= $(this).parent().parent().children().length; r++) {
                        var temp = $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + c + ")").text();
                        $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + c + ")").text(
                            $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + (c + 1) + ")").text());
                        $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + (c + 1) + ")").text(temp);
                    }
                    switching = true;
                }
                else 
                  if (!$(this).hasClass("descendent") && $(this).hasClass("ascendent") && CurrValue < NextValue) {
                    for (var r = 1; r <= $(this).parent().parent().children().length; r++) {
                        var temp = $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + (c + 1) + ")").text();
                        $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + (c + 1) + ")").text(
                            $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + c + ")").text());  
                        $(this).parent().parent().children("tr:nth-child(" + r + ")").children("td:nth-child(" + c + ")").text(temp);
                    }    
                    switching = true;
                }
            }
        } while (switching);
        
        if ($(this).hasClass("ascendent"))
            $(this).removeClass("ascendent").addClass("descendent");
        else
            $(this).removeClass("descendent").addClass("ascendent");
    });
});