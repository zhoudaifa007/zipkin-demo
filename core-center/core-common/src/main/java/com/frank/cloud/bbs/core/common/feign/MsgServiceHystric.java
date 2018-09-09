package com.frank.cloud.bbs.core.common.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MsgServiceHystric implements MsgService{

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void testMsg() {
	}
}
