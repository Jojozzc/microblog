$(document).ready(function() {
	$(".sec").click(function() {
		$("#xiaLa").css({"transform":"rotate(90deg)"});
		
	});
});


   window.onload = function() {
   		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);
   					
   					var name = document.querySelectorAll(".name");
   					var time = document.querySelectorAll(".time");
   					var content = document.querySelectorAll(".remen-inner p");
   					var zhuan = document.querySelectorAll(".zhuan");
   					var ping = document.querySelectorAll(".ping");
   					var dian = document.querySelectorAll(".dian");
   					
   					for(var i=0; i<10; ++i) {
   						name[i].innerHTML = obj[i].nickName;
   						time[i].innerHTML = obj[i].createTime;
   						content[i].innerHTML = obj[i].content;
   						zhuan[i].innerHTML = obj[i].repostCount;
   						ping[i].innerHTML = obj[i].commentCount;
   						dian[i].innerHTML = obj[i].upvoteCount;
   						
   					}
   					
   					alert(obj[0].content);
   				}
   			}
   			t.open("POST","http://172.17.164.36:8080/blog/View.do",true);
   			t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   			t.send("page=1");
   }
// 

$("#myInput").bind('input propertychange',function() {
	var text = $("#myInput").val();
	if(!text) {
		document.querySelector("#myFa").style.disabled = "disabled"
	}
	else {
		document.querySelector("#myFa").removeAttribute("disabled");
	}
});



// document.querySelector("#myInput").onchange = function() {
// 	console.log("Bingo");
// 	document.querySelector("#myFa").removeAttribute("disabled");
// }