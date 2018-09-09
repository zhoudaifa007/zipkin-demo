package com.frank.cloud.message.common.cache.impl;

import java.util.List;
import java.util.Map;

import com.frank.cloud.message.common.cache.ICacheManager;
import org.springframework.stereotype.Component;


@Component("cacheManagerHandler")
public class CacheManagerImpl implements ICacheManager {

	@Override
	public boolean put(String key, String value, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setMap(String key, Map<String, String> hashMap, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getMap(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setField(String key, String field, String value, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getField(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long delField(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setList(String key, String value, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getList(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long llen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshExpire(String key, int seconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long delete(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lrem(String key, int count, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lset(String key, int index, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Resource
//    private SmartCacheOperator redisService;
//	
//	@Override
//	public boolean put(String key,String value,int seconds) 
//	{
//		String result = redisService.set(key, value);
//		if(StringUtils.isEmpty(result))
//		{
//			return false;
//		}
//		if(seconds != -1)
//		{//永不过期
//			redisService.expire(key, seconds);
//		}
//		return true;
//	}
//
//	@Override
//	public String get(String key) 
//	{
//		return redisService.get(key);
//	}
//
//	@Override
//	public boolean setMap(String key, Map<String, String> hashMap, int seconds) 
//	{
//		String result = redisService.hsetMany(key, hashMap);
//		if(StringUtils.isEmpty(result))
//		{
//			return false;
//		}
//		if(seconds != -1)
//		{//永不过期
//			redisService.expire(key, seconds);
//		}
//		return true;
//	}
//
//	@Override
//	public Map<String, String> getMap(String key) 
//	{
//		
//		return redisService.hgetAll(key);
//	}
//
//	@Override
//	public String getField(String key, String field) 
//	{
//		
//		return redisService.hget(key, field);
//	}
//	
//	@Override
//	public boolean setField(String key, String field,String value, int seconds) 
//	{
//		long result = redisService.hset(key, field, value);
//		if(result < 0)
//		{
//			return false;
//		}
//		if(seconds != -1)
//		{//永不过期
//			redisService.expire(key, seconds);
//		}
//		return true;
//	}
//    
//	@Override
//	public boolean setList(String key,String value,int seconds) 
//	{
//		Long result = redisService.lpush(key,value);
//		
//		if(StringUtils.isEmpty(result) && result < 0)
//		{
//			return false;
//		}
//		if(seconds != -1)
//		{//永不过期
//			redisService.expire(key, seconds);
//		}
//		return true;
//	}
//
//	@Override
//	public List<String> getList(String key,long start,long end) 
//	{
//		
//		return redisService.lrange(key, start, end);
//	}
//
//	@Override
//	public Long llen(String key) 
//	{
//		
//		return redisService.llen(key);
//	}
//
//	@Override
//	public void refreshExpire(String key,int seconds) 
//	{
//		
//		redisService.expire(key, seconds);
//	}
//
//	@Override
//	public Long delete(String key) 
//	{
//		
//		return redisService.delete(key);
//	}
//
//	@Override
//	public Long lrem(String key, int count, String value) 
//	{
//		
//		return redisService.lrem(key, count, value);
//	}
//
//	@Override
//	public String lset(String key, int index, String value) 
//	{
//		return redisService.lset(key, index, value);
//	}
//
//	@Override
//	public boolean exists(String key) 
//	{
//		return redisService.exists(key);
//	}
//
//	@Override
//	public Long delField(String key, String field) 
//	{
//		return redisService.hdel(key, field);
//	}

}
