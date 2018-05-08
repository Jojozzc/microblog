$(document).ready(function(){
	$("#search").focus(function()
	{
		$(".header-search").css("border-color","#eb7350");
	});
	
	$("#search").blur(function()
	{
		$(".header-search").css("border-color","#d8d8d9");
	});
	
	
	
});
$(window).scroll(function()
{
	var height=window.pageYOffset;
	if(height>10)
	{
		$(".register-nav").css({
			"border-bottom":"1px solid #cecece",
			"opacity":"0.89"
		});
	}
	else{
		$(".register-nav").css({
			"border-bottom":"1px solid #f3f3f3",
			"opacity":"1.0"
		});
	}
	
});



