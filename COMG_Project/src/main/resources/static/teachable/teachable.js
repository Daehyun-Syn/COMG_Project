// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image

// the link to your model provided by Teachable Machine export panel
const URL = "https://teachablemachine.withgoogle.com/models/Cm3WfKU9Q/";

let model, webcam, labelContainer, maxPredictions;

// Load the image model and setup the webcam
async function init() { //start 버튼을 누르면 init이라는 함수가 실행된다 init으로 모델을 실행
    const modelURL = URL + "model.json";
    const metadataURL = URL + "metadata.json";

    // load the model and metadata
    // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
    // or files from your local hard drive
    // Note: the pose library adds "tmImage" object to your window (window.tmImage)
    model = await tmImage.load(modelURL, metadataURL); //모델이 저장된 파일을 가져와서 load하고 model에 저장해준다.
    maxPredictions = model.getTotalClasses();
    labelContainer = document.getElementById("label-container");
}


//지정된 정보에대해 찾을때 그 설명에 대해 찾을 수 있도록 나오게 하기
// run the webcam image through the image model
async function predict() {
    console.log("pridict 안에 들어옴!")
    // predict can take in an image, video or canvas html element
    var image = document.getElementById("uploadimage") //id를 통해서 이미지를 가져옴
    var group_seq = document.getElementById("group_seq").value;

    let send_data = {"group_seq" : group_seq};
    console.log("image"+image);
    console.log("group_seq" + group_seq);
    const prediction = await model.predict(image,false);  //예측하기위한 이미지를 넣음 두번째인자는 flipped로 뒤집혔는지 안뒤집혔는지 확인
    if(prediction[0].className == "음성" &&  prediction[0].probability.toFixed(2)==1.00){
        swal.fire({
            icon : 'success',
            title: '음성 입니다.',
            text : "그룹페이지로 돌아갑니다.",
        }).then(function(){
            location.href="/COMG/Group?GroupSEQ="+group_seq;
        });
    }else if(prediction[1].className == "양성" &&  prediction[1].probability.toFixed(2)==1.00){

        swal.fire({
            icon : "warning",
            title : "양성 입니다.",
            text : "카카오톡으로 안내를 받으시려면 확인을 눌러주세요.",
            closeOnClickOutside: false,
            showCancelButton: true,
            confirmButton: true,
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then(result => {
            if(result.value){
                $.ajax({
                    url: '/kakaoSend',
                    data : send_data,
                    type: "GET",
                    success: function(data){
                        if(data == 1){
                            console.log("카카오톡 안내 성공");
                            swal.fire({
                                icon : 'success',
                                title: '카카오톡 발송 성공',
                                text : "안내메세지가 전송되었습니다.",
                            }).then(function(){
                                location.href="/COMG/main";
                            });
                        }else{
                            console.log("탈퇴 실패");
                            swal.fire({
                                icon : 'warning',
                                title: '카카오톡 발송 실패',
                                text : "다시한번 시도해 주세요.",
                            })
                            return false;
                        }
                    },
                    error: function (){

                    }
                });
            }
        });

    }else if(prediction[2].className =="양성(연한)" &&  prediction[2].probability.toFixed(2)==1.00){
        swal.fire({
            icon : "warning",
            title : "양성(연한줄) 입니다.",
            text : "카카오톡으로 안내를 받으시려면 확인을 눌러주세요.",
            closeOnClickOutside: false,
            showCancelButton: true,
            confirmButton: true,
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then(result => {
            if(result.value){
                $.ajax({
                    url: '/kakaoSend',
                    data : send_data,
                    type: "GET",
                    success: function(data){
                        if(data == 1){
                            console.log("카카오톡 안내 성공");
                            swal.fire({
                                icon : 'success',
                                title: '카카오톡 발송 성공',
                                text : "안내메세지가 전송되었습니다.",
                            }).then(function(){
                                location.href="/COMG/main";
                            });
                        }else{
                            console.log("카카오톡 발송 실패");
                            swal.fire({
                                icon : 'warning',
                                title: '카카오톡 발송 실패',
                                text : "다시한번 시도해 주세요.",
                            })
                            return false;
                        }
                    },
                    error: function (){

                    }
                });
            }
        });
    }else{
        swal.fire({
            icon : 'error',
            title: '분석할 수 없습니다.',
            text : "다른 각도로 찍은 사진을 다시 업로드 해주세요.",
        });
    }
}


document.write("<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>");
document.write("<script src=\"/js/jquery-3.6.0.min.js\"></script>");
document.write("<script src=\"/js/sweetalert2.all.min.js\"></script>");

