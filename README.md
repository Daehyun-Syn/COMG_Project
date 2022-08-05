## <img width=20px src=https://user-images.githubusercontent.com/42789819/115147514-42221300-a096-11eb-9526-a68b8094f79c.png>  Project
**Teachable Machine을 활용한 학생관리 및 커뮤니티 서비스, COMG(COvid-19 student ManaGement System)**

> **👨‍🏫  개인프로젝트(아래 이미지를 클릭하면 유튜브 📼영상으로 이동됩니다)**
> 
> [![COMG(COvid-19 student ManaGement System)](https://user-images.githubusercontent.com/80372103/182903943-9c9fb79e-f281-40ec-b735-4c05c305a2d2.png)](https://youtu.be/ZaGxmUL0TSk)
> 
> **팀장: 신대현**  
> 
> **프로젝트 기간: 2022.03 ~ 2022.06**  
## 목차
[1.프로젝트 소개](#프로젝트-소개)
* 제안 배경
* 프로젝트 개요
* 기대 효과
  
[2.사용 기술](#사용-)
*  Language
*  Data Base
*  Framework
*  Tool
*  Server
*  Storage
  
[3.디렉터리 정보](#디렉터리-정보)
* COMG_Project/src/main/java/kopo/poly/config/S3Configuration.java
* COMG_Project/src/main/java/kopo/poly/util/DownloadView.java
* COMG_Project/src/main/java/kopo/poly/controller/CImgUploadController.java
* COMG_Project/src/main/java/kopo/poly/controller/CKeditorController.java
* COMG_Project/src/main/webapp/WEB-INF/views/COMGBoard/BoardUpdate.jsp
* COMG_Project/src/main/webapp/WEB-INF/views/COMGBoard/BoardWrite.jsp
* COMG_Project/src/main/webapp/WEB-INF/views/COVID/KitSend.jsp
* COMG_Project/src/main/resources/static/teachable/teachable.js<br>
  

## 프로젝트 소개
### | 제안 배경
* 코로나19 확진자가 점차 증가하고 있음에 따라 각 학교에서는 학생 관리에 어려움을 겪고 있다.
* 각 학교마다 코로나19 확진자 관련 공지내용이 다르다.
* 따라서 각 학교별 코로나19 확진자에게 학교별로 정확한 공지 내용 전달과 과제를 출제하고 관리하는 올인원 시스템이 필요하다고 생각해 이 프로젝트를 기획하게 되었다.  
### | 프로젝트 개요
* 사용자는 해당하는 학교 그룹에 가입 후 사용자가 사용한 코로나 키트를 제출하면 키트 검사 결과에 따라 각 그룹별로 설정된 코로나 관련 공지사항을 카카오톡으로 받아볼 수 있다.
* 카카오톡으로 간단하게 사용자들을 그룹에 초대할 수 있으며, 과제를 생성 제출함에 CKEditor를 사용해 다양한 양식을 편하게 사용할 수 있다. 
### | 기대 효과
* Teachable machine을 활용하여 코로나 검사 키트를 분석하고 분석결과에 따라 설정된 카카오톡 메시지를 보내줌으로써 그룹별 코로나 환자 관리가 용이함
* 과제 생성 / 제출기능을 통해 손쉽게 과제를 생성하고 제출할 수 있음
* 과제 관리 기능을 통해 과목별로 제출된 과제를 확인하고 피드백을 작성하여 손쉽게 과제를 관리할 수 있음
* 그룹별 게시판 및 댓글기능을 통해 커뮤니케이션을 활성화할 수 있음
* 제출된 과제를 관리자가 확인 시 제출자에게 카카오톡 메시지가 전송되어 제출 현황 파악에 용이함
## 사용 기술
### | Language 
* <img src="https://img.shields.io/badge/Java-색코드?style=for-the-badge&logo=이미지 이름&logoColor=white">
* <img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white"> 
* <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">
* <img src="https://img.shields.io/badge/Css3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"> 
* <img src="https://img.shields.io/badge/Html5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white">
### | Data Base
* <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white">
### | Framework
* <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> 
### | Tool
* <img src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"> 
* <img src="https://img.shields.io/badge/PyCharm-000000?style=for-the-badge&logo=PyCharm&logoColor=white"> 
* <img src="https://img.shields.io/badge/Git Hub-181717?style=for-the-badge&logo=GitHub&logoColor=white"> 
* <img src="https://img.shields.io/badge/Git Kraken-179287?style=for-the-badge&logo=GitKraken&logoColor=white">
* <img src="https://img.shields.io/badge/Data Grip-000000?style=for-the-badge&logo=DataGrip&logoColor=white">
### | Server
* <img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=for-the-badge&logo=Apache Tomcat&logoColor=white">
* <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
### | Storage
* <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
## 디렉터리 정보
### | COMG_Project/src/main/java/kopo/poly/config/S3Configuration.java
* S3 버킷을 사용하기 위해 버킷 시크릿키, 패스워드, 버킷 이름 등을 설정하는 Java파일
### | COMG_Project/src/main/java/kopo/poly/util/DownloadView.java
* 파일을 다운로드 하기 위한 Java 유틸
### | COMG_Project/src/main/java/kopo/poly/controller/CImgUploadController.java
* 사진 및 파일을 업로드하기 위한 Java Controller
### | COMG_Project/src/main/java/kopo/poly/controller/CKeditorController.java
* CKEditor로 파일을 업로드하여 CKEditor서버로 보내주기 위한 Java Controller
### | COMG_Project/src/main/webapp/WEB-INF/views/COMGBoard/BoardWrite.jsp
* CKEditor가 적용된 글 작성 Jsp
### | COMG_Project/src/main/webapp/WEB-INF/views/COMGBoard/BoardUpdate.jsp
* CKEditor가 적용된 글 수정 Jsp
### | COMG_Project/src/main/webapp/WEB-INF/views/COVID/KitSend.jsp
* JavaScript로 Teachable Machine에 학습된 모델을 호출하기 위한 Jsp
### | COMG_Project/src/main/resources/static/teachable/teachable.js
* Teachable Machine을 사용하기 위한 Teachable Machine설정 Js
