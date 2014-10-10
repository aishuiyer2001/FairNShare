$ ->
   $(document).on('click', '#showFriends', ( ->
      $.get "/showFriends",(friends) ->
         $.each friends,(index,friend) ->
            $('#friends').append $("<li>").text friend.fname+" "+friend.lname
            ))
   $(document).on('click', '#showTasks', ( ->
      $.get "/showTasks",(tasks) ->
         $.each tasks,(index,task) ->
            $('#tasks').append $("<li>").text task.title
            ))