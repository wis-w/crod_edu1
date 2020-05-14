<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="/WEB-INF/include-head.jsp"%>

<script type="text/javascript">
	// 异步方式运行函数，是由于ajax在浏览器中和主程序使用的不是同一个线程
	$(function(){
		$("#asyncBtn").click(function(){
			console.log("ajax函数之前");
			$.ajax({
				"url":"test/ajax/async.html",
				"type":"post",
				"dataType":"text",
				"async":false,// 修改成同步请求 所有操作在同一个线程内
				"success":function(response){
					console.log("内部"+response);
				}
			});
			console.log("ajax函数之后");
		});
	});
</script>
<body>
	<%@include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<button id = "asyncBtn"> ajax 异步请求</button>
			</div>
		</div>
	</div>

</body>
</html>
