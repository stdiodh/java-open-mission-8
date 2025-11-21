// 예: src/main/java/.../config/SwaggerConfig.java

package woowacourse_precoruse.java_open_mission_8.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private final String serverUrl;

    public SwaggerConfig(@Value("${app.server.url}") String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Java 오픈 미션 8 - API 명세서")
                .description("WebFlux 기반의 자동차 경주 및 로또 미션 API 문서입니다.")
                .version("v1.0.0");

        Server server = new Server().url(serverUrl);

        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }
}
