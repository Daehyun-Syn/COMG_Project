<%@ page import="kopo.poly.dto.CAssignmentDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %><%--
  Created by IntelliJ IDEA.
  User: 신 대현
  Date: 2022-03-26(026)
  Time: 오후 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    List<CAssignmentDTO> rList = (List<CAssignmentDTO>) request.getAttribute("rList");
    List<CAssignmentDTO> pList = (List<CAssignmentDTO>) request.getAttribute("pList");

    String user_name = CmmUtil.nvl((String)request.getAttribute("user_name"));
    String group_seq = CmmUtil.nvl((String) request.getAttribute("group_seq"));

    String homework="";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <style>
        .divTable{
            display: table;
            width: 100%;

        }
        .divTableRow {
            display: table-row;
            border-top: 10px solid black;


        }
        .divTableHeading {
            background-color: #EEE;
            display: table-header-group;

        }
        .divTableCell, .divTableHead {

            display: table-cell;
            padding: 3px 10px;
            border-bottom: 1px solid #808080;
        }
        .divTableHeading {
            background-color: #EEE;
            display: table-header-group;
            font-weight: bold;
        }
        .divTableFoot {
            background-color: #EEE;
            display: table-footer-group;
            font-weight: bold;
        }
        .divTableBody {
            display: table-row-group;
        }

    </style>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="/img/apple-icon.png">
    <link rel="icon" type="image/png" href="/img/favicon.png">
    <title>
        과제 & 키트 제출내역 | COMG
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


</head>

<body class="g-sidenav-show  bg-gray-100">

