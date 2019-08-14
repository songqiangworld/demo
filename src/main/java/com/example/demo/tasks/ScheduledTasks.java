package com.example.demo.tasks;


import com.example.demo.config.TestConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @package: cn.com.gmmedicare.datasyn.tasks
 * @author: 陈明磊<minglei.chen@gm-medicare.com>
 * @date: 2017/10/30 20:25
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */

@Component
public class ScheduledTasks {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestConfigProperties testConfigProperties;


    /**
     * 分发 定时任务
     */

   @Scheduled(cron = "${task.distributeCron}")
    public void distribute() {
       System.out.println(testConfigProperties.getName());
       System.out.println(testConfigProperties.getPassword());
    }

}
