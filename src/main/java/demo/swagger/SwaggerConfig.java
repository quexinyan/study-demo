package demo.swagger;/*
 * Time : 2018/7/18 15:23
 * Author : gaox
 * Description :
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"demo.swagger.controller"})
@EnableWebMvc
/**
 * @Time           2018/7/18 11:21
 * @Author          gaox
 * @Description     @Configuration 配置注解，自动在本类上下文加载一些环境变量信息
 @EnableWebMvc
 @EnableSwagger2 使swagger2生效
 @ComponentScan("com.scan.controll") 需要扫描的包路径
 */
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("老王", "https://www.baidu.me", "baidu_666@icloud.com");
        return new ApiInfo("Blog前台API接口",//大标题 title
                "Swagger测试demo",//小标题
                "0.0.1",//版本
                "www.baidu.com",//termsOfServiceUrl
                contact,//作者
                "Blog",//链接显示文字
                "https://www.baidu.me"//网站链接
        );
    }
}
