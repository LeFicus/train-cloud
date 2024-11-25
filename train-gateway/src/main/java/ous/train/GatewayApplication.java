package ous.train;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GatewayApplication {
		public static Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);
		public static void main(String[] args) {
			SpringApplication application = new SpringApplication(GatewayApplication.class);
			Environment env = application.run(args).getEnvironment();
			LOG.info("网关地址: http://127.0.0.1:{}/",env.getProperty("server.port"));
		}
}
