package com.frank.cloud.bbs.core.api;

import com.frank.cloud.bbs.core.common.feign.MsgService;
import com.frank.cloud.bbs.core.common.model.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

/**
 * Created by Daifa on 2018/7/24.
 */
@RestController
@RequestMapping("/v1/bbs/core")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    protected MsgService msgService;

    @RequestMapping(value = "/test0", method = RequestMethod.GET)
    public ServerResponse<Object> notice(HttpServletRequest request, HttpServletResponse response) {
        return ServerResponse.successWithData(null);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public Callable<ServerResponse> articleList(HttpServletRequest request, HttpServletResponse response
                                                ) {
        Callable<ServerResponse> callable = new Callable<ServerResponse>() {
            @Override
            public ServerResponse call() throws Exception {
                Thread.sleep(5000);
                return ServerResponse.successWithData(null);
            }
        };
        return callable;
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public ServerResponse articleList1(HttpServletRequest request, HttpServletResponse response) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ServerResponse.successWithData(null);
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public ServerResponse<Object> collectArticle(HttpServletRequest request, HttpServletResponse response) {
        logger.info("---------------------------------------------------------------");
        msgService.testMsg();
        return ServerResponse.successWithData(null);
    }
}
