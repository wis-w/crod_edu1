<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
	$(function(){
		
		initPagination();// 函数调用，对页面的导航条进行初始化
		
	});
	
	function initPagination(){
		
		// 获取总记录数
		var totalRecord = ${requestScope.pageInfo.total};
		
		// 声明json对象存储设置属性
		var properties = {
				num_edge_entries: 3, //边缘页数
				num_display_entries: 5, //主体页数
				callback: pageSelectCallback,// 翻页的回调函数
				items_per_page:${requestScope.pageInfo.pageSize}, //每页显示1项
				current_page:${requestScope.pageInfo.pageNum - 1},// 当前页码
				prev_text:"上一页",
				next_text:"下一页"
		};
		
		// 生成页码导航条
		$("#Pagination").pagination(totalRecord,properties);
	}
	
	// 用户点击1，2，3 调用该函数跳转
	// 回调函数的含义，声明出来交给系统或者框架调用
	function pageSelectCallback(pageIndex,jQuery){
		// 根据pageIndex计算得到pageNum
		var pageNum = pageIndex + 1;
		// 跳转到pageNum页面
		window.location.href = "admin/get/page.html?pageNum="+pageNum+"&keyWord=${param.keyWord}";
		// 由于每一个页码都是超链接，所以取消超链接的默认行为
		return false;
	}
	
</script>
<body>
	<%@include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form action="admin/get/page.html" method="post" class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input name="keyWord" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<a class="btn btn-primary" style="float: right;" href="admin/to/admin/add.html">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</a>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th>创建时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty requestScope.pageInfo.list }">
										<tr>
											<td colspan="6" align="center">抱歉没有查询到您要的数据</td>
										</tr>
									</c:if>

									<c:if test="${!empty requestScope.pageInfo.list }">
										<c:forEach items="${requestScope.pageInfo.list }" var="admin" varStatus="myStatus">
											<tr>
												<td>${myStatus.count }</td>
												<td><input type="checkbox"></td>
												<td>${admin.loginAcct }</td>
												<td>${admin.userName }</td>
												<td>${admin.email }</td>
												<td>${admin.createTime }</td>
												<td>
													<!-- 查詢 -->
													<button type="button" class="btn btn-success btn-xs">
														<i class=" glyphicon glyphicon-check"></i>
													</button>
													<!-- 修改 -->
													<a href="admin/to/edit/page.html?adminId=${admin.id }&pageNum=${requestScope.pageInfo.pageNum }&keyWord=${param.keyWord }" class="btn btn-primary btn-xs">
														<i class=" glyphicon glyphicon-pencil"></i>
													</a>
													<!-- 刪除 -->
													<a href="admin/remove/${admin.id }/${requestScope.pageInfo.pageNum }/${param.keyWord }.html" class="btn btn-danger btn-xs">
														<i class=" glyphicon glyphicon-remove"></i>
													</a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
