$ ->
   $('#dashboard_div').append $('<h2>').text "Welcome to dashboard, (Under construction)"  
   $(document).on('click', '#friend-page', ( ->
      $.get "/showFriends",(friends) ->
         $('#friend_div').show()
         $('#friend_div').append $('<h2>').text "My Friends"
         $('#task_div').hide()
         $('#dashboard_div').hide()  
         $.each friends,(index,friend) ->
            $('#friend_div').append $('<li class="list-group-item">').text friend.fname+" "+friend.lname
            ))
   $(document).on('click', '#showTasks', ( ->
      $.get "/showTasks",(tasks) ->
         $('#task_div').show()
         $('#task_div').append $('<h2>').text "Tasks"
         $('#friend_div').hide()
         $('#dashboard_div').hide()
         $.each tasks,(index,task) ->
            $('#task_div').append $('<li class="list-group-item">').text task.title+" Score: "+task.points
            ))
   