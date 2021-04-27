package com.example.springbootrmiserver.Config;

import com.example.springbootrmiserver.Service.ShellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class RMIServer {
    @Autowired
    ShellService shellService;

    public RmiServiceExporter getRmiServiceExporter(){
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("shellService");
        rmiServiceExporter.setService(shellService);
        rmiServiceExporter.setServiceInterface(ShellService.class);
        rmiServiceExporter.setRegistryPort(9099);
        return rmiServiceExporter;
    }
}
