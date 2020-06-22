package com.paperfly.system.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.paperfly.system.properties.SmsProperties;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SendSmsServiceImpl implements SendSmsService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SmsProperties smsProperties;
    public boolean sendSms(String phoneNumber, Map<String,String> code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");//不要动
        request.setVersion("2017-05-25");//不要动
        request.setAction("SendSms");//一般不修改
        request.putQueryParameter("PhoneNumbers",phoneNumber);
        request.putQueryParameter("SignName",smsProperties.getSignName());
        request.putQueryParameter("TemplateCode",smsProperties.getTemplateCode());
        request.putQueryParameter("TemplateParam", JSON.toJSONString(code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            System.out.println(data);
            boolean isSuccess = response.getHttpResponse().isSuccess();
            if(isSuccess){
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}