package com.frank.cloud.bbs.core.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "message-center", fallback = MsgServiceHystric.class)
public interface MsgService {


    @RequestMapping(value = "/v1/bbs/message/test/message", method = RequestMethod.GET)
    void testMsg();

}
