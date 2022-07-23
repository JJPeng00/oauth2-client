package com.jjpeng.oauth2client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

/**
 * @author JJPeng
 * @date 2022/7/23 17:25
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ClientRegistrationRepository clientRegistration() {
        //一个ClientRegistration保存了一个客户端在某个oauth provider中注册的信息，我们可以给在github中注册的app创建一个ClientRegistration，
        //也可以在facebook中注册的app创建一个ClientRegistration
        //一个ClientRegistration就像是一个UserDetails
        ClientRegistration clientRegistration = buildClientRegistration();

        //ClientRegistrationRepository和ClientRegistration的关系就类似于UserDetails和UserDetailsService的关系
        //InMemoryClientRegistrationRepository是ClientRegistrationRepository的一种实现；InMemoryUserDetailsManager是UserDetailsService的一种实习
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    //这里的配置也可以在yml等文件中进行
    //CommonOAuth2Provider提供了几种常用的provider的默认配置，可以使我们简化配置
    private ClientRegistration buildClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("7920f623644104b9c3bd")
                .clientSecret("dca43ab32e011b17a1c17ea240b0c0db8d5ad244")
                .build();
    }

    //github文档https://docs.github.com/cn/developers/apps/building-oauth-apps/authorizing-oauth-apps#web-application-flow
//    private ClientRegistration buildClientRegistration() {
//        ClientRegistration cr =
//                ClientRegistration.withRegistrationId("github")
//                        //在github注册的app的id
//                        .clientId("7920f623644104b9c3bd")
//                        //app对应的密钥
//                        .clientSecret("dca43ab32e011b17a1c17ea240b0c0db8d5ad244")
//                        .scope(new String[]{"read:user"})
//                        //授权的url
//                        .authorizationUri("https://github.com/login/oauth/authorize")
//                        //获取access token和refresh token的url
//                        .tokenUri("https://github.com/login/oauth/access_token")
//                        .userInfoUri("https://api.github.com/user")
//                        .userNameAttributeName("id")
//                        .clientName("GitHub")
//                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                        .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
//                        .build();
//        return cr;
//    }

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
