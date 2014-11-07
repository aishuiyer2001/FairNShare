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
   ###
   Script that displays the list of tasks of each user when he clicks on "My Tasks", so that the user can mark them as Done using the button beneath the Task 
   ###
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
            $('#mytask_div').append  $('<form id="getTaskIDForm" role="form" action="/personUpdate" method="POST">
					<input name="taskID" type="hidden" id="taskID" value="'+task.taskID+'"><input type="submit" class="btn btn-success" id="mybutton" value="Done!"></button></form>')
            ))
            
            
            
   ###
   Script that displays the list of recurring tasks of each user when he clicks on "My Recurring Tasks", so that the user can mark them as Done using the button beneath the Task 
   ###
   $(document).on('click', '#myRecurringtask-page', ( ->
      $.get "/showMyRecurringTasks",(myRecurringtasks) ->
         $('#task_recurring').show()
         $('#task_recurring').append $('<h2>').text " My Recurring Tasks"
         $('#friend_div').hide()
         $('#userPoints').hide()
         $('#task_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each myRecurringtasks,(index,task) ->
            $('#task_recurring').append $('<li class="list-group-item">').text task.title+" Score: "+task.points+"  Start Date:"+task.startDate+" Due Date: "+task.endDate+"Assigned to:  "+task.emailAssignedTo
            $('#task_recurring').append  $('<form id="getTaskIDForm" role="form" >
					<input name="taskID" type="hidden" id="taskID" value="'+task.taskID+'"><input type="submit" class="btn btn-success" id="mybutton" value="Done!"></button></form>')
            ))
            
            
   ###
   Script that displays the list of incomplete tasks in the list of all tasks, when the user clicks on "Incomplete Tasks", so that the user can assign the task to himself
   by clicking 'Assign to Me!' button below
   ###
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
            $('#task_incomplete').append $('<li class="list-group-item" id="task.taskID">').text task.title+" Score: "+task.points+" Task ID: "+task.taskID+"   Start Date:"+task.startDate+"  Due Date: "+task.endDate+"Assigned to:  "+task.emailAssignedTo 
            $('#task_incomplete').append  $('<form id="TaskIDForm" role="form" action="/taskUpdate" method="POST">
					<input name="taskID" type="hidden" id="taskID" value="'+task.taskID+'"><input type="submit" class="btn btn-success" id="mybutton" value="Assign it to Me!"></button></form>')
            ))
  