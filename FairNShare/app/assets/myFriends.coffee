$ ->
	$.get "/showFriends",(friends) ->
		$.each friends,(index,friend) ->
			$('#friends').append $("<li>").text friend.fname+" "+friend.lname 