package com.xunpay.money.alipay;

import java.io.IOException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.AlipayParser;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayRequestWrapper;
import com.alipay.api.AlipayResponse;
import com.alipay.api.AlipayUploadRequest;
import com.alipay.api.BatchAlipayRequest;
import com.alipay.api.BatchAlipayResponse;
import com.alipay.api.Decryptor;
import com.alipay.api.Encryptor;
import com.alipay.api.FileItem;
import com.alipay.api.ResponseEncryptItem;
import com.alipay.api.SignChecker;
import com.alipay.api.SignItem;
import com.alipay.api.Signer;
import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.alipay.api.internal.parser.xml.ObjectXmlParser;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.internal.util.AlipayLogger;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.AlipayUtils;
import com.alipay.api.internal.util.RequestParametersHolder;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.internal.util.WebUtils;
import com.alipay.api.internal.util.json.JSONWriter;
import com.alipay.api.response.AlipayFundCouponOrderPagePayResponse;

/**
 *
 * @author liuqun.lq
 * @version $Id: AbstractAlipayClient.java, v 0.1 2018-07-03 10:45:21 liuqun.lq Exp $
 */
public abstract class AbstractAlipayClient implements AlipayClient {

    private String serverUrl;
    private String appId;
    private String prodCode;
    private String format         = AlipayConstants.FORMAT_JSON;
    private String signType       = AlipayConstants.SIGN_TYPE_RSA;
    private String encryptType    = AlipayConstants.ENCRYPT_TYPE_AES;

    private String charset;

    private int    connectTimeout = 3000;
    private int    readTimeout    = 15000;

    private String proxyHost;
    private int    proxyPort;

    static {
        //�����ȫ����
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");
    }

    /** ����APIĬ�Ϸָ��� **/
    private static final String BATCH_API_DEFAULT_SPLIT = "#S#";

    public AbstractAlipayClient(String serverUrl, String appId, String format,
                                String charset, String signType) {
        this.serverUrl = serverUrl;
        this.appId = appId;
        if (!StringUtils.isEmpty(format)) {
            this.format = format;
        }
        this.charset = charset;
        if (!StringUtils.isEmpty(signType)) {
            this.signType = signType;
        }
    }

