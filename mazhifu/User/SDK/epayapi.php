<?php
include("../../LinPay/Common.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>即时到账支付接口</title>
</head>
<?php
/* *
 * 功能：即时到账交易接口接入页
 * 
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
 

/**************************请求参数**************************/

        //商户订单号
        $out_trade_no = $_POST['WIDout_trade_no']?$_POST['WIDout_trade_no']:'LinPay'.rand(10000,99999).date("YmdHis");;
		//付款金额
        $money = $_POST['WIDtotal_fee']?$_POST['WIDtotal_fee']:$_GET['WIDtotal_fee'];
        //必填

		//支付方式
        $type = $_POST['type']?$_POST['type']:$_GET['type'];
		//站点名
        //订单描述

//商品名称
$name = urldecode($_POST['WIDsubject']?$_POST['WIDsubject']:$_GET['WIDsubject']);
if(strpos($name,'测试商品')){
$user_pid  = $userrow['user_pid'];
$user_key  = $userrow['user_key'];
}else{
$row=$DB->query("SELECT * FROM LinPay_User WHERE user_pid='{$conf['user_pid']}' limit 1")->fetch();
if(!$row){
    showmsg('未配置收款用户PID,请至后台配置',1);
}
$user_pid  = $row['user_pid'];
$user_key  = $row['user_key'];
}
if(strstr($name,'购买套餐')){
    $stmt = $DB->prepare("SELECT * FROM LinPay_Package WHERE name = :name");
    $stmt->bindParam(':name', explode('|',$name)[2], PDO::PARAM_INT);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $pack1_row=$DB->query("SELECT * FROM `LinPay_Package_Log` WHERE `user_pid`='{$userrow['user_pid']}' and `Package_id`='{$row['id']}' limit 1")->fetch();
    if($pack1_row['gmcs'] == $row['xgcs']){
        showmsg('已售尽',1);
    }
}
if($name == "购买额度"){
$exchange_rate = 1 / $conf['ed']; // 1元等于100ed

// 用户输入的ed数量
$ed_input = $money;

// 计算换算成元的数量
$money = $ed_input * $exchange_rate;
    $name = $userrow['user_pid'].'|购买额度|'.$money;
}

$notify_url = ($_SERVER['SERVER_PORT'] == '443' ? 'https://' : 'http://').$_SERVER['HTTP_HOST']."/User/SDK/notify_url.php";
$return_url = ($_SERVER['SERVER_PORT'] == '443' ? 'https://' : 'http://').$_SERVER['HTTP_HOST']."/User/SDK/return_url.php";


/************************************************************/

//构造要请求的参数数组，无需改动
$parameter = array(
		"pid" => trim($user_pid),
		"type" => $type,
		"notify_url"	=> $notify_url,
		"return_url"	=> $return_url,
		"out_trade_no"	=> $out_trade_no,
		"name"	=> $name,
		"money"	=> $money,
		"sitename"	=> $sitename
);

//建立请求
$html_text = submit_pay($parameter,'GET',$user_key);
echo $html_text;

?>

</body>
</html>