package com.zts.educational.admin;

import com.sfsctech.cloud.net.starter.EnableCloudController;
import org.springframework.boot.SpringApplication;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableCloudController(packages = {"com.zts.educational.*.inf"})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

}
