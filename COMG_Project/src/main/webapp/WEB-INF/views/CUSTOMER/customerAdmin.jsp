<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kopo.poly.dto.ChatDTO" %>

<%
	ChatDTO rDTO = (ChatDTO) request.getAttribute("pDTO"); //사용자이름 룸키 담아옴
	List<ChatDTO> rList = (List<ChatDTO>) request.getAttribute("rList");
	String userProfile = CmmUtil.nvl((String) session.getAttribute("user_profile"));
	String profilePath = CmmUtil.nvl(request.getParameter("profilePath"));
	String userName = CmmUtil.nvl(request.getParameter("userName"));
	String myName = CmmUtil.nvl(request.getParameter("myName"));
//채팅리스트 조회 결과 보여주기
	if (rList == null) {
		rList = new ArrayList<ChatDTO>();

	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>COMG | 고객센터</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.js"></script>
	<link href="/css/cust2.css" rel="stylesheet" />
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- Material Icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
	<script src="/js/cust2.js"></script>
</head>
<body>
<!-- Navbar -->

<nav class="navbar navbar-expand-lg blur blur-rounded top-0 z-index-3 shadow position-absolute my-3 py-2 start-0 end-0 mx-4">
	<div class="container-fluid pe-0">
		<a class="navbar-brand font-weight-bolder ms-lg-0 ms-3 " href="/COMG/home">
			COMG
		</a>
		<button class="navbar-toggler shadow-none ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#navigation" aria-controls="navigation" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon mt-2">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
              </span>
		</button>
		<div class="collapse navbar-collapse" id="navigation">
			<ul class="navbar-nav mx-auto ms-xl-auto me-xl-7">
				<li class="nav-item">
				</li>
				<li class="nav-item">
				</li>
				<li class="nav-item">
				</li>
				<li class="nav-item">
				</li>
			</ul>
			<ul class="navbar-nav d-lg-block d-none">

			</ul>
			<ul class="navbar-nav d-lg-block d-none">

			</ul>
		</div>
	</div>
</nav>
<!-- End Navbar -->
<div class="container-fluid h-100">
	<div class="row justify-content-center h-100">
		<div class="col-md-4 col-xl-3 chat"><div class="card mb-sm-3 mb-md-0 contacts_card">
			<div class="card-header">
				<div class="input-group">
					<input type="text" value="고객센터" name="" class="form-control search" readonly>
					<div class="input-group-prepend">
						<span class="input-group-text search_btn"></span>
					</div>
				</div>
			</div>
			<div class="card-body contacts_body">
				<ui class="contacts">
					<li class="active">
						<div class="d-flex bd-highlight">
							<div class="img_cont">
								<img src="<%=profilePath%>" class="rounded-circle user_img">
								<span class="online_icon"></span>
							</div>
							<div class="user_info">
								<span><%=userName%></span>
								<p><%=userName%> is online</p>
							</div>
						</div>
					</li>

				</ui>
			</div>
			<div class="card-footer"></div>
		</div></div>

			<div class="col-md-8 col-xl-6 chat">
				<div class="card">
				<div class="card-header msg_head">
					<div class="d-flex bd-highlight">
						<div class="img_cont">
							<img src="<%=profilePath%>" class="rounded-circle user_img">
							<span class="online_icon"></span>
						</div>
						<div class="user_info">
							<span><%=userName%></span>
							<p><%=userName%> 님의 고객센터 메세지입니다.</p>
						</div>
						<div class="video_cam">

						</div>
					</div>
					<span id="action_menu_btn"><i class="fas fa-ellipsis-v"></i></span>
					<div class="action_menu">
						<ul>
							<li><i class="fas fa-user-circle"></i> View profile</li>
							<li><i class="fas fa-users"></i> Add to close friends</li>
							<li><i class="fas fa-plus"></i> Add to group</li>
							<li><i class="fas fa-ban"></i> Block</li>
						</ul>
					</div>
				</div>
				<div class="card-body msg_card_body" id="talk">
					<%for ( ChatDTO pDTO : rList) { %>
					<%       if (!pDTO.getUser_name().equals(myName)) { %>
					<div class="d-flex justify-content-start mb-4">
						<div class="img_cont_msg">
							<img src="<%=profilePath%>" class="rounded-circle user_img_msg">
						</div>
						<div class="msg_cotainer">
							<h6>
							<%=pDTO.getMsg()%>
							</h6>
							<p>
							<span class="msg_time"><%=pDTO.getDateTime()%></span>
							</p>
						</div>
					</div>
					<% }else {%>
					<div class="d-flex justify-content-end mb-4">
						<div class="msg_cotainer_send">
							<%=pDTO.getMsg()%>
							<span class="msg_time_send"><%=pDTO.getDateTime()%></span>
						</div>
						<div class="img_cont_msg">
							<img src="<%=userProfile%>" class="rounded-circle user_img_msg">
						</div>
					</div>
					<% } %> <!--else -->
					<% } %>

				</div>

				<div class="card-footer">
					<div class="input-group">
						<div class="input-group-append">
							<span class="input-group-text attach_btn"><i class="fas fa-paperclip"></i></span>
						</div>
						<textarea name="" class="form-control type_msg" placeholder="Type your message..." id="msg"></textarea>
						<div class="input-group-append">
							<span id="btnSend" class="input-group-text send_btn"><i class="fas fa-location-arrow"></i></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	function getId(id) {

		console.log(id)
		return document.getElementById(id);
	}

	var data = {};//전송 데이터(JSON)

	var ws;
	var btnSend = getId('btnSend');
	var msg = getId('msg');
	var talk = getId('talk');



	$(document).ready(function () {

		//웹소켓 객체를 생성하는중
		console.log("openSocket");
		if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
			console.log("WebSocket is already opened.");
			return;
		}

		//ws = new WebSocket("ws://43.200.109.62:11000/chatt");  //aws 소켓
		ws = new WebSocket("ws://"+location.host+"/chatt"); //local 소켓

		ws.onopen = function (event) {
			if (event.data === undefined)
				return;

			console.log(event.data)
		};

		//웹소캣으로부터 메세지를 받음
		ws.onmessage = function (msg) {

			var data = JSON.parse(msg.data);
			console.log("여기는 onmessage zone 입니다");
			console.log(data);
			console.log(data.msg);
			console.log(data.date);

			var other = data.name; //상대방이름
			var message = data.msg; //메세지
			var date = data.date; //날짜

			var ss_name = '<%=myName%>';


			console.log(data.name); //보낸사람
			console.log(ss_name); //세션에 저장된 아이디

			if (other == ss_name) {

				console.log("나다"); //보낸사람이랑랑 세션이름일아같으면 오른쪽에 출력

				var html = '';


				html += '<div class="d-flex justify-content-end mb-4"><div class="msg_cotainer_send">' + message+'<br>';
				html += '<span class="msg_time_send">' + date + '</span> </div>';
				html += '<div class="img_cont_msg"> <img src="<%=userProfile%>" class="rounded-circle user_img_msg"> </div> </div>';

				talk.innerHTML += html;
				//스크롤 최 하단으로 내리기
				var chatscroll = document.getElementById("talk");
				chatscroll.scrollTop = chatscroll.scrollHeight;


			} else {

				console.log("내가아님")  //세션이랑 보낸사람이랑 다르면 왼쪾에 출력

				var html = '';
				html += '<div class="d-flex justify-content-start mb-4"> <div class="img_cont_msg"> <img src="<%=profilePath%>" class="rounded-circle user_img_msg"> </div>';
				html += '<div class="msg_cotainer">' + message+'<br>';
				html += '<span class="msg_time">' + date + '</span> </div> </div>';


				talk.innerHTML += html;
				var chatscroll = document.getElementById("talk");
				chatscroll.scrollTop = chatscroll.scrollHeight;

			}


		}
	});

	// Get the input field
	var input = document.getElementById("msg");

	// Execute a function when the user presses a key on the keyboard
	input.addEventListener("keypress", function(event) {
		// If the user presses the "Enter" key on the keyboard
		if (event.key === "Enter") {
			// Cancel the default action, if needed
			event.preventDefault();
			// Trigger the button element with a click
			document.getElementById("btnSend").click();
		}
	});

	btnSend.onclick = function () {
		send();
	}

	function send() {
		if (msg.value.trim() != '') {
			data.name = '<%=myName%>'; //세션에 저장된이름
			data.msg = msg.value;
			data.date = new Date().toLocaleString();
			data.roomKey ='<%=rDTO.getRoomKey()%>';
			var temp = JSON.stringify(data);
			console.log("send보내는 곳입니다.");
			console.log(temp);
			ws.send(temp);


			$.ajax({
				url: "/chat/msg",
				type:"post",
				dataType:"JSON",
				async:true,
				timeout:5000,
				contentType: "application/json;charset=utf-8",
				data:temp,
				sucess:function (data){
					console.log(data);
					console.log("데이터보내기 성공");
				}
			})
		}
		msg.value = '';

	};

</script>
</body>
</html>