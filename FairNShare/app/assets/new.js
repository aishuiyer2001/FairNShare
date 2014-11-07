$(document).ready(
    function() {
        $.get("/getPointsToComplete", function(data, status) {
            $("#toComplete").text(data.PointsToComplete);
            $("#earned").text(data.EarnedPoints);
        });
 $("#notifications").click(function(){
  $("#task_div").hide();
  $('#friend_div').hide();
  $("#dashboard_div").hide();
  $.get("/getPointsToComplete",function(data,status){
     $("#toComplete").text(data.PointsToComplete);
     $("#earned").text(data.EarnedPoints);
     $("#userPoints").show();
     $("#userPoints").prepend("<h2 id='notification_div' style='top:70px;'>Notifications</h2>");

           });
        });        

$("#dashboard_nav").click(function(){
      $("#task_div").hide();
      $("#friend_div").hide();
      $("#notification_div").hide();
      $("#dashboard_div").show();
      $.get("/getPointsToComplete",function(data,status){
      $("#toComplete").text(data.PointsToComplete);
      $("#earned").text(data.EarnedPoints);
      $("#userPoints").show();
               });
         }); 
    });