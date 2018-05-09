var Zhong = {
	myDian: 1,
	status: 1,
	ff: 1,
	name: ""
};

$(document).ready(function() {
	$(".sec").click(function() {
		if(Zhong.ff === 1) {
			
		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);
   					var len = obj.length;
   					var b = document.querySelectorAll(".haoyou");
   					
   					for(var j=0; j < len; ++j) {
   						$(".guan-zhu").append("<li class=\"haoyou\">"+obj[j].name+"</li>");
   						
   					}
   					
   				}
   			}
   			t.open("GET","http://172.17.164.36:8080/blog/Fwl.do",true);
   			t.send();
   			
   			Zhong.ff = 0;
		}
		
		
		if(Zhong.myDian === 1)
		{
			$("#xiaLa").css({"transform":"rotate(90deg)"});
			Zhong.myDian = 0;
			document.querySelector(".guan-zhu").style.visibility = "visible";
			
		}
		else 
		{
			$("#xiaLa").css({"transform":"rotate(0deg)"});
			Zhong.myDian = 1;
			document.querySelector(".guan-zhu").style.visibility = "hidden";
			
			
		}
		
		
		
	});
	

});
document.querySelector(".shou-ye").onclick = function() {
	Zhong.status = 1;
   		var t=new XMLHttpRequest,
			obj;
		t.onreadystatechange=function() {
			if(t.readyState==4&&t.status==200)
			{
				obj=JSON.parse(this.responseText);
   				var id = document.querySelectorAll(".my-id")
				
				var name = document.querySelectorAll(".name");
				var time = document.querySelectorAll(".time");
				var content = document.querySelectorAll(".remen-inner p");
				var zhuan = document.querySelectorAll(".zhuan");
				var ping = document.querySelectorAll(".ping");
				var dian = document.querySelectorAll(".dian");
				
				for(var i=0; i<10; ++i) {
   						id[i].innerHTML = obj[i].userid;
					
					name[i].innerHTML = obj[i].nickName;
					time[i].innerHTML = obj[i].createTime;
					content[i].innerHTML = obj[i].content;
					zhuan[i].innerHTML = obj[i].repostCount;
					ping[i].innerHTML = obj[i].commentCount;
					dian[i].innerHTML = obj[i].upvoteCount;
					
				}
				
			}
		}
		t.open("POST","http://172.17.164.36:8080/blog/View.do",true);
		t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		t.send("page=1"+"&ishot=true");
	
};




