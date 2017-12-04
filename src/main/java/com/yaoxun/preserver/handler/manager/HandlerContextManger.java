package com.yaoxun.preserver.handler.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.yaoxun.preserver.entity.AttrExt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * ChannelHandlerContext 管理类
 * @author Loren
 * @createTime 2017年12月4日 上午11:49:39
 */
public class HandlerContextManger {
	
	public static final String ATTR_EXT_KEY = "attrExt";
	
	public static final AttributeKey<AttrExt> ATTR_EXT = AttributeKey.valueOf(ATTR_EXT_KEY);
	
	private HandlerContextManger() {}
	
	private static Map<String, ChannelHandlerContext> ctxMap = new ConcurrentHashMap<>();
	
	/**
	 * 添加ChannelHandlerContext，添加时如果key相同, <br />
	 * 存在会覆盖原来key的ChannelHandlerContext，并返回原来的ChannelHandlerContext
	 * @author Loren
	 * @createTime 2017年12月4日 上午11:58:41
	 * @param key 唯一的标志
	 * @param ctx ChannelHandlerContext
	 * @return key不存在返回null,key存在返回上一次的ChannelHandlerContext
	 */
	public static ChannelHandlerContext put(String key, ChannelHandlerContext ctx) {
		Attribute<AttrExt> attr = ctx.channel().attr(ATTR_EXT);
		AttrExt attrExt = attr.get();
		if(attrExt == null) {
			attrExt = new AttrExt(key, System.currentTimeMillis());
			attr.set(attrExt);
		}
		ChannelHandlerContext channelHandlerContext = ctxMap.put(key, ctx);
		return channelHandlerContext;
	}
	
	public static ChannelHandlerContext delete(ChannelHandlerContext ctx) {
		Attribute<AttrExt> attr = ctx.channel().attr(ATTR_EXT);
		AttrExt attrExt = attr.get();
		if(attrExt != null) {
			return delete(attrExt.getUniqueKey());
		}
		return null;
	}
	
	public static ChannelHandlerContext delete(String key) {
		return ctxMap.remove(key);
	}
	
	public static int size() {
		return ctxMap.size();
	}
	
	public static Set<Entry<String,ChannelHandlerContext>> entrySet() {
		return ctxMap.entrySet();
	}
	
	public static ChannelHandlerContext get(String key) {
		return ctxMap.get(key);
	}
	
	public static Attribute<AttrExt> getAttr(String key) {
		ChannelHandlerContext ctx = get(key);
		if(ctx != null) {
			return ctx.channel().attr(ATTR_EXT);
		}
		return null;
	}
	
	public static AttrExt getAttrExt(String key) {
		Attribute<AttrExt> attr = getAttr(key);
		if(attr != null) {
			return attr.get();
		}
		return null;
	}
	
}
