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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/resources/img/apple-icon.png">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/img/favicon.png">
    <title>
        회원가입 | COMG
    </title>
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
    <!-- Nucleo Icons -->
    <link href="${pageContext.request.contextPath}/resources/css/nucleo-icons.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/nucleo-svg.css" rel="stylesheet" />
    <!-- Font Awesome Icons -->
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <link href="${pageContext.request.contextPath}/resources/css/nucleo-svg.css" rel="stylesheet" />
    <!-- CSS Files -->
    <link id="pagestyle" href="${pageContext.request.contextPath}/resources/css/soft-ui-dashboard.css?v=1.0.4" rel="stylesheet" />
</head>

<body class="">
<!-- Navbar -->
<nav class="navbar navbar-expand-lg position-absolute top-0 z-index-3 w-100 shadow-none my-3 navbar-transparent mt-4">
    <div class="container">
        <a class="navbar-brand font-weight-bolder ms-lg-0 ms-3 text-white" href="/COMG">
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
                    <a class="nav-link me-2">

                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link me-2" >

                    </a>
                </li>
            </ul>
            <li class="nav-item d-flex align-items-center">
                <a class="btn btn-sm btn-round mb-0 me-1 bg-gradient-success"  href="COMGlogin">로그인</a>
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
        <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg" style="background-image: url('${pageContext.request.contextPath}/resources/img/curved-images/curved14.jpg');">
            <span class="mask bg-gradient-dark opacity-6"></span>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 text-center mx-auto">
                        <h1 class="text-white mb-2 mt-5">환영합니다!</h1>
                        <p class="text-lead text-white"><br>회원가입을 통해 COMG를 시작하세요.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row mt-lg-n10 mt-md-n11 mt-n10">
                <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
                    <div class="card z-index-0">
                        <div class="card-header text-center pt-4">
                            <h5>회원가입</h5>
                        </div>
                        <div class="row px-xl-5 px-sm-4 px-3">

                        </div>
                        <div class="card-body">
                            <form role="form text-left">
                                <div class="mb-3">
                                    <input type="text" class="form-control" placeholder="이름" aria-label="Name" aria-describedby="email-addon">
                                </div>
                                <div class="mb-3">
                                    <input type="email" class="form-control" placeholder="이메일" aria-label="Email" aria-describedby="email-addon">
                                </div>
                                <div class="mb-3">
                                    <input type="password" class="form-control" placeholder="비밀번호" aria-label="Password" aria-describedby="password-addon">
                                </div>
                                <div class="mb-3">
                                    <input type="birth" class="form-control" placeholder="학번" aria-label="StudentID" aria-describedby="password-addon">
                                </div>
                                <div class="text-center">
                                    <button type="button" class="btn bg-gradient-primary w-100 my-4 mb-2">회원가입</button>
                                </div>
                                <p class="text-sm mt-3 mb-0">이미 가입하셨나요? <a href="javascript:;" class="text-dark font-weight-bolder">로그인하기</a></p>
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
<script src="${pageContext.request.contextPath}/resources/js/core/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/core/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugins/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugins/smooth-scrollbar.min.js"></script>
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
<script src="${pageContext.request.contextPath}/resources/js/soft-ui-dashboard.min.js?v=1.0.4"></script>
</body>

</html>
