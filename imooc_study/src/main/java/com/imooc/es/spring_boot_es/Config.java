package com.imooc.es.spring_boot_es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class Config {
    @Bean
    public TransportClient client() throws UnknownHostException {
        byte[] masterIPAddress = new byte[]{(byte) 192, (byte) 168, 8, 10};
        InetSocketTransportAddress masterAddress = new InetSocketTransportAddress(
                InetAddress.getByAddress(masterIPAddress),
                9300
        );
        byte[] slave1IPAddress = new byte[]{(byte) 192, (byte) 168, 8, 11};
        InetSocketTransportAddress slave1Address = new InetSocketTransportAddress(
                InetAddress.getByAddress(slave1IPAddress),
                9300
        );
        Settings settings = Settings.builder().put("cluster.name", "es-cluster").build();
        TransportClient client = new PreBuiltTransportClient(settings);

        client.addTransportAddress(masterAddress);
        client.addTransportAddress(slave1Address);

        return client;
    }
}
