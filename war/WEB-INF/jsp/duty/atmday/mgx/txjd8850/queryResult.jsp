<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<style>
<!--
	table th {
		white-space: nowrap;
	}
	table td{
		white-space: nowrap;
	}
	table{
		empty-cells: show;
		border-collapse: collapse;
		table-layout: auto;
	}
-->
</style>
<script type="text/javascript">
	/* $(function() {
		// 跟踪
		$('.trace').click(graphTrace);
	}); */
	var rootUrl = '${rootUrl }';
</script> 
<div style="width:100%; overflow: scroll;">

	<table id="dataTable"  border="0" cellspacing="0"
		cellpadding="2" class="tablesorter">
	<thead>
			  <tr>
			    <th width="7%" rowspan="2" scope="col">巡检时间</th>
			    <th colspan="3" scope="col">设备运行环境</th>
			    <th colspan="10" scope="col">连通性检查</th>  
			    <th width="8%" rowspan="2" scope="col">创建情况</th>
				<th width="8%" rowspan="2" scope="col">审核情况</th>
			    <th width="12%" rowspan="2" scope="col">操作</th>
			  </tr>
			  <tr>
                <th width="5%" scope="col">风扇</th>
                <th width="5%" scope="col">电源</th>
                <th width="5%" scope="col">温度</th>
               <th width="5%" scope="col">与十里河8850节点的连通性</th>
                <th width="5%" scope="col">与通信基地8830节点的连通性</th>
                <th width="5%" scope="col">民航局</th>
			    <th width="5%" scope="col">首都机场</th>
			    <th width="5%" scope="col">西安</th>
			    <th width="5%" scope="col">广州</th>
			    <th width="5%" scope="col">成都</th>
			    <th width="5%" scope="col">上海</th>
			    <th width="5%" scope="col">沈阳</th>
			    <th width="5%" scope="col">乌鲁木齐</th>  
              </tr>
  </thead>
  <tbody>
			<c:if test="${pagn.totalCount==0}">
				<td colspan="14">记录为空！</td>
			</c:if>
			<c:forEach items="${pagn.result}" var="vo" varStatus="voStatus">
				<%@ include file="row.jsp"%>
			</c:forEach>
		</tbody>
</table>
	<div class="pager" totalcount="${pagn.totalCount }" pagecount="${pagn.pageCount}"></div>
</div>

