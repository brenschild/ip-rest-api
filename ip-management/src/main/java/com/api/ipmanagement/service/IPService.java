package com.api.ipmanagement.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.api.ipmanagement.entity.IPEntity;
import com.api.ipmanagement.repository.IPRepository;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.persistence.EntityNotFoundException;
 
@Service
public class IPService {
 
    @Autowired
    IPRepository sampleRepo;
 
    public List<IPEntity> getIPs() {
        return (List<IPEntity>) sampleRepo.findAll();
    }
    
    public String createIPAddresses(String ip) {
        IPEntity instance;
        // split block from ip
        String[] ipCDIRSplit = ip.split("/");
        int cidr = Integer.parseInt(ipCDIRSplit[1]);
        
        // return only ip if cidr = 32
        if(cidr == 32)
        {
            instance = new IPEntity();
            instance.setIP(ipCDIRSplit[0]);
            instance.setStatus("available");
            sampleRepo.save(instance);
            return "Success!";
        }
        // find range of ips with cidr
        int ipsNeeded = 32-cidr;
        
        // split ip into sections
        String[] ipSections = ipCDIRSplit[0].split(Pattern.quote("."));
        
       // start with least sig bit and calculate ips
       for(int i = ipSections.length-1; i>=0 ; i--){
           int ipSection = Integer.parseInt(ipSections[i]);
           
           // Plus 1 to the 256 because the range is [0,255]
           int ipsAvailableInSection = 256 - ipSection;
           
           if(ipsNeeded > 0){
               
               // go through and generate IPs
               for(int j=0; j < ipsAvailableInSection; j++){
                   String newIPSection;
                   int num;
                   instance = new IPEntity();
                   instance.setStatus("available");
                   
            
                   num = Integer.parseInt(ipSections[i]) + j;
                   newIPSection = Integer.toString(num);
                   instance.setIP(ipSections[0] +"."+ ipSections[1]
                                      + "." + ipSections[2] + 
                                      "." + newIPSection);
                   sampleRepo.save(instance);
                   ipsNeeded--;
               }
               
               if(Integer.parseInt(ipSections[3]) == 256)
               {
                   int s2;
                   
                   ipSections[3] = "0";
                   
                   s2 = Integer.parseInt(ipSections[2]);
                   s2++;
                   ipSections[2] = Integer.toString(s2);
               }
               
               if(Integer.parseInt(ipSections[2]) == 256)
               {
                   int s1;
                   
                   ipSections[2] = "0";
                   
                   s1 = Integer.parseInt(ipSections[1]);
                   s1++;
                   ipSections[1] = Integer.toString(s1);
               }
               
               if(Integer.parseInt(ipSections[1]) == 256)
               {
                   int s0;
                   
                   ipSections[1] = "0";
                   
                   s0 = Integer.parseInt(ipSections[0]);
                   s0++;
                   ipSections[0] = Integer.toString(s0);
                   
               }
               if(Integer.parseInt(ipSections[0]) == 256)
               {
                   break;
               }
           }
       }
       return "Success";
    }
    
    public IPEntity acquireIP(String ip_address) {
        IPEntity updatedSample;
        List<IPEntity> currentlist;
        long Id=0;
        boolean isFound = false;
        currentlist = (List<IPEntity>) sampleRepo.findAll();
        for(IPEntity i : currentlist)
        {
            if(i.getIP() == null ? ip_address == null : i.getIP().equals(ip_address)){
                Id = i.getId();
                isFound = true;
            }
        }
        if(isFound){
            IPEntity ipInDB = sampleRepo.findById(Id).get();
            
                ipInDB.setStatus("acquired");
                updatedSample = sampleRepo.save(ipInDB);
        } 
        else {
            throw new EntityNotFoundException();
            }
        
        return updatedSample;
    }
    
    public IPEntity releaseIP(String ip_address) {
        IPEntity updatedSample;
        List<IPEntity> currentlist;
        long Id=0;
        boolean isFound = false;
        currentlist = (List<IPEntity>) sampleRepo.findAll();
        for(IPEntity i : currentlist)
        {
            if(i.getIP() == null ? ip_address == null : i.getIP().equals(ip_address)){
                Id = i.getId();
                isFound = true;
            }
        }
        if(isFound){
            IPEntity ipInDB = sampleRepo.findById(Id).get();
            
                ipInDB.setStatus("available");
                updatedSample = sampleRepo.save(ipInDB);
        } 
        else {
            throw new EntityNotFoundException();
            }
        
        return updatedSample;
    }
}
