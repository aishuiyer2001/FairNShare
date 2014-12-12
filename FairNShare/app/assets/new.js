$(document).ready(

		/* displays the points earned by user and points needed by him to do fair share of work,
		 * when the user is redirected to the dashboard after log in
		 */

		function() {       
			$.get("/getPointsToComplete",function(data,status){
				$("#toComplete").text(data.PointsToComplete);
				$("#earned").text(data.EarnedPoints);
			});    



			$("#myrecurringtask-page").click(function(){
				$.get("/showMyRecurringTasks",function(myRecurringTasks,status){
					$('#friend_div').hide();
					$('#task_incomplete').hide();
					$('#userPoints').hide();
					$("#notification_div").hide();
					$('#dashboard_div').hide();
					$('#task_div').hide();
					$('#mytask_div').hide();
					$('#task_recurring').append("<h2>Recurring Tasks</h2>");
					$('#task_recurring').append("<b>Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---"+"End Date"+" ---"+"Score");
					jQuery.each(myRecurringTasks, function(i,task) {
						$('#task_recurring').append('<tr><td><li class="list-group-item">'+task.title+' ---- '+task.emailAssignedTo+' ---- '+task.createdBy+'---- '+task.startDate+' ---- '+task.endDate+' ---- '+task.newPoints+' <form role="form" action="" method=""> <input type="submit" class="btn btn-success" id="mybutton" value="Done!"></input></form></li></td></tr>');

						$('#task_recurring').show();
				});
			});
			});



			$("#mytask-page").click(function(){
				$.get("/showMyTasks",function(myTasks,status){
					$('#friend_div').hide();
					$('#task_recurring').hide();
					$("#notification_div").hide();
					$('#task_incomplete').hide();
					$('#userPoints').hide();
					$('#dashboard_div').hide();
					$('#task_div').hide();
					$('#mytask_div').append("<h2>My Tasks</h2>");
					$('#mytask_div').append("<b>Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---"+"End Date"+" ---"+"Score");
					jQuery.each(myTasks, function(i,task) {
						$('#mytask_div').append('<tr><td><li class="list-group-item">'+task.title+' ---- '+task.emailAssignedTo+' ---- '+task.createdBy+'---- '+task.startDate+' ---- '+task.endDate+' ---- '+task.oldPoints+' <form role="form" action="/personUpdate/'+task.taskID+'" method="POST"> <input type="submit" class="btn btn-success" id="mybutton" value="Done!"></input></form></li></td></tr>');
						$('#mytask_div').show();
				});
			});
			});



			$("#incomplete-page").click(function(){
				$.get("/showIncompleteTasks",function(incompleteTasks,status){
					$('#friend_div').hide();
					$('#mytask_div').hide();
					$('#task_recurring').hide();
					$("#notification_div").hide();
					$('#userPoints').hide();
					$('#dashboard_div').hide();
					$('#task_div').hide();
					$('#task_incomplete').prepend("<h2>Incomplete Tasks</h2>");
					$('#task_incomplete').append("<b>Task Name"+" ----- "+"Assigned to"+" ------ "+"Assigned by"+" -------- "+"Start Date"+" ---"+"End Date"+" ---"+"Score");
					jQuery.each(incompleteTasks, function(i,task) {
						$('#task_incomplete').append('<tr><td><li class="list-group-item">'+task.title+' ---- '+task.emailAssignedTo+' ---- '+task.createdBy+'---- '+task.startDate+' ---- '+task.endDate+' ---- '+task.newPoints+' <form id="TaskIDForm" role="form" action="/taskUpdate/'+task.taskID+'" method="POST"> <input type="submit" class="btn btn-success" id="mybutton" value="Assign it to Me!"></input></form></li></td></tr>');
						$('#task_incomplete').show();
				});
			});
			});


			/* displays the points earned by user and points needed by him to do fair share of work,
			 * when the user clicks the notifications tab on the left menu bar
			 */
			$("#notifications").click(function(){
				$("#task_div").hide();
				$('#friend_div').hide();
				$("#dashboard_div").hide();
				$('#task_recurring').hide();
				$('#task_incomplete').hide();
				$('#mytask_div').hide();
				$.get("/getPointsToComplete",function(data,status){
					$("#toComplete").text(data.PointsToComplete);
					$("#earned").text(data.EarnedPoints);
					$("#userPoints").prepend("<h2 id='notification_div' style='top:70px;'>Notifications</h2>");
					$("#userPoints").show();

				});
			});   

			
			
			

			
			

			/* displays the points earned by user and points needed by him to do fair share of work,
			 * when the user clicks the dashboard tab on the left menu bar
			 */

			$("#dashboard_nav").click(function(){
				$("#task_div").hide();
				$('#mytask_div').hide();
				$("#friend_div").hide();
				$('#task_incomplete').hide();
				$("#notification_div").hide();
				$('#task_recurring').hide();
				$("#dashboard_div").show();
				$.get("/getPointsToComplete",function(data,status){
					$("#toComplete").text(data.PointsToComplete);
					$("#earned").text(data.EarnedPoints);
					$("#userPoints").show();
				});
			}); 
		});