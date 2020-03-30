package com.zhangaochong.modbusrtu.gateway.constant;

import io.netty.channel.Channel;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储Channel与客户端id绑定关系
 *
 * @author Aochong Zhang
 */
public class ChannelConstant {
    /**
     * key: 客户端id
     * value: 该客户端channel
     */
    public static Map<String, Channel> clientChannelMap = new ConcurrentHashMap<>();

    /**
     * 根据客户端id获取channel
     *
     * @param clientId 客户端id
     * @return 该客户端channel
     */
    public static Channel get(String clientId) {
        return clientChannelMap.get(clientId);
    }

    /**
     * 添加Channel与客户端id绑定关系
     *
     * @param clientId 客户端id
     * @param channel 该客户端channel
     */
    public static void set(String clientId, Channel channel) {
        if (StringUtils.isEmpty(clientId) || channel == null) {
            throw new IllegalArgumentException("clientId或channel不能为空");
        }
        clientChannelMap.put(clientId, channel);
    }

    /**
     * 删除Channel与客户端id绑定关系
     *
     * @param clientId 客户端id
     */
    public static void remove(String clientId) {
        if (!StringUtils.isEmpty(clientId)) {
            clientChannelMap.remove(clientId);
        }
    }

    /**
     * 根据channel获取绑定的客户端id
     *
     * @param channel 客户端channel
     * @return 客户端id
     */
    public static String getIdByChannel(Channel channel) {
        for (String key : clientChannelMap.keySet()) {
            if (clientChannelMap.get(key).equals(channel)) {
                return key;
            }
        }
        return null;
    }
}
