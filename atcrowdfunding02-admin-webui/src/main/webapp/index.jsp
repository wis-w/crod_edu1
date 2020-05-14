<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hello</title>
<!-- http://localhost:8080/atcrowdfunding02-admin-webui/test/ssm.html -->
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<!-- 引入jq -->
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script><!-- 由于layer是基于js，所以需要放到后引用 -->
<script type="text/javascript">
	$(function(){
		$("#btn1").click(function(){
			$.ajax({
				"url": "send/array/one.html",		//请求地址
				"type": "post",					//请求方式
				"data": {
					"array": [5,8,12]
				},								//请求数据
				"dataType": "text",				//如何对待服务端返回的数据
				"success": function(response){	//成功的方案	response响应体数据
					alert(response);
				},

				"error" : function(response) {	//失败的方案
					alert(response);
				}
			});
		});
	});
	
	$(function(){
		$("#btn2").click(function(){
			$.ajax({
				"url": "send/array/two.html",		//请求地址
				"type": "post",					//请求方式
				"data": {
					"array[0]": 5,
					"array[1]": 8,
					"array[2]": 12
				},								//请求数据
				"dataType": "text",				//如何对待服务端返回的数据
				"success": function(response){	//成功的方案	response响应体数据
					alert(response);
				},

				"error" : function(response) {	//失败的方案
					alert(response);
				}
			});
		});
	});
	
	$(function(){
		$("#btn3").click(function(){
			// 准备好发送的数组
			var array = [5,8,12];
			// 将json数组转换成json字符串  "['5','8','12']"
			var requestBody = JSON.stringify(array);
			console.log(requestBody);
			$.ajax({
				"url": "send/array/three.html",		//请求地址
				"type": "post",					//请求方式
				"contentType": "application/json;charset=UTF-8",// 告诉服务器这个json数据，字符集utf8
				"data": requestBody,			//请求数据
				"dataType": "text",				//如何对待服务端返回的数据
				"success": function(response){	//成功的方案	response响应体数据
					alert(response);
				},

				"error" : function(response) {	//失败的方案
					alert(response);
				}
			});
		});
	});
	
	$(function(){
		$("#btn4").click(function(){
			// 先准备需要发送的数据
			var stuent = {
					"stuId": 5,
					"stuName": "tom",
					"address":{
						"province": "广东",
						"city": "深圳",
						"street": "后台"
					},
					"subject":[
						{
							"subjectName": "javaSE",
							"subjectScore": 100
						},{
							"subjectName": "SSM",
							"subjectScore": 100
						}
					],
					"map":{
						"k1": "v1",
						"k2": "v2"
					}
			};
			//将对象转换成json字符串
			var requestBody = JSON.stringify(stuent);
			//发送ajax请求
			$.ajax({
				"url": "send/compose/object.json",		//请求地址
				"type": "post",					//请求方式
				"contentType": "application/json;charset=UTF-8",// 告诉服务器这个json数据，字符集utf8
				"data": requestBody,			//请求数据
				"dataType": "json",				//如何对待服务端返回的数据
				"success": function(response){	//成功的方案	response响应体数据
					console.log(response);
				},

				"error" : function(response) {	//失败的方案
					console.log(response);
				}
			});
		});
		
		$("#btn5").click(function(){
			layer.msg("layer弹窗");
		});
	});
	
</script>
</head>
<body>
	<a href="test/ssm.html">测试ssm的整合环境</a>
	<br/>
	
	<button id="btn1">send [5,6,4] One</button>
	<br/>
	
	<button id="btn2">send [5,6,4] Two</button>
	
	<br/>
	<button id="btn3">send [5,6,4] Three</button>
	
	<br/>
	<button id="btn4">send compose Object</button>
	
	<br/>
	<button id="btn5">弹窗啦</button>
	
	<br/>
	<a href="admin/do/login/page.html"><h2>跳转到主页了</h2></a>
	
	
</body>
</html>