document.querySelector(".ye input").onchange = function() {
			var c = document.querySelector('.ye input');
	   		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);
   					var id = document.querySelectorAll(".my-id")
   					
   					var name = document.querySelectorAll(".name");
   					var time = document.querySelectorAll(".time");
   					var content = document.querySelectorAll(".remen-inner p");
   					var zhuan = document.querySelectorAll(".zhuan");
   					var ping = document.querySelectorAll(".ping");
   					var dian = document.querySelectorAll(".dian");
   					
   					for(var i=0; i<10; ++i) {
   						id[i].innerHTML = obj[i].userid;
   						
   						name[i].innerHTML = obj[i].nickName;
   						time[i].innerHTML = obj[i].createTime;
   						content[i].innerHTML = obj[i].content;
   						zhuan[i].innerHTML = obj[i].repostCount;
   						ping[i].innerHTML = obj[i].commentCount;
   						dian[i].innerHTML = obj[i].upvoteCount;
   						
   					}
   					
   				}
   			}
   			t.open("POST","http://172.17.164.36:8080/blog/View.do",true);
   			t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   			if(Zhong.status === 1) {
   				t.send("page="+c.value+"&ishot=true");
   				
   			}
   			else {
   				t.send("page="+c.value+"&ishot=false");
   				
   			}
	
	
};


   window.onload = function() {
   		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);
   					
   					var id = document.querySelectorAll(".my-id")
   					var name = document.querySelectorAll(".name");
   					var time = document.querySelectorAll(".time");
   					var content = document.querySelectorAll(".remen-inner p");
   					var zhuan = document.querySelectorAll(".zhuan");
   					var ping = document.querySelectorAll(".ping");
   					var dian = document.querySelectorAll(".dian");
   					
   					for(var i=0; i<10; ++i) {
   						id[i].innerHTML = obj[i].userid;
   						name[i].innerHTML = obj[i].nickName;
   						time[i].innerHTML = obj[i].createTime;
   						content[i].innerHTML = obj[i].content;
   						zhuan[i].innerHTML = obj[i].repostCount;
   						ping[i].innerHTML = obj[i].commentCount;
   						dian[i].innerHTML = obj[i].upvoteCount;
   						
   					}
   					
   				}
   			}
   			t.open("POST","http://172.17.164.36:8080/blog/View.do",true);
   			t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   			t.send("page=1"+"&ishot=true");
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
		document.querySelector("#myFa").onclick = function() {
			var c = document.querySelector("#myInput");
	   		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);

   				}
   			}
   			t.open("POST","http://172.17.164.36:8080/blog/Pub.do",true);
   			t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   			t.send("content="+c.value);
   			
   			document.querySelector("#myInput").value = '';
   			document.querySelector("#faSuccess").style["animation-name"] = "zoom";
   			
	
};
document.querySelector(".he2 a").onclick = function() {
	
		Zhong.status = 0;
		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);
   					var id = document.querySelectorAll(".my-id")
   					
   					var name = document.querySelectorAll(".name");
   					var time = document.querySelectorAll(".time");
   					var content = document.querySelectorAll(".remen-inner p");
   					var zhuan = document.querySelectorAll(".zhuan");
   					var ping = document.querySelectorAll(".ping");
   					var dian = document.querySelectorAll(".dian");
   					
   					for(var i=0; i<10; ++i) {
   						id[i].innerHTML = obj[i].userid;
   						
   						name[i].innerHTML = obj[i].nickName;
   						time[i].innerHTML = obj[i].createTime;
   						content[i].innerHTML = obj[i].content;
   						zhuan[i].innerHTML = obj[i].repostCount;
   						ping[i].innerHTML = obj[i].commentCount;
   						dian[i].innerHTML = obj[i].upvoteCount;
   						
   					}
   					
   				}
   			}
   			t.open("POST","http://172.17.164.36:8080/blog/View.do",true);
   			t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   			t.send("page=1&ishot=false");
	
};

document.querySelector(".name").onclick = function(this) {
			Zhong.status = 0;
			alert(this.nextSibling.nextSibling.innerHTML);
		
		var t=new XMLHttpRequest,
   				obj;
   			t.onreadystatechange=function() {
   				if(t.readyState==4&&t.status==200)
   				{
   					obj=JSON.parse(this.responseText);
   					var id = document.querySelectorAll(".my-id")
   					
   					var name = document.querySelectorAll(".name");
   					var time = document.querySelectorAll(".time");
   					var content = document.querySelectorAll(".remen-inner p");
   					var zhuan = document.querySelectorAll(".zhuan");
   					var ping = document.querySelectorAll(".ping");
   					var dian = document.querySelectorAll(".dian");
   					
   					for(var i=0; i<10; ++i) {
   						id[i].innerHTML = obj[i].userid;
   						
   						name[i].innerHTML = obj[i].nickName;
   						time[i].innerHTML = obj[i].createTime;
   						content[i].innerHTML = obj[i].content;
   						zhuan[i].innerHTML = obj[i].repostCount;
   						ping[i].innerHTML = obj[i].commentCount;
   						dian[i].innerHTML = obj[i].upvoteCount;
   						
   					}
   					
   				}
   			}
   			t.open("GET","http://172.17.164.36:8080/blog/View.do?page=1&ishot=false&name="+document.querySelector(".my-id").innerHTML,true);
// 			t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
   			t.send();
};

