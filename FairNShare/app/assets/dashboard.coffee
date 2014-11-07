$ ->
    
   $(document).on('click', '#friend-page', ( ->
      $.get "/showFriends",(friends) ->
         $('#friend_div').show()
         $('#friend_div').append $('<h2>').text "My Friends"
         $('#task_div').hide()
         $('#mytask_div').hide()
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
         $('#friend_div').hide()
         $('#userPoints').hide()
         $('#mytask_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each tasks,(index,task) ->
            $('#task_div').append $('<li class="list-group-item">').text task.title+" Score: "+task.points+"  Start Date:"+task.startDate+" Due Date: "+task.endDate+"Assigned to:  "+task.emailAssignedTo
            ))
   $(document).on('click', '#mytask-page', ( ->
      $.get "/showMyTasks",(mytasks) ->
         $('#mytask_div').show()
         $('#mytask_div').append $('<h2>').text " My Tasks"
         $('#friend_div').hide()
         $('#userPoints').hide()
         $('#task_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each mytasks,(index,task) ->
            $('#mytask_div').append $('<li class="list-group-item">').text task.title+" Score: "+task.points+"  Start Date:"+task.startDate+" Due Date: "+task.endDate+"Assigned to:  "+task.emailAssignedTo
            $('#mytask_div').append  $('<form action="personUpdate" onclick="personUpdate();"
					method="GET"><input type="submit" class="btn btn-success" id="button" value="Done!"></form>')
            ))
   
   $(document).on('click', '#incomplete-page', ( ->
      $.get "/showIncompleteTasks",(incompleteTasks) ->
         $('#task_incomplete').show()
         $('#task_incomplete').append $('<h2>').text "Incomplete Tasks"
         $('#friend_div').hide()
         $('#mytask_div').hide()
         $('#userPoints').hide()
         $('#dashboard_div').hide()
         $('#task_div').hide()
         $.each incompleteTasks,(index,task) ->
            $('#task_incomplete').append $('<li class="list-group-item">').text task.title+" Score: "+task.points+" Task ID: "+task.taskID+"   Start Date:"+task.startDate+"  Due Date: "+task.endDate; 
            $('#task_incomplete').append  $('<form action="taskUpdate" onclick="taskUpdate();"
					method="GET"><input type="submit" class="btn btn-success" id="button" value="Assign it to Me!"></form>')
            ))