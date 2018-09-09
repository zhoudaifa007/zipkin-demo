package com.frank.cloud.message.common.cache;

import java.util.List;
import java.util.Map;

public interface ICacheManager {

	/**
	 * key-value存入缓存
	 * @param key
	 * @param value
	 * @param seconds 单位：秒
	 * @return
	 */
	boolean put(String key,String value,int seconds);
	
	/**
	 * 查询获取对应key的值
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * key-map存入缓存
	 * @param key
	 * @param hashMap
	 * @param seconds 单位：秒
	 * @return
	 */
	boolean setMap(String key,Map<String,String> hashMap,int seconds);
	
	/**
	 * 查询获取对应key的Map值
	 * @param key
	 * @return
	 */
	Map<String,String> getMap(String key);
	
	/**
	 * key-map中field对应的value存入缓存
	 * @param key
	 * @param field
	 * @param value
	 * @param seconds 单位：秒
	 * @return
	 */
	boolean setField(String key,String field,String value,int seconds);
	
	/**
	 * 查询获取对应key的Map中对于field的值
	 * @param key
	 * @param field
	 * @return
	 */
	String getField(String key,String field);
	
	/**
	 * 删除对应key的Map中的field
	 * @param key
	 * @param field
	 * @return
	 */
	Long delField(String key,String field);
	
	/**
	 * key-list存入缓存
	 * @param key
	 * @param value
	 * @param seconds 单位：秒
	 * @return
	 */
	boolean setList(String key,String value,int seconds);
	
	/**
	 * 查询获取对应key的list值
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	List<String> getList(String key,long start,long end);
	
	/**
	 * 查询获取对应key的list长度
	 * @param key
	 * @return
	 */
	Long llen(String key);
	
	/**
	 * 重新设置对应key的过期时间
	 * @param key
	 * @param seconds 单位：秒
	 * @return
	 */
	void refreshExpire(String key,int seconds);
	
	/**
	 * 删除对应key的缓存
	 * @param key
	 * @return
	 */
	Long delete(String key);
	
	/**
	 * 移除等于value的元素
	 * @param key
	 * @param count 当count>0，从头移除count个；当count=0，从头移除所有等于value的；当count<0，从尾移除count个
	 * @param value
	 * @return
	 */
	Long lrem(String key,int count,String value);
	
	/**
	 * 修改列表指定索引的值
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	String lset(String key,int index,String value);
	
	/**
	 * key是否存在
	 * @param key
	 * @return
	 */
	boolean exists(String key);
	
}
