<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<script src="${rootUrl }js/datepicker/WdatePicker.js"
	type="text/javascript"></script>
<div style="margin: 10px 20px 0px; padding: 0px">
	<dl class="title">
		<dd>通信基地TES网管工作站连通性--更新</dd>
	</dl>
</div>
<table width="100%" border="0">
	<tr>
		<th colspan="2" scope="col">检查内容</th>
		<th scope="col">检查结果</th>
		<th scope="col">评判标准</th>
		<th scope="col">检查方法</th>
	</tr>
	<tr>
		<th rowspan="4" scope="col">网管工作站进程</th>
		<th scope="col">081丢包率(%)</th>
		<th scope="col"><input name="packetLoss081" id="packetLoss081"
			value="${vo.packetLoss081 }" /></th>
		<th rowspan="4" scope="col">0%</th>
		<th rowspan="4" scope="col">UCX窗口Ping四台工作站10个数据包。081/281/381/481IP地址分别为172.16.100.8（21/31/41）</th>
	</tr>
	<tr>
		<th scope="col">281丢包率(%)</th>
		<th scope="col"><input name="packetLoss281" id="packetLoss281"
			value="${vo.packetLoss281 }" /></th>
	</tr>
	<tr>
		<th scope="col">381丢包率(%)</th>
		<th scope="col"><input name="packetLoss381" id="packetLoss381"
			value="${vo.packetLoss381 }" /></th>
	</tr>
	<tr>
		<th scope="col">481丢包率(%)</th>
		<th scope="col"><input name="packetLoss481" id="packetLoss481"
			value="${vo.packetLoss481 }" /></th>
	</tr>
	<tr>
		<td>审核备注</td>
		<td colspan="4">${vo.auditRemark}</td>
	</tr>
</table>

<div class="boxbtn">
	<c:if test="${!view}">
		<div class="btn">
			<a href="#none" id="submit">保存</a>
		</div>
	</c:if>
	<div class="btn">
		<a href="#none" class="close">取消</a>
	</div>
</div>