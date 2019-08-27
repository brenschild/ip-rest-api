package com.api.ipmanagement.controller;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.ipmanagement.entity.IPEntity;
import com.api.ipmanagement.service.IPService;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class IPController {
 
    @Autowired
    IPService sampleService;
 
    @RequestMapping(value = "/ip")
        public List<IPEntity> listIPAddresses() {
        return sampleService.getIPs();
    }
        
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createIPAddress(@RequestParam String cidrBlock) {
        return sampleService.createIPAddresses(cidrBlock);
    }
 
    @RequestMapping(value = "/acquire", method = RequestMethod.PUT)
    public IPEntity acquireAnIP(@RequestParam String ip_address) {
        //return ip_address;
        return sampleService.acquireIP(ip_address);
    }
    
    @RequestMapping(value = "/release", method = RequestMethod.PUT)
    public IPEntity releaseAnIP(@RequestParam String ip_address) {
        
        return sampleService.releaseIP(ip_address);
    }
}
