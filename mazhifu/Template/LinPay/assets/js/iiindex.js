
/*ctrl+u*/
document.onkeydown=function(){if(event.ctrlKey&&window.event.keyCode==85){new Vue({data:function(){this.$notify({title:"嘿！别瞎按",message:"老弟，想偷我源码？~",position:'bottom-right',offset:50,showClose:true,type:"error"});return{visible:false}}})
return false;}
/*f 12*/
if(window.event&&window.event.keyCode==123){event.keyCode=0;event.returnValue=false;new Vue({data:function(){this.$notify({title:"嘿！Bingo~",message:"老弟，试试 Alt+Shift+Fn+F4",position:'bottom-right',offset:50,showClose:true,type:"error"});return{visible:false}}})
return false;}
/*ctrl+s*/
if(event.ctrlKey&&window.event.keyCode==83){new Vue({data:function(){this.$notify({title:"嘿！你瞧瞧你",message:"网页得换方法保存哦~",position:'bottom-right',offset:50,showClose:true,type:"error"});return{visible:false}}})
return false;}
/*ctrl+shift+i*/
if((event.ctrlKey)&&(event.shiftKey)&&(event.keyCode==73)){new Vue({data:function(){this.$notify({title:"嘿！哈哈哈",message:"老弟，调试方法也得换换哟~",position:'bottom-right',offset:50,showClose:true,type:"error"});return{visible:false}}})
return false;}
/*f5*/
if(window.event&&window.event.keyCode==116){event.keyCode=0;event.returnValue=false;new Vue({data:function(){this.$notify({title:"嘿！喂喂喂",message:"浏览器自带刷新按钮不香吗？",position:'bottom-right',offset:50,showClose:true,type:"warning"});return{visible:false}}})
return false;}}
/*复制tips*/
document.addEventListener("copy",function(e){new Vue({data:function(){this.$notify({title:"叮！复制成功",message:"若要转载必须保留本站原文链接！",position:'bottom-right',offset:50,showClose:true,type:"success"});return{visible:false}}})})
/* 禁用右键菜单并提醒 */
document.oncontextmenu = function (){new Vue({data:function(){this.$notify({title:"嘿！没有右键菜单",message:"复制请用键盘快捷键 Ctrl+C",position:'bottom-right',offset:50,showClose:true,type:"warning"});return{visible:false}}})
return false;}
