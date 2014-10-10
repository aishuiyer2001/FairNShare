$ ->
   $(document).on('click', '#friend-page', ( ->
      $.get "/showFriends",(friends) ->
         $('#friend_div').show()
         $('#task_div').hide()
         $.each friends,(index,friend) ->
            $('#friend_div').append $("<li>").text friend.fname+" "+friend.lname
            ))
   $(document).on('click', '#showTasks', ( ->
      $.get "/showTasks",(tasks) ->
         $('#task_div').show()
         $('#friend_div').hide()
         $.each tasks,(index,task) ->
            $('#task_div').append $("<li>").text task.title
            ))