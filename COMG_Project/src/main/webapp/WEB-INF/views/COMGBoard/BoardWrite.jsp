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
<%
    String user_name = CmmUtil.nvl((String)request.getAttribute("user_name"));
    String group_seq = CmmUtil.nvl(request.getParameter("GroupSEQ"));
    String fk_group_user_seq = CmmUtil.nvl(request.getParameter("FK_GROUP_USER_SEQ"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>게시글 작성 | COMG</title>
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
    <script src="/ckeditor/ckeditor.js"></script>
    <script src="/ckeditor/config.js"></script>
    <link rel="stylesheet" href="/ckeditor/css/samples.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Try the latest sample of CKEditor 4 and learn more about customizing your WYSIWYG editor with endless possibilities.">
    <script>
        CKEDITOR.editorConfig = function(config) {
            config.toolbarGroups = [
                { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
                { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
                { name: 'links', groups: [ 'links' ] },
                { name: 'insert', groups: [ 'insert' ] },
                { name: 'forms', groups: [ 'forms' ] },
                { name: 'tools', groups: [ 'tools' ] },
                { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
                '/',
                '/',
                '/',
                { name: 'others', groups: [ 'others' ] },
                { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
                { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
                { name: 'styles', groups: [ 'styles' ] },
                { name: 'colors', groups: [ 'colors' ] },
                { name: 'about', groups: [ 'about' ] }
            ];

            config.removeButtons = 'Underline,Subscript,Superscript,About,Styles,Source,Scayt,PasteText,PasteFromWord,Anchor,base64image';
        };

        function file_upload(input){
            var fileCheck = document.getElementById("uploadImgUser").value;
            if (!fileCheck) {
                console.log("파일 없으므로 게시글만 작성 시작");
                let textarea = CKEDITOR.instances.board.getData();
                let data = {"board" : textarea, "group_seq" : '<%=group_seq%>', "fk_group_user_seq" : '<%=fk_group_user_seq%>'};
                console.log(CKEDITOR.instances.board.getData());
                console.log(data);
                $.ajax({
                    url: '/COMG/BoardWriteLogic',
                    type: "GET",
                    contentType : "application/json; charset=utf-8",
                    data: data,
                    dataType: "text",
                    success: function(data){
                        if(data == 1){
                            console.log("게시글 작성 성공!");
                            swal('게시글 작성 성공!', "게시글이 작성되었습니다", 'success')
                                .then(function(){
                                    location.href="/COMG/Group?GroupSEQ=<%=group_seq%>";
                                });
                        }else {
                            console.log("게시글 작성 실패!");
                            swal('게시글 작성 실패!', "오류로 인해 게시글 작성이 실패하였습니다.", 'warning');
                        }
                    },
                    error: function (){
                        console.log("아작스 에러");
                        swal('게시글 작성 실패!', "파일 용량이 10mb를 초과합니다.", 'warning');
                    }
                });

            }else {
                console.log("파일 존재하여 파일 업로드+게시판 작성 시작");
                let textarea = CKEDITOR.instances.board.getData();
                console.log(textarea);
                var form = $('#form')[0];
                var formData = new FormData(form);
                formData.append("from_table", "BOARD_INFO")
                formData.append("COMG_Board_Contents", CKEDITOR.instances.board.getData())
                formData.append("group_seq", "<%=group_seq%>")
                formData.append("FK_GROUP_USER_SEQ", "<%=fk_group_user_seq%>")
                $.ajax({
                    url: '/img/imgUpload',
                    type: "POST",
                    enctype: 'multipart/form-data',
                    data: formData,
                    dataType: "text",
                    processData: false,
                    contentType: false,
                    //cache: false,
                    timeout: 600000,
                    success: function(data){
                        if(data == 1){
                            console.log("게시글 작성 성공!");
                            swal('게시글 작성 성공!', "게시글이 작성되었습니다", 'success')
                                .then(function(){
                                    location.href="/COMG/Group?GroupSEQ=<%=group_seq%>";
                                });
                        }else {
                            console.log("게시글 작성 실패!");
                            swal('게시글 작성 실패!', "오류로 인해 게시글 작성이 실패하였습니다.", 'warning');
                        }
                    },
                    error: function (){
                        console.log("아작스 에러");
                        swal('게시글 작성 실패!', "파일 용량이 10mb를 초과합니다.", 'warning');
                    }
                });
            }
        }


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
<body id="main">



<main class="main-content position-relative">
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
    <div class="adjoined-top">
        <div class="grid-container">
            <div class="content grid-width-100">
                <h1>게시글 작성</h1>

            </div>
        </div>
    </div>

    <div class="adjoined-bottom">
        <div class="grid-container">
            <form id="form"  method="post">
            <div class="grid-width-100">


                <label for="board"></label>
                <textarea id="board" name="board"></textarea>
                <script type="text/javascript">	// 글쓰기 editor 및 사진 업로드 기능
                CKEDITOR.replace('board',{filebrowserUploadUrl:'/img/imgUpload'});
                </script>

            </div>
                <div>
                    <div style="margin-left: 5%">
                        <label for="uploadImgUser" class="form-label">첨부할 파일을 넣어주세요.</label>
                        <input class="form-control" type="file" id="uploadImgUser" name="uploadImgUser" style="width: 94.7%"/>
                    </div>
                </div>
                <div class="text-center">

                    <button type="button" class="btn bg-gradient-info w-90 my-4 mb-2" onclick="file_upload(this);">작성하기</button>
                </div>
            </form>
        </div>
    </div>

</main>

</body>

<!--   Core JS Files   -->
<script src="/js/core/popper.min.js"></script>
<script src="/js/core/bootstrap.min.js"></script>
<script src="/boot2/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/boot2/js/plugins/smooth-scrollbar.min.js"></script>
<script src="/vendor/libs/jquery/jquery.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>


<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/js/soft-ui-dashboard.min.js?v=1.0.4"></script>

</html>
