<%--
  Created by IntelliJ IDEA.
  User: 신 대현
  Date: 2022-03-20(020)
  Time: 오후 5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="kopo.poly.dto.*" %>
<!DOCTYPE html>
<html lang="en">
<%
    String user_name = CmmUtil.nvl((String)request.getAttribute("user_name"));
    String user_id = CmmUtil.nvl((String)request.getAttribute("user_id"));
    CGroupDTO rDTO = (CGroupDTO)request.getAttribute("rDTO");
    String group_name = CmmUtil.nvl(rDTO.getGroup_name());
    String group_introduce = CmmUtil.nvl(rDTO.getGroup_introduce());
    String group_regdate = CmmUtil.nvl(rDTO.getGroup_regdate());
    String file_path = CmmUtil.nvl((String) request.getAttribute("file_path"));
    String user_profile = CmmUtil.nvl((String) request.getAttribute("user_profile"));
    String group_seq = CmmUtil.nvl((String) request.getAttribute("group_seq"));
    String fk_group_user_seq = CmmUtil.nvl((String) request.getAttribute("fk_group_user_seq"));
    List<CBoardDTO> rList = (List<CBoardDTO>) request.getAttribute("rList");
    CGroupDTO nDTO = (CGroupDTO)request.getAttribute("nDTO");
    List<CFileDTO> fileList = (List<CFileDTO>) request.getAttribute("fileList");
    List<CAssignmentDTO> sList = (List<CAssignmentDTO>) request.getAttribute("sList");
    List<ScheduleDTO> eList = (List<ScheduleDTO>) request.getAttribute("eList");
%>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="/img/apple-icon.png">
    <link rel="icon" type="image/png" href="/img/favicon.png">
    <title>
        <%=group_name%> | COMG
    </title>
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
    <!-- Nucleo Icons -->
    <link href="/css/nucleo-icons.css" rel="stylesheet" />
    <link href="/css/nucleo-svg.css" rel="stylesheet" />
    <!-- Font Awesome Icons -->
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <script src="/vendor/libs/jquery/jquery.js"></script>
    <script src='/js/fullcal/main.js'></script>
    <script src='/js/fullcal/locales-all.js'></script>
    <link href='/js/fullcal/main.css' rel='stylesheet' />
    <link href="/css/nucleo-svg.css" rel="stylesheet" />
    <!-- CSS Files -->
    <link id="pagestyle" href="/css/soft-ui-dashboard.css?v=1.0.4" rel="stylesheet" />
    <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
    <style>
        #marginF{
            margin-top: 4%;
        }
        #inputS{
            width: 89%;
            border: none;
            outline:none;
        }
        #divS{
            border: 1px solid #cb0c9f;
            padding: 0.5% 0.5% 0.5% 1.5%;
        }

        #dispaly {

        }
        #modal.modal-overlay {
            width: 100%;
            height: 100%;
            position: absolute;
            left: 0;
            top: 0;
            display: none;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: rgba(255, 255, 255, 0.25);
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            backdrop-filter: blur(1.5px);
            -webkit-backdrop-filter: blur(1.5px);
            border-radius: 10px;
            border: 1px solid rgba(255, 255, 255, 0.18);
        }
        #modal .modal-window {
            background: rgba( 69, 139, 197, 0.70 );
            box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );
            backdrop-filter: blur( 13.5px );
            -webkit-backdrop-filter: blur( 13.5px );
            border-radius: 10px;
            border: 1px solid rgba( 255, 255, 255, 0.18 );
            width: 800px;
            height: 900px;
            position: relative;
            top: -100px;
            padding: 10px;
        }
        #modal .title {
            padding-left: 10px;
            display: inline;
            text-shadow: 1px 1px 2px gray;
            color: white;

        }
        #modal .title h2 {
            display: inline;
        }
        #modal .close-area {
            display: inline;
            float: right;
            padding-right: 10px;
            cursor: pointer;
            text-shadow: 1px 1px 2px gray;
            color: white;
        }

        #modal .content {
            margin-top: 20px;
            padding: 0px 10px;
            text-shadow: 1px 1px 2px gray;
            color: white;
        }
        .calendar * {
            border: solid 1px black;
        }

        #kakaoLogin {
            border: 0;
            outline: 0;
        }

        /* 월화수목금 */
        .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
            color: black;
        }

        .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
            color: black;
        }

        /* 토요일 */
        .fc-day-sat .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
            color: blue;
        }

        .fc-day-sat .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
            color: blue;
        }

        /* 일요일 */
        .fc-day-sun .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
            color: red;
        }

        .fc-day-sun .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
            color: red;
        }
    </style>
    <script>
        $(document).on('click', '.send_button', function(){
            let div_res = $(this).attr('id');
            let result = div_res.substring(7);

            console.log(result);

            var form = $('#comment_form'+result)[0];
            console.log(form);
            let data = new FormData(form);
            $.ajax({
                url: '/COMG/CommentWrite',
                type: "POST",
                data: data ,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function(data){
                    if(data == 1){
                        console.log("댓글 작성 성공");
                        swal('댓글 작성 성공!', "댓글이 작성되었습니다", 'success')
                            .then(function(){
                                location.href="/COMG/Group?GroupSEQ=<%=group_seq%>";
                            });
                    }else{
                        console.log("댓글 작성 실패");
                        swal('댓글 작성 실패!', "댓글 작성이 실패하였습니다.", 'error');
                        return false;
                    }
                },
                error: function (){
                    console.log("아작스 에러 : 댓글 작성 실패");
                    swal('내부 에러!', "다시한번 시도해주세요.", 'warning');
                    return false;
                }
            });
        });

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

        document.addEventListener('DOMContentLoaded', function() {
            let calendarEl = document.getElementById('calendar');
            let calendar = new FullCalendar.Calendar(calendarEl, {
                locale: 'ko',
                headerToolbar: {
                    left: 'title',
                    center: '',
                    right: 'prev,next,today'
                },
                height: 'auto',
                selectable: true,
                weekends: true,
                events: [
                    <%
                           for(ScheduleDTO eDTO : eList) {
                               String id = String.valueOf(eDTO.getAssignmentRoomSeq());
                               String title = eDTO.getAssignmentRoomName();
                               String start = eDTO.getAssignmentRegDate();
                               String end = eDTO.getAssignmentDeadLine();
                               %>
                    {
                        id : '<%=id%>',
                        title  : '<%=title%>',
                        start  : '<%=start%>',
                        end    : '<%=end%>'
                    },

                    <%
                           }
                       %>
                ],
            });
            calendar.render();
        });
    </script>


