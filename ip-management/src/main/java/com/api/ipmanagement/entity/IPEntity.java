package com.api.ipmanagement.entity;

/**
 *
 * @author asus
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
 
@Entity
@Table(name = "ip")
public class IPEntity {
 
    @Id
    @Column(name = "ip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
 
    @NotNull
    @Column(name = "ip_address")
    String ipAddress;
 
    @NotNull
    @Column(name = "status")
    String status;
 
    public Long getId() {
        return Id;
    }
 
    public void setId(Long id) {
        Id = id;
    }
 
    public String getIP() {
        return ipAddress;
    }
    
    public void setIP(String ipAddress) {
        this.ipAddress = ipAddress;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
}
