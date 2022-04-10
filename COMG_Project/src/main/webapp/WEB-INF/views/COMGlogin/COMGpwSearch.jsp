<%--
  Created by IntelliJ IDEA.
  User: 신 대현
  Date: 2022-03-20(020)
  Time: 오후 1:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <style>/* DivTable.com */
    .divTable{
        display: table;
        width: 100%;
    }
    .divTableRow {
        display: table-row;
    }
    .divTableHeading {
        background-color: #EEE;
        display: table-header-group;
    }
    .divTableCell, .divTableHead {
        border: 1px solid #999999;
        display: table-cell;
        padding: 3px 10px;
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
    }</style>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="/img/apple-icon.png">
    <link rel="icon" type="image/png" href="/img/favicon.png">
    <title>
        비밀번호 찾기 | COMG
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
</head>

<body class="">
<!-- Navbar -->
<nav class="navbar navbar-expand-lg position-absolute top-0 z-index-3 w-100 shadow-none my-3 navbar-transparent mt-4">
    <div class="container">
        <a class="navbar-brand font-weight-bolder ms-lg-0 ms-3 text-white" href="/COMG/home">
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
                    <a class="nav-link d-flex align-items-center me-2 active" aria-current="page" href="../pages/dashboard.html">

                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link me-2" >

                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link me-2" href="/COMG/IdSearch">
                        <i class="fas fa-user-circle opacity-6  me-1"></i>
                        아이디 찾기
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link me-2" >

                    </a>
                </li>
            </ul>
            <li class="nav-item d-flex align-items-center">
                <a class="btn btn-sm btn-round mb-0 me-1 bg-gradient-success"  href="/COMG/login">로그인</a>
            </li>
            <ul class="navbar-nav d-lg-block d-none">
                <li class="nav-item">

                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- End Navbar -->
<main class="main-content  mt-0">
    <section class="min-vh-100 mb-8">
        <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg" style="background-image: url('/img/curved-images/curved14.jpg');">
            <span class="mask bg-gradient-dark opacity-6"></span>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 text-center mx-auto">
                        <h1 class="text-white mb-2 mt-5">환영합니다!</h1>
                        <p class="text-lead text-white"><br>본인확인을 위해 인증번호를 입력 후<br>가입하신 이메일로 임시비밀번호를 전송합니다.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row mt-lg-n10 mt-md-n11 mt-n10">
                <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
                    <div class="card z-index-0">
                        <div class="card-header text-center pt-4">
                            <h5>비밀번호 찾기</h5>
                        </div>
                        <div class="row px-xl-5 px-sm-4 px-3">

                        </div>
                        <div class="card-body">
                            <form role="form text-left" method="post" action="/COMG/updatePassWord">

                                <div style="float: left; width: 75%; margin-bottom: 15px;" class="table1">
                                    <input id="id" class="form-control" name="id" required="required" type="email" placeholder="이메일" />
                                </div>
                                <div style="float: right;" class="table2">
                                    <button id="code_send" class="btn bg-gradient-info w-100 " style="font-size: 75%;" type="button" onclick="code_sending()">전송</button>
                                </div>

                                <p style="float: left; width: 75%" >
                                    <input id="Certification_Number" class="form-control" name="Certification_Number" required="required" type="text" placeholder="인증번호" />
                                </p>
                                <p style="float: right;" >
                                    <button id="code_check" class="btn bg-gradient-secondary w-100 " style="font-size: 75%;" type="button" onclick="code_checking()">확인</button>
                                </p>


                                <div class="text-center">
                                    <button type="submit" class="btn bg-gradient-warning w-100 my-4 mb-2">임시 비밀번호 전송</button>
                                </div>
                                <p class="text-sm mt-3 mb-0">아이디가 기억나지 않는다면? <a href="/COMG/IdSearch" class="text-dark font-weight-bolder">아이디 찾기 바로가기</a></p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- -------- START FOOTER 3 w/ COMPANY DESCRIPTION WITH LINKS & SOCIAL ICONS & COPYRIGHT ------- -->
    <footer class="footer py-5">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 mb-4 mx-auto text-center">
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2">

                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2">

                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2">

                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2">

                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2">

                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2">

                    </a>
                </div>
                <div class="col-lg-8 mx-auto text-center mb-4 mt-2">
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class=""></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class=""></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-instagram"></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class=""></span>
                    </a>
                    <a href="javascript:;" target="_blank" class="text-secondary me-xl-4 me-4">
                        <span class="text-lg fab fa-github"></span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-8 mx-auto text-center mt-1">
                    <p class="mb-0 text-secondary">
                        Copyright © <script>
                        document.write(new Date().getFullYear())
                    </script> Soft by Syn
                    </p>
                </div>
            </div>
        </div>
    </footer>
    <!-- -------- END FOOTER 3 w/ COMPANY DESCRIPTION WITH LINKS & SOCIAL ICONS & COPYRIGHT ------- -->
</main>
<!--   Core JS Files   -->
<script src="/js/core/popper.min.js"></script>
<script src="/js/core/bootstrap.min.js"></script>
<script src="/js/plugins/perfect-scrollbar.min.js"></script>
<script src="/js/plugins/smooth-scrollbar.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<!-- 아이디 중복 체크 .ajax -->
<script>
    function code_sending(){
        let id = $("#id");
        console.log(id);
        $.ajax({
            url: '/COMG/emailCheckLogic',
            type: "POST",
            data: id ,
            success: function(data){
                if(data == 1){
                    console.log("전송 성공");
                    swal('인증번호 전송', "인증번호가 전송되었습니다.", 'success');
                }else{
                    console.log("전송 실패");
                    swal('인증번호 전송', "가입된 아이디가 없어 인증번호 전송이 실패하였습니다.", 'error');
                    return false;
                }
            },
            error: function (){

            }
        });
    }

    function code_checking(){

        let Certification_Number = $("#Certification_Number");
        console.log(Certification_Number);
        $.ajax({
            url: '/COMG/codeCheckLogic',
            type: "POST",
            data: Certification_Number ,
            success: function(data){
                if(data == 1){
                    console.log("인증번호 일치");
                    swal('인증번호 확인', "인증번호가 일치합니다.", 'success');
                }else{
                    console.log("인증번호 불일치");
                    swal('인증번호 확인', "인증번호가 불일치합니다. 다시한번 시도해주세요,", 'error');
                    return false;
                }
            },
            error: function (){

            }
        });
    }
</script>
<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/js/soft-ui-dashboard.min.js?v=1.0.4"></script>
</body>

</html>