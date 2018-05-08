
$(document).ready(function() {
// 	$("#ceshi").click(function() {
// 		var t=new XMLHttpRequest,
// 		 	obj;
// 		t.onreadystatechange=function() {
// 			if(t.readyState==4&&t.status==200)
// 			{
// 				obj=JSON.parse(this.responseText);
// 				document.getElementById("userID").value=obj.name;
// 				document.getElementById("userPassWord").value=obj.password;
// 			}
// 		}
// 		t.open("GET","json/ceshi.json",true);
// 		t.send();

});
// 	$("#regBut").click(function() {
// 		$.ajax({
// 			type:"post",
// 			url:"",
// 			data: 
// 			dataType: "json",
// 			success: function(msg) {
// 				alert(typeof msg.name);
// 			}
// 		});
// 
// });
		
	
	$("#zhanghao").click(function()
	{
		$("#zhanghao").css(
		{
			"border-bottom":"3px solid #fa7d3c",
			"color":"#0f1012"
		});
		$("#anquan").css(
			{
			"border-bottom":"1px solid #f2f2f5",
			"color":"#999"
				
			}
		);
		
	});
	$("#anquan").click(function()
	{
		$("#zhanghao").css(
		{
			"border-bottom":"1px solid #f2f2f5",
			"color":"#999"
			
		});
		$("#anquan").css(
			{
			"border-bottom":"3px solid #fa7d3c",
			"color":"#0f1012"
			}
		);
		
	});

	
	$("#userID").focus(function()
	{
		$(".yonghuming").css("border-color","#eb7350");
		$(".biaodan").css("box-shadow","0 4px 10px 0 rgb(0,0,0,0.6)");
		
	});
	$("#demo").hover(function()
	{
		$("#demo").css("box-shadow","0 4px 10px 0 rgb(0,0,0,0.6)");
	});
	$("#demo").mouseleave(function()
	{
		$("#demo").css("box-shadow","none");
	});


	
	
		
	$("#userID").blur(function()
	{
		$(".yonghuming").css("border-color","#cfcfcf");
	});
		$("#userPassWord").focus(function()
	{
		$(".mima").css("border-color","#eb7350");
	});
	
		
	$("#userPassWord").blur(function()
	{
		$(".mima").css("border-color","#cfcfcf");
		$(".biaodan").css("box-shadow","none");
	});
	$(".biaodan").blur(function()
	{
		$(".biaodan").css("box-shadow","none");
	});
	



   $("#anquan").click(function(){
   	$(".biaodankuang2").css({
   		"display":"none"
   		
   	});
   	
   	$(".erwei").css(
   		{
   			"display":"inline-block"
   		}
   	);
   	$(".kuang").css({
   		"background-color": "#f2f2f5"
   	});
   });
    $("#zhanghao").click(function(){
   	$(".biaodankuang2").css({
   		"display":"block"
   		
   	});
   	$(".erwei").css(
   		{
   			"display":"none"
   		}
   	);
   	$(".kuang").css({
   		"background-color": "white"
   	});
   });
	






