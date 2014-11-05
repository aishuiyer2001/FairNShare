$(document).ready(
    function() {       
        $.get("/getPointsToComplete",function(data,status){
             $("#toComplete").text(data.PointsToComplete);
             $("#earned").text(data.EarnedPoints);
                     });
        
    }
  );
  