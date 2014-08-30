<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
    <meta charset="UTF-8">
    <title>DataGrid with Toolbar - jQuery EasyUI Demo</title>
</head>
<body>
    <h2>DataGrid with Toolbar</h2>
    <p>Put buttons on top toolbar of DataGrid.</p>
    <div style="margin:20px 0;"></div>
    <table class="easyui-datagrid" title="DataGrid with Toolbar" style="width:700px;height:250px"
            data-options="rownumbers:true,singleSelect:true,url:'datagrid_data1.json',method:'get',toolbar:toolbar">
        <thead>
            <tr>
                <th data-options="field:'itemid',width:80">Item ID</th>
                <th data-options="field:'productid',width:100">Product</th>
                <th data-options="field:'listprice',width:80,align:'right'">List Price</th>
                <th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
                <th data-options="field:'attr1',width:240">Attribute</th>
                <th data-options="field:'status',width:60,align:'center'">Status</th>
            </tr>
        </thead>
    </table>
    <script type="text/javascript">
        var toolbar = [{
            text:'Add',
            iconCls:'icon-add',
            handler:function(){alert('add')}
        },{
            text:'Cut',
            iconCls:'icon-cut',
            handler:function(){alert('cut')}
        },'-',{
            text:'Save',
            iconCls:'icon-save',
            handler:function(){alert('save')}
        }];
    </script>
    
    <fieldset style="top:inherit;width:100%">
		<form name="form1" method="post" action="supplierQuery" id="form1">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td">开始时间:</td>
					<td align="left" class="l-table-edit-td"><input name="txtDateBegin" type="text" id="txtDateBegin" ltype="date" />
					</td>
					<td align="right" class="l-table-edit-td">结束时间:</td>
					<td align="left" class="l-table-edit-td"><input name="txtDateEnd" type="text" id="txtDateEnd" ltype="date" />
					</td>
					<td align="right" class="l-table-edit-td">订单状态:</td>
					<td align="left" class="l-table-edit-td"><select name="orderState" id="orderState" ltype="select">
							<option value="审核通过">审核通过</option>
							<option value="审核未通过">审核未通过</option>
							<option value="正在发货">正在发货</option>
							<option value="发货完毕">发货完毕</option>
							<option value="订单关闭">订单关闭</option>
							<option value="全部" selected="selected">全部</option>

					</select></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">订单编号:</td>
					<td align="left" class="l-table-edit-td"><input name="orderId" type="text" id="orderId" /></td>
					<td align="right" class="l-table-edit-td">客户名称:</td>
					<td align="left" class="l-table-edit-td"><input name="clientName" type="text" id="clientName"
						onclick="updateNameData()" /></td>
					<td align="right" class="l-table-edit-td">SCM订货单号:</td>
					<td align="left" class="l-table-edit-td"><input name="erpOrderId" type="text" id="erpOrderId" /></td>
				</tr>
			</table>
			<table>
				<tr>
					<td align="right" class="l-table-edit-td"><input type="button" value="查询" id="Button1" class="l-button"
						style="width:80px" onclick="f_query()" /></td>
					<td align="left" class="l-table-edit-td"><input type="button" value="查询所有" id="Button1" class="l-button"
						style="width:100px" onclick="f_query_all()" /></td>
					<td style="width:100px"></td>
					<td><input type="button" id="excel" class="l-button" value="导出Excel" style="width: 110px" /></td>
					<tr>
        
			</table>
    	</form>
    </fieldset>
    
    
    <!-- 显示查询条件 -->
    <%-- <table width="100%">
		<tr>
			<td colspan="2" height="5"></td>
		</tr>
		<tr>
			<td class="blackbold">
				<img src="${pageContext.request.contextPath}/style/images/arrow6.gif" hspace="5">查询条件
			</td>
		</tr>
		<tr >
			<td colspan="2" height="1" bgcolor="#BBD2E9" ></td>
		</tr>
	</table> --%>
    
    
</body>
</html>