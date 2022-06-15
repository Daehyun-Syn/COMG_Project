<%--
  Created by IntelliJ IDEA.
  User: 신 대현
  Date: 2022-03-29(020)
  Time: 오후 2:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="kopo.poly.dto.CGroupDTO"%>
<%@ page import="kopo.poly.dto.CFileDTO" %>
<%
    //컨트롤러에서 전달받은 값
    String user_name = CmmUtil.nvl((String)request.getAttribute("user_name"));
    String student_id = CmmUtil.nvl((String)request.getAttribute("student_id"));
    String user_id = CmmUtil.nvl((String)request.getAttribute("user_id"));
    String user_profile = CmmUtil.nvl((String)request.getAttribute("user_profile"));
    List<CGroupDTO> rList = (List<CGroupDTO>) request.getAttribute("rList");
    List<CFileDTO> fList = (List<CFileDTO>) request.getAttribute("fList");
    int i = 0;
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="apple-touch-icon" sizes="76x76" href="/boot2/img/apple-icon.png">
<link rel="icon" type="image/png" href="/boot2/img/favicon.png">
<title>
    마이페이지 | COMG
</title>
<!--     Fonts and icons     -->
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
<!-- Nucleo Icons -->
<link href="/boot2/css/nucleo-icons.css" rel="stylesheet" />
<link href="/boot2/css/nucleo-svg.css" rel="stylesheet" />
<!-- Font Awesome Icons -->
<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
<!-- Material Icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
<!-- CSS Files -->
<link id="pagestyle" href="/boot2/css/material-dashboard.css?v=3.0.2" rel="stylesheet" />
<script>
    let password = "<%=user_name%>";
    function delete_user() {
        swal.fire({
            icon : "warning",
            text : "정말 탈퇴하시겠습니까 ?",
            closeOnClickOutside: false,
            showCancelButton: true,
            confirmButton: true,
            cancelButtonColor: '#d33',
            confirmButtonText: '탈퇴',
            cancelButtonText: '취소'
    }).then(result => {
        if(result.value) {
            swal.fire({
                icon : "warning",
                text : "탈퇴하시려면 비밀번호를 입력해 주세요",
                input : 'text',
                inputPlaceholder : '비밀번호를 입력해 주세요',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                closeOnClickOutside: false,
                showCloseButton: true,
                confirmButton: true,
                confirmButtonText: '탈퇴',
            }).then(result => {
                if(result.value === password) {
                    $.ajax({
                        url: '/COMG/DeleteUser',
                        type: "POST",
                        success: function(data){
                            if(data == 1){
                                console.log("탈퇴 성공");
                                swal.fire({
                                    icon : 'success',
                                    title: '회원 탈퇴 성공',
                                    text : "회원 탈퇴가 정상적으로 이루어졌습니다.",
                                }).then(function(){
                                    location.href="${pageContext.request.contextPath}/COMG/home";
                                });
                            }else{
                                console.log("탈퇴 실패");
                                swal.fire({
                                    icon : 'warning',
                                    title: '회원 탈퇴 실패',
                                    text : "다시한번 시도해 주세요.",
                                })
                                return false;
                            }
                        },
                        error: function (){

                        }
                    });
                }else {
                    console.log("회원 탈퇴 실패!");
                    swal.fire({
                        icon :'warning',
                        title : '회원 탈퇴 실패!',
                        text : "비밀번호가 일치하지 않습니다",
                    })
                }
            })
        }else {
            console.log("탈퇴 취소");
        }
        });
    }

    function change_user_Info() {
        var form = $('#form')[0];
        let data = new FormData(form);
        $.ajax({
            url: '/COMG/changeUserInfo',
            type: "POST",
            data: data ,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function(data){
                if(data == 1){
                    console.log("회원정보 수정 성공");
                    swal.fire({
                        title : '회원정보 수정 성공',
                        text : "회원정보가 정상적으로 수정되었습니다.",
                        icon : 'success',
                    });
                }else{
                    console.log("회원정보 수정 실패");
                    swal.fire({
                        title : '회원정보 수정 실패',
                        text : "회원정보가 수정이 실패하였습니다.",
                        icon : 'error',
                    });
                    return false;
                }
            },
            error: function (){
                console.log("아작스 에러 : 회원정보 변경 실패");
                swal.fire({
                    title : '내부 에러',
                    text : "다시한번 시도해주세요,",
                    icon : 'error',
                });
                return false;
            }
        });
    }

</script>

</head>

<body class="g-sidenav-show bg-gray-200">

