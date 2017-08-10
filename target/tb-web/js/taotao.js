var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("SSO_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://queryserver.tb.com/user/queryUserByToken/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
					var html =data.userName+"，欢迎来到嗨淘！<a href=\"http://sso.tb.com/service/user/logout/"+_ticket+"\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});