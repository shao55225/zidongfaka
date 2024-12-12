<?php
include("../../LinPay/Common.php");
/* *
 * 功能：支付异步通知页面
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


 *************************页面功能说明*************************
 * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
 * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
 * 该页面调试工具请使用写文本函数logResult，该函数已被默认关闭，见alipay_notify_class.php中的函数verifyNotify
 */
//商品名称
$name = $_GET['name'];
$money = $_GET['money'];
$srow=$DB->query("SELECT * FROM LinPay_Order WHERE `trade_no`='{$_GET['trade_no']}' limit 1")->fetch();
if(strpos($name,'测试商品')){
$row=$DB->query("SELECT * FROM LinPay_User WHERE user_pid='{$srow['user_pid']}' limit 1")->fetch();
$user_pid  = $row['user_pid'];
$user_key  = $row['user_key'];
}else{
$row=$DB->query("SELECT * FROM LinPay_User WHERE user_pid='{$conf['user_pid']}' limit 1")->fetch();
$user_pid  = $row['user_pid'];
$user_key  = $row['user_key'];
}
$notify_url = ($_SERVER['SERVER_PORT'] == '443' ? 'https://' : 'http://').$_SERVER['HTTP_HOST']."/User/SDK/notify_url.php";
$return_url = ($_SERVER['SERVER_PORT'] == '443' ? 'https://' : 'http://').$_SERVER['HTTP_HOST']."/User/SDK/return_url.php";

//计算得出通知验证结果
$verify_result = verifyNotify($user_key);
if($verify_result) {//验证成功
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//请在这里加上商户的业务逻辑程序代

	
	//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
	
    //获取支付宝的通知返回参数，可参考技术文档中服务器异步通知参数列表
	
	//商户订单号

	$out_trade_no = $_GET['out_trade_no'];

	//支付交易号

	$trade_no = $_GET['trade_no'];

	//交易状态
	$trade_status = $_GET['trade_status'];

	//支付方式
	$type = $_GET['type'];


if ($_GET['trade_status'] == 'TRADE_SUCCESS') {
    if(strpos($name,'购买套餐')){
        $user_pid = explode('|',$name)[0];
        $pack_name = explode('|',$name)[2];
        $pack_row=$DB->query("SELECT * FROM `LinPay_Package` WHERE `name`='{$pack_name}' limit 1")->fetch();
        $pack1_row=$DB->query("SELECT * FROM `LinPay_Package_Log` WHERE `user_pid`='{$user_pid}' and `Package_id`='{$pack_row['id']}' limit 1")->fetch();
        $user_row=$DB->query("SELECT * FROM `LinPay_User` WHERE `user_pid`='{$user_pid}' limit 1")->fetch();
        //计算已购次数
        if($pack1_row['gmcs'] != $pack_row['xgcs'] | $pack1_row['gmcs'] < $pack_row['xgcs']){
        //判断· 是否手动访问页面以达到重复购买
    if($srow['trade_no'] != $pack1_row['trade_no']){
        //计算额度
        $money = $user_row['money'] + $pack_row['quota'];
        
        // 计算微信时间
        if(empty($user_row['vip_time']) | strtotime($user_row['vip_time']) < time()){
            $wxpay_time = strtotime(date('Y-m-d H:i:s',strtotime("+".$pack_row['vip_time']." day")));
            $wxpay_time = date('Y-m-d H:i:s',$wxpay_time);
        }else{
            $wxpay_time = strtotime($user_row['vip_time'])-time();
            $wxpay_time = strtotime(date('Y-m-d H:i:s',strtotime("+".$pack_row['vip_time']." day")))+$wxpay_time;
            $wxpay_time = date('Y-m-d H:i:s',$wxpay_time);
        }

        $DB->exec("update `LinPay_User` set `vip_time`='{$wxpay_time}' where user_pid='{$user_pid}'");
        $DB->exec("update `LinPay_User` set `money`='{$money}' where user_pid='{$user_pid}'");
        if(!$pack1_row){
        $DB->exec("insert into `LinPay_Package_Log` (`Package_id`,`trade_no`,`user_pid`,`gmcs`,`ip`) values ('".$pack_row['id']."','".$srow['trade_no']."','".$user_pid."','1','".$ip."')");
        }else{
            $DB->exec("update `LinPay_Package_Log` set `gmcs`='".($pack1_row['gmcs']+1)."',`trade_no`='".$srow['trade_no']."',`ip`='".$ip."' where user_pid='{$user_pid}'");
        }
    }
        }
    }
    if(strpos($name,'购买额度')){
        $user_pid = explode('|',$name)[0];
        $userrow=$DB->query("SELECT * FROM LinPay_User WHERE `user_pid`='{$user_pid}' limit 1")->fetch();
        $ed = explode('|',$name)[2] * $conf['ed'];
        $ed = bcadd($userrow['money'],$ed);
        $DB->exec("update `LinPay_User` set `money`='{$ed}' where user_pid='{$user_pid}'");
        echo $ed;
    }
}

	//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
        
	echo "success";		//请不要修改或删除
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
else {
    //验证失败
    echo "fail";
}
?>