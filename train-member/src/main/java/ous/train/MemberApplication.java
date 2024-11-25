package ous.train;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan("ous.train.mapper")
public class MemberApplication {
	public static Logger LOG = LoggerFactory.getLogger(MemberApplication.class);
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MemberApplication.class);
		Environment env = application.run(args).getEnvironment();

		LOG.info("项目启动地址: http://127.0.0.1:{}/member",env.getProperty("server.port"));
	}

}
