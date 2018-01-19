<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<table id="dataTable" width="100%" border="0" cellspacing="0"
	cellpadding="2" class="tablesorter">
	<thead>
		<tr>
			<th width="5%">序号</th>
			<th width="5%">区域</th>
			<th width="5%">站点名称</th>
			<th width="5%">站点代码</th>
			<th width="5%">站点类型</th>
			<!-- <th width="5%">天线代码</th>
			<th width="5%">ODU名称</th> -->
			<th width="5%">ODU类型</th>
			<th width="5%">ODU功率</th>
			<th width="5%">中频发射固定/可调衰减值</th>
			<th width="5%">中频接收固定/可调衰减值</th>
			<th width="5%">发射增益/衰减</th>
			<th width="5%">接收增益/衰减</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagn.result}" var="vo" varStatus="voStatus">
			<%@ include file="row.jsp"%>
		</c:forEach>
	</tbody>
</table>
<div class="pager" totalcount="${pagn.totalCount }"
	pagecount="${pagn.pageCount}"></div>


