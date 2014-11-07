$(document).ready(
		
		/* displays the points earned by user and points needed by him to do fair share of work,
		* when the user is redirected to the dashboard after log in
		*/
		
    function() {       
        $.get("/getPointsToComplete",function(data,status){
             $("#toComplete").text(data.PointsToComplete);
             $("#earned").text(data.EarnedPoints);
                     });    
        
        
        
        /* displays the points earned by user and points needed by him to do fair share of work,
		* when the user clicks the notifications tab on the left menu bar
		*/
        
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


/* displays the points earned by user and points needed by him to do fair share of work,
* when the user clicks the dashboard tab on the left menu bar
*/

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
    

    
    
