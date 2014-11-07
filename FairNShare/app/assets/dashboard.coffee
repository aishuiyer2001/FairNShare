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
            $('#task_div'). append $('<tr><td><li class="list-group-item"></td>').text task.title+" ---- "+task.emailAssignedTo+" ---- "+task.createdBy+" ---- "+task.startDate+" ---- "+task.endDate+" ---- "+task.points 
            ))
   ###
   Script that displays the list of tasks of each user when he clicks on "My Tasks", so that the user can mark them as Done using the button beneath the Task 
   ###
   $(document).on('click', '#mytask-page', ( ->
      $.get "/showMyTasks",(mytasks) ->
         $('#mytask_div').show()
         $('#mytask_div').append $('<h2>').text " My Tasks"
         $('#mytask_div').append $('<b>').text "Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---	 "+"End Date"+" ---"+"Score"
         $('#friend_div').hide()
         $('#task_recurring').hide()
         $('#userPoints').hide()
         $('#task_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each mytasks,(index,task) ->
            $('#mytask_div').append $('<tr><td><li class="list-group-item"></td>').text task.title+" ---- "+task.emailAssignedTo+" ---- "+task.createdBy+" ---- "+task.startDate+" ---- "+task.endDate+" ---- "+task.points 
            $('#mytask_div').append  $('</td><td><form id="getTaskIDForm" role="form" action="/personUpdate" method="POST">
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
         $('#task_incomplete').append $('<b>').text "Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---	 "+"End Date"+" ---"+"Score"
         $('#friend_div').hide()
         $('#mytask_div').hide()
         $('#task_recurring').hide()
         $('#userPoints').hide()
         $('#dashboard_div').hide()
         $('#task_div').hide()
         $.each incompleteTasks,(index,task) ->
            $('#task_incomplete'). append $('<tr><td><li class="list-group-item"></td>').text task.title+" ---- "+task.emailAssignedTo+" ---- "+task.createdBy+" ---- "+task.startDate+" ---- "+task.endDate+" ---- "+task.points 
            $('#task_incomplete').append  $('<form id="TaskIDForm" role="form" action="/taskUpdate" method="POST">
					<input name="taskID" type="hidden" id="taskID" value="'+task.taskID+'"><input type="submit" class="btn btn-success" id="mybutton" value="Assign it to Me!"></button></form>')
            ))
  

            
            
            
   ###
   Script that displays the list of recurring tasks of each user when he clicks on "My Recurring Tasks", so that the user can mark them as Done using the button beneath the Task 
   ###
   $(document).on('click', '#myRecurringtask-page', ( ->
      $.get "/showMyRecurringTasks",(myRecurringtasks) ->
         $('#task_recurring').show()
         $('#task_recurring').append $('<h2>').text " My Recurring Tasks"
         $('#task_recurring').append $('<b>').text "Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---	 "+"End Date"+" ---"+"Score"
         $('#friend_div').hide()
         $('#userPoints').hide()
         $('#task_div').hide()
         $('#mytask_div').hide()
         $('#task_incomplete').hide()
         $('#dashboard_div').hide()
         $.each myRecurringtasks,(index,task) ->
            $('#task_recurring').append $('<tr><td><li class="list-group-item"></td>').text task.title+" ---- "+task.emailAssignedTo+" ---- "+task.createdBy+" ---- "+task.startDate+" ---- "+task.endDate+" ---- "+task.points 
            $('#task_recurring').append  $('</td><td><form id="getTaskIDForm" role="form" action="" method="">
					<input name="taskID" type="hidden" id="taskID" value="'+task.taskID+'"><input type="submit" class="btn btn-success" id="mybutton" value="Done!"></button></form>')
            ))
            
 