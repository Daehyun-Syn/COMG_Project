<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<script src="/vendor/libs/jquery/jquery.js"></script>



	<style>
		.rollingbanner {
			position: relative;
			width: 380px;
			height: 40px;
			font-size: .875rem;
			letter-spacing: -1px;
			padding: 7px 15px;
			box-sizing: border-box;

			border-radius: 16px;
		}

		/* 타이틀 */
		.rollingbanner > .title {
			font-weight: bold;
			float: left;
			padding-right: 10px;
		}

		/* 롤링 배너 */
		.rollingbanner > .wrap {
			position: relative;
			width: auto;
			height: 100%;
			box-sizing: border-box;
			overflow: hidden;
		}

		.rolling ul {
			list-style: none;
		}

		.rollingbanner li {
			position: absolute;
			top: -36px;
			left: 0;
		}

		/* 이전, 현재, 다음 롤링 배너 표시 */
		.rollingbanner li.prev {
			top: 36px;
			transition: top 0.5s ease;
		}

		.rollingbanner li.current {
			top: 0;
			transition: top 0.5s ease;
		}

		.rollingbanner li.next {
			top: -36px;
		}

		.rollingbanner a {
			display: block;
			display: -webkit-box;
			text-decoration: none;
			-webkit-line-clamp: 1;
			-webkit-box-orient: vertical;
			overflow: hidden;
			color: #000;
		}
	</style>

	<script>
		if (navigator.geolocation) { //geolocation이 작동되는지 확인하기
			navigator.geolocation.getCurrentPosition(function(position){
				console.log(position); //콘솔창 확인하기
			});
		}
	</script>
	<!--날씨 openweatherAPI 가져오기-->
	<script>


		const APIKY = ""

		navigator.geolocation.getCurrentPosition(function (position) {

			var lat = position.coords.latitude, // 위도
					lon = position.coords.longitude; // 경도
			console.log(lat);
			console.log(lon);

			const url = 'https://api.openweathermap.org/data/2.5/weather?lat=' + lat + '&lon=' + lon + '&appid=' + APIKY + '&units=metric';
			console.log(url);
			//javascript가 url을 자동으로 연결 시켜줌 직접 url을 눌러서 갈 필요가없음
			fetch(url).then(reponse => reponse.json()).then(data => {
				console.log(data);

				const city = document.querySelector(".next");
				const weather = document.querySelector(".prev");
				const wether = data.weather[0].main;
				const temp = Math.floor(data.main.temp);
				const temp_max = Math.floor(data.main.temp_max);
				const temp_min = Math.floor(data.main.temp_min);
				const weatherIcon = data.weather[0].icon;
				const place = data.name;


				console.log(place);

				weather.innerHTML = '<img src="http://openweathermap.org/img/wn/' + weatherIcon + '@2x.png" width="30px" height="30px"/>' + '&nbsp;&nbsp;';
				+temp + "℃" + '&nbsp;&nbsp;' + wether;
				weather.innerHTML += "최저" + '<span style="color:blue">' + temp_min + "℃" + '</span>' + '&nbsp;&nbsp;' + "/";
				weather.innerHTML += '&nbsp;&nbsp;' + "최고" + '<span style="color:red">' + temp_max + "℃" + '</span>' + '&nbsp;&nbsp;' + place;


			});


		});

	</script>

	<script>
		document.addEventListener('DOMContentLoaded', () => {
			var interval = window.setInterval(rollingCallback, 4000);
		})

		function rollingCallback() {
			//.prev 클래스 삭제
			document.querySelector('.rollingbanner .prev').classList.remove('prev');

			//.current -> .prev
			let current = document.querySelector('.rollingbanner .current');
			current.classList.remove('current');
			current.classList.add('prev');

			//.next -> .current
			let next = document.querySelector('.rollingbanner .next');
			//다음 목록 요소가 널인지 체크
			if (next.nextElementSibling == null) {
				document.querySelector('.rollingbanner ul li:first-child').classList.add('next');
			} else {
				//목록 처음 요소를 다음 요소로 선택
				next.nextElementSibling.classList.add('next');
			}
			next.classList.remove('next');
			next.classList.add('current');
		}
	</script>
</head>
<script type="text/javascript">

</script>

<body>
<header>
	<div class="container">
		<div class="page-banner home-banner">
			<div class="row align-items-center flex-wrap-reverse h-100">
				<div class="col-md-6 py-5 wow fadeInLeft">
					<div class="btn btn-primary " id="weather" style="margin-top: 0px;height: 60px;">
						<div class="rollingbanner ">

							<div class="wrap">
								<ul>
									<li class="current">오늘의 날씨</li>
									<li class="next"></li>
									<li></li>
									<li class="prev"></li>
								</ul>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</header>



</body>
</html>
