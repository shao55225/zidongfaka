package com.xunpay.money.utils;

import com.alibaba.fastjson.JSONObject;
import com.xunpay.money.model.Coke;
import com.xunpay.money.model.CompanyAgentOrder;
import com.xunpay.money.utils.util.TencentCrawler;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CuccCard {
    private static final Logger logger = Logger.getLogger(CuccCard.class);

    /**
     * 下单
     */
    public static void topUp(CompanyAgentOrder companyAgentOrder, String ticketNew, int i) {
        String ip = "";
        Integer prot = null;
        Coke coke = Coke.dao.findFirst("select * from coke");
        try {
            //String getip = HttpClientFactory.get("http://api.wandoudl.com/api/ip?app_key=3f2cb2a6af6b0c0925d528c0132283bc&pack=0&num=1&xy=1&type=1&lb=&mr=1&");
            String getip = HttpClientFactory.get("http://t.11jsq.com/index.php/api/entry?method=proxyServer.generate_api_url&packid=1&fa=0&fetch_key=&groupid=0&qty=1&time=1&pro=&city=&port=1&format=txt&ss=1&css=&dt=1&specialTxt=3&specialJson=&usertype=16");
            String[] ips = getip.split(":");
            ip = ips[0];
            prot = Integer.parseInt(ips[1]);
            Map param = new HashMap();
            //String url = "http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeCheck.action";
            String url = "http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeCheck.action";

            param.put("commonBean.phoneNo", companyAgentOrder.getRechargeNo());
            param.put("rechargeBean.cardPwd", companyAgentOrder.getCardPwd());
            param.put("ticketNew", ticketNew);

            String cokes = "";
            if (coke != null) {
                cokes = coke.getCoke();
            }
            String rest = HttpClientHelperCuccCard.postStart(url, param, cokes, ip, prot);
            System.out.println(rest);
            Map state = JSONObject.parseObject(rest);
            if ("success".equals(state.get("out"))) {
                String postUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeConfirm";
                Map paramPost = new HashMap();
                paramPost.put("secstate.state", state.get("secstate"));
                String restPost = "";
                try {
                    restPost = HttpClientHelperCuccCard.postStart(postUrl, paramPost, cokes, ip, prot);
                } catch (SocketTimeoutException e) {
                    System.out.println(e);
                    companyAgentOrder.setFailedCode("F009");
                    companyAgentOrder.setRemark("其他原因");
                    companyAgentOrder.setStatus(Constant.UNKNOWN);
                }catch (Exception e){
                    System.out.println(e);
                    companyAgentOrder.setFailedCode("F009");
                    companyAgentOrder.setRemark("其他原因");
                    companyAgentOrder.setStatus(Constant.UNKNOWN);
                }
                //System.out.println(restPost);
                Document document = Jsoup.parse(restPost);
                Elements er = document.select("div[class='errormessage']");
                Elements success = document.select("ul[class='infor-list-success'] li");
                if ("".equals(er) && !"".equals(success)) {
                    String[] s = success.text().split(" ");
                    String actualAmount = s[1].substring(s[1].indexOf("：") + 1).replace("元", "");//实际金额
                    String cuccOrder = s[4]; //联通订单号
                    String time = s[2].substring(s[2].indexOf("：") + 1) + " " + s[3]; //完成时间
                    companyAgentOrder.setActualAmount(new BigDecimal(actualAmount));
                    companyAgentOrder.setCredentials(cuccOrder);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    companyAgentOrder.setTopTime(simpleDateFormat.parse(time));
                    if (!actualAmount.equals(companyAgentOrder.getAmount())) {
                        companyAgentOrder.setStatus(Constant.UNCONFIRMED);
                    } else {
                        companyAgentOrder.setStatus(Constant.SUCCESS);
                    }

                } else {
                    if (er.text().indexOf("充值卡密码有误") > 0) {
                        companyAgentOrder.setFailedCode("F004");
                        companyAgentOrder.setRemark("充值卡密码错误");
                    } else if (er.text().indexOf("充值卡状态已失效") > 0) {
                        companyAgentOrder.setFailedCode("F003");
                        companyAgentOrder.setRemark("充值卡状态已失效");
                    } else if (er.text().indexOf("充值卡状态有误") > 0) {
                        companyAgentOrder.setFailedCode("F002");
                        companyAgentOrder.setRemark("充值卡状态有误");
                    } else if (er.text() == null || "".equals(er.text())) {
                        companyAgentOrder.setRemark("未确认");
                    } else {
                        companyAgentOrder.setRemark(er.text());
                    }
                    System.out.println(er.text());
                    companyAgentOrder.setTopTime(new Date());
                    companyAgentOrder.setStatus(Constant.FAILED);

                }
            } else if (state.get("out").equals("验证码校验异常!")) {
                //companyAgentOrder.setRemark("系统异常");
                /*if (coke == null) {
                    companyAgentOrder.setFailedCode("F010");
                    companyAgentOrder.setRemark("充值失败,可再次提交");
                    companyAgentOrder.setStatus(Constant.FAILED);
                } else {

                }*/
                throw new NullPointerException();

            } else if (state.get("out").toString().indexOf("请正确输入手机号码") > 0) {
                companyAgentOrder.setFailedCode("F009");
                companyAgentOrder.setRemark("其他原因");
                companyAgentOrder.setStatus(Constant.FAILED);
            } else if (state.get("out").toString().indexOf("无法使用该业务") > 0) {
                companyAgentOrder.setFailedCode("F001");
                companyAgentOrder.setRemark("请求号码无法使用该业务");
                companyAgentOrder.setStatus(Constant.FAILED);
            } else if (state.get("out").toString().indexOf("号码不存在") > 0) {
                companyAgentOrder.setFailedCode("F005");
                companyAgentOrder.setRemark("请求号码不存在");
                companyAgentOrder.setStatus(Constant.FAILED);
            } else if (state.get("out").toString().indexOf("操作太频繁") > 0) {
                throw new NullPointerException();
            } else {
                logger.info(state.get("out"));
                companyAgentOrder.setFailedCode("F009");
                companyAgentOrder.setRemark("其他原因");
                companyAgentOrder.setStatus(Constant.FAILED);
            }
            if(companyAgentOrder.getTopTime()==null){
                companyAgentOrder.setTopTime(new Date());
            }
            companyAgentOrder.update();//更新数据
            /***  发送通知 */
            if (companyAgentOrder.getNotifyUrl() != null && !"".equals(companyAgentOrder.getNotifyUrl())) {
                CardNotify.notifys(companyAgentOrder);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            if (coke != null) {
                Coke.dao.deleteById(coke.getId());
            }
            if (i < 1) {
                i++;
                topUp2(companyAgentOrder, TencentCrawler.getNewTicket(ip, prot), i);
                return;
            } else {
                companyAgentOrder.setFailedCode("F010");
                companyAgentOrder.setRemark("充值失败,可再次提交");
                companyAgentOrder.setStatus(Constant.FAILED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            companyAgentOrder.setFailedCode("F010");
            companyAgentOrder.setRemark("充值失败,可再次提交");
            companyAgentOrder.setStatus(Constant.FAILED);
        } finally {

        }

    }


    public static void topUp2(CompanyAgentOrder companyAgentOrder, String ticketNew, int i) {
        String ip = "";
        Integer prot = null;
        Coke coke = Coke.dao.findFirst("select * from coke");
        try {
            //String getip = HttpClientFactory.get("http://api.wandoudl.com/api/ip?app_key=3f2cb2a6af6b0c0925d528c0132283bc&pack=0&num=1&xy=1&type=1&lb=&mr=1&");
            String getip = HttpClientFactory.get("http://t.11jsq.com/index.php/api/entry?method=proxyServer.generate_api_url&packid=1&fa=0&fetch_key=&groupid=0&qty=1&time=1&pro=&city=&port=1&format=txt&ss=1&css=&dt=1&specialTxt=3&specialJson=&usertype=16");
            String[] ips = getip.split(":");
            ip = ips[0];
            prot = Integer.parseInt(ips[1]);
            Map param = new HashMap();
            //String url = "http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeCheck.action";
            String url = "http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeCheck.action";

            param.put("commonBean.phoneNo", companyAgentOrder.getRechargeNo());
            param.put("rechargeBean.cardPwd", companyAgentOrder.getCardPwd());
            param.put("ticketNew", ticketNew);

            String cokes = "";
            if (coke != null) {
                cokes = coke.getCoke();
            }
            String rest = HttpClientHelperCuccCard.postStart(url, param, cokes, ip, prot);
            System.out.println(rest);
            Map state = JSONObject.parseObject(rest);
            if ("success".equals(state.get("out"))) {
                String postUrl = "http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeConfirm";
                Map paramPost = new HashMap();
                paramPost.put("secstate.state", state.get("secstate"));
                String restPost = "";
                try {
                    restPost = HttpClientHelperCuccCard.postStart(postUrl, paramPost, cokes, ip, prot);
                } catch (SocketTimeoutException e) {
                    System.out.println(e);
                    companyAgentOrder.setFailedCode("F009");
                    companyAgentOrder.setRemark("其他原因");
                    companyAgentOrder.setStatus(Constant.UNKNOWN);
                }catch (Exception e){
                    System.out.println(e);
                    companyAgentOrder.setFailedCode("F009");
                    companyAgentOrder.setRemark("其他原因");
                    companyAgentOrder.setStatus(Constant.UNKNOWN);
                }
                //System.out.println(restPost);
                Document document = Jsoup.parse(restPost);
                Elements er = document.select("div[class='errormessage']");
                Elements success = document.select("ul[class='infor-list-success'] li");
                if ("".equals(er) && !"".equals(success)) {
                    String[] s = success.text().split(" ");
                    String actualAmount = s[1].substring(s[1].indexOf("：") + 1).replace("元", "");//实际金额
                    String cuccOrder = s[4]; //联通订单号
                    String time = s[2].substring(s[2].indexOf("：") + 1) + " " + s[3]; //完成时间
                    companyAgentOrder.setActualAmount(new BigDecimal(actualAmount));
                    companyAgentOrder.setCredentials(cuccOrder);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    companyAgentOrder.setTopTime(simpleDateFormat.parse(time));
                    if (!actualAmount.equals(companyAgentOrder.getAmount())) {
                        companyAgentOrder.setStatus(Constant.UNCONFIRMED);
                    } else {
                        companyAgentOrder.setStatus(Constant.SUCCESS);
                    }

                } else {
                    if (er.text().indexOf("充值卡密码有误") > 0) {
                        companyAgentOrder.setFailedCode("F004");
                        companyAgentOrder.setRemark("充值卡密码错误");
                    } else if (er.text().indexOf("充值卡状态已失效") > 0) {
                        companyAgentOrder.setFailedCode("F003");
                        companyAgentOrder.setRemark("充值卡状态已失效");
                    } else if (er.text().indexOf("充值卡状态有误") > 0) {
                        companyAgentOrder.setFailedCode("F002");
                        companyAgentOrder.setRemark("充值卡状态有误");
                    } else if (er.text() == null || "".equals(er.text())) {
                        companyAgentOrder.setRemark("未确认");
                    } else {
                        companyAgentOrder.setRemark(er.text());
                    }
                    System.out.println(er.text());
                    companyAgentOrder.setTopTime(new Date());
                    companyAgentOrder.setStatus(Constant.FAILED);

                }
            } else if (state.get("out").equals("验证码校验异常!")) {
                //companyAgentOrder.setRemark("系统异常");
                /*if (coke == null) {
                    companyAgentOrder.setFailedCode("F010");
                    companyAgentOrder.setRemark("充值失败,可再次提交");
                    companyAgentOrder.setStatus(Constant.FAILED);
                } else {

                }*/
                throw new NullPointerException();

            } else if (state.get("out").toString().indexOf("请正确输入手机号码") > 0) {
                companyAgentOrder.setFailedCode("F009");
                companyAgentOrder.setRemark("其他原因");
                companyAgentOrder.setStatus(Constant.FAILED);
            } else if (state.get("out").toString().indexOf("无法使用该业务") > 0) {
                companyAgentOrder.setFailedCode("F001");
                companyAgentOrder.setRemark("请求号码无法使用该业务");
                companyAgentOrder.setStatus(Constant.FAILED);
            } else if (state.get("out").toString().indexOf("号码不存在") > 0) {
                companyAgentOrder.setFailedCode("F005");
                companyAgentOrder.setRemark("请求号码不存在");
                companyAgentOrder.setStatus(Constant.FAILED);
            } else if (state.get("out").toString().indexOf("操作太频繁") > 0) {
                throw new NullPointerException();
            } else {
                logger.info(state.get("out"));
                companyAgentOrder.setFailedCode("F009");
                companyAgentOrder.setRemark("其他原因");
                companyAgentOrder.setStatus(Constant.FAILED);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            if (coke != null) {
                Coke.dao.deleteById(coke.getId());
            }
            if (i < 1) {
                i++;
                topUp(companyAgentOrder, TencentCrawler.getNewTicket(ip, prot), i);
                return;
            } else {
                companyAgentOrder.setFailedCode("F010");
                companyAgentOrder.setRemark("充值失败,可再次提交");
                companyAgentOrder.setStatus(Constant.FAILED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            companyAgentOrder.setFailedCode("F010");
            companyAgentOrder.setRemark("充值失败,可再次提交");
            companyAgentOrder.setStatus(Constant.FAILED);
        } finally {
            if(companyAgentOrder.getTopTime()==null){
                companyAgentOrder.setTopTime(new Date());
            }
            companyAgentOrder.update();//更新数据
            /***  发送通知 */
            if (companyAgentOrder.getNotifyUrl() != null && !"".equals(companyAgentOrder.getNotifyUrl())) {
                CardNotify.notifys(companyAgentOrder);
            }
        }

    }


    public static void main(String[] arg) throws IOException {

        /*String cookie = HttpClientHelperCuccCard.getCookie("http://upay.10010.com/npfwap/NpfMob/needCode?channelType=307&_=1592557545419");
        System.out.println(cookie);
       *//* Map param=new HashMap();
        String url="http://upay.10010.com/npfwap/NpfMob/mobWapReCharge/wapRechargeCheck.action";
        param.put("commonBean.phoneNo","17501609934");
        param.put("rechargeBean.cardPwd","9805779255973804412");
        param.put("ticketNew", TencentCrawler.getNewTicket("",0));
        String rest = HttpClientHelperCuccCard.postStart(url, param, "upay_user=b0fa54ae9f8c9e682dc36510b04e9e2c");
        System.out.println(rest);*/
        int i = 1;
        System.out.println(i < 1);
    }


}
