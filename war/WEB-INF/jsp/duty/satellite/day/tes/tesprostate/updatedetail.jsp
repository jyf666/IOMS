<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<script src="${rootUrl }js/datepicker/WdatePicker.js"
	type="text/javascript"></script>
<div style="margin: 10px 20px 0px; padding: 0px">
	<dl class="title">
		<dd>通信基地TES网管进程状态--更新</dd>
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
		<th scope="col">081</th>
		<th scope="col"><c:choose>
				<c:when test="${vo.PROCESS_STATE081 eq 'normal'}">
					<input type="radio" name="PROCESS_STATE081" id="PROCESS_STATE081"
						value="normal" checked="checked" /> 正常
					 <input type="radio" name="PROCESS_STATE081" id="PROCESS_STATE081"
						value="unnormal" /> 异常
				</c:when>
				<c:otherwise>
					<input type="radio" name="PROCESS_STATE081" id="PROCESS_STATE081"
						value="normal" /> 正常
					 <input type="radio" name="PROCESS_STATE081" id="PROCESS_STATE081"
						value="unnormal" checked="checked" /> 异常
				</c:otherwise>
			</c:choose></th>
		<th rowspan="4" scope="col">ALL NCP PROCESSES ARE OPEARTIONAL</th>
		<th rowspan="4" scope="col">DEC Term 中使用Check proc 命令</th>
	</tr>
	<tr>
		<th scope="col">281</th>
		<th scope="col"><c:choose>
				<c:when test="${vo.PROCESS_STATE281 eq 'normal'}">
					<input type="radio" name="PROCESS_STATE281" id="PROCESS_STATE281"
						value="normal" checked="checked" /> 正常
					 <input type="radio" name="PROCESS_STATE281" id="PROCESS_STATE281"
						value="unnormal" /> 异常
				</c:when>
				<c:otherwise>
					<input type="radio" name="PROCESS_STATE281" id="PROCESS_STATE281"
						value="normal" /> 正常
					 <input type="radio" name="PROCESS_STATE281" id="PROCESS_STATE281"
						value="unnormal" checked="checked" /> 异常
				</c:otherwise>
			</c:choose></th>
	</tr>
	<tr>
		<th scope="col">381</th>
		<th scope="col"><c:choose>
				<c:when test="${vo.PROCESS_STATE381 eq 'normal'}">
					<input type="radio" name="PROCESS_STATE381" id="PROCESS_STATE381"
						value="normal" checked="checked" /> 正常
					 <input type="radio" name="PROCESS_STATE381" id="PROCESS_STATE381"
						value="unnormal" /> 异常
				</c:when>
				<c:otherwise>
					<input type="radio" name="PROCESS_STATE381" id="PROCESS_STATE381"
						value="normal" /> 正常
					 <input type="radio" name="PROCESS_STATE381" id="PROCESS_STATE381"
						value="unnormal" checked="checked" /> 异常
				</c:otherwise>
			</c:choose></th>
	</tr>
	<tr>
		<th scope="col">481</th>
		<th scope="col"><c:choose>
				<c:when test="${vo.PROCESS_STATE481 eq 'normal'}">
					<input type="radio" name="PROCESS_STATE481" id="PROCESS_STATE481"
						value="normal" checked="checked" /> 正常
						 <input type="radio" name="PROCESS_STATE481" id="PROCESS_STATE481"
						value="unnormal" /> 异常
					</c:when>
				<c:otherwise>
					<input type="radio" name="PROCESS_STATE481" id="PROCESS_STATE481"
						value="normal" /> 正常
						 <input type="radio" name="PROCESS_STATE481" id="PROCESS_STATE481"
						value="unnormal" checked="checked" /> 异常
					</c:otherwise>
			</c:choose></th>
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
