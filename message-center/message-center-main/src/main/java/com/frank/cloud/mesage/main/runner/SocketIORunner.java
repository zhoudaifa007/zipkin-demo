package com.frank.cloud.mesage.main.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component  
public class SocketIORunner implements CommandLineRunner {
	
    private final SocketIOServer server;  
  
    @Autowired 
    public SocketIORunner(SocketIOServer server) 
    {  
        this.server = server;  
    }  
  
    @Override  
    public void run(String... args) throws Exception {  
        server.start();  
    }  
}