<div class="main-content position-relative max-height-vh-100 h-100">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg bg-transparent shadow-none position-absolute px-4 w-100 z-index-2">
        <div class="container-fluid py-1">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 ps-2 me-sm-6 me-5">
                    <li class=""></li>
                    <li class=""><br></li>
                </ol>
                <a href="/COMG/main">
                <h6 class="text-white font-weight-bolder ms-2">COMG</h6>
                </a>
            </nav>
            <div class="collapse navbar-collapse me-md-0 me-sm-4 mt-sm-0 mt-2" id="navbar">
                <div class="ms-md-auto pe-md-3 d-flex align-items-center">
                    <div class="input-group">
                        <a href="/COMG/logout" class="nav-link text-white font-weight-bold px-0">
                            <i class="fas fa-key opacity-6 text-white me-1"></i>
                            <span class="d-sm-inline d-none">로그아웃</span>
                        </a>
                    </div>
                </div>
                <ul class="navbar-nav justify-content-end">

                    <li class="nav-item d-flex align-items-center">
                        <a href="/COMG/Mypage" class="nav-link text-white font-weight-bold px-0">
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
                    <li class="nav-item dropdown pe-2 d-flex align-items-center">
                        <a href="javascript:;" class="nav-link text-white p-0" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
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
    <div class="container-fluid px-2 px-md-4">
        <div class="page-header min-height-300 border-radius-xl mt-4" style="background-image: url('/img/curved-images/curved9.jpg');">
            <span class="mask  bg-gradient-primary  opacity-6"></span>
        </div>
        <div class="card card-body mx-3 mx-md-4 mt-n6">
            <div class="row gx-4 mb-2">
                <div class="col-auto">
                    <div class="avatar avatar-xl position-relative">
                        <img src="<%=user_profile%>" alt="profile_image" class="w-100 border-radius-lg shadow-sm">
                    </div>
                </div>
                <div class="col-auto my-auto">
                    <div class="h-100">
                        <h5 class="mb-1">
                            <%=user_name%>
                        </h5>
                        <p class="mb-0 font-weight-normal text-sm">

                        </p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 my-sm-auto ms-sm-auto me-sm-0 mx-auto mt-3">
                    <div class="nav-wrapper position-relative end-0">
                        <ul class="nav nav-pills nav-fill p-1" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 active " data-bs-toggle="tab" href="#" role="tab" aria-selected="true" onclick="delete_user(this);">
                                    <i class="material-icons text-lg position-relative">input</i>
                                    <span class="ms-1">회원 탈퇴</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 " href="/COMG/Pwchange" role="tab" aria-selected="false">
                                    <i class="material-icons text-lg position-relative">redo</i>
                                    <span class="ms-1">비밀번호 변경</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 " href="/COMG/ChangeProfile" role="tab" aria-selected="false">
                                    <i class="material-icons text-lg position-relative">settings</i>
                                    <span class="ms-1">프로필 사진 변경</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="row">

                    <div class="col-12 col-xl-4">
                        <div class="card card-plain h-100">
                            <form id="form" name="form">
                            <div class="card-header pb-0 p-3">
                                <div class="row">
                                    <div class="col-md-8 d-flex align-items-center">
                                        <h6 class="mb-0">내 프로필</h6>
                                    </div>
                                    <div class="col-md-4 text-end" >
                                        <a href="#" onclick="change_user_Info()">
                                            <i class="fas fa-user-edit text-secondary text-sm" data-bs-toggle="tooltip" data-bs-placement="top" title="Edit Profile"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body p-3">

                                <hr class="horizontal gray-light my-4">
                                <ul class="list-group">
                                    <li class="list-group-item border-0 ps-0 pt-0 text-sm"><strong class="text-dark">이름 :</strong> &nbsp; <input type="text" style="border: none" id="user_name" name="user_name" value="<%=user_name%>" readonly></li>
                                    <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">학번 :</strong> &nbsp; <input type="text" style="border: none" id="student_id" name="student_id" value="<%=student_id%>"></li>
                                    <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">아이디:</strong> &nbsp; <input type="text" style="border: none" id="user_id" name="user_id" value="<%=user_id%>" readonly></li>
                                </ul>
                            </div>
                            </form>
                        </div>
                    </div>

                    <div class="col-12 mt-4">
                        <div class="mb-5 ps-3">
                            <h6 class="mb-1">내 목록</h6>
                            <p class="text-sm">현재 가입된 목록을 관리할수 있습니다</p>
                        </div>
                        <div class="row">
                            <%
                                for (CGroupDTO p : rList) {
                                    i += 1;

                            %>

                            <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                                <div class="card card-blog card-plain">
                                    <div class="position-relative">
                                        <a class="d-block shadow-xl border-radius-xl">
                                            <img src="<%=fList.get(i-1).getServer_file_path()%>" alt="img-blur-shadow" class="img-fluid shadow border-radius-lg">
                                        </a>
                                    </div>
                                    <div class="card-body px-1 pb-0">
                                        <p class="text-gradient text-dark mb-2 text-sm">그룹 #<%=i%></p>
                                        <a href="javascript:;">
                                            <h5>
                                                <%=p.getGroup_name()%>
                                            </h5>
                                        </a>
                                        <p class="mb-4 text-sm">
                                            <%=p.getGroup_introduce()%>
                                        </p>
                                        <div class="d-flex align-items-center justify-content-between">
                                            <a href="/COMG/Group">
                                                <button type="button" class="btn btn-outline-primary btn-sm mb-0">탈퇴하기</button>
                                            </a>
                                            <div class="avatar-group mt-2">
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                                    <img alt="Image placeholder" src="/img/team-3.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                                    <img alt="Image placeholder" src="/img/team-4.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                                    <img alt="Image placeholder" src="/img/team-1.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                                    <img alt="Image placeholder" src="/img/team-2.jpg">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%

                                }
                            %>
                            <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">

                            </div>
                        </div>
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
                        <a href="https://www.creative-tim.com" class="font-weight-bold" target="_blank">Syn</a>
                        for a better web.
                    </div>
                </div>

            </div>
        </div>
    </footer>
</div>

<!--   Core JS Files   -->
<script src="/boot2/js/core/popper.min.js"></script>
<script src="/boot2/js/core/bootstrap.min.js"></script>
<script src="/boot2/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/boot2/js/plugins/smooth-scrollbar.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/js/sweetalert2.all.min.js"></script>

<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/boot2/js/material-dashboard.min.js?v=3.0.2"></script>
</body>

</html>
