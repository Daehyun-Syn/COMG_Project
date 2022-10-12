
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.CustomerDTO" %>
<%@ page import="java.util.List" %>

<%
	List<CustomerDTO> rList = (List<CustomerDTO>) request.getAttribute("rList");
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
					<input type="text" placeholder="Search..." name="" class="form-control search">
					<div class="input-group-prepend">
						<span class="input-group-text search_btn"><i class="fas fa-search"></i></span>
					</div>
				</div>
			</div>
			<div class="card-body contacts_body">
				<ui class="contacts">
					<%
						for ( CustomerDTO pDTO : rList) {
					%>
					<div class="send_button" id="sendS<%=pDTO.getUserSeq()%>">
					<form action="/COMG/adminCustomer">
						<input type="hidden" name="user_id" value="<%=pDTO.getUserId()%>">
						<input type="hidden" name="room_key" value="<%=pDTO.getCustRoom()%>">
						<input type="hidden" name="profilePath" value="<%=pDTO.getServerFilePath()%>">
						<input type="hidden" name="userName" value="<%=pDTO.getUserName()%>">
						<input type="submit" id="submit<%=pDTO.getUserSeq()%>" style="display: none">
					</form>
					<li class="active">
						<div class="d-flex bd-highlight">
							<div class="img_cont">
								<img src="<%=pDTO.getServerFilePath()%>" class="rounded-circle user_img">
								<span class="online_icon offline"></span>
							</div>
							<div class="user_info">
								<span><%=pDTO.getUserName()%></span>
								<p><%=pDTO.getUserName()%> is offline</p>
							</div>
						</div>
					</li>
					</div>
					<%

					}%>

				</ui>
			</div>
			<div class="card-footer"></div>
		</div></div>
		<div class="col-md-8 col-xl-6 chat">
			<div class="card">
				<div class="card-header msg_head">
					<div class="d-flex bd-highlight">
						<div class="img_cont">

						</div>
						<div class="user_info">
							<span>고객센터</span>
							<p>좌측에 사용자를 클릭하여 고객에게 답변을 전송하세요.</p>
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
				<div class="card-body msg_card_body">
					<div class="d-flex justify-content-start mb-4">

					</div>
					<div class="d-flex justify-content-end mb-4">

					</div>
					<div class="d-flex justify-content-start mb-4">

					</div>
					<div class="d-flex justify-content-end mb-4">

					</div>
					<div class="d-flex justify-content-start mb-4">

					</div>
					<div class="d-flex justify-content-end mb-4">

					</div>
					<div class="d-flex justify-content-start mb-4">

					</div>
				</div>
				<div class="card-footer">
					<div class="input-group">
						<div class="input-group-append">
							<span class="input-group-text attach_btn"><i class="fas fa-paperclip"></i></span>
						</div>
						<textarea name="" class="form-control type_msg" placeholder="Type your message..."></textarea>
						<div class="input-group-append">
							<span class="input-group-text send_btn"><i class="fas fa-location-arrow"></i></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).on('click', '.send_button', function() {
		let div_res = $(this).attr('id');
		let result = div_res.substring(5);
		console.log(result);
		console.log("펑션안에들어옴");
		return document.getElementById("submit"+result).click();
	});
</script>
</body>
</html>