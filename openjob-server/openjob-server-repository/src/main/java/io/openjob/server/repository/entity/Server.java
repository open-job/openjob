package io.openjob.server.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "server")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Server IP
     */
    private String ip;

    /**
     * Server address
     */
    @Column(name = "akka_address")
    private String akkaAddress;

    /**
     * Server status
     */
    private Integer status;

    /**
     * Create time
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * Update time
     */
    @Column(name = "update_time")
    private Integer updateTime;
}
