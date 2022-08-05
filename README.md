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
* OCR<br>
  

## 프로젝트 소개
### | 제안 배경
* 한국을 여행하는 외국인 관광객이 매년 증가하고 있다.
* 한국 여행업협회(KATA)가 조사한 결과에 따르면, 외국인 관광객의 21%는 맛집 탐방을 목적으로 한국을 방문.
* 문화체육관광부에서 조사한 자료에 따르면, 외국인 관광객의 여행 시 불편사항으로는 언어 소통이 1순위(45.2%)로 조사됨.
* 한국의 알레르기유발식품 표시제는 알레르기 유발 식품을 포괄적으로 표시하는 EU, 미국 등 다른 나라에 비해 미흡함.
* 소비자원에 접수된 식품 알레르기 사례 중 표시 의무 품목이 아닌 원료 식품에 의한 알레르기 사고는 236건(54%)으로 절반 이상을 차지.
### | 기대 효과
* 한국 식당에서의 외국인 관광객과의 의사소통 문제 해소에 기여할 수 있다.
* 미흡한 알레르기 성분 표기를 보완할 수 있다.
* 식품 섭취 전 경고 알림을 통해 식품 섭취로 생기는 아나필락시스 발생을 방지할 수 있다.
* 데이터 누적으로 유의미한 통계 자료 생성시, 축척된 데이터를 공공 API 형태로 데이터를 공공기관에 제공하여 알레르기 분야에서의 데이터 활용도를 극대화 할 수 있다.
* 사진 촬영만으로 알레르기 정보를 바로 받아볼수 있어, 식품 알레르기 환자가 알레르기 성분을 확인하는데 걸리는 시간을 단축할 수 있다.
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
