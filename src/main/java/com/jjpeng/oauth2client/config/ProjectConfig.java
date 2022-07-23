package com.jjpeng.oauth2client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author JJPeng
 * @date 2022/7/23 17:25
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public InMemoryClientRegistrationRepository clientRegistration() {
        ClientRegistration clientRegistration = buildClientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    //github文档https://docs.github.com/cn/developers/apps/building-oauth-apps/authorizing-oauth-apps#web-application-flow
    private ClientRegistration buildClientRegistration() {
        ClientRegistration cr =
                ClientRegistration.withRegistrationId("github")
                        //在github注册的app的id
                        .clientId("7920f623644104b9c3bd")
                        //app对应的密钥
                        .clientSecret("dca43ab32e011b17a1c17ea240b0c0db8d5ad244")
                        .scope(new String[]{"read:user"})
                        //授权的url
                        .authorizationUri("https://github.com/login/oauth/authorize")
                        //获取access token和refresh token的url
                        .tokenUri("https://github.com/login/oauth/access_token")
                        .userInfoUri("https://api.github.com/user")
                        .userNameAttributeName("id")
                        .clientName("GitHub")
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
                        .build();
        return cr;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //框架会将过滤器Auth2LoginAuthenticationFilter添加到过滤器链中
        //这个过滤器拦截请求并应用所需的逻辑来进行OAuth 2认证所需的逻辑
        //需要实现ClientRegistration接口
        http.oauth2Login();

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
