/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaif.loggerjmx;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 *
 * @author if
 */
public class CommandLineBean {

    private String jdbcUrl;
    private String login;
    private String password;
    
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public CommandLineBean() {
        Consumer<String> action = null;

        for (String s : Runner.getArgs()) {
            if (action != null) {
                action.accept(s);
                action = null;
                continue;
            }
            if (s.equals("-jdbcUrl")) {
                action = this::setJdbcUrl;
            } else if (s.equals("-login")) {
                action = this::setLogin;
            } else if (s.equals("-password")) {
                action = this::setPassword;
            }else{
                throw new IllegalArgumentException(String.format("Unknown parameter:%s", s));
            }
        }
        if (jdbcUrl == null || login == null || password == null) {
            throw new IllegalArgumentException("Not all parameters are set");
        }
    }

    public String getJdbcUrl() {
        log.trace("JDBC has required; it has value of {}", jdbcUrl);
        return jdbcUrl;
    }

    Predicate<String> matcher = Pattern.compile("^jdbc:postgresql://").asPredicate();
    public void setJdbcUrl(String jdbcUrl) {
        if(!matcher.test(jdbcUrl)){
            throw new IllegalArgumentException(String.format("Wrong jdbc url:%s, it must start 'jdbc:postgresql'", jdbcUrl));
        }
        this.jdbcUrl = jdbcUrl;
        log.trace("JDBC has been set; it has value of {}", jdbcUrl);
    }

    public String getLogin() {
        log.trace("login has required; it has value of {}", login);
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        log.trace("login has been set; it has value of {}", login);
    }

    public String getPassword() {
        log.trace("password has required; it has value of {}", password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        log.trace("password has been set; it has value of {}", password);
    }

}
