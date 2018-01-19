<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>转报巡检</title>

<link
	href="${rootUrl }js/plugins/jui/themes/redmond/jquery-ui-1.9.2.custom.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${rootUrl }css/main.css" />
<link rel="stylesheet" type="text/css" href="${rootUrl }css/top_b.css" />
<link rel="stylesheet" type="text/css" href="${rootUrl }css/pager.css" />
<link rel="stylesheet" type="text/css"
	href="${rootUrl }js/jquery/themes/default/style.css" />
<link href="${rootUrl }js/plugins/qtip/jquery.qtip.min.css"
	type="text/css" rel="stylesheet" />

<script src="${rootUrl }js/jquery/jquery.js" type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.tablesorter.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.pager.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.manage.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.nyroModal.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.validate.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/datepicker/WdatePicker.js"
	type="text/javascript"></script>

<script src="${rootUrl }js/plugins/jui/jquery-ui-1.9.2.min.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/plugins/qtip/jquery.qtip.pack.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/plugins/html/jquery.outerhtml.js"
	type="text/javascript"></script>
	

<script src="${rootUrl }js/left-tree-menu.js" 
type="text/javascript"></script>

<script type="text/javascript">
	var $tabs;
	$(function() {
		//Tab分页功能实现必须添加的部分
		$tabs = $("#tabs").tabs();
		$("#queryResult").manage({
			pagerUrl : true,
			add : true,
			edit : true,
			update : true,
			view : true,
			remove : true,
			sortable:true,//是否使用页面排序功能
			sortConfig:{  //页面排序的配置
				0: {sorter: "text"}, 
				6: {sorter: false},
				7: {sorter: false}
			},
			//Tab页内操作元素结束后的回调函数
			popupRemoveCallback:function(){
				//loadTask1();
				loadTask('teleGraphCheck');
				loadTask('vgCheck');
			}
		});
		//进入页面 统计不同Tab页待操作的记录条数
		//loadTask1();
		loadTask('teleGraphCheck');
		loadTask('vgCheck');
		//页面内刷新之后, 依然显示当前Tab页,而不是跳转到第一个Tab页
		var index = $('#index').val();//取出点击该Tab页时保存的Tab页序号
		$tabs.tabs('select',parseInt(index));
		
		//通过事件绑定将当前tab页序号保存到页面input元素中,等待刷新页面的时候重新取出该值	
		$("#tabs").tabs({
			select:function(event,ui){
/* 				alert(ui.index);
				alert(ui.panel);
				alert(ui.tab); */
				var index = ui.index;
				$("#index").val(ui.index);
			}
		});
	});
	/*function loadTask1() {
		$.ajax({
					type : "GET",
					url : "${rootUrl}duty/telegraphduty/ajaxLoad.dox",
					dataType : "json",
					success : function(result) {
						$("#take1")
								.append(
										"<div style='position:absolute; top:-13px;right:0px;width:22px;height:22px;background-image: url(${rootUrl}images/remind_bubble_red.png);text-align:center;line-height:21px;font-size:10px;color:yellow'>"
												+ result.totalCount + "</div>");
					}
				}); 
	}*/
	function loadTask(optType) {
		$.ajax({
					type : "GET",
					url : "${rootUrl}duty/telegraphduty/ajaxLoad.dox",
					dataType : "json",
					data : {
						optType : optType
					},
					success : function(result) {
						$("#" + optType)
								.append(
										"<div style='position:absolute; top:-13px;right:0px;width:22px;height:22px;background-image: url(${rootUrl}images/remind_bubble_red.png);text-align:center;line-height:21px;font-size:10px;color:yellow'>"
												+ result.totalCount + "</div>");
					}
				}); 
	}
	//点击某个Tab页时更新所有Tab页中的待处理条数
	function change(optType) {
		$("#optType").val(optType);
		if(optType=='teleGraphCheck'){
		    loadTask('teleGraphCheck');
		    loadTask('vgCheck');
		} else {
			if(optType=='vgCheck'){
			    $('#info1').show();
				loadTask('teleGraphCheck');
			    loadTask('vgCheck');
			} else {
				loadTask('teleGraphCheck');
			    loadTask('vgCheck');
			}
		}
	}
</script>

