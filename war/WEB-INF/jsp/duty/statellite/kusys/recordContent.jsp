<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<script src="${rootUrl }js/datepicker/WdatePicker.js"
	type="text/javascript"></script>
<div style="margin: 10px 20px 0px; padding: 0px">
	<dl class="title">
		<c:if test="${checkType eq 'MONTH' }">
			<dd>通信基地KU网管系统维护月检--添加</dd>
		</c:if>
		<c:if test="${checkType eq 'QUARTER' }">
			<dd>通信基地KU网管系统维护季检--添加</dd>
		</c:if>
		<c:if test="${checkType eq 'YEAR' }">
			<dd>通信基地KU网管系统维护年检--添加</dd>
		</c:if>

	</dl>
</div>
<table width="100%" border="1">
	<tr>
		<th colspan="4" scope="col">通信基地KU网管系统维护</th>
	</tr>
	<tr>
		<th width="17%" scope="row">序号</th>
		<td width="34%">检查内容</td>
		<td width="29%">结果记录</td>
		<td width="20%">备注</td>
	</tr>
	<tr>
		<th rowspan="5" scope="row">1</th>
		<td>子网1网管中作站状态</td>
		<td><label> <input type="radio" name="net1Workstate"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net1Workstate"
				value="INNORMAL" /> 异常
		</label></td>
		<td rowspan="5"><form:input path="subnetWorkRemark" /></td>
	</tr>
	<tr>
		<td>子网2网管中作站状态</td>
		<td><label> <input type="radio" name="net2Workstate"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net2Workstate"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>子网3网管中作站状态</td>
		<td><label> <input type="radio" name="net3Workstate"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net3Workstate"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>子网4网管中作站状态</td>
		<td><label> <input type="radio" name="net4Workstate"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net4Workstate"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>帧中继网管中作站状态</td>
		<td><label> <input type="radio" name="netzWorkstate"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="netzWorkstate"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<th rowspan="6" scope="row">2</th>
		<td>子网1网管工作站数据备份至磁盘</td>
		<td><label> <input type="radio" name="net1WorkstateBf"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net1WorkstateBf"
				value="INNORMAL" /> 异常
		</label></td>
		<td rowspan="5"><form:input path="subnetDataRemark" /></td>
	</tr>
	<tr>
		<td>子网2网管工作站数据备份至磁盘</td>
		<td><label> <input type="radio" name="net2WorkstateBf"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net2WorkstateBf"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>子网3网管工作站数据备份至磁盘</td>
		<td><label> <input type="radio" name="net3WorkstateBf"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net3WorkstateBf"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>子网4网管工作站数据备份至磁盘</td>
		<td><label> <input type="radio" name="net4WorkstateBf"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="net4WorkstateBf"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>帧中继网网管工作站数据备份至磁盘</td>
		<td><label> <input type="radio" name="netzWorkstateBf"
				value="NORMAL" checked="checked" /> 正常
		</label> <label> <input type="radio" name="netzWorkstateBf"
				value="INNORMAL" /> 异常
		</label></td>
	</tr>
	<tr>
		<td>备份磁盘存放位置</td>
		<td><input type="text" name="tapeLocate" /></td>
		<td><form:input path="tapeLocateRemark" /></td>
	</tr>
	<tr>
		<th scope="row">3</th>
		<td>网管工作站除尘</td>
		<td><label> <input type="radio" name="workDust"
				value="COMPLETE" checked="checked" /> 完成
		</label> <label> <input type="radio" name="workDust"
				value="NOCOMPLETE" /> 未完成
		</label></td>
		<td><form:input path="workDustRemark" /></td>
	</tr>
	<tr>
		<th scope="row">4</th>
		<td>转发器底噪电平（切空图查看）</td>
		<td><form:input path="forward" />dBm</td>
		<td><form:input path="forwardRemark" /></td>
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