## <img width=20px src=https://user-images.githubusercontent.com/42789819/115147514-42221300-a096-11eb-9526-a68b8094f79c.png>  Project
**Teachable Machine을 활용한 학생관리 및 커뮤니티 서비스, COMG(COvid-19 student ManaGement System)**

> **👨‍🏫  개인프로젝트**
> 
> [![COMG(COvid-19 student ManaGement System)](https://user-images.githubusercontent.com/80372103/182903943-9c9fb79e-f281-40ec-b735-4c05c305a2d2.png)](https://youtu.be/ZaGxmUL0TSk)
> 
> **팀장: 신대현**  
> 
> **프로젝트 기간: 2022.03 ~ 2022.06**  
## 목차
[1.프로젝트 설명](#프로젝트-설명)
* 제안 배경
* 기대 효과
* 단점 및 해결 방안
  
[2.사용 기술](#솔루션에-사용된-기술-및-버전)
*  Language
*  Data Base
*  Framework
*  Tool
*  Server
  
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
* 한국을 여행하는 외국인 관광객이 매년 증가하고 있다.
* 한국 여행업협회(KATA)가 조사한 결과에 따르면, 외국인 관광객의 21%는 맛집 탐방을 목적으로 한국을 방문.
* 문화체육관광부에서 조사한 자료에 따르면, 외국인 관광객의 여행 시 불편사항으로는 언어 소통이 1순위(45.2%)로 조사됨.
* 한국의 알레르기유발식품 표시제는 알레르기 유발 식품을 포괄적으로 표시하는 EU, 미국 등 다른 나라에 비해 미흡함.
* 소비자원에 접수된 식품 알레르기 사례 중 표시 의무 품목이 아닌 원료 식품에 의한 알레르기 사고는 236건(54%)으로 절반 이상을 차지.
### | 기대 효과
* Teachable machine을 활용하여 코로나 검사 키트를 분석하고 분석결과에 따라 설정된 카카오톡 메시지를 보내줌으로써 그룹별 코로나 환자 관리가 용이함
* 과제 생성 / 제출기능을 통해 손쉽게 과제를 생성하고 제출할 수 있음
* 과제 관리 기능을 통해 과목별로 제출된 과제를 확인하고 피드백을 작성하여 손쉽게 과제를 관리할 수 있음
* 그룹별 게시판 및 댓글기능을 통해 커뮤니케이션을 활성화할 수 있음
* 제출된 과제를 관리자가 확인 시 제출자에게 카카오톡 메시지가 전송되어 제출 현황 파악에 용이함
### | 단점 및 해결 방안
* 같은 음식이더라도 식당마다 레시피가 달라 정확한 알레르기 성분을 확인할 수 없다. 
<br>=> Allergy For You 서비스를 상용화 하여 식당마다 등록된 알레르기 성분 외 알레르기 성분을 추가 등록하여 해당 식당 전용 데이터를 구축하여 정확한 알레르기 정보를 확인할 수 있도록 한다.
## 사용 기술
### | Language 
* Java 
* Python 
* Html5
* Css3
* JavaScript
### | Data Base
* MySQL 
### | Framework
* Spring Framework 
### | Tool
* Eclipse
* Pycharm
* Git Hub
* Git Bash
* MySQL Workbench
### | Server
* Tomcat 
* AWS
## 디렉터리 정보
### | 2021_Hanium_Project
* Spring Framework Project 디렉터리
### | OCR
* 이미지 분석및 판독을 위한 TensorFlow 및 Spring Framework와 통신을 위한 Flask의 Python 디렉터리