    public AbstractAlipayClient(String serverUrl, String appId, String format,
                                String charset, String signType, String proxyHost,
                                int proxyPort) {
        this(serverUrl, appId, format, charset, signType);
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public AbstractAlipayClient(String serverUrl, String appId, String format,
                                String charset, String signType, String encryptType) {
        this(serverUrl, appId, format, charset, signType);
        if (!StringUtils.isEmpty(encryptType)) {
            this.encryptType = encryptType;
        }
    }

    public <T extends AlipayResponse> T execute(AlipayRequest<T> request) throws AlipayApiException {
        return execute(request, null);
    }

    public <T extends AlipayResponse> T execute(AlipayRequest<T> request,
                                                String accessToken) throws AlipayApiException {

        return execute(request, accessToken, null);
    }

    public <T extends AlipayResponse> T execute(AlipayRequest<T> request, String accessToken,
                                                String appAuthToken) throws AlipayApiException {

        AlipayParser<T> parser = null;
        if (AlipayConstants.FORMAT_XML.equals(this.format)) {
            parser = new ObjectXmlParser<T>(request.getResponseClass());
        } else {
            parser = new ObjectJsonParser<T>(request.getResponseClass());
        }

        return _execute(request, parser, accessToken, appAuthToken);
    }

    public BatchAlipayResponse execute(BatchAlipayRequest request) throws AlipayApiException {

        long beginTime = System.currentTimeMillis();

        //��������
        Map<String, Object> rt = doPost(request);
        if (rt == null) {
            return null;
        }
        Map<String, Long> costTimeMap = new HashMap<String, Long>();
        if (rt.containsKey("prepareTime")) {
            costTimeMap.put("prepareCostTime", (Long)(rt.get("prepareTime")) - beginTime);
            if (rt.containsKey("requestTime")) {
                costTimeMap.put("requestCostTime", (Long)(rt.get("requestTime")) - (Long)(rt.get("prepareTime")));
            }
        }

        AlipayParser<BatchAlipayResponse> parser = null;
        if (AlipayConstants.FORMAT_XML.equals(this.format)) {
            parser = new ObjectXmlParser<BatchAlipayResponse>(request.getResponseClass());
        } else {
            parser = new ObjectJsonParser<BatchAlipayResponse>(request.getResponseClass());
        }

        BatchAlipayResponse batchAlipayResponse = null;
        try {

            // ����Ҫ�������Ƚ���
            ResponseEncryptItem responseItem = decryptResponse(request, rt, parser);

            // ����ʵ�ʴ�
            batchAlipayResponse = parser.parse(responseItem.getRealContent());
            batchAlipayResponse.setBody(responseItem.getRealContent());

            // ��ǩ�Ƕ����󷵻�ԭʼ��
            checkResponseSign(request, parser, responseItem.getRespContent(), batchAlipayResponse.isSuccess());

            if (costTimeMap.containsKey("requestCostTime")) {
                costTimeMap.put("postCostTime", System.currentTimeMillis() - (Long)(rt.get("requestTime")));
            }

            // ������Ӧ������
            List<AlipayParser<?>> parserList = new ArrayList<AlipayParser<?>>();
            List<AlipayRequestWrapper> requestList = request.getRequestList();
            if (AlipayConstants.FORMAT_XML.equals(this.format)) {
                for (int i = 0; i < requestList.size(); i++) {
                    parserList.add(new ObjectXmlParser(requestList.get(i).getAlipayRequest().getResponseClass()));
                }
            } else {
                for (int i = 0; i < requestList.size(); i++) {
                    parserList.add(new ObjectJsonParser(requestList.get(i).getAlipayRequest().getResponseClass()));
                }
            }

            //��������ʧ�ܣ�ֱ�ӷ���
            if (!batchAlipayResponse.isSuccess()) {
                return batchAlipayResponse;
            }
            String[] responseArray = batchAlipayResponse.getResponseBody().split(BATCH_API_DEFAULT_SPLIT);
            //ѭ������ҵ��API��Ӧ
            for (int index = 0; index < responseArray.length; index++) {
                AlipayResponse alipayResponse = parserList.get(index).parse(responseArray[index]);
                alipayResponse.setBody(responseArray[index]);
                batchAlipayResponse.addResponse(alipayResponse);
            }
            AlipayLogger.logBizDebug((String) rt.get("rsp"));
            return batchAlipayResponse;
        } catch (RuntimeException e) {

            AlipayLogger.logBizError((String) rt.get("rsp"), costTimeMap);
            throw e;
        } catch (AlipayApiException e) {

            AlipayLogger.logBizError((String) rt.get("rsp"), costTimeMap);
            throw new AlipayApiException(e);
        }
    }

    /**
     * @param request
     * @return
     * @throws AlipayApiException
     */
    private Map<String, Object> doPost(BatchAlipayRequest request) throws AlipayApiException {

        Map<String, Object> result = new HashMap<String, Object>();
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request);

        String url = getRequestUrl(requestHolder);

        // ��ӡ����������
        if (AlipayLogger.isBizDebugEnabled()) {
            AlipayLogger.logBizDebug(getRedirectUrl(requestHolder));
        }
        result.put("prepareTime", System.currentTimeMillis());

        String rsp = null;
        try {
            rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), charset,
                connectTimeout, readTimeout, proxyHost, proxyPort);
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }
        result.put("requestTime", System.currentTimeMillis());
        result.put("rsp", rsp);
        result.put("textParams", requestHolder.getApplicationParams());
        result.put("protocalMustParams", requestHolder.getProtocalMustParams());
        result.put("protocalOptParams", requestHolder.getProtocalOptParams());
        result.put("url", url);
        return result;
    }

    /**
     * ��װ��������ӿڲ�����������ܡ�ǩ���߼�
     *
     * @param request
     * @return
     * @throws AlipayApiException
     */
    private <T extends AlipayResponse> RequestParametersHolder getRequestHolderWithSign(BatchAlipayRequest request) throws AlipayApiException {

        List<AlipayRequestWrapper> requestList = request.getRequestList();
        //У��ӿ��б�ǿ�
        if (requestList == null || requestList.isEmpty()) {
            throw new AlipayApiException("40", "client-error:api request list is empty");
        }

        RequestParametersHolder requestHolder = new RequestParametersHolder();

        //���Э��������
        AlipayHashMap protocalMustParams = new AlipayHashMap();
        protocalMustParams.put(AlipayConstants.METHOD, request.getApiMethodName());
        protocalMustParams.put(AlipayConstants.APP_ID, this.appId);
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, this.signType);
        protocalMustParams.put(AlipayConstants.CHARSET, charset);
        protocalMustParams.put(AlipayConstants.VERSION, request.getApiVersion());

        if (request.isNeedEncrypt()) {
            protocalMustParams.put(AlipayConstants.ENCRYPT_TYPE, this.encryptType);
        }

        //���Э���ѡ����
        AlipayHashMap protocalOptParams = new AlipayHashMap();
        protocalOptParams.put(AlipayConstants.FORMAT, format);
        protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
        requestHolder.setProtocalOptParams(protocalOptParams);

        Long timestamp = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
        protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
        requestHolder.setProtocalMustParams(protocalMustParams);

        //����ҵ�����
        AlipayHashMap appParams = new AlipayHashMap();

        //������������
        StringBuilder requestBody = new StringBuilder();
        // ��װÿ��API���������
        for (int index = 0; index < requestList.size(); index++) {
            AlipayRequestWrapper alipayRequestWrapper = requestList.get(index);
            AlipayRequest alipayRequest = alipayRequestWrapper.getAlipayRequest();
            Map<String, Object> apiParams = alipayRequest.getTextParams();
            apiParams.put(AlipayConstants.METHOD, alipayRequest.getApiMethodName());
            apiParams.put(AlipayConstants.APP_AUTH_TOKEN, alipayRequestWrapper.getAppAuthToken());
            apiParams.put(AlipayConstants.ACCESS_TOKEN, alipayRequestWrapper.getAccessToken());
            apiParams.put(AlipayConstants.PROD_CODE, alipayRequest.getProdCode());
            apiParams.put(AlipayConstants.NOTIFY_URL, alipayRequest.getNotifyUrl());
            apiParams.put(AlipayConstants.RETURN_URL, alipayRequest.getReturnUrl());
            apiParams.put(AlipayConstants.TERMINAL_INFO, alipayRequest.getTerminalInfo());
            apiParams.put(AlipayConstants.TERMINAL_TYPE, alipayRequest.getTerminalType());
//            apiParams.put(AlipayConstants.BATCH_REQUEST_ID, String.valueOf(index));

            // ����API����biz_content������ֵΪ��ʱ�����л�bizModel���bizContent
            try {
                if (alipayRequest.getClass().getMethod("getBizContent") != null
                    && StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))
                    && alipayRequest.getBizModel() != null) {
                    apiParams.put(AlipayConstants.BIZ_CONTENT_KEY,
                        new JSONWriter().write(alipayRequest.getBizModel(), true));
                }
            } catch (NoSuchMethodException e) {
                // �Ҳ���getBizContent��ʲô������
            } catch (SecurityException e) {
                AlipayLogger.logBizError(e);
            }
            requestBody.append(new JSONWriter().write(apiParams, false));
            if (index != requestList.size() - 1) {
                requestBody.append(BATCH_API_DEFAULT_SPLIT);
            }
        }
        appParams.put(AlipayConstants.BIZ_CONTENT_KEY, requestBody.toString());

        // ֻ���½ӿں�������Կ����֧�ּ���
        if (request.isNeedEncrypt()) {

            if (StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))) {

                throw new AlipayApiException("��ǰAPI��֧�ּ�������");
            }

            // ��Ҫ���ܱ���������Կ�ͼ����㷨
            if (StringUtils.isEmpty(this.encryptType) || getEncryptor() == null) {

                throw new AlipayApiException("API����Ҫ����ܣ������������Կ���ͺͼ�����");
            }

            String encryptContent = getEncryptor().encrypt(
                appParams.get(AlipayConstants.BIZ_CONTENT_KEY), this.encryptType, this.charset);

            appParams.put(AlipayConstants.BIZ_CONTENT_KEY, encryptContent);
        }

        requestHolder.setApplicationParams(appParams);

        if (!StringUtils.isEmpty(this.signType)) {

            String signContent = AlipaySignature.getSignatureContent(requestHolder);
            protocalMustParams.put(AlipayConstants.SIGN,
                getSigner().sign(signContent, this.signType, charset));

        } else {
            protocalMustParams.put(AlipayConstants.SIGN, "");
        }
        return requestHolder;
    }

    public <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request) throws AlipayApiException {
        return pageExecute(request, "POST");
    }

    public <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request,
                                                    String httpMethod) throws AlipayApiException {
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request, null, null);
        // ��ӡ����������
        if (AlipayLogger.isBizDebugEnabled()) {
            AlipayLogger.logBizDebug(getRedirectUrl(requestHolder));
        }
        T rsp = null;
        try {
            Class<T> clazz = request.getResponseClass();
            rsp = clazz.newInstance();
        } catch (InstantiationException e) {
            AlipayLogger.logBizError(e);
        } catch (IllegalAccessException e) {
            AlipayLogger.logBizError(e);
        }
        if ("GET".equalsIgnoreCase(httpMethod)) {
            rsp.setBody(getRedirectUrl(requestHolder));
        } else {
            String baseUrl = getRequestUrl(requestHolder);
            rsp.setBody(WebUtils.buildForm(baseUrl, requestHolder.getApplicationParams()));
        }
        return rsp;
    }

    public <T extends AlipayResponse> T sdkExecute(AlipayRequest<T> request) throws AlipayApiException {
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request, null, null);
        // ��ӡ����������
        if (AlipayLogger.isBizDebugEnabled()) {
            AlipayLogger.logBizDebug(getSdkParams(requestHolder));
        }
        T rsp = null;
        try {
            Class<T> clazz = request.getResponseClass();
            rsp = clazz.newInstance();
        } catch (InstantiationException e) {
            AlipayLogger.logBizError(e);
        } catch (IllegalAccessException e) {
            AlipayLogger.logBizError(e);
        }
        rsp.setBody(getSdkParams(requestHolder));
        return rsp;
    }

    public <TR extends AlipayResponse, T extends AlipayRequest<TR>> TR parseAppSyncResult(Map<String, String> result,
                                                                                          Class<T> requsetClazz) throws AlipayApiException {
        TR tRsp = null;
        String rsp = result.get("result");

        try {
            T request = requsetClazz.newInstance();
            Class<TR> responseClazz = request.getResponseClass();

            //resultΪ��ֱ�ӷ���SYSTEM_ERROR
            if (StringUtils.isEmpty(rsp)) {
                tRsp = responseClazz.newInstance();
                tRsp.setCode("20000");
                tRsp.setSubCode("ACQ.SYSTEM_ERROR");
                tRsp.setSubMsg(result.get("memo"));
            } else {
                AlipayParser<TR> parser = null;
                if (AlipayConstants.FORMAT_XML.equals(this.format)) {
                    parser = new ObjectXmlParser<TR>(responseClazz);
                } else {
                    parser = new ObjectJsonParser<TR>(responseClazz);
                }

                // ����ʵ�ʴ�
                tRsp = parser.parse(rsp);
                tRsp.setBody(rsp);

                // ��ǩ�Ƕ����󷵻�ԭʼ��
                checkResponseSign(request, parser, rsp, tRsp.isSuccess());
                if (!tRsp.isSuccess()) {
                    AlipayLogger.logBizError(rsp);
                }
            }
        } catch (RuntimeException e) {
            AlipayLogger.logBizError(rsp);
            throw e;
        } catch (AlipayApiException e) {
            AlipayLogger.logBizError(rsp);
            throw new AlipayApiException(e);
        } catch (InstantiationException e) {
            AlipayLogger.logBizError(rsp);
            throw new AlipayApiException(e);
        } catch (IllegalAccessException e) {
            AlipayLogger.logBizError(rsp);
            throw new AlipayApiException(e);
        }
        return tRsp;
    }

    /**
     * ��װ�ӿڲ�����������ܡ�ǩ���߼�
     *
     * @param request
     * @param accessToken
     * @param appAuthToken
     * @return
     * @throws AlipayApiException
     */
    private <T extends AlipayResponse> RequestParametersHolder getRequestHolderWithSign(AlipayRequest<?> request,
                                                                                        String accessToken,
                                                                                        String appAuthToken) throws AlipayApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        AlipayHashMap appParams = new AlipayHashMap(request.getTextParams());

        // ����API����biz_content������ֵΪ��ʱ�����л�bizModel���bizContent
        try {
            if (request.getClass().getMethod("getBizContent") != null
                    && StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))
                    && request.getBizModel() != null) {
                appParams.put(AlipayConstants.BIZ_CONTENT_KEY,
                        new JSONWriter().write(request.getBizModel(), true));
            }
        } catch (NoSuchMethodException e) {
            // �Ҳ���getBizContent��ʲô������
        } catch (SecurityException e) {
            AlipayLogger.logBizError(e);
        }

        // ֻ���½ӿں�������Կ����֧�ּ���
        if (request.isNeedEncrypt()) {

            if (StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))) {

                throw new AlipayApiException("��ǰAPI��֧�ּ�������");
            }

            // ��Ҫ���ܱ���������Կ�ͼ����㷨
            if (StringUtils.isEmpty(this.encryptType) || getEncryptor() == null) {

                throw new AlipayApiException("API����Ҫ����ܣ������������Կ���ͺͼ�����");
            }

            String encryptContent = getEncryptor().encrypt(
                    appParams.get(AlipayConstants.BIZ_CONTENT_KEY), this.encryptType, this.charset);

            appParams.put(AlipayConstants.BIZ_CONTENT_KEY, encryptContent);
        }

        if (!StringUtils.isEmpty(appAuthToken)) {
            appParams.put(AlipayConstants.APP_AUTH_TOKEN, appAuthToken);
        }

        requestHolder.setApplicationParams(appParams);

        if (StringUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_UTF8;
        }

        AlipayHashMap protocalMustParams = new AlipayHashMap();
        protocalMustParams.put(AlipayConstants.METHOD, request.getApiMethodName());
        protocalMustParams.put(AlipayConstants.VERSION, request.getApiVersion());
        protocalMustParams.put(AlipayConstants.APP_ID, this.appId);
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, this.signType);
        protocalMustParams.put(AlipayConstants.TERMINAL_TYPE, request.getTerminalType());
        protocalMustParams.put(AlipayConstants.TERMINAL_INFO, request.getTerminalInfo());
        protocalMustParams.put(AlipayConstants.NOTIFY_URL, request.getNotifyUrl());
        protocalMustParams.put(AlipayConstants.RETURN_URL, request.getReturnUrl());
        protocalMustParams.put(AlipayConstants.CHARSET, charset);

        if (request.isNeedEncrypt()) {
            protocalMustParams.put(AlipayConstants.ENCRYPT_TYPE, this.encryptType);
        }

        Long timestamp = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
        protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
        requestHolder.setProtocalMustParams(protocalMustParams);

        AlipayHashMap protocalOptParams = new AlipayHashMap();
        protocalOptParams.put(AlipayConstants.FORMAT, format);
        protocalOptParams.put(AlipayConstants.ACCESS_TOKEN, accessToken);
        protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
        protocalOptParams.put(AlipayConstants.PROD_CODE, request.getProdCode());
        requestHolder.setProtocalOptParams(protocalOptParams);

        if (!StringUtils.isEmpty(this.signType)) {

            String signContent = AlipaySignature.getSignatureContent(requestHolder);
            protocalMustParams.put(AlipayConstants.SIGN,
                    getSigner().sign(signContent, this.signType, charset));

        } else {
            protocalMustParams.put(AlipayConstants.SIGN, "");
        }
        return requestHolder;
    }

    /**
     * ��ȡPOST�����base url
     *
     * @param requestHolder
     * @return
     * @throws AlipayApiException
     */
    private String getRequestUrl(RequestParametersHolder requestHolder) throws AlipayApiException {
        StringBuffer urlSb = new StringBuffer(serverUrl);
        try {
            String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(),
                    charset);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), charset);

            urlSb.append("?");
            urlSb.append(sysMustQuery);
            if (sysOptQuery != null & sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        return urlSb.toString();
    }

    /**
     * GETģʽ�»�ȡ��ת����
     *
     * @param requestHolder
     * @return
     * @throws AlipayApiException
     */
    private String getRedirectUrl(RequestParametersHolder requestHolder) throws AlipayApiException {
        StringBuffer urlSb = new StringBuffer(serverUrl);
        try {
            Map<String, String> sortedMap = AlipaySignature.getSortedMap(requestHolder);
            String sortedQuery = WebUtils.buildQuery(sortedMap, charset);
            urlSb.append("?");
            urlSb.append(sortedQuery);
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        return urlSb.toString();
    }

    /**
     * ƴװsdk����ʱ��������
     *
     * @param requestHolder
     * @return
     * @throws AlipayApiException
     */
    private String getSdkParams(RequestParametersHolder requestHolder) throws AlipayApiException {
        StringBuffer urlSb = new StringBuffer();
        try {
            Map<String, String> sortedMap = AlipaySignature.getSortedMap(requestHolder);
            String sortedQuery = WebUtils.buildQuery(sortedMap, charset);
            urlSb.append(sortedQuery);
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        return urlSb.toString();
    }

    private <T extends AlipayResponse> T _execute(AlipayRequest<T> request, AlipayParser<T> parser,
                                                  String authToken,
                                                  String appAuthToken) throws AlipayApiException {

        long beginTime = System.currentTimeMillis();
        Map<String, Object> rt = doPost(request, authToken, appAuthToken);
        if (rt == null) {
            return null;
        }
        Map<String, Long> costTimeMap = new HashMap<String, Long>();
        if (rt.containsKey("prepareTime")) {
            costTimeMap.put("prepareCostTime", (Long)(rt.get("prepareTime")) - beginTime);
            if (rt.containsKey("requestTime")) {
                costTimeMap.put("requestCostTime", (Long)(rt.get("requestTime")) - (Long)(rt.get("prepareTime")));
            }
        }


        T tRsp = null;

        try {

            // ����Ҫ�������Ƚ���
            ResponseEncryptItem responseItem = decryptResponse(request, rt, parser);

            // ����ʵ�ʴ�
            //tRsp = parser.parse(responseItem.getRealContent());
            tRsp = (T) new AlipayFundCouponOrderPagePayResponse();
            tRsp.setBody(responseItem.getRealContent());

            // ��ǩ�Ƕ����󷵻�ԭʼ��
            //checkResponseSign(request, parser, responseItem.getRespContent(), tRsp.isSuccess());

            if (costTimeMap.containsKey("requestCostTime")) {
                costTimeMap.put("postCostTime", System.currentTimeMillis() - (Long)(rt.get("requestTime")));
            }
        } catch (RuntimeException e) {

            AlipayLogger.logBizError((String) rt.get("rsp"), costTimeMap);
            throw e;
        } catch (AlipayApiException e) {

            AlipayLogger.logBizError((String) rt.get("rsp"), costTimeMap);
            throw new AlipayApiException(e);
        }

        tRsp.setParams((AlipayHashMap) rt.get("textParams"));
        if (!tRsp.isSuccess()) {
            AlipayLogger.logErrorScene(rt, tRsp, "", costTimeMap);
        } else {
            AlipayLogger.logBizSummary(rt, tRsp, costTimeMap);
        }
        return tRsp;
    }

    /**
     *
     *
     * @param request
     * @param accessToken
     * @param appAuthToken
     * @return
     * @throws AlipayApiException
     */
    private <T extends AlipayResponse> Map<String, Object> doPost(AlipayRequest<T> request,
                                                                  String accessToken,
                                                                  String appAuthToken) throws AlipayApiException {
        Map<String, Object> result = new HashMap<String, Object>();
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request, accessToken,
                appAuthToken);

        String url = getRequestUrl(requestHolder);

        // ��ӡ����������
        if (AlipayLogger.isBizDebugEnabled()) {
            AlipayLogger.logBizDebug(getRedirectUrl(requestHolder));
        }
        result.put("prepareTime", System.currentTimeMillis());

        String rsp = null;
        try {
            if (request instanceof AlipayUploadRequest) {
                AlipayUploadRequest<T> uRequest = (AlipayUploadRequest<T>) request;
                Map<String, FileItem> fileParams = AlipayUtils.cleanupMap(uRequest.getFileParams());
                rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), fileParams,
                        charset, connectTimeout, readTimeout, proxyHost, proxyPort);
            } else {
                rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), charset,
                        connectTimeout, readTimeout, proxyHost, proxyPort);
            }
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }
        result.put("requestTime", System.currentTimeMillis());
        result.put("rsp", rsp);
        result.put("textParams", requestHolder.getApplicationParams());
        result.put("protocalMustParams", requestHolder.getProtocalMustParams());
        result.put("protocalOptParams", requestHolder.getProtocalOptParams());
        result.put("url", url);
        return result;
    }

    /**
     *  �����Ӧǩ��
     *
     * @param request
     * @param parser
     * @param responseBody
     * @param responseIsSucess
     * @throws AlipayApiException
     */
    private <T extends AlipayResponse> void checkResponseSign(AlipayRequest<T> request,
                                                              AlipayParser<T> parser,
                                                              String responseBody,
                                                              boolean responseIsSucess) throws AlipayApiException {
        // ��Գɹ��������֧������Կ�Ľ�����ǩ
        if (getSignChecker() != null) {

            SignItem signItem = parser.getSignItem(request, responseBody);

            if (signItem == null) {

                throw new AlipayApiException("sign check fail: Body is Empty!");
            }

            if (responseIsSucess
                    || (!responseIsSucess && !StringUtils.isEmpty(signItem.getSign()))) {

                boolean rsaCheckContent = getSignChecker().check(signItem.getSignSourceDate(),
                        signItem.getSign(), this.signType, this.charset);

                if (!rsaCheckContent) {

                    // ���JSON \/���⣬�滻/���ٳ�����һ����֤
                    if (!StringUtils.isEmpty(signItem.getSignSourceDate())
                            && signItem.getSignSourceDate().contains("\\/")) {

                        String srouceData = signItem.getSignSourceDate().replace("\\/", "/");

                        boolean jsonCheck = getSignChecker().check(srouceData, signItem.getSign(),
                                this.signType, this.charset);

                        if (!jsonCheck) {
                            throw new AlipayApiException(
                                    "sign check fail: check Sign and Data Fail��JSON also��");
                        }
                    } else {

                        throw new AlipayApiException("sign check fail: check Sign and Data Fail!");
                    }
                }
            }

        }
    }

    /**
     *  ������Ӧ
     *
     * @param request
     * @param rt
     * @param parser
     * @return
     * @throws AlipayApiException
     */
    private <T extends AlipayResponse> ResponseEncryptItem decryptResponse(AlipayRequest<T> request,
                                                                           Map<String, Object> rt,
                                                                           AlipayParser<T> parser) throws AlipayApiException {

        String responseBody = (String) rt.get("rsp");

        String realBody = null;

        // ����
        if (request.isNeedEncrypt()) {

            // ����ԭʼ��
            realBody = parser.decryptSourceData(request, responseBody, this.format,
                    getDecryptor(), this.encryptType, this.charset);
        } else {

            // ����ԭ���ݴ�
            realBody = (String) rt.get("rsp");
        }

        return new ResponseEncryptItem(responseBody, realBody);

    }

    void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    void setAppId(String appId) {
        this.appId = appId;
    }

    void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    void setFormat(String format) {
        this.format = format;
    }

    void setSignType(String signType) {
        this.signType = signType;
    }

    void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    void setCharset(String charset) {
        this.charset = charset;
    }

    void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public abstract Signer getSigner();

    public abstract SignChecker getSignChecker();

    public abstract Encryptor getEncryptor();

    public abstract Decryptor getDecryptor();
}