</head>

<body>

<main class="main-content position-relative bg-gray-100 max-height-vh-100 h-100">

    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg bg-transparent shadow-none position-absolute px-4 w-100 z-index-2">
        <div class="container-fluid py-1" id="navbar2" style="z-index: 2;">
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
                            학생 / <%=group_name%>
                        </p>
                    </div>
                </div>

                <div class="col-lg-4 col-md-6 my-sm-auto ms-sm-auto me-sm-0 mx-auto mt-3">
                    <div class="nav-wrapper position-relative end-0">
                        <ul class="nav nav-pills nav-fill p-1 bg-transparent" role="tablist">
                            <%
                                if (Objects.equals(nDTO.getUser_auth(), "1")){
                            %>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 active " href="/COMG/CreateAssignment?GroupSEQ=<%=group_seq%>" aria-selected="true">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 42 42" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-2319.000000, -291.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(603.000000, 0.000000)">
                                                        <path class="color-background" d="M22.7597136,19.3090182 L38.8987031,11.2395234 C39.3926816,10.9925342 39.592906,10.3918611 39.3459167,9.89788265 C39.249157,9.70436312 39.0922432,9.5474453 38.8987261,9.45068056 L20.2741875,0.1378125 L20.2741875,0.1378125 C19.905375,-0.04725 19.469625,-0.04725 19.0995,0.1378125 L3.1011696,8.13815822 C2.60720568,8.38517662 2.40701679,8.98586148 2.6540352,9.4798254 C2.75080129,9.67332903 2.90771305,9.83023153 3.10122239,9.9269862 L21.8652864,19.3090182 C22.1468139,19.4497819 22.4781861,19.4497819 22.7597136,19.3090182 Z">
                                                        </path>
                                                        <path class="color-background" d="M23.625,22.429159 L23.625,39.8805372 C23.625,40.4328219 24.0727153,40.8805372 24.625,40.8805372 C24.7802551,40.8805372 24.9333778,40.8443874 25.0722402,40.7749511 L41.2741875,32.673375 L41.2741875,32.673375 C41.719125,32.4515625 42,31.9974375 42,31.5 L42,14.241659 C42,13.6893742 41.5522847,13.241659 41,13.241659 C40.8447549,13.241659 40.6916418,13.2778041 40.5527864,13.3472318 L24.1777864,21.5347318 C23.8390024,21.7041238 23.625,22.0503869 23.625,22.429159 Z" opacity="0.7"></path>
                                                        <path class="color-background" d="M20.4472136,21.5347318 L1.4472136,12.0347318 C0.953235098,11.7877425 0.352562058,11.9879669 0.105572809,12.4819454 C0.0361450918,12.6208008 6.47121774e-16,12.7739139 0,12.929159 L0,30.1875 L0,30.1875 C0,30.6849375 0.280875,31.1390625 0.7258125,31.3621875 L19.5528096,40.7750766 C20.0467945,41.0220531 20.6474623,40.8218132 20.8944388,40.3278283 C20.963859,40.1889789 21,40.0358742 21,39.8806379 L21,22.429159 C21,22.0503869 20.7859976,21.7041238 20.4472136,21.5347318 Z" opacity="0.7"></path>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">과제 생성</span>
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 active " href="/COMG/EditMessage?GroupSEQ=<%=group_seq%>" aria-selected="true">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 42 42" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-2319.000000, -291.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(603.000000, 0.000000)">
                                                        <path class="color-background" d="M22.7597136,19.3090182 L38.8987031,11.2395234 C39.3926816,10.9925342 39.592906,10.3918611 39.3459167,9.89788265 C39.249157,9.70436312 39.0922432,9.5474453 38.8987261,9.45068056 L20.2741875,0.1378125 L20.2741875,0.1378125 C19.905375,-0.04725 19.469625,-0.04725 19.0995,0.1378125 L3.1011696,8.13815822 C2.60720568,8.38517662 2.40701679,8.98586148 2.6540352,9.4798254 C2.75080129,9.67332903 2.90771305,9.83023153 3.10122239,9.9269862 L21.8652864,19.3090182 C22.1468139,19.4497819 22.4781861,19.4497819 22.7597136,19.3090182 Z">
                                                        </path>
                                                        <path class="color-background" d="M23.625,22.429159 L23.625,39.8805372 C23.625,40.4328219 24.0727153,40.8805372 24.625,40.8805372 C24.7802551,40.8805372 24.9333778,40.8443874 25.0722402,40.7749511 L41.2741875,32.673375 L41.2741875,32.673375 C41.719125,32.4515625 42,31.9974375 42,31.5 L42,14.241659 C42,13.6893742 41.5522847,13.241659 41,13.241659 C40.8447549,13.241659 40.6916418,13.2778041 40.5527864,13.3472318 L24.1777864,21.5347318 C23.8390024,21.7041238 23.625,22.0503869 23.625,22.429159 Z" opacity="0.7"></path>
                                                        <path class="color-background" d="M20.4472136,21.5347318 L1.4472136,12.0347318 C0.953235098,11.7877425 0.352562058,11.9879669 0.105572809,12.4819454 C0.0361450918,12.6208008 6.47121774e-16,12.7739139 0,12.929159 L0,30.1875 L0,30.1875 C0,30.6849375 0.280875,31.1390625 0.7258125,31.3621875 L19.5528096,40.7750766 C20.0467945,41.0220531 20.6474623,40.8218132 20.8944388,40.3278283 C20.963859,40.1889789 21,40.0358742 21,39.8806379 L21,22.429159 C21,22.0503869 20.7859976,21.7041238 20.4472136,21.5347318 Z" opacity="0.7"></path>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">메세지 관리</span>
                                </a>
                            </li>
                            <%
                                }
                            %>

                            <%
                                if (!Objects.equals(nDTO.getUser_auth(), "1")){
                            %>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 " href="/COMG/AssignmentSubmit?GroupSEQ=<%=group_seq%>" aria-selected="false">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 40 44" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <title>document</title>
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-1870.000000, -591.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(154.000000, 300.000000)">
                                                        <path class="color-background" d="M40,40 L36.3636364,40 L36.3636364,3.63636364 L5.45454545,3.63636364 L5.45454545,0 L38.1818182,0 C39.1854545,0 40,0.814545455 40,1.81818182 L40,40 Z" opacity="0.603585379"></path>
                                                        <path class="color-background" d="M30.9090909,7.27272727 L1.81818182,7.27272727 C0.814545455,7.27272727 0,8.08727273 0,9.09090909 L0,41.8181818 C0,42.8218182 0.814545455,43.6363636 1.81818182,43.6363636 L30.9090909,43.6363636 C31.9127273,43.6363636 32.7272727,42.8218182 32.7272727,41.8181818 L32.7272727,9.09090909 C32.7272727,8.08727273 31.9127273,7.27272727 30.9090909,7.27272727 Z M18.1818182,34.5454545 L7.27272727,34.5454545 L7.27272727,30.9090909 L18.1818182,30.9090909 L18.1818182,34.5454545 Z M25.4545455,27.2727273 L7.27272727,27.2727273 L7.27272727,23.6363636 L25.4545455,23.6363636 L25.4545455,27.2727273 Z M25.4545455,20 L7.27272727,20 L7.27272727,16.3636364 L25.4545455,16.3636364 L25.4545455,20 Z">
                                                        </path>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">과제 제출</span>
                                </a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 active " href="/COMG/COVID_KIT?GroupSEQ=<%=group_seq%>" aria-selected="true">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 42 42" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-2319.000000, -291.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(603.000000, 0.000000)">
                                                        <path class="color-background" d="M22.7597136,19.3090182 L38.8987031,11.2395234 C39.3926816,10.9925342 39.592906,10.3918611 39.3459167,9.89788265 C39.249157,9.70436312 39.0922432,9.5474453 38.8987261,9.45068056 L20.2741875,0.1378125 L20.2741875,0.1378125 C19.905375,-0.04725 19.469625,-0.04725 19.0995,0.1378125 L3.1011696,8.13815822 C2.60720568,8.38517662 2.40701679,8.98586148 2.6540352,9.4798254 C2.75080129,9.67332903 2.90771305,9.83023153 3.10122239,9.9269862 L21.8652864,19.3090182 C22.1468139,19.4497819 22.4781861,19.4497819 22.7597136,19.3090182 Z">
                                                        </path>
                                                        <path class="color-background" d="M23.625,22.429159 L23.625,39.8805372 C23.625,40.4328219 24.0727153,40.8805372 24.625,40.8805372 C24.7802551,40.8805372 24.9333778,40.8443874 25.0722402,40.7749511 L41.2741875,32.673375 L41.2741875,32.673375 C41.719125,32.4515625 42,31.9974375 42,31.5 L42,14.241659 C42,13.6893742 41.5522847,13.241659 41,13.241659 C40.8447549,13.241659 40.6916418,13.2778041 40.5527864,13.3472318 L24.1777864,21.5347318 C23.8390024,21.7041238 23.625,22.0503869 23.625,22.429159 Z" opacity="0.7"></path>
                                                        <path class="color-background" d="M20.4472136,21.5347318 L1.4472136,12.0347318 C0.953235098,11.7877425 0.352562058,11.9879669 0.105572809,12.4819454 C0.0361450918,12.6208008 6.47121774e-16,12.7739139 0,12.929159 L0,30.1875 L0,30.1875 C0,30.6849375 0.280875,31.1390625 0.7258125,31.3621875 L19.5528096,40.7750766 C20.0467945,41.0220531 20.6474623,40.8218132 20.8944388,40.3278283 C20.963859,40.1889789 21,40.0358742 21,39.8806379 L21,22.429159 C21,22.0503869 20.7859976,21.7041238 20.4472136,21.5347318 Z" opacity="0.7"></path>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">키트 제출</span>
                                </a>
                            </li>
                            <%
                                }
                            %>
                            <%
                                if (Objects.equals(nDTO.getUser_auth(), "1")){
                            %>
                            <li class="nav-item">
                                <a class="nav-link mb-0 px-0 py-1 " href="/COMG/assignment?group_seq=<%=group_seq%>" aria-selected="false">
                                    <svg class="text-dark" width="16px" height="16px" viewBox="0 0 40 44" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <title>document</title>
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <g transform="translate(-1870.000000, -591.000000)" fill="#FFFFFF" fill-rule="nonzero">
                                                <g transform="translate(1716.000000, 291.000000)">
                                                    <g transform="translate(154.000000, 300.000000)">
                                                        <path class="color-background" d="M40,40 L36.3636364,40 L36.3636364,3.63636364 L5.45454545,3.63636364 L5.45454545,0 L38.1818182,0 C39.1854545,0 40,0.814545455 40,1.81818182 L40,40 Z" opacity="0.603585379"></path>
                                                        <path class="color-background" d="M30.9090909,7.27272727 L1.81818182,7.27272727 C0.814545455,7.27272727 0,8.08727273 0,9.09090909 L0,41.8181818 C0,42.8218182 0.814545455,43.6363636 1.81818182,43.6363636 L30.9090909,43.6363636 C31.9127273,43.6363636 32.7272727,42.8218182 32.7272727,41.8181818 L32.7272727,9.09090909 C32.7272727,8.08727273 31.9127273,7.27272727 30.9090909,7.27272727 Z M18.1818182,34.5454545 L7.27272727,34.5454545 L7.27272727,30.9090909 L18.1818182,30.9090909 L18.1818182,34.5454545 Z M25.4545455,27.2727273 L7.27272727,27.2727273 L7.27272727,23.6363636 L25.4545455,23.6363636 L25.4545455,27.2727273 Z M25.4545455,20 L7.27272727,20 L7.27272727,16.3636364 L25.4545455,16.3636364 L25.4545455,20 Z">
                                                        </path>
                                                    </g>
                                                </g>
                                            </g>
                                        </g>
                                    </svg>
                                    <span class="ms-1">과제 제출현황</span>
                                </a>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- fullcalender -->
    <div class="container-fluid py-4 display display2">
        <div id="modal" class="modal-overlay" style="z-index: 1;">
            <div class="modal-window">
                <div class="title">
                    <h2>FullCalender</h2>
                </div>
                <div class="close-area">X</div>
                <div class="content">
                    <div id="calendar"></div>
                </div>
            </div>
        </div>
        <!-- fullcalender -->
        <div class="display">
        <div class="row width" id="marginleft2"> <!---->
            <div class="col-12 col-xl-4"> <!---->
                <div class="card "> <!--h-100-->
                    <div>
                    <a class="d-block shadow-xl border-radius-bottom-end-0">
                        <img src="<%=file_path%>" alt="img-blur-shadow" class="img-fluid shadow border-radius-xl">
                    </a>
                    </div>
                    <div class="card-header pb-0 p-3">
                        <h6 class="mb-0"><%=group_name%><br></h6>
                    </div>
                      <br>&nbsp;&nbsp;&nbsp;&nbsp;<%=group_introduce%> <br>&nbsp;&nbsp;&nbsp;&nbsp;SINCE <%=group_regdate%><br>&nbsp;&nbsp;&nbsp;
                    <div>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" id="create-kakaotalk-sharing-btn" style="text-decoration: none; border: none; background-color: white; float: right; padding-right: 25px" >
                            <b>초대</b>
                        </button>
                        <%
                            if (Objects.equals(nDTO.getUser_auth(), "1")){
                        %>
                        <form action="/COMG/EditGroup" method="get" style="float: right">
                        <input type="hidden" name="update_GroupSEQ" id="update_GroupSEQ" value="<%=group_seq%>">
                        <button type="submit" style="text-decoration: none; border: none; background-color: white; float: right">
                        <b>그룹수정</b>
                        </button>&nbsp;
                        </form>
                        <%
                            }
                        %>
                    </div>

                    <div class="text-center">
                        <form action="/COMG/Board" method="get">
                        <input type="hidden" name="GroupSEQ" id="GroupSEQ" value="<%=group_seq%>">
                        <input type="hidden" name="FK_GROUP_USER_SEQ" id="FK_GROUP_USER_SEQ" value="<%=fk_group_user_seq%>">
                        <%
                            if (Objects.equals(nDTO.getUser_auth(), "1")){
                        %>
                        <button type="submit" class="btn bg-gradient-success w-90 my-4 mb-2">글쓰기</button>
                        <%
                            }
                        %>
                        </form>
                    </div>
                    <br>
                </div>
            </div>
        </div>

            <div class="col-12 col-xl-4 width " id="marginleft" style="height: 1020px; overflow-y: auto;"> <!-- -->
                <%
                        for(int i = rList.size() - 1; i >= 0 ; i--) {
                %>
                <div class="card margintop "> <!--h-100-->
                    <div class="card-header pb-0 p-3">
                    </div>

                    <div class="card-body p-3">
                        <div class="row gx-4">
                            <div class="col-auto">
                                <div class="avatar position-relative " style="width: 60px; height: 74px;">
                                    <img src="<%=rList.get(i).getBoard_writer_profile()%>" alt="profile_image" class="w-100 border-radius-lg shadow-sm">
                                </div>
                            </div>
                            <div class="col-auto my-auto">
                                <div class="h-100">
                                    <h5 class="mb-1">
                                        <%=rList.get(i).getWriter_name()%>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card-body p-3" style="padding-bottom: 0px ">
                        <%=rList.get(i).getBoard_contents()%>
                        <br>
                        <br>
                        <%
                            if(!Objects.equals(rList.get(i).getFile_seq(), "0")){
                        %>
                        <div class="card-body p-3 pb-0" style="text-align: center">
                            <ul class="list-group">


                                <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                    <div class="d-flex flex-column">
                                        <h6 class="mb-1 text-dark font-weight-bold text-sm"><%=rList.get(i).getBoard_file_name()%></h6>

                                    </div>
                                    <div class="d-flex align-items-center text-sm">

                                        <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><a href="/downloadfile?filename=<%=rList.get(i).getBoard_file_path()%>"  download><i class="fas fa-file-pdf text-lg me-1"></i> 다운로드</a></button>

                                    </div>
                                </li>
                            </ul>
                        </div>
                        <%
                            }
                        %>
                        <div style="float: right; padding-bottom: 10px">
                            <%
                                if (Objects.equals(nDTO.getUser_auth(), "1")){
                            %>
                            <form action="/COMG/BoardUpdate" method="get">
                            <input type="hidden" name="board_seq" value="<%=rList.get(i).getBoard_seq()%>">
                            <button type="submit" class="fas fa-pen" style="margin-left: 20px; border: none; text-decoration: none; background-color: white"><b>수정</b></button>
                            </form>
                            <%
                                }
                            %>
                        </div>
                        <div style="float: right; padding-bottom: 10px">
                            <%
                                if (Objects.equals(nDTO.getUser_auth(), "1")){
                            %>
                            <form action="/COMG/BoardDeleteLogic" method="get">
                            <input type="hidden" name="group_seq" value="<%=group_seq%>">
                            <input type="hidden" name="board_seq" value="<%=rList.get(i).getBoard_seq()%>">
                            <button type="submit" class="fas fa-trash" style="border: none; text-decoration: none; background-color: white"><b>삭제</b></button>
                            </form>
                            <%
                                }
                            %>
                        </div>
                        <script>
                            function openCloseToc<%=i%>() {
                                if(document.getElementById('toc-content<%=i%>').style.display === 'block') {

                                    document.getElementById('toc-content<%=i%>').style.display = 'none';
                                    document.getElementById('toc-toggle<%=i%>').innerHTML = '<button type="button" class="fa fa-caret-square-o-down" aria-hidden="true" style="border: 0; outline: 0; text-decoration: none; background-color: white"></button>';
                                } else {

                                    document.getElementById('toc-content<%=i%>').style.display = 'block';
                                    document.getElementById('toc-toggle<%=i%>').innerHTML = '<button type="button" class="fa fa-caret-square-o-up" aria-hidden="true" style="border: 0; outline: 0; text-decoration: none; background-color: white"></button>';
                                }
                            }


                        </script>
                        <span id="toc-toggle<%=i%>" onclick="openCloseToc<%=i%>()">
                            댓글 <%=rList.get(i).getReviews().size()%><button type="button" class="fa fa-caret-square-o-down" aria-hidden="true" style="border: 0; outline: 0; text-decoration: none; background-color: white"></button>
                        </span>


                    </div>
                </div>

                <div class="card margintop" id="toc-content<%=i%>" style="display: none">

                    <div class="card-body p-3">
                        <%
                            for (int a = rList.get(i).getReviews().size() -1; a >= 0; a --){
                        %>
                    <hr>
                    <div class="row gx-4">
                        <div class="col-auto">
                            <div class="avatar position-relative" style="width: 50px;  height: 74px;">
                                <img src="<%=rList.get(i).getReviews().get(a).getComment_writer_profile()%>" alt="profile_image" class="w-100 border-radius-lg shadow-sm">
                            </div>
                        </div>
                        <div class="col-auto my-auto" style="padding: 0; width: 80%;">
                            <div class="h-100">
                                <h5 class="mb-1">
                                    <%=rList.get(i).getReviews().get(a).getComment_writer_name()%>
                                </h5>
                                <form name="CommentUpdate" action="/COMG/CommentUpdateLogic" method="post">
                                <p class="text-sm" style="float: right; padding-bottom: 10px">
                                    <p class="text-sm" style="float: left; padding-bottom: 10px">
                                    <input type="text" style="border: none" id="update_comment_contents" name="update_comment_contents" value="<%=rList.get(i).getReviews().get(a).getComment_contents()%>">
                                    <input type="hidden" name="Update_Comment_seq" value="<%=rList.get(i).getReviews().get(a).getComment_seq()%>">
                                    </p>
                                    <input type="hidden" name="Update_comment_group_seq" value="<%=group_seq%>">
                                    <%
                                        if(Objects.equals(rList.get(i).getReviews().get(a).getComment_writer(), user_id)) {
                                    %>
                                    <button type="submit" class="fas fa-user-edit text-secondary text-sm" style="border: none">수정</button>
                                    <%
                                        }
                                    %>
                                </p>
                                </form>
                                <p class="text-sm" style="float: right; padding-bottom: 10px">
                                <form name="CommentDelete" action="/COMG/CommentDelete" method="post">
                                <input type="hidden" name="Delete_Comment_seq" value="<%=rList.get(i).getReviews().get(a).getComment_seq()%>">
                                <input type="hidden" name="Delete_comment_group_seq" value="<%=group_seq%>">
                                <%
                                    if(Objects.equals(rList.get(i).getReviews().get(a).getComment_writer(), user_id)) {
                                %>
                                <button type="submit" class="fas fa-user-slash text-secondary text-sm" style="border: none"><b>삭제</b></button>
                                <%
                                    }
                                %>
                                </form>
                                </p>

                            </div>
                        </div>

                    </div>

                    <hr>
                        <%
                            }
                        %>
                    <div class="card-body p-3" style="">

                        <ul class="list-group">
                            <li class="list-group-item border-0 ps-0 text-sm" style="padding-right: 0.3%;">
                                <div id="divS">
                                    <form id="comment_form<%=rList.get(i).getBoard_seq()%>">
                                    <input type="hidden" name="fk_board_seq" value="<%=rList.get(i).getBoard_seq()%>">
                                    <input type="hidden" name="fk_group_seq" value="<%=group_seq%>">
                                    <input type="hidden" name="fk_group_user_seq" value="<%=fk_group_user_seq%>">
                                    <input type="text" name="comment_contents" placeholder="댓글 내용을 입력해주세요." id="inputS">
                                    <button type="button" class="send_button" id="buttonS<%=rList.get(i).getBoard_seq()%>" value="" >작성</button>
<%--                                    <button type="button" class="btn btn-outline-primary btn-sm mb" id="buttonS" >작성</button>--%>
                                    </form>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>

                </div>
                <%
                    }
                %>

            </div>

            <!---file path filename Start!! 과제 파일 목록 가져오기 Start--->

            <div class="display display3" id="margintop">

            <div class="">  <!--col-lg-4여기111111111111111111111111111111111111-->
                <div class="card h-100">
                    <div class="card-header pb-0 p-3">
                        <div class="row">
                            <div class="col-6 d-flex align-items-center">
                                <h6 class="mb-0">파일목록</h6>
                            </div>
                            <div class="col-6 text-end">
                                <button class="btn btn-outline-primary btn-sm mb-0">전체보기</button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body p-3 pb-0">
                        <ul class="list-group">

                            <%for( int i =0; i<fileList.size();i++){%>
                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                <div class="d-flex flex-column">
                                    <h6 class="mb-1 text-dark font-weight-bold text-sm"><%=fileList.get(i).getServer_file_name()%></h6>

                                </div>
                                <div class="d-flex align-items-center text-sm">

                                    <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><a href="/downloadfile?filename=<%=fileList.get(i).getServer_file_name()+"/"+fileList.get(i).getOrigin_file_name()%>"  download><i class="fas fa-file-pdf text-lg me-1"></i> 다운로드</a></button>

                                </div>
                            </li>
                         <% } %>
                        </ul>
                    </div>
                </div>
            </div>
                <!---file path filename Start!! 과제 파일 목록 가져오기 END!!!!!!!!--->

            <div class=" mt-4 "> <!---col-md-5 marginleft3여기222222222222222222222222222222222222222-->
                <div class="card h-100 mb-4">
                    <div class="card-header pb-0 px-3">
                        <div class="row">
                            <div class="col-md-6">
                                <h6 class="mb-0">과제목록</h6>
                            </div>
                            <div class="col-md-6 d-flex justify-content-end align-items-center">
                                <button id="btn-modal" style="border: 0; outline: 0; background-color: white;">
                                <i class="far fa-calendar-alt me-2"></i>
                                </button>
                                <div id="lorem-ipsum"></div>
                                <small></small>
                            </div>
                        </div>
                    </div>
                    <div class="card-body pt-4 p-3">
                        <h6 class="text-uppercase text-body text-xs font-weight-bolder mb-3"></h6>
                        <ul class="list-group">
                            <%for (int i=0; i<sList.size(); i++){%>
                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                <div class="d-flex align-items-center">
                                    <%
                                        if (sList.get(i).getDiffrence_deadline() > 0){
                                    %>
                                    <button class="btn btn-icon-only btn-rounded btn-outline-success mb-0 me-3 btn-sm d-flex align-items-center justify-content-center"><i class="fas fa-arrow-up"></i></button>
                                    <%
                                    }else {
                                    %>
                                    <button class="btn btn-icon-only btn-rounded btn-outline-danger mb-0 me-3 btn-sm d-flex align-items-center justify-content-center"><i class="fas fa-arrow-down"></i></button>
                                    <%
                                        }
                                    %>
                                    <div class="d-flex flex-column">
                                        <h6 class="mb-1 text-dark text-sm"><%=sList.get(i).getAssignment_room_name()%></h6>
                                        <span class="text-xs"><%=sList.get(i).getAssignment_start_date()%></span>
                                    </div>
                                </div>
                                <%
                                    if (sList.get(i).getDiffrence_deadline() > 0){
                                %>
                                <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                    <%=sList.get(i).getDiffrence_deadline()%> 일
                                </div>
                                <%
                                    }else {
                                %>

                                <div class="d-flex align-items-center text-danger text-gradient text-sm font-weight-bold">
                                    <%=sList.get(i).getDiffrence_deadline()%> 일
                                </div>
                                <%
                                    }
                                %>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </div>

            </div>











        </div>

        <footer class="footer pt-3  " id="marginF">
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
    </main>
</body>

<!--   Core JS Files   -->
<script src="/js/core/popper.min.js"></script>
<script src="/js/core/bootstrap.min.js"></script>
<script src="/boot2/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/boot2/js/plugins/smooth-scrollbar.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>


<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/js/soft-ui-dashboard.min.js?v=1.0.4"></script>
<script>
    var link = document.location.href;
    var group_name = '<%=group_name%>';
    var group_password = '<%=rDTO.getGroup_password()%>';
    var file_path = '<%=file_path%>';

    var method = "가입비밀번호 : "+group_password;
    //document.getElementById('method').value; //내용
    var DicName = "[COMG]"+group_name;
    //document.getElementById('dicnm').value; //제목
    console.log(method);
    console.log(DicName);

    Kakao.init('11296481aedede6bb159c54451eb5836');
    Kakao.Link.createDefaultButton({
        container: '#create-kakaotalk-sharing-btn',
        objectType: 'feed',
        content: {
            title: DicName,
            description: method,
            imageUrl:
                file_path,
            link: {
                mobileWebUrl: link,
                webUrl: link,
            },
        },

        buttons: [
            {
                title: '가입하기',
                link: {
                    mobileWebUrl: link,
                    webUrl: link,
                },
            },

        ],
    })

    const modal = document.getElementById("modal")
    const navbar2 = document.getElementById("navbar2")
    function modalOn() {
        navbar2.style.display = "none"
        modal.style.display = "flex"
    }
    function isModalOn() {
        return modal.style.display === "flex"
    }
    function modalOff() {
        navbar2.style.display = "flex"
        modal.style.display = "none"
    }
    const btnModal = document.getElementById("btn-modal")
    btnModal.addEventListener("click", e => {
        modalOn()
    })
    const closeBtn = modal.querySelector(".close-area")
    closeBtn.addEventListener("click", e => {
        modalOff()
    })
    modal.addEventListener("click", e => {
        const evTarget = e.target
        if(evTarget.classList.contains("modal-overlay")) {
            modalOff()
        }
    })
    window.addEventListener("keyup", e => {
        if(isModalOn() && e.key === "Escape") {
            modalOff()
        }
    })

    document.addEventListener('DOMContentLoaded', function() {
        let calendarEl = document.getElementById('calendar');
        let calendar = new FullCalendar.Calendar(calendarEl, {
            locale: 'ko',
            headerToolbar: {
                left: 'title',
                center: '',
                right: 'prev,next,today'
            },
            height: 'auto',
            selectable: true,
            weekends: true,
            events: [
                <%
                       for(ScheduleDTO eDTO : eList) {
                           String id = String.valueOf(eDTO.getAssignmentRoomSeq());
                           String title = eDTO.getAssignmentRoomName();
                           String start = eDTO.getAssignmentRegDate();
                           String end = eDTO.getAssignmentDeadLine();
                           %>
                {
                    id : '<%=id%>',
                    title  : '<%=title%>',
                    start  : '<%=start%>',
                    end    : '<%=end%>'
                },

                <%
                       }
                   %>
            ],
        });
        calendar.render();
    });
</script>

</html>