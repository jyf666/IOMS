<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<meta http-equiv="expires" content="0" />
<link rel="stylesheet" type="text/css" href="${rootUrl}css/window.css" />
<link rel="stylesheet" type="text/css" href="${rootUrl }css/main.css" />
<link rel="stylesheet" type="text/css" href="${rootUrl }css/top_b.css" />
<link rel="stylesheet" type="text/css" href="${rootUrl }css/pager.css" />
<link rel="stylesheet" type="text/css"
	href="${rootUrl }js/jqueryui/themes/jquery-ui.css" />

<script src="${rootUrl }js/jquery/jquery.js" type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${rootUrl }js/jqueryui/jquery-ui.js" type="text/javascript"></script>
<script src="${rootUrl }js/autocompleteExtends.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.validate.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/messages_cn.js" type="text/javascript"></script>
<script src="${rootUrl }js/datepicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/jquery/jquery.validate.manage.js"
	type="text/javascript"></script>
<script src="${rootUrl }js/datepicker/WdatePicker.js"
	type="text/javascript"></script>
<style type="text/css">
select {
	width: 80px;
}
</style>
<script type="text/javascript">
	$(function() {
		$.ajaxSetup({
			cache : false
		});
		$("#auditForm").validate({
			rules : {
				auditRemark : {
					required : true,
					maxlength : 100
				},
			},
			submitHandler : function(form) {
				$("#auditForm").ajaxSubmit({
					beforeSubmit : validateEditForm,
					dataType : "json",
					success : function(data, status, xhr) {
						if (data.success == true) {
							alert("审核成功!")
							parent.$.nyroModalRemove();
							window.top.location.reload();
						} else {
							alert("审核失败，请检查!")
							parent.$.nyroModalRemove();
							window.top.location.reload();
						}
					}
				})
			},
			errorPlacement : function(error, element) {
				error.appendTo(element.parent("td"));
			}
		});
		function validateEditForm(formData, jqForm, options) {
			return $("#auditForm").valid();
		}
		$("#submit").click(function() {
			$("#auditForm").trigger("submit");
		});
		$(".close").click(function() {
			parent.$.nyroModalRemove();
		});

	});
</script>
</head>
<body>
	<div class="window">
		<dl class="title">
			<dd>通信基地TES网管进程状态审核</dd>
			<dt>
				<a href="#none" class="close"><img
					src="${rootUrl }images/close.png" alt="关闭" /> </a>
			</dt>
		</dl>
		<div
			style="float: right; padding-right: 70px; color: blue; padding-bottom: 5px;"></div>
		<div class="mtab">
			<form:form modelAttribute="pageModel" id="auditForm" name="auditForm"
				action="${rootUrl}duty/satellite/day/tes/audit.do" method="post">
				<input type="hidden" id="id" name="id" value="${vo.id }" />
				<input type="hidden" id="optType" name="optType" value="${optType }" />
				<%@include file="detail.jsp"%>
				<br />
			</form:form>
		</div>
	</div>
</body>
</html>