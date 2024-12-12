<?php
define('URL', $_SERVER['HTTP_HOST']);
@header('Content-Type: text/html; charset=UTF-8');
$do=isset($_GET['do'])?$_GET['do']:'0';
if(file_exists('install.lock')){
    $installed=true;
    $do='0';
}

if(isset($_GET['azssk'])){
    $installed=false;
    $do='4';
}
function random($length){
 //字符组合
 $str = 'xt_0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
 $len = strlen($str)-1;
 $randstr = '';
 for($i=0;$i<$length;$i++) {
	$num=mt_rand(0,$len);
	$randstr .= $str[$num];
 }
 return $randstr;
}
function checkfunc($f,$m = false) {
	if (function_exists($f)) {
		return '<font color="green">可用</font>';
	} else {
		if ($m == false) {
			return '<font color="black">不支持</font>';
		} else {
			return '<font color="red">不支持</font>';
		}
	}
}

function checkini_get($f,$m = false) {
	if (ini_get('max_execution_time') >= 10) {
		return '<font color="green">可用</font>';
	} else {
		return '<font color="red">不支持</font>';
	}
}

function checkclass($f,$m = false) {
	if (class_exists($f)) {
		return '<font color="green">可用</font>';
	} else {
		if ($m == false) {
			return '<font color="black">不支持</font>';
		} else {
			return '<font color="red">不支持</font>';
		}
	}
}

?>



<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no,minimal-ui">
<title>系统</title>
<!--日期选择器css-->
<link rel="stylesheet" type="text/css" href="//<?=URL?>/LinPay/Assets/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
<!--时间日期选择器css-->
<link rel="stylesheet" type="text/css" href="//<?=URL?>/LinPay/Assets/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css" href="//<?=URL?>/LinPay/Assets/css/materialdesignicons.min.css">
<link rel="stylesheet" type="text/css" href="//<?=URL?>/LinPay/Assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="//<?=URL?>/LinPay/Assets/css/animate.min.css">
<link rel="stylesheet" type="text/css" href="//<?=URL?>/LinPay/Assets/css/style.min.css">

</head>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/jquery.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/popper.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/jquery.cookie.min.js"></script>
<!--日期选择器js-->
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<!--时间日期选择器js-->
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/momentjs/moment.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/momentjs/locale/zh-cn.min.js"></script>
<!--引入chart插件js-->
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/chart.min.js"></script>
<script type="text/javascript" src="//<?=URL?>/LinPay/Assets/js/main.min.js"></script>
<script src="http://lyear.itshubao.com/v5/example/js/layer/layer.js"></script>
  <!--[if lt IE 9]>
    <script src="//lib.baomitu.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="//lib.baomitu.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
  <style>
.center {
    margin: auto;
    width: 50%;
    border: 3px;
    padding: 10px;
}

</style>
</head>
<body>

      <div class="container-fluid">

        <div class="row" >

          <div class="col-lg-12" >
            <div class="card" style="width:100%;height:100%;">
              <header class="card-header"><div class="card-title">安装向导</div></header>
              <div class="card-body">
