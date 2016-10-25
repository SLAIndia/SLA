package com.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.app.security.AppSecurityConfig;

@Configuration
@Import({ AppSecurityConfig.class})
@ImportResource( {"classpath:**/WEB-INF/user-servlet.xml"})
public class AppConfig {
   public AppConfig() {
      super();
   }
}