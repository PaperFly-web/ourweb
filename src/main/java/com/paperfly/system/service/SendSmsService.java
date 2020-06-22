package com.paperfly.system.service;

import java.util.Map;

public interface SendSmsService {
    public boolean sendSms(String phoneNumber, Map<String,String> code);
}
