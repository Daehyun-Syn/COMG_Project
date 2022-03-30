<!DOCTYPE html>
<html>
<head>
    <script src="./jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="./bootstrapt/css/bootstrap.min.css" />
    <script src="./bootstrapt/js/bootstrap.min.js"></script>

</head>
<body>
<button id="testBtn" class="btn">모달 테스트</button>
<!-- 회원가입 확인 Modal-->
<div class="modal fade" id="testModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">모달테스트</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">X</span>
                </button>
            </div>
            <div class="modal-body">내용 입력 !!</div>
            <div class="modal-footer">
                <a class="btn" id="modalY" href="#">예</a>
                <button class="btn" type="button" data-dismiss="modal">아니요</button>
            </div>
        </div>
    </div>
</div>

</body>