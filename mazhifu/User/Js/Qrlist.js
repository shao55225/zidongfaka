//添加通道
function Add_Qrlist(){
    var ii = layer.load(1, {
        shade: [0.1, '#fff']
    });
    var type = document.getElementById('type').value;
    var td_type = document.getElementById('td_type').value;
    var qr_url = document.getElementById('qr_url').value;
    var yd_url = document.getElementById('yd_url').value;
    var beizhu = document.getElementById('beizhu').value;
    var alipay_f2fid = document.getElementById('alipay_f2fid').value;
    var alipay_f2fkey = document.getElementById('alipay_f2fkey').value;
    var alipay_f2fpubilc = document.getElementById('alipay_f2fpubilc').value;
    $.ajax({
        type : "POST",
        url : "Ajax.php",
        data : {'act':'Add_Qrlist',type,td_type,qr_url,beizhu,alipay_f2fid,alipay_f2fpubilc,alipay_f2fkey,yd_url},
        dataType : 'json',
        timeout:10000,
        success : function(data) {
            if(data.code==1){
            layer.close(ii);
            layer.alert(data.msg,{icon : 'success'});
                setTimeout(function () {
                    location.href="?";
                }, 1000); //延时1秒跳转
            }else{
            layer.close(ii);
            layer.alert(data.msg,{icon : 'error'});
            }
        },
        error:function(data){
            layer.msg('服务器错误',{icon : 'error'});
        }
    });
}

//修改用户
function Edit_Qrlist(id){
    var ii = layer.load(1, {
        shade: [0.1, '#fff']
    });
    var qr_url = document.getElementById('qr_url').value;
    var beizhu = document.getElementById('beizhu').value;
    $.ajax({
        type : "POST",
        url : "Ajax.php",
        data : {'act':'Edit_Qrlist',id,qr_url,beizhu},
        dataType : 'json',
        timeout:10000,
        success : function(data) {
            if(data.code==1){
                layer.close(ii);
                layer.alert(data.msg,{icon : 'success'});
                setTimeout(function () {
                    location.href="?";
                }, 1000); //延时1秒跳转
            }else if(data.code == -1){
                layer.close(ii);
                layer.alert(data.msg,{icon : 'error'});
            }
        },
        error:function(data){
            layer.msg('服务器错误',{icon : 'error'});
        }
    });
}


function handlePaste(e) {
    e.preventDefault();
    console.log('粘贴操作已被禁止');
}

//删除用户
function Del_Qrlist(id){
          document.addEventListener('copy', function(e) {
            e.preventDefault();
            console.log('复制操作已被禁止');
          });
        var confirmobj = layer.confirm('<label class="form-label" for="user"><font color="red">此操作会造成不可逆后果!</font><br>请输入:我已知晓风险</label><br><input placeholder="我已知晓风险" id="qr" class="form-control" onpaste="handlePaste(event);" type="text">', {
                btn: ['确定', '取消']
            },
            function() {
                var ii = layer.load(1, {
                    shade: [0.1, '#fff']
                });
            var qr = $("#qr").val();
            if(qr !== "我已知晓风险"){
                layer.close(ii);
                            layer.alert('输入错误，请重新输入！',{icon : 'error'});
            }else{
                $.ajax({
                    type: 'POST',
                    url : "Ajax.php",
                    data: {
                        'act':'Del_Qrlist',
                        id
                    },
                    dataType: 'json',
                    success: function(data) {
                        layer.close(ii);
                        if (data.code == 1) {
                            layer.alert(data.msg, {
                                    icon: 'success'
                                },
                                function() {
                                    location.href = "?";
                                });
                        } else {
                            layer.alert(data.msg,{icon : 'error'});
                        }
                    },
                    error: function(data) {
                        layer.msg('服务器错误');
                        return false;
                    }
                });
            }
            },
            function() {
                layer.close(confirmobj);
            });
}

$(document).ready(function() {
    var type = $("#type").val();
    $('.picurl > input').bind('focus mouseover',
        function() {
            if (this.value) {
                this.select()
            }
        });
    $("input[type='file']").change(function(e) {
        $('#qr_url').val('解码中');
        Upload(this.files)
    });
});

function Upload() {
    var ii = layer.load(1, {
        shade: [0.1, '#fff']
    });
    var file = document.getElementById("imgfile").files[0];
    var formData = new FormData();
    formData.append('image_field', file);
    $.ajax({
        url: "Ajax.php?act=Qrcode_Url",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        mimeType: "multipart/form-data",
        dataType: 'json',
        success: function(data) {
            if (data.code == 1) {
                layer.close(ii);
                $('#qr_url').val(data.qrcode);
                layer.msg(data.msg,{icon: 'success'});
            } else {
                layer.close(ii);
                layer.msg(data.msg);
                setTimeout(function() {
                        location.href = "?";
                    },
                    3000); //延时1秒跳转
            }
        },
    });
}
function Up_MCK(qr_id){
    var ii = layer.load(1, {
        shade: [0.1, '#fff']
    });
    $.ajax({
        url: "Ajax.php",
        type: "POST",
        data: {
            'act':'Up_MCK',
            qr_id,
        },
        dataType: 'json',
        success: function(data) {
            if (data.code == 1) {
                layer.close(ii);
                layer.alert(data.msg,{icon: 'success'});
                setTimeout(function() {
                        location.href = "?";
                    },
                    1000); //延时1秒跳转
            } else {
                layer.close(ii);
                layer.alert(data.msg,{icon:'error'});
            }
        },
        error: function(data) {
            layer.msg('服务器错误');
            return false;
        }
    });
}
function Check_Login(qr_id,return_id){
    var ii = layer.load(1, {
        shade: [0.1, '#fff']
    });
    $.ajax({
        url: "Ajax.php",
        type: "POST",
        data: {
            'act':'Check_Login',
            qr_id,
            return_id
        },
        dataType: 'json',
        success: function(data) {
            if (data.code == 1) {
                layer.close(ii);
                layer.alert(data.msg,{icon: 'success'});
                setTimeout(function() {
                        location.href = "?";
                    },
                    1000); //延时1秒跳转
            } else {
                layer.close(ii);
                layer.alert(data.msg,{icon:'error'});
            }
        },
        error: function(data) {
            layer.msg('服务器错误');
            return false;
        }
    });
}
$("select[name='td_type']").change(function(){
	var type = $(this).val();
	if(type=='alipay_mck'){
	$("#alipay_mcksr").css('display','inherit');
    }else{
    $("#alipay_mcksr").css('display','none');
    }
	if(type=='wxpay_uos' || type=='qqpay_yd' || type=='wxpay_apad' || type=='wxpay_ipad'){
	$("#yd_xy").css('display','inherit');
    }else{
    $("#yd_xy").css('display','none');
    }
    if(type === 'qqpay_yd'){
        document.getElementById('basic-bq').textContent = 'QQ号';
    }else if(type === 'wxpay_pc'){
        document.getElementById('basic-bq').textContent = '微信昵称';
    }else{
        document.getElementById('basic-bq').textContent = '备注';
    }

    if(type === 'alipay_mck'){
        document.getElementById('viptime_text').textContent = '免CK配置教程';
        document.getElementById('viptime_text').href = 'https://www.kancloud.cn/yzcw1314/linpay/3243915';
    }else if(type === 'wxpay_pc'){
        document.getElementById('viptime_text').textContent = 'PC自挂配置教程';
        document.getElementById('viptime_text').href = 'https://www.kancloud.cn/yzcw1314/linpay/3243916';
    }
});