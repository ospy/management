$(document).ready(function () {

  



    $(".more").click(function () {
        $(".more").toggleClass("less");
        $(".hidebox").toggle();
        $(".more").html("更多 <i class='more-arrow'></i>");
        $(".less").html("收起 <i class='less-arrow'></i>");
    });


});




