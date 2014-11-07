function validatePassword(){
  var x = document.forms["login"]["password"].value;
    if(x.length<6){
    alert("enter a valid password");
   }
   
  }