<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" navbar-scroll="true">
        <div class="container-fluid py-1 px-3">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                    <li class=""></li>
                    <li class=""><br></li>
                </ol>
                <a href="/COMG/main">
                <h6 class="font-weight-bolder mb-0">COMG</h6>
                </a>
            </nav>
            <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
                <div class="ms-md-auto pe-md-3 d-flex align-items-center">
                    <div class="input-group">
                    </div>
                </div>
                <ul class="navbar-nav  justify-content-end">
                    <li class="nav-item d-flex align-items-center">
                    </li>
                    <li class="nav-item d-flex align-items-center">
                        <a href="/COMG/Mypage" class="nav-link text-black font-weight-bold px-0">
                        <i class="fa fa-user me-sm-1"></i>
                            <span class="d-sm-inline d-none"><%=user_name%></span>
                        </a>
                    </li>
                    <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
                        <a href="javascript:;" class="nav-link text-body p-0" id="iconNavbarSidenav">
                            <div class="sidenav-toggler-inner">
                                <i class="sidenav-toggler-line"></i>
                                <i class="sidenav-toggler-line"></i>
                                <i class="sidenav-toggler-line"></i>
                            </div>
                        </a>
                    </li>
                    <li class="nav-item px-3 d-flex align-items-center">

                    </li>
                    <li class="nav-item dropdown pe-2 d-flex align-items-center">
                        <a href="javascript:;" class="nav-link text-body p-0" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa fa-bell cursor-pointer"></i>
                        </a>
                        <ul class="dropdown-menu  dropdown-menu-end  px-2 py-3 me-sm-n4" aria-labelledby="dropdownMenuButton">
                            <li class="mb-2">
                                <a class="dropdown-item border-radius-md" href="javascript:;">
                                    <div class="d-flex py-1">
                                        <div class="my-auto">
                                            <img src="/img/team-2.jpg" class="avatar avatar-sm  me-3 ">
                                        </div>
                                        <div class="d-flex flex-column justify-content-center">
                                            <h6 class="text-sm font-weight-normal mb-1">
                                                <span class="font-weight-bold">New message</span> from Laur
                                            </h6>
                                            <p class="text-xs text-secondary mb-0 ">
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
                                            <img src="/img/small-logos/logo-spotify.svg" class="avatar avatar-sm bg-gradient-dark  me-3 ">
                                        </div>
                                        <div class="d-flex flex-column justify-content-center">
                                            <h6 class="text-sm font-weight-normal mb-1">
                                                <span class="font-weight-bold">New album</span> by Travis Scott
                                            </h6>
                                            <p class="text-xs text-secondary mb-0 ">
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
                                        <div class="avatar avatar-sm bg-gradient-secondary  me-3  my-auto">
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
                                            <p class="text-xs text-secondary mb-0 ">
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
        <div class="row">
            <div class="col-12">
                <div class="card mb-4">
                    <div class="card-header pb-0">
                        <h6>융합프로젝트 실습 | 제출내역</h6>
                    </div>
                    <div class="card-body px-0 pt-0 pb-2">
                        <div class="table-responsive p-0">

                            <div class="divTable">
                                <div class="divTableHeading">
                                    <div class="divTableRow" style="background-color: #FFFFFF">
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">이름</div></div>
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">학번</div></div>
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">상태</div></div>
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">제출일</div></div>
                                        <div class="divTableHead">&nbsp;</div>
                                    </div>
                                </div>
                                <div class="divTableBody">
                                    <%
                                        for (int i=0; i<pList.size(); i++){
                                    %>
                                    <div class="divTableRow">
                                        <div class="divTableCell">
                                            <div class="d-flex px-2 py-1">
                                                <div><img class="avatar avatar-sm me-3" src="<%=pList.get(i).getServer_file_path()%>" alt="user6" /></div>
                                                <div class="d-flex flex-column justify-content-center">
                                                    <h6 class="mb-0 text-sm"><%=pList.get(i).getUser_name()%></h6>
                                                    <p class="text-xs text-secondary mb-0"><%=pList.get(i).getUser_id()%></p>
                                                    <input type="hidden" id="homework<%=i%>" value="<%=pList.get(i).getAssignment_seq()%>">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="divTableCell">
                                            <p class="text-xs font-weight-bold mb-0"><%=pList.get(i).getStudent_id()%></p>
                                            <p class="text-xs text-secondary mb-0">&nbsp;</p>
                                        </div>
                                        <%
                                            if(pList.get(i).getAssignment_send_regdate()==null){
                                        %>
                                        <div class="divTableCell"><span class="badge badge-sm bg-gradient-secondary">
                                            미제출
                                        </span></div>
                                        <%
                                            }else{
                                        %>
                                        <input type="hidden" value="<%=pList.get(i).getDownload_file_path()%>" id="download_file">
                                        <div class="divTableCell" onclick="downfile(this)" style="cursor: pointer"><span class="badge badge-sm bg-gradient-success">제출</span></div>
                                        <%
                                            }
                                        %>
                                        <div class="divTableCell"><%=pList.get(i).getAssignment_send_regdate()%></div>
                                        <div class="divTableCell2" id="work<%=pList.get(i).getAssignment_seq()%>" style="cursor: pointer"><a class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user"> 피드백  </a></div>
                                    </div>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>




                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="card mb-4">
                    <div class="card-header pb-0">
                        <h6>과제 목록</h6>
                    </div>
                    <div class="card-body px-0 pt-0 pb-2">
                        <div class="table-responsive p-0">

                            <div class="divTable">
                                <div class="divTableHeading">
                                    <div class="divTableRow" style="background-color: #FFFFFF">
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">과목명</div></div>
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">기간</div></div>
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">상태</div></div>
                                        <div class="divTableHead"><div class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">제출현황</div></div>
                                        <div class="divTableHead"></div>
                                    </div>
                                </div>
                                <div class="divTableBody">
                                    <%
                                        for(int i = rList.size() - 1; i >= 0 ; i--) {
                                    %>

                                    <div class="divTableRow" onclick="location.href='/COMG/assignment2?room_seq=<%=rList.get(i).getAssignment_room_seq()%>&&group_seq=<%=group_seq%>'" style="cursor: pointer" >

                                        <div class="divTableCell">
                                            <div class="d-flex px-2">
                                                <div><img class="avatar avatar-sm rounded-circle me-2" alt="spotify" src="<%=rList.get(i).getAssignment_profile_path()%>"/>

                                                </div>
                                                <div class="my-auto"><h6 class="mb-0 text-sm"><%=rList.get(i).getAssignment_room_name()%></h6></div>
                                            </div>
                                        </div>

                                        <div class="divTableCell">
                                            <p class="text-sm font-weight-bold mb-0"><%=rList.get(i).getAssignment_start_date()%> ~ <%=rList.get(i).getAssignment_deadline()%></p>
                                        </div>
                                        <%
                                            if(rList.get(i).getDiffrence_deadline() > 0) {
                                        %>
                                        <div class="divTableCell"><span class="text-xs font-weight-bold">진행중</span></div>
                                        <%
                                        }else {
                                        %>
                                        <div class="divTableCell"><span class="text-xs font-weight-bold">마감됨</span></div>
                                        <%
                                            }
                                        %>
                                        <div class="divTableCell">
                                            <div class="d-flex align-items-center justify-content-center"><span class="me-2 text-xs font-weight-bold"><%=rList.get(i).getAssignment_count()%>%</span>
                                                <div>

                                                    <%
                                                        if(rList.get(i).getAssignment_count() == 0) {
                                                    %>
                                                    <div class="progress">
                                                        <div class="progress-bar bg-gradient-success" role="progressbar" aria-valuenow="<%=rList.get(i).getAssignment_count()%>%" aria-valuemin="0" aria-valuemax="<%=rList.get(i).getAssignment_count()%>%" style="width: <%=rList.get(i).getAssignment_count()%>%; height: 300%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                                    </div>
                                                    <%
                                                    }else if (rList.get(i).getAssignment_count() > 1 && rList.get(i).getAssignment_count() <41){
                                                    %>
                                                    <div class="progress">
                                                        <div class="progress-bar bg-gradient-danger" role="progressbar" aria-valuenow="<%=rList.get(i).getAssignment_count()%>%" aria-valuemin="0" aria-valuemax="<%=rList.get(i).getAssignment_count()%>%" style="width: <%=rList.get(i).getAssignment_count()%>%; height: 300%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                                    </div>
                                                    <%
                                                    }else if (rList.get(i).getAssignment_count() > 40 && rList.get(i).getAssignment_count() <100){
                                                    %>
                                                    <div class="progress">
                                                        <div class="progress-bar bg-gradient-info" role="progressbar" aria-valuenow="<%=rList.get(i).getAssignment_count()%>%" aria-valuemin="0" aria-valuemax="<%=rList.get(i).getAssignment_count()%>%" style="width: <%=rList.get(i).getAssignment_count()%>%; height: 300%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                                    </div>
                                                    <%
                                                    } else if (rList.get(i).getAssignment_count() == 100) {
                                                    %>
                                                    <div class="progress">
                                                        <div class="progress-bar bg-gradient-success" role="progressbar" aria-valuenow="<%=rList.get(i).getAssignment_count()%>%" aria-valuemin="0" aria-valuemax="<%=rList.get(i).getAssignment_count()%>%" style="width: <%=rList.get(i).getAssignment_count()%>%; height: 300%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                                    </div>
                                                    <%
                                                        }
                                                    %>
                                                </div>
                                            </div>
                                        </div>
                                        <button class="btn btn-link text-secondary mb-0">
                                            <i class="fa fa-ellipsis-v text-xs"></i>
                                        </button>

                                    </div>
                                    <%
                                        }
                                    %>
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
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/js/sweetalert2.all.min.js"></script>
<script>
    $(document).on('click', '.divTableCell2', function(){
        swal.fire({
            icon : "success",
            text : "피드백을 작성해주세요",
            input : 'text',
            inputPlaceholder : '피드백을 작성해주세요',
            inputAttributes: {
                autocapitalize: 'off'
            },
            closeOnClickOutside: false,
            showCloseButton: true,
            confirmButton: true,
            confirmButtonText: '확인',
        }).then(result => {
            let res_homework = $(this).attr('id');
            console.log(res_homework);
            let result_home = res_homework.substring(4);
            console.log(result_home);
            let feedback = result.value

            send_data = {
                "feedback" : feedback,
                "homework_seq" : result_home
            }
            $.ajax({
                url: '/COMG/CrateFeedBack',
                type: "POST",
                data : send_data,
                success: function(data){
                    if(data == 1){
                        console.log("피드백 작성 성공");
                        swal.fire({
                            icon : 'success',
                            title: '피드백 작성 성공',
                            text : "피드백 작성이 정상적으로 이루어졌습니다.",
                        }).then(function(){
                            location.href="/COMG/main";
                        });
                    }else{
                        console.log("피드백 작성 실패");
                        swal.fire({
                            icon : 'warning',
                            title: '피드백 작성 실패',
                            text : "다시한번 시도해 주세요.",
                        })
                        return false;
                    }
                },
                error: function (){

                }
            });
        });
    });

    function downfile(){
        var file_path = document.getElementById("download_file").value;
        const encoded = encodeURI(file_path);
        location.href="/downloadfile?filename="+encoded;
    }
</script>
</body>

</html>