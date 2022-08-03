<%--
  Created by IntelliJ IDEA.
  User: 신 대현
  Date: 2022-03-30(030)
  Time: 오전 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.CGroupDTO" %>

<!DOCTYPE html>
<html lang="en">
<%
	String user_name = CmmUtil.nvl((String)request.getAttribute("user_name"));
	CGroupDTO rDTO = (CGroupDTO)request.getAttribute("rDTO");
	String group_name = CmmUtil.nvl(rDTO.getGroup_name());
	String group_introduce = CmmUtil.nvl(rDTO.getGroup_introduce());
	String group_regdate = CmmUtil.nvl(rDTO.getGroup_regdate());
	String group_seq = CmmUtil.nvl(rDTO.getGroup_seq());
	String group_password = CmmUtil.nvl(rDTO.getGroup_password());
	String file_path = CmmUtil.nvl((String) request.getAttribute("file_path"));

%>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="apple-touch-icon" sizes="76x76" href="/img/apple-icon.png">
	<link rel="icon" type="image/png" href="/img/favicon.png">
	<title>
		그룹 가입 | COMG
	</title>
	<!--     Fonts and icons     -->
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
	<!-- Nucleo Icons -->
	<link href="/css/nucleo-icons.css" rel="stylesheet" />
	<link href="/css/nucleo-svg.css" rel="stylesheet" />
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- Material Icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
	<!-- CSS Files -->
	<link id="pagestyle" href="/css/soft-ui-dashboard.css?v=1.0.4" rel="stylesheet" />
	<script>
		let group_password = "<%=group_password%>";
		let group_name = "<%=group_name%>";
		let group_seq = "<%=group_seq%>";
		console.log(group_seq)
		function join_group() {
			swal.fire({
				icon : "warning",
				text : "가입하시려면 그룹의 비밀번호를 입력해 주세요",
				input : 'text',
				inputPlaceholder : '그룹 비밀번호를 입력해 주세요',
				inputAttributes: {
					autocapitalize: 'off'
				},
				closeOnClickOutside: false,
				showCloseButton: true,
				confirmButton: true,
				confirmButtonText: '가입',
			}).then(result => {
				if(result.value === group_password) {
					$.ajax({
						url: '/COMG/JoingroupLogic',
						type: "POST",
						data: {"group_seq":group_seq},
						success: function(data){
							if(data === 1){
								console.log("그룹 가입 성공");
								swal.fire({
									icon : 'success',
									title: '그룹 가입 성공!',
									text : "["+group_name+"] 그룹에 가입 되었습니다.",
								}).then(function(){
									location.href="/COMG/Group?GroupSEQ="+group_seq;
								});
							}else{
								console.log("그룹 가입 로직 에러!");
								swal.fire({
									icon : 'warning',
									title: '그룹 가입 실패!',
									text : "다시한번 시도해 주세요.",
								})
								return false;
							}
						},
						error: function (){
							console.log("JavaScript 쿼리오류 !")
						}
					});
				}else {
					console.log("그룹 가입 실패!");
					swal.fire({
						icon :'warning',
						title : '그룹 가입 실패!',
						text : "그룹 비밀번호가 일치하지 않습니다",
					})
				}
			})
		}

	</script>
</head>

<body class="g-sidenav-show  bg-gray-200">

