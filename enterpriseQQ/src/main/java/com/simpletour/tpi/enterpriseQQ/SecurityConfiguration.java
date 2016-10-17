package com.simpletour.tpi.enterpriseQQ;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;


/**
 * Created by zt on 2016/5/17.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService() {
        return new SaleAppDetailsManager();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    /**
     * 验证/**请求,
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.addFilterAfter(digestAuthenticationFilter(), BasicAuthenticationFilter.class)
//                .authorizeRequests().antMatchers("/**").authenticated()
                .authorizeRequests().antMatchers("/**").anonymous()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter() {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setUserDetailsService(userDetailsService());
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());

        //增加缓存获取的用户信息,减轻服务器负荷/提升访问速度
        //每次验证都会去loadUserByUsername一次,一般情况该操作都是代价很大,所以建议实现缓存降低调用次数
        //digestAuthenticationFilter.setUserCache();
        return digestAuthenticationFilter;
    }

    @Bean
    public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntryPoint.setKey("acegi");
        digestAuthenticationEntryPoint.setRealmName("Contacts Realm via Digest Authentication");
        digestAuthenticationEntryPoint.setNonceValiditySeconds(300);
        return digestAuthenticationEntryPoint;
    }

    private class SaleAppDetailsManager implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
//            ConditionOrderByQuery query = new ConditionOrderByQuery();
//            Condition c = new Condition("key", str);
//            query.setCondition(c);
//            List<SaleApp> saleAppList = saleAppServiceImp.querySaleAppList(query);
//            if (saleAppList!=null & saleAppList.size()==1) {
//                SaleApp saleApp = saleAppList.get(0);
//                if (!saleApp.getDel().equals(Boolean.TRUE)) {
//                    List<GrantedAuthority> authorities = new Vector();
//                    authorities.add(new GrantedAuthority(){
//                        @Override
//                        public String getAuthority() {
//                            return "sale_app";
//                        }
//                    });
//                    return new SaleAppUser(saleApp.getId(), saleApp.getKey(), saleApp.getSecret(), authorities);
//                }
//            }
            throw new UsernameNotFoundException(str);
        }
    }
}
