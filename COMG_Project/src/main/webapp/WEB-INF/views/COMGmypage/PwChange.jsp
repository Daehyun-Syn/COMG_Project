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
    <link rel="apple-touch-icon" sizes="76x76" href="/img/apple-icon.png">
    <link rel="icon" type="image/png" href="/img/favicon.png">
    <title>
        비밀번호 변경 | COMG
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
    <script>
        function password_Check() {

            var password1 = document.getElementById('password1').value;
            var password2 = document.getElementById('password2').value;
            console.log(password1);
            console.log(password2);
            if(password1 === password2) {
                document.getElementById('check').innerHTML = '비밀번호가 일치합니다.'
                document.getElementById('check').style.color = 'Green';
            }else {
                document.getElementById('check').innerHTML = '비밀번호가 일치하지 않습니다.'
                document.getElementById('check').style.color = 'Red';
                return false;
            }
        }

    </script>
</head>

<body class="">
<!-- Navbar -->
<nav class="navbar navbar-expand-lg position-absolute top-0 z-index-3 w-100 shadow-none my-3 navbar-transparent mt-4">
    <div class="container">
        <a class="navbar-brand font-weight-bolder ms-lg-0 ms-3 text-white" href="/COMG/main">
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
                    <a class="nav-link me-2" href="">

                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link me-2" >

                    </a>
                </li>
            </ul>
            <li class="nav-item d-flex align-items-center">

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
                        <h1 class="text-white mb-2 mt-5"></h1>
                        <p class="text-lead text-white"><br>임시 비밀번호, 혹은 기존 비밀번호로 비밀번호를 변경할 수 있습니다.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row mt-lg-n10 mt-md-n11 mt-n10">
                <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
                    <div class="card z-index-0">
                        <div class="card-header text-center pt-4">
                            <h5>비밀번호 변경</h5>
                        </div>
                        <div class="row px-xl-5 px-sm-4 px-3">

                        </div>
                        <div class="card-body">
                            <form role="form text-left" action="/COMG/passwordChange" method="post">

                                <div class="mb-3">
                                    <input type="password" class="form-control" id="origin_password" name="origin_password" placeholder="현재 비밀번호를 입력하세요." aria-label="Email" required="required" aria-describedby="email-addon">
                                </div>
                                <div class="mb-3">
                                    <input type="password" class="form-control" id="password1" name="password1" placeholder="새 비밀번호를 입력하세요." aria-label="Email" required="required" aria-describedby="email-addon">
                                </div>
                                <div class="mb-3">
                                    <input type="password" class="form-control" id="password2" name="password2" placeholder="새 비밀번호를 한번 더 입력하세요." aria-label="Email" aria-describedby="email-addon" required="required" onkeyup="password_Check()">

                                </div>
                                <p id="check"></p>
                                <div class="text-center">
                                    <button type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2">비밀번호 변경</button>
                                </div>

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
<script src="/boot2/js/core/popper.min.js"></script>
<script src="/boot2/js/core/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/js/jquery-3.6.0.min.js"></script>
<!-- Github buttons -->
<script async defer src="https://buttons.github.io/buttons.js"></script>
<!-- Control Center for Soft Dashboard: parallax effects, scripts for the example pages etc -->
<script src="/js/soft-ui-dashboard.min.js?v=1.0.4"></script>
</body>

</html>
