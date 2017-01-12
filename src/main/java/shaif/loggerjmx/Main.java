/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaif.loggerjmx;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author if
 */
class Main {
    private JdbcTemplate jt;
    private Main self;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Transactional(propagation = Propagation.NESTED)
    public void run() throws InterruptedException{
        int i;
        log.warn("jt classname={}", jt.getClass());
        for(i=0;i<1000000000;i++){
            jt.query("select * from tstfn(?,?+10)", //select n from generate_series(?,?+10) as gs(n)", 
                    rs ->{
                        int n = rs.getInt("n");
                        log.info("Got n={}",n);
                    }
                    , i,i);
            log.debug("here we are");
            Thread.sleep(1000);
        }
    }

    public void setJt(JdbcTemplate jt) {
        this.jt = jt;
    }

    public void setSelf(Main self) {
        this.self = self;
    }

    public void setLog(Logger log) {
        this.log = log;
    }
}