</head>
<body>
	<!-- LOGO -->
	<%@ include file="/WEB-INF/jsp/top.jsp"%>
	<!-- LOGO -->
	<div id="boxmain">
		<!-- 左侧菜单 -->
		<%@ include file="/WEB-INF/jsp/left.jsp"%>
		<!-- 左侧菜单 -->

		<!-- 内容页 -->
		<div id="boxright">

			<div id="main">
				<!--place-->
				<div id="boxplace">
					<div class="place">
						<span class="bold">转报巡检</span>
					</div>
				</div>
				<!--place-->
				<div class="minfo">
					<div class="bl">
						<div class="br">
							<div class="clear"></div>
							<div id="lmsx">
								<div class="clear"></div>
								<form:form modelAttribute="queryModel" id="listForm"
									name="listForm" action="${rootUrl}duty/telegraphduty/manage.do"
									method="post">
									<!-- 保存所选TAB页 -->
									<form:hidden id="optType" path="optType" />
									<!-- 查询条件 -->
									<div id="tabs" style="padding-bottom: 23px">
										<ul>
											<sec:authorize
												ifAnyGranted="${jspAuthorities['telegraph_check_tab']}">
												<li id="teleGraphCheck" style="position: relative;"><a
													id="queryButton" href="#tabs-1"
													onclick="change('teleGraphCheck');">转报系统检查 </a></li>
											</sec:authorize>
											<li id="vgCheck" style="position: relative;"><a
												id="queryButton" href="#tabs-2" onclick="change('vgCheck');">VG系统检查</a>
											</li>
											<li id="sitaCheck" style="position: relative;"><a
												id="queryButton" href="#tabs-1"
												onclick="change('sitaCheck');">SITA状态检查</a></li>
											<li id="sitaCheck" style="position: relative;"><a
												id="queryButton" href="#tabs-1"
												onclick="change('sitaCheck');">同步异步信道流量统计</a></li>
											<li id="emergencyChannel" style="position: relative;"><a
												id="queryButton" href="#tabs-1"
												onclick="change('emergencyChannel');">应急信道测试</a></li>
										</ul>
										<div>
											<div class="bclear"></div>
											<!-- 查询条件 -->
											<div class="boxbtn">

												<div id="info1" class="info" style="height: 50px">
													填写人：
													<form:input path="creater" size="15" maxlength="50" />
													审核人：
													<form:input path="auditer" size="15" maxlength="50" />
													<div class="bclear"></div>
													填写时间：
													<form:input cssStyle="width:120px" path="fromCreateTime"
														cssClass="inputDate" readonly="true"
														onfocus="WdatePicker({el:$dp.$('fromCreateTime'),dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\\'toCreateTime\\')}'});" />
													<img src="${rootUrl}images/datePicker.gif"
														onclick="WdatePicker({el:$dp.$('fromCreateTime'),dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'toCreateTime\')}'});"
														style="cursor: pointer;" /> 至
													<form:input cssStyle="width:120px" path="toCreateTime"
														cssClass="inputDate" readonly="true"
														onfocus="WdatePicker({el:$dp.$('toCreateTime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\\'fromCreateTime\\')}'});" />
													<img src="${rootUrl}images/datePicker.gif"
														onclick="WdatePicker({el:$dp.$('toCreateTime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'fromCreateTime\')}'});"
														style="cursor: pointer;" />
													<div class="bclear"></div>
												</div>
												<br /> <br /> <a href="#none" class="sbtn"
													id="queryButton">查询</a> <a href="#none" class="sbtn"
													onclick="listForm.reset();">重置</a>
											</div>
											<div class="bclear"></div>
											<!-- 查询条件 -->
											<!--添加按钮-->
											<div id="tabs-1">
												<sec:authorize
													ifAnyGranted="${jspAuthorities['telegraph_check_add']}">
													<div class="boxbtn">
														<a href="${rootUrl}duty/telegraphduty/add.do"
															class="my_add {boxwidth:1000,boxheight:700} btn"><img
															src="${rootUrl}images/btn/new.gif" />添加</a>
													</div>
													<div class="bclear"></div>
												</sec:authorize>
											</div>
											<div id="tabs-2">
												<sec:authorize
													ifAnyGranted="${jspAuthorities['vg_check_add']}">
													<div class="boxbtn">
														<a href="${rootUrl}duty/vgduty/add.do"
															class="my_add {boxwidth:1000,boxheight:700} btn"><img
															src="${rootUrl}images/btn/new.gif" />添加</a>
													</div>
													<div class="bclear"></div>
												</sec:authorize>
											</div>
											<div class="mtab">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0" class="title">
													<tr>
														<td class="td1"><img src="${rootUrl}images/pl1.gif"
															alt="" />转报巡检</td>
														<td class="td2" id="noteInfo"></td>
													</tr>
												</table>
												<!--查询结果-->
												<div id="queryResult"></div>
												<!--查询结果-->
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 内容页 -->
	</div>

	<!-- 版权页 -->
	<%@ include file="/WEB-INF/jsp/bottom.jsp"%>
	<!-- 版权页 -->
</body>
</html>