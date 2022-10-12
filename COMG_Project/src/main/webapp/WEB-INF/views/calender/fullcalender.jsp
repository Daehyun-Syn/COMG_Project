<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="kopo.poly.dto.ScheduleDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	List<ScheduleDTO> eList = (List<ScheduleDTO>) request.getAttribute("eList");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<
	<script src="/js/jquery-3.6.0.min.js"></script>
	<!-- 풀캘린더 -->
	<script src='/js/fullcal/main.js'></script>
	<script src='/js/fullcal/locales-all.js'></script>
	<link href='/js/fullcal/main.css' rel='stylesheet' />
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			let calendarEl = document.getElementById('calendar');
			let calendar = new FullCalendar.Calendar(calendarEl, {
				locale: 'ko',
				headerToolbar: {
					left: 'title',
					center: '',
					right: 'prev,next,today'
				},
				height: 'auto',
				selectable: true,
				weekends: true,
				events: [
					<%
						   for(ScheduleDTO eDTO : eList) {
							   String id = String.valueOf(eDTO.getAssignmentRoomSeq());
							   String title = eDTO.getAssignmentRoomName();
							   String start = eDTO.getAssignmentRegDate();
							   String end = eDTO.getAssignmentDeadLine();
							   %>
					{
						id : '<%=id%>',
						title  : '<%=title%>',
						start  : '<%=start%>',
						end    : '<%=end%>'
					},

					<%
						   }
					   %>
				],
			});
			calendar.render();
		});
	</script>

	<style>
		.calendar * {
			border: solid 1px black;
		}

		#kakaoLogin {
			border: 0;
			outline: 0;
		}

		/* 월화수목금 */
		.fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
			color: black;
		}

		.fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
			color: black;
		}

		/* 토요일 */
		.fc-day-sat .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
			color: blue;
		}

		.fc-day-sat .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
			color: blue;
		}

		/* 일요일 */
		.fc-day-sun .fc-scrollgrid-sync-inner .fc-col-header-cell-cushion {
			color: red;
		}

		.fc-day-sun .fc-daygrid-day-frame .fc-daygrid-day-top .fc-daygrid-day-number {
			color: red;
		}
	</style>
</head>
<body>
<div id="calendar"></div>

</body>

</html>