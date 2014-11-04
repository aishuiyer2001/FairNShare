$(document).ready(
    function() {
        //$('div.userPoints').$('h5').$('span').text('test');
        
        
        $.get("/getPoints",function(data,status){
             $("#earned").text(data);
          });
        
    }
);