$ ->
    
   $(document).on('click', '#friend-page', ( ->
      $.get "/showFriends",(friends) ->
         $('#friend_div').show()
         $('#friend_div').append $('<h2>').text "My Friends"
         $('#task_div').hide()
         $('#dashboard_div').hide()
         $('#task_incomplete').hide()  
         $.each friends,(index,friend) ->
            $('#friend_div').append $('<li class="list-group-item">').text friend.fname+" "+friend.lname
            ))
   $(document).on('click', '#task-page', ( ->
      $.get "/showTasks",(tasks) ->
         $('#task_div').show()
         $('#task_div').append $('<h2>').text "Tasks"
         $('#friend_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each tasks,(index,task) ->
            $('#task_div').append $('<li class="list-group-item">').text task.title+" Score: "+task.points+"  Start Date:"+task.startDate+" Due Date: "+task.endDate
  		    ))
   $(document).on('click', '#incomplete-page', ( ->
      $.get "/showIncompleteTasks",(incompleteTasks) ->
         $('#task_incomplete').show()
         $('#task_incomplete').append $('<h2>').text "Incomplete Tasks"
         $('#friend_div').hide()
         $('#dashboard_div').hide()
         $('#task_div').hide()
         $.each incompleteTasks,(index,task) ->
            $('#task_incomplete').append $('<li class="list-group-item">').text task.title+" Score: "+task.points+"   Start Date:"+task.startDate+"  Due Date: "+task.endDate;
            )) 