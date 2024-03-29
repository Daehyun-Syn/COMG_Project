<%@ page import="kopo.poly.dto.CUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: 신 대현
  Date: 2022-03-20(020)
  Time: 오후 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="kopo.poly.dto.CGroupDTO"%>
<%@ page import="kopo.poly.dto.CFileDTO" %>
<%@ page import="java.util.Objects" %>
<%
    //컨트롤러에서 전달받은 값
    String user_name = CmmUtil.nvl((String)request.getAttribute("user_name"));
    String student_id = CmmUtil.nvl((String)request.getAttribute("student_id"));
    List<CGroupDTO> rList = (List<CGroupDTO>) request.getAttribute("rList");
    int i = 0;
    String user_profile = CmmUtil.nvl((String)request.getAttribute("user_profile"));
    CUserDTO uDTO = null;
    uDTO = new CUserDTO();
    uDTO = (CUserDTO) session.getAttribute("suDTO");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="/img/apple-icon.png">
    <link rel="icon" type="image/png" href="/img/favicon.png">
    <title>
        COMG 홈 | COMG
    </title>
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
    <!-- Nucleo Icons -->
    <link href="/css/nucleo-icons.css" rel="stylesheet" />
    <link href="/css/nucleo-svg.css" rel="stylesheet" />
    <!-- Font Awesome Icons -->
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <link href="/css/nucleo-svg.css" rel="stylesheet" />
    <!-- CSS Files -->
    <link id="pagestyle" href="/css/soft-ui-dashboard.css?v=1.0.4" rel="stylesheet" />
    <script src="/js/jquery-3.6.0.min.js"></script>
    <style>
        .row {
            white-space: nowrap;
            overflow-x: auto;
        }



        /*Resize the wrap to see the search bar change!*/
    </style>
    <script>
        function logout() {
            $.ajax({
                url: '/COMG/logout',
                type: "GET",

                success: function(data){
                    if(data == 1){
                        console.log("로그아웃 성공");

                    }else{
                        console.log("로그아웃 실패");
                    }
                },
                error: function (){
                    console.log("아작스 에러 : 로그아웃 실패");
                    swal('내부 에러!', "다시한번 시도해주세요.", 'warning');
                    return false;
                }
            });
        }
    </script>



</head>

<body>

