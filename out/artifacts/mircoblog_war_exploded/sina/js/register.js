function register() {
	document.querySelector(".register").style.display="block";
}

function closeInner()
{
	document.querySelector(".register").style.display="none";
	
}
function myRegister() {
	var t=new XMLHttpRequest,
	obj;
t.onreadystatechange=function() {
	if(t.readyState==4&&t.status==200)
	{
		obj=JSON.parse(this.responseText);
		if(obj.code == 2001) {
			alert("注册成功！");
			closeInner();
		}
		else if(obj.code == 4001) {
			alert("账户名或密码错误！");
		}
		else if(obj.code == 5001) {
			alert("服务器错误！");
		}
		else if(obj.code == 6001) {
			alert("已经是好友！");
		}
		
	}
}
	t.open("POST","/blog/Reg.do",true);
	t.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	t.send("register-name="+document.querySelector("#register-name").value+"&register-mingzi="+document.querySelector("#register-mingzi").value+"&register-pas="+document.querySelector("#register-pas").value);
}

