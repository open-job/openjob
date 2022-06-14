package io.openjob.server.openapi.controller;

import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {
    private final JobDAO jobDAO;

    @Autowired
    public IndexController(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    @GetMapping("/test")
    public String index() {
        return "openapi" + jobDAO.save(new Job());
    }
}
