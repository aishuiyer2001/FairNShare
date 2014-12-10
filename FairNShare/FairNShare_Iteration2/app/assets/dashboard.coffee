$ ->
    
   $(document).on('click', '#friend-page', ( ->
      $.get "/showFriends",(friends) ->
         $('#friend_div').show()
         $('#friend_div').append $('<h2>').text "My Friends"
         $('#task_div').hide()
         $('#mytask_div').hide()
         $('#task_recurring').hide()
         $('#userPoints').hide()
         $('#dashboard_div').hide()
         $('#task_incomplete').hide()  
         $.each friends,(index,friend) ->
            $('#friend_div').append $('<li class="list-group-item">').text friend.fname+" "+friend.lname
            ))
   
   $(document).on('click', '#task-page', ( ->
      $.get "/showTasks",(tasks) ->
         $('#task_div').show()
         $('#task_div').append $('<h2>').text " All Tasks"
         $('#task_div').append $('<b>').text "Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---	 "+"End Date"+" ---"+"Score"
         $('#friend_div').hide()
         $('#task_recurring').hide()
         $('#userPoints').hide()
         $('#mytask_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each tasks,(index,task) ->
            $('#task_div'). append $('<tr><td><li class="list-group-item"></td>').text task.title+" ---- "+task.emailAssignedTo+" ---- "+task.createdBy+" ---- "+task.startDate+" ---- "+task.endDate+" ---- "+task.newPoints 
            ))
  