<div class="main-content position-relative bg-gray-100 max-height-vh-100 h-100">
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
                        <a href="https://kauth.kakao.com/oauth/logout?client_id=3f3dc7847eecf953477701d6680035e2&logout_redirect_uri=http://localhost:11000/" class="nav-link text-white font-weight-bold px-0">
                            <i class="fas fa-key opacity-6 text-white me-1"></i>
                            <span class="d-sm-inline d-none" onclick="logout()">로그아웃</span>
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
    <div class="container-fluid">
        <div class="page-header min-height-300 border-radius-xl mt-4" style="background-image: url('/img/curved-images/curved0.jpg'); background-position-y: 50%;">
            <span class="mask bg-gradient-primary opacity-6"></span>
            <%@include file="../weather/weather.jsp"%>
        </div>
        <div class="card card-body blur shadow-blur mx-4 mt-n6 overflow-hidden">
            <div class="row gx-4">
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
                        <p class="mb-0 font-weight-bold text-sm">
                            학번 : <%=student_id%>
                        </p>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 my-sm-auto ms-sm-auto me-sm-0 mx-auto mt-3">
                    <div class="nav-wrapper position-relative end-0">
                        <ul class="nav nav-pills nav-fill p-1 bg-transparent" role="tablist">

                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1" href="/COMG/Mypage" role="tab" aria-selected="false">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 40 40" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <title>person</title>
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-2020.000000, -442.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(304.000000, 151.000000)">
                                                        <polygon class="color-background" opacity="0.596981957" points="18.0883333 15.7316667 11.1783333 8.82166667 13.3333333 6.66666667 6.66666667 0 0 6.66666667 6.66666667 13.3333333 8.82166667 11.1783333 15.315 17.6716667">
                                                        </polygon>
                                                        <path class="color-background" d="M31.5666667,23.2333333 C31.0516667,23.2933333 30.53,23.3333333 30,23.3333333 C29.4916667,23.3333333 28.9866667,23.3033333 28.48,23.245 L22.4116667,30.7433333 L29.9416667,38.2733333 C32.2433333,40.575 35.9733333,40.575 38.275,38.2733333 L38.275,38.2733333 C40.5766667,35.9716667 40.5766667,32.2416667 38.275,29.94 L31.5666667,23.2333333 Z" opacity="0.596981957"></path>
                                                        <path class="color-background" d="M33.785,11.285 L28.715,6.215 L34.0616667,0.868333333 C32.82,0.315 31.4483333,0 30,0 C24.4766667,0 20,4.47666667 20,10 C20,10.99 20.1483333,11.9433333 20.4166667,12.8466667 L2.435,27.3966667 C0.95,28.7083333 0.0633333333,30.595 0.00333333333,32.5733333 C-0.0583333333,34.5533333 0.71,36.4916667 2.11,37.89 C3.47,39.2516667 5.27833333,40 7.20166667,40 C9.26666667,40 11.2366667,39.1133333 12.6033333,37.565 L27.1533333,19.5833333 C28.0566667,19.8516667 29.01,20 30,20 C35.5233333,20 40,15.5233333 40,10 C40,8.55166667 39.685,7.18 39.1316667,5.93666667 L33.785,11.285 Z">
                                                        </path>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">마이페이지</span>
                                </a>
                            </li>
                            <%if(Objects.equals(uDTO.getUser_id(), "Admin@comg.com")){%>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1" href="/COMG/adminCustomerList" role="tab" aria-selected="false">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 40 40" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <title>person</title>
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-2020.000000, -442.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(304.000000, 151.000000)">

                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">고객센터</span>
                                </a>
                            </li>
                            <%}else{%>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1" href="/chatingroom" role="tab" aria-selected="false">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 40 40" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <title>person</title>
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-2020.000000, -442.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(304.000000, 151.000000)">

                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">고객센터</span>
                                </a>
                            </li>
                            <%}%>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

        <div class="container-fluid py-4">
            <div class="col-12 mt-4" >
                <div class="card mb-4">
                    <div class="card-header pb-0 p-3">

                        <h6 class="mb-1">내 목록</h6>


                            <div class="search" style="float:right;">
                                <form action="/COMG/SearchGroupName"  method="get" id="searchForm" name="searchForm">
                                <input type="text" class="searchTerm" id="searchGroupName" name="searchGroupName" placeholder="그룹 이름을 입력하여 검색하세요">
                                <input type="hidden" id="user_name" name="user_name" value="<%=user_name%>">
                                <input type="hidden" id="student_id" name="student_id" value="<%=student_id%>">
                                <input type="hidden" id="user_profile" name="user_profile" value="<%=user_profile%>">
                                <button type="submit" class="searchButton">
                                    <i class="fa fa-search"></i>
                                </button>
                                </form>
                            </div>
                        <h4 class="text-sm">현재 가입된 목록들 입니다.</h4>

                    </div>

                    <div class="card-body p-3">

                        <div class="row">
                            <%--<div class="col-xl-3 col-md-6 mb-xl-0 mb-4">--%>
                               <%-- <div class="card card-blog card-plain">--%>
                                    <%--<div class="position-relative">
                                        <a class="d-block shadow-xl border-radius-xl">
                                            <img src="/img/home-decor-1.jpg" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl">
                                        </a>
                                    </div>--%>

                                    <%--<div class="card-body px-1 pb-0">
                                        <p class="text-gradient text-dark mb-2 text-sm">그룹 #</p>
                                        <a href="javascript:;">
                                            <h5>

                                            </h5>
                                        </a>
                                        <p class="mb-4 text-sm">

                                        </p>
                                        <div class="d-flex align-items-center justify-content-between">
                                            <button type="button" class="btn btn-outline-primary btn-sm mb-0">입장하기</button>
                                            <div class="avatar-group mt-2">
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                                    <img alt="Image placeholder" src="/img/team-1.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                                    <img alt="Image placeholder" src="/img/team-2.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                                    <img alt="Image placeholder" src="/img/team-3.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                                    <img alt="Image placeholder" src="/img/team-4.jpg">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>--%>

                            <!-- 그룹 2-->
                            <!-- rList안의 ProjectsDTO가 p에 순차적으로 대입되며 실행되는 for문 -->
                            <%
                                for (CGroupDTO p : rList) {
                                    i += 1;

                            %>

                            <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                                <div class="card card-blog card-plain">
                                    <div class="position-relative">
                                        <a class="d-block shadow-xl border-radius-xl">
                                            <img src="<%=p.getGroup_profile()%>" alt="img-blur-shadow" class="img-fluid shadow border-radius-lg">
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
                                            <a href="/COMG/Group?GroupSEQ=<%=p.getGroup_seq()%>">
                                            <button type="button" class="btn btn-outline-primary btn-sm mb-0">입장하기</button>
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
                            <!-- 그룹 2 끝 -->
                            <!-- 그룹 3  -->
                            <%--<div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                                <div class="card card-blog card-plain">
                                    <div class="position-relative">
                                        <a class="d-block shadow-xl border-radius-xl">
                                            <img src="/img/home-decor-3.jpg" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl">
                                        </a>
                                    </div>
                                    <div class="card-body px-1 pb-0">
                                        <p class="text-gradient text-dark mb-2 text-sm">그룹 #3</p>
                                        <a href="javascript:;">
                                            <h5>
                                                데이터분석과 6기
                                            </h5>
                                        </a>
                                        <p class="mb-4 text-sm">
                                            데이터분석과 6기 학생들을 위한 공간입니다.
                                        </p>
                                        <div class="d-flex align-items-center justify-content-between">
                                            <button type="button" class="btn btn-outline-primary btn-sm mb-0">입장하기</button>
                                            <div class="avatar-group mt-2">
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Peterson">
                                                    <img alt="Image placeholder" src="/img/team-4.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Nick Daniel">
                                                    <img alt="Image placeholder" src="/img/team-3.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Ryan Milly">
                                                    <img alt="Image placeholder" src="/img/team-2.jpg">
                                                </a>
                                                <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Elena Morison">
                                                    <img alt="Image placeholder" src="/img/team-1.jpg">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>--%>
                            <!-- 그룹 3 끝 -->

                            <div class="col-xl-3 col-md-6 mb-xl-0 mb-4">
                                <div class="card h-100 card-plain border">
                                    <div class="card-body d-flex flex-column justify-content-center text-center">
                                        <a href="/COMG/Creategroup">
                                            <i class="fa fa-plus text-secondary mb-3"></i>
                                            <h5 class=" text-secondary"> 새 그룹 만들기 </h5>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </div>
        <footer class="footer pt-3  ">
            <div class="container-fluid">
                <div class="row align-items-center justify-content-lg-between">
                    <div class="col-lg-6 mb-lg-0 mb-4">
                        <div class="copyright text-center text-sm text-muted text-lg-start">
                            © <script>
                            document.write(new Date().getFullYear())
                        </script>,
                            made with <i class="fa fa-heart"></i> by
                            <a href="https://versed-jar-acf.notion.site/String-Building-a33341a47e0b4bd5ac80fa8aae3a2a01" class="font-weight-bold" target="_blank">Syn</a>
                            for a better web.
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <ul class="nav nav-footer justify-content-center justify-content-lg-end">
                            <li class="nav-item">
                                <a href="" class="nav-link text-muted"></a>
                            </li>
                            <li class="nav-item">
                                <a href="" class="nav-link text-muted"></a>
                            </li>
                            <li class="nav-item">
                                <a href="https://progressive-form.tistory.com/" class="nav-link text-muted" target="_blank">Blog</a>
                            </li>
                            <li class="nav-item">
                                <a href="" class="nav-link pe-0 text-muted"></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    </div>


<!--   Core JS Files   -->
<script src="/js/core/popper.min.js"></script>
<script src="/js/core/bootstrap.min.js"></script>
<script src="/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/js/plugins/smooth-scrollbar.min.js"></script>
<script>
    var win = navigator.platform.indexOf('Win') > -1;
    if (win && document.querySelector('#sidenav-scrollbar')) {
        var options = {
            damping: '0.5'
        }
        Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
    }


</script>
<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/js/soft-ui-dashboard.min.js?v=1.0.4"></script>
</body>

</html>