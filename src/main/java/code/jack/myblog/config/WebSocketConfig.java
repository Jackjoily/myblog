package code.jack.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * @program: sell
 * @description:
 * @author: jackjoily
 * @create: 2019-10-17 19:04
 * 这里必须配置ServerEndpointExporter
 */
@Component
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return  new ServerEndpointExporter();
    }
}