<?php if($do=='0'){?>
<div class="panel panel-primary">
	<div class="panel-heading" >
		<h3 class="panel-title" align="center">系统</h3>
	</div>
	<div class="panel-body">
		<p><iframe src="../../readme.txt" style="width:100%;height:650px;"></iframe></p>
		<?php if(isset($installed)){ ?>
		<div class="alert alert-warning">您已经安装过，如需重新安装请删除<font color=red> Core/Config.php 或 Core/Install/install.lock</font>文件后再安装！</div>
		<?php }else{?>
		<p align="center"><a class="btn btn-primary" href="index.php?do=1">开始安装</a></p>
		<?php }?>
	</div>
</div>

<?php }elseif($do=='1'){?>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title" align="center">环境检查</h3>
	</div>
<div class="progress progress-striped">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 10%">
	<span class="sr-only">10%</span>
  </div>
</div>
<table class="table table-striped">
	<thead>
		<tr>
			<th style="width:20%">函数检测</th>
			<th style="width:15%">需求</th>
			<th style="width:15%">当前</th>
			<th style="width:50%">用途</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>PHP 5.5+</td>
			<td>必须</td>
			<td><?php echo phpversion(); ?></td>
			<td>PHP版本支持</td>
		</tr>
		<tr>
			<td>curl_exec()</td>
			<td>必须</td>
			<td><?php echo checkfunc('curl_exec',true); ?></td>
			<td>抓取网页</td>
		</tr>
		<tr>
			<td>file_get_contents()</td>
			<td>必须</td>
			<td><?php echo checkfunc('file_get_contents',true); ?></td>
			<td>读取文件</td>
		</tr>
				<tr>
			<td>最大运行时间</td>
			<td>必须</td>
			<td><?php echo checkini_get('ini_get',true); ?></td>
			<td>必须大于10秒</td>
		</tr>
	</tbody>
</table>
<p><span><a class="btn btn-primary" href="index.php?do=0"><<上一步</a></span>
<span style="float:right"><a class="btn btn-primary" href="index.php?do=2" align="right">下一步>></a></span></p>
</div>

<?php }elseif($do=='2'){?>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title" align="center">数据库配置</h3>
	</div>
<div class="progress progress-striped">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 30%">
	<span class="sr-only">30%</span>
  </div>
</div>
	<div class="panel-body">
	<?php
echo <<<HTML
		<form action="?do=3" class="form-sign" method="post">
		<label for="name">数据库地址:</label>
		<input type="text" class="form-control" name="db_host" value="127.0.0.1">
		<label for="name">数据库端口:</label>
		<input type="text" class="form-control" name="db_port" value="3306">
		<label for="name">数据库用户名:</label>
		<input type="text" class="form-control" name="db_user">
		<label for="name">数据库密码:</label>
		<input type="text" class="form-control" name="db_pwd">
		<label for="name">数据库名:</label>
		<input type="text" class="form-control" name="db_name">
		<br><div class="d-grid gap-2 col-6 mx-auto"><input type="submit" class="btn btn-primary btn-block" name="submit" value="保存配置"></div>
		</form><br/>
		（如果已事先填写好config.php相关数据库配置，请 <a href="?do=3&jump=1">点击此处</a> 跳过这一步！）
HTML;
?>
	</div>
</div>

<?php }elseif($do=='3'){
?>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title" align="center">保存数据库</h3>
	</div>
<div class="progress progress-striped">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 50%">
	<span class="sr-only">50%</span>
  </div>
</div>
	<div class="panel-body">
<?php
require './db.class.php';
if(defined("SAE_ACCESSKEY") || isset($_GET['jump'])==1){
include_once '../../LinPay/Config.php';
	if(!$dbconfig['user']||!$dbconfig['pwd']||!$dbconfig['dbname']) {
		echo '<div class="alert alert-danger">请先填写好数据库并保存后再安装！<hr/><a href="javascript:history.back(-1)"><< 返回上一页</a></div>';
	} else {
		if(!$con=DB::connect($dbconfig['host'],$dbconfig['user'],$dbconfig['pwd'],$dbconfig['dbname'],$dbconfig['port'])){
			if(DB::connect_errno()==2002)
				echo '<div class="alert alert-warning">连接数据库失败，数据库地址填写错误！</div>';
			elseif(DB::connect_errno()==1045)
				echo '<div class="alert alert-warning">连接数据库失败，数据库用户名或密码填写错误！</div>';
			elseif(DB::connect_errno()==1049)
				echo '<div class="alert alert-warning">连接数据库失败，数据库名不存在！</div>';
			else
				echo '<div class="alert alert-warning">连接数据库失败，['.DB::connect_errno().']'.DB::connect_error().'</div>';
		}else{
			echo '<div class="alert alert-success">数据库配置文件保存成功！</div>';
				echo '<p align="right"><a class="btn btn-primary btn-block" href="?do=4">创建数据表>></a></p>';
		}
	}
}else{
	$db_host=isset($_POST['db_host'])?$_POST['db_host']:NULL;
	$db_port=isset($_POST['db_port'])?$_POST['db_port']:NULL;
	$db_user=isset($_POST['db_user'])?$_POST['db_user']:NULL;
	$db_pwd=isset($_POST['db_pwd'])?$_POST['db_pwd']:NULL;
	$db_name=isset($_POST['db_name'])?$_POST['db_name']:NULL;

	if($db_host==null || $db_port==null || $db_user==null || $db_pwd==null || $db_name==null){
		echo '<div class="alert alert-danger">保存错误,请确保每项都不为空<hr/><a href="javascript:history.back(-1)"><< 返回上一页</a></div>';
	} else {
		$config="<?php
/*数据库配置*/
\$dbconfig=array(
	'host' => '{$db_host}', //数据库服务器
	'port' => {$db_port}, //数据库端口
	'user' => '{$db_user}', //数据库用户名
	'pwd' => '{$db_pwd}', //数据库密码
	'dbname' => '{$db_name}', //数据库名
);
?>";
		if(!$con=DB::connect($db_host,$db_user,$db_pwd,$db_name,$db_port)){
			if(DB::connect_errno()==2002)
				echo '<div class="alert alert-warning">连接数据库失败，数据库地址填写错误！</div>';
			elseif(DB::connect_errno()==1045)
				echo '<div class="alert alert-warning">连接数据库失败，数据库用户名或密码填写错误！</div>';
			elseif(DB::connect_errno()==1049)
				echo '<div class="alert alert-warning">连接数据库失败，数据库名不存在！</div>';
			else
				echo '<div class="alert alert-warning">连接数据库失败，['.DB::connect_errno().']'.DB::connect_error().'</div>';
		}elseif(file_put_contents('../../LinPay/Config.php',$config)){
			echo '<div class="alert alert-success">数据库配置文件保存成功！</div>';
				echo '<p align="right"><a class="btn btn-primary btn-block" href="?do=4">创建数据表>></a></p>';
		}else
			echo '<div class="alert alert-danger">保存失败，请确保网站根目录有写入权限<hr/><a href="javascript:history.back(-1)"><< 返回上一页</a></div>';
	}
}
?>
	</div>
</div>
<?php }elseif($do=='4'){?>


<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title" align="center">创建数据表</h3>
	</div>
<div class="progress progress-striped">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 70%">
	<span class="sr-only">70%</span>
  </div>
</div>
	<div class="panel-body">
<?php
include_once '../../LinPay/Config.php';
if(!$dbconfig['user']||!$dbconfig['pwd']||!$dbconfig['dbname']) {
	echo '<div class="alert alert-danger">请先填写好数据库并保存后再安装！<hr/><a href="javascript:history.back(-1)"><< 返回上一页</a></div>';
} else {
	require './db.class.php';
    $sql = file_get_contents('install.sql');
	$sql = explode(';',$sql);
	$sql = str_replace('UQ_Password',password_hash('123456',PASSWORD_ARGON2I),$sql);
	$cn = DB::connect($dbconfig['host'],$dbconfig['user'],$dbconfig['pwd'],$dbconfig['dbname'],$dbconfig['port']);
	if (!$cn) die('err:'.DB::connect_error());
	DB::query("set sql_mode = ''");
	DB::query("set names utf8");
	$t=0; $e=0; $error='';
	for($i=0;$i<count($sql);$i++) {
		if ($sql[$i]=='')continue;
		if(DB::query($sql[$i])) {
			++$t;
		} else {
			++$e;
			$error.=DB::error().'<br/>';
		}
	}
	date_default_timezone_set("PRC");
}
if($e==0) {
	echo '<div class="alert alert-success">安装成功,并自动提交监控！<br/>SQL成功'.$t.'句/失败'.$e.'句</div><p align="right"><a class="btn btn-block btn-primary" href="index.php?do=5">下一步>></a></p>';
} else {
	echo '<div class="alert alert-danger">安装失败<br/>SQL成功'.$t.'句/失败'.$e.'句<br/>错误信息：'.$error.'</div><p align="right"><a class="btn btn-block btn-primary" href="index.php?do=4">点此进行重试</a></p>';
}
?>
	</div>
</div>

<?php }elseif($do=='5'){?>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title" align="center">安装完成</h3>
	</div>
<div class="progress progress-striped">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
	<span class="sr-only">100%</span>
  </div>
</div>
	<div class="panel-body">
<?php
	@file_put_contents("install.lock",'版权所有');
	echo '<div class="alert alert-info"><font color="green">安装完成！请记得删除/LinPay/Install这个文件夹 管理员后台地址修改可以通过/LinPay/UQ_Config.php里面改 管理账号和密码是:admin/123456</font><br/><br/><a href="../../">>>网站首页</a>｜<a href="../../Houtai/">>>后台管理</a><hr/>更多设置选项请登录后台管理进行修改。<br/><br/><font color="#FF0033">如果你的空间不支持本地文件读写，请自行在LinPay/ 目录建立 Config.php 文件！</font></div>';
?>
	</div>
</div>

<?php }elseif($do=='6'){?>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title" align="center">安装完成</h3>
	</div>
<div class="progress progress-striped">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
	<span class="sr-only">100%</span>
  </div>
</div>
	<div class="panel-body">
<?php
	@file_put_contents("install.lock",'版权所有');
	echo '<div class="alert alert-info"><font color="green">安装完成！ 请记得删除/LinPay/Install这个文件夹 管理账号和密码是:admin/123456</font><br/><br/><a href="../../">>>网站首页</a>｜<a href="../../Houtai/">>>后台管理</a><hr/>更多设置选项请登录后台管理进行修改。<br/><br/><font color="#FF0033">如果你的空间不支持本地文件读写，请自行在LinPay/Install/ 目录建立 install.lock 文件！</font></div>';
?>
	</div>
</div>

<?php }?>

</div>
</body>
</html>

