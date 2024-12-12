<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <%@ include file="../_meta.jsp" %>
  <script type="text/javascript">
  	function loadData(id) {
  		$("#online_fuhao_count_" + id).html("加载中...");
  		$("#tixiancount_" + id).html("加载中...");
  		$("#allshouru_" + id).html("加载中...");
  		$("#jintianshou_" + id).html("加载中...");
  		$("#jintianzhi_" + id).html("加载中...");
  		$("#yesshou_" + id).html("加载中...");
  		$("#ketixian_" + id).html("加载中...");
  		$("#chenggonglv_" + id).html("加载中...");
  		$("#mid_" + id).html("加载中...");
  		$("#onehour_" + id).html("加载中...");
  		$("#hourshou_" + id).html("加载中...");
  		$("#shifenshou_" + id).html("加载中...");
  		$("#shifending_" + id).html("加载中...");
  		$("#thound_" + id).html("加载中...");
  		$("#online_count_" + id).html("加载中...");
  		$("#allzhichu_" + id).html("加载中...");
  		$("#jieyu_" + id).html("加载中...");
  		$("#Bufaordercount_" + id).html("加载中...");
  		$.get("${ctx}/tianhao/load/" + id, function(data){
  			$("#online_fuhao_count_" + id).html(data.online_fuhao_count);
  			$("#tixiancount_" + id).html(data.tixiancount);
  			$("#allshouru_" + id).html(data.allshouru);
  			$("#jintianshou_" + id).html(data.jintianshou);
  			$("#jintianzhi_" + id).html(data.jintianzhi);
  			$("#yesshou_" + id).html(data.yesshou);
  			$("#ketixian_" + id).html(data.ketixian);
  			$("#chenggonglv_" + id).html(data.chenggonglv);
  			$("#mid_" + id).html(data.mid);
  			$("#onehour_" + id).html(data.onehour);
  			$("#hourshou_" + id).html(data.hourshou);
  			$("#shifenshou_" + id).html(data.shifenshou);
  			$("#shifending_" + id).html(data.shifending);
  			$("#thound_" + id).html(data.thound);
  			$("#online_count_" + id).html(data.online_count);
  			$("#allzhichu_" + id).html(data.allzhichu);
  			$("#jieyu_" + id).html(data.jieyu);
  			$("#Bufaordercount_" + id).html(data.Bufaordercount);
  		});
  	}
  </script>
</head>
<body>
<div class="am-cf admin-main">
	<div class="am-cf am-padding">
		<div class="am-fl am-cf">
 			<small>位置</small>：
 			<small>订单中心</small>&nbsp;/&nbsp;
 			<small>数据统计</small>
 		</div>
	</div>
	
	<div class="am-g" style="font-size:12px;">
		<div class="am-u-sm-12 am-scrollable-horizontal">
			<table class="am-table am-table-striped am-table-hover am-table-striped am-text-nowrap">
				<thead>
					<tr>
						<th>序号</th>
						<th>昵称</th>
						<th>接口地址</th>
						<th>在线副号</th>
						<th>可提现</th>
						<th>总收</th>
						<th>今收</th>
						<th>今提</th>
						<th>昨收</th>
						<th>可提现</th>
						<th>百笔成功率</th>
						<th>500笔成功率</th>
						<th>千笔成功率</th>
						<th>一小时收</th>
						<th>10分钟收</th>
						<th>10分钟笔数/成功数</th>
						<th>扫码成功率</th>
						<th>在线主号</th>
						<th>总支出</th>
						<th>利润</th>
						<th>补发金额</th>
						<th class="table-set">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${items}" var="t" varStatus="i">
					<tr>
						<td>${i.index + 1}</td>
						<td>${t.nickname}</td>
						<td>${t.url}</td>
						<td id="online_fuhao_count_${t.id}">加载中...</td>
						<td id="tixiancount_${t.id}">加载中...</td>
						<td id="allshouru_${t.id}">加载中...</td>
						<td id="jintianshou_${t.id}">加载中...</td>
						<td id="jintianzhi_${t.id}">加载中...</td>
						<td id="yesshou_${t.id}">加载中...</td>
						<td id="ketixian_${t.id}">加载中...</td>
						<td id="chenggonglv_${t.id}">加载中...</td>
						<td id="mid_${t.id}">加载中...</td>
						<td id="onehour_${t.id}">加载中...</td>
						<td id="hourshou_${t.id}">加载中...</td>
						<td id="shifenshou_${t.id}">加载中...</td>
						<td id="shifending_${t.id}">加载中...</td>
						<td id="thound_${t.id}">加载中...</td>
						<td id="online_count_${t.id}">加载中...</td>
						<td id="allzhichu_${t.id}">加载中...</td>
						<td id="jieyu_${t.id}">加载中...</td>
						<td id="Bufaordercount_${t.id}">加载中...</td>
						<td>
							<script type="text/javascript">
							loadData(${t.id});
							</script>
							<a href="javascript:loadData(${t.id});" class="am-btn am-btn-secondary am-btn-xs">刷新</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
	</div>
</div>
</body>
</html>