<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">
	<!-- Navbar -->
	<nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" navbar-scroll="true">
		<div class="container-fluid py-1 px-3">
			<nav aria-label="breadcrumb">
				<ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 ps-2 me-sm-6 me-5">
					<li class=""></li>
					<li class=""><br></li>
				</ol>
				<a href="/COMG/main">
				<h6 class="text-black font-weight-bolder ms-2">COMG</h6>
				</a>
			</nav>
			<div class="collapse navbar-collapse me-md-0 me-sm-4 mt-sm-0 mt-2" id="navbar">
				<div class="ms-md-auto pe-md-3 d-flex align-items-center">
					<div class="input-group">
						<a href="/COMG/logout" class="nav-link text-black font-weight-bold px-0">
							<i class="fas fa-key opacity-6 text-black me-1"></i>
							<span class="d-sm-inline d-none">로그아웃</span>
						</a>
					</div>
				</div>
				<ul class="navbar-nav justify-content-end">

					<li class="nav-item d-flex align-items-center">
						<a href="/COMG/Mypage" class="nav-link text-black font-weight-bold px-0">
							<i class="fa fa-user me-sm-1"></i>
							<span class="d-sm-inline d-none"><%=user_name%></span>
						</a>
					</li>
					<li class="nav-item d-xl-none ps-3 pe-0 d-flex align-items-center">
						<a href="javascript:;" class="nav-link text-white p-0">
							<a href="javascript:;" class="nav-link text-body p-0" id="iconNavbarSidenav">
								<div class="sidenav-toggler-inner">
									<i class="sidenav-toggler-line bg-white"></i>
									<i class="sidenav-toggler-line bg-white"></i>
									<i class="sidenav-toggler-line bg-white"></i>
								</div>
							</a>
						</a>
					</li>
					<li class="nav-item px-3 d-flex align-items-center">

					</li>
					<!-- 알림 -->
					<li class="nav-item dropdown pe-2 d-flex align-items-center">
						<a href="javascript:;" class="nav-link text-black p-0" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
							<i class="fa fa-bell cursor-pointer"></i>
						</a>
						<ul class="dropdown-menu dropdown-menu-end px-2 py-3 ms-n4" aria-labelledby="dropdownMenuButton">
							<li class="mb-2">
								<a class="dropdown-item border-radius-md" href="javascript:;">
									<div class="d-flex py-1">
										<div class="my-auto">
											<img src="/img/team-2.jpg" class="avatar avatar-sm me-3">
										</div>
										<div class="d-flex flex-column justify-content-center">
											<h6 class="text-sm font-weight-normal mb-1">
												<span class="font-weight-bold">New message</span> from Laur
											</h6>
											<p class="text-xs text-secondary mb-0">
												<i class="fa fa-clock me-1"></i>
												13 minutes ago
											</p>
										</div>
									</div>
								</a>
							</li>
							<li class="mb-2">
								<a class="dropdown-item border-radius-md" href="javascript:;">
									<div class="d-flex py-1">
										<div class="my-auto">
											<img src="/img/small-logos/logo-spotify.svg" class="avatar avatar-sm bg-gradient-dark me-3">
										</div>
										<div class="d-flex flex-column justify-content-center">
											<h6 class="text-sm font-weight-normal mb-1">
												<span class="font-weight-bold">New album</span> by Travis Scott
											</h6>
											<p class="text-xs text-secondary mb-0">
												<i class="fa fa-clock me-1"></i>
												1 day
											</p>
										</div>
									</div>
								</a>
							</li>
							<li>
								<a class="dropdown-item border-radius-md" href="javascript:;">
									<div class="d-flex py-1">
										<div class="avatar avatar-sm bg-gradient-secondary me-3 my-auto">
											<svg width="12px" height="12px" viewBox="0 0 43 36" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
												<title>credit-card</title>
												<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
													<g transform="translate(-2169.000000, -745.000000)" fill="#FFFFFF" fill-rule="nonzero">
														<g transform="translate(1716.000000, 291.000000)">
															<g transform="translate(453.000000, 454.000000)">
																<path class="color-background" d="M43,10.7482083 L43,3.58333333 C43,1.60354167 41.3964583,0 39.4166667,0 L3.58333333,0 C1.60354167,0 0,1.60354167 0,3.58333333 L0,10.7482083 L43,10.7482083 Z" opacity="0.593633743"></path>
																<path class="color-background" d="M0,16.125 L0,32.25 C0,34.2297917 1.60354167,35.8333333 3.58333333,35.8333333 L39.4166667,35.8333333 C41.3964583,35.8333333 43,34.2297917 43,32.25 L43,16.125 L0,16.125 Z M19.7083333,26.875 L7.16666667,26.875 L7.16666667,23.2916667 L19.7083333,23.2916667 L19.7083333,26.875 Z M35.8333333,26.875 L28.6666667,26.875 L28.6666667,23.2916667 L35.8333333,23.2916667 L35.8333333,26.875 Z"></path>
															</g>
														</g>
													</g>
												</g>
											</svg>
										</div>
										<div class="d-flex flex-column justify-content-center">
											<h6 class="text-sm font-weight-normal mb-1">
												Payment successfully completed
											</h6>
											<p class="text-xs text-secondary mb-0">
												<i class="fa fa-clock me-1"></i>
												2 days
											</p>
										</div>
									</div>
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- End Navbar -->
	<div class="container-fluid py-4">
		<div class="row min-vh-80">
			<div class="col-6 mx-auto">
				<div class="card mt-4">
					<div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
						<div class="bg-gradient-success shadow-danger border-radius-lg pt-4 pb-3">
							<h4 class="text-white font-weight-bold text-capitalize ps-3">그룹 가입</h4>
							<p class="mb-0 text-white font-weight-bold ps-3">그룹 가입 버튼을 눌러 가입할 그룹의 비밀번호를 입력해 주세요.

							</p>
						</div>
					</div>
					<div class="card-body">

						<div class="mb-3">
							<div class="position-relative">
								<a class="d-block shadow-xl border-radius-xl">
									<img src="<%=file_path%>" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl">
								</a>
							</div>
							<br>
							<div class="card "> <!--h-100-->

								<div class="card-header pb-0 p-3">
									<h6 class="mb-0"><%=group_name%><br></h6>
								</div>
								<br>&nbsp;&nbsp;&nbsp;&nbsp;<%=group_introduce%> <br>&nbsp;&nbsp;&nbsp;&nbsp;SINCE <%=group_regdate%><br>&nbsp;&nbsp;&nbsp;
							</div>


						</div>


						<div class="text-center">
							<button type="button" class="btn bg-gradient-success w-100 my-4 mb-2"  onclick="join_group(this);">그룹 가입</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<footer class="footer py-4  ">
			<div class="container-fluid">
				<div class="row align-items-center justify-content-lg-between">
					<div class="col-lg-6 mb-lg-0 mb-4">
						<div class="copyright text-center text-sm text-muted text-lg-start">
							© <script>
							document.write(new Date().getFullYear())
						</script>,
							made with <i class="fa fa-heart"></i> by
							<a href="https://www.creative-tim.com" class="font-weight-bold" target="_blank">Creative Tim</a>
							for a better web.
						</div>
					</div>
					<div class="col-lg-6">
						<ul class="nav nav-footer justify-content-center justify-content-lg-end">
							<li class="nav-item">
								<a href="https://www.creative-tim.com" class="nav-link text-muted" target="_blank">Creative Tim</a>
							</li>
							<li class="nav-item">
								<a href="https://www.creative-tim.com/presentation" class="nav-link text-muted" target="_blank">About Us</a>
							</li>
							<li class="nav-item">
								<a href="https://www.creative-tim.com/blog" class="nav-link text-muted" target="_blank">Blog</a>
							</li>
							<li class="nav-item">
								<a href="https://www.creative-tim.com/license" class="nav-link pe-0 text-muted" target="_blank">License</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</footer>
	</div>
</main>


<!--   Core JS Files   -->
<script src="/boot2/js/core/popper.min.js"></script>
<script src="/boot2/js/core/bootstrap.min.js"></script>
<script src="/boot2/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/boot2/js/plugins/smooth-scrollbar.min.js"></script>
<script src="/vendor/js/main.js"></script>
<script src="/vendor/libs/jquery/jquery.js"></script>
<script src="/vendor/libs/popper/popper.js"></script>
<script src="/vendor/js/bootstrap.js"></script>
<script src="/vendor/libs/perfect-scrollbar/perfect-scrollbar.js">
	<script src="/vendor/js/menu.js"></script>
<!-- Place this tag in your head or just before your close body tag. -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/boot2/js/material-dashboard.min.js?v=3.0.2"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/js/sweetalert2.all.min.js"></script>
</body>

</html>