/*
 * Copyright (C) 2018 afrikannerd <https://github.com/afrikannerd>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.afrikannerd.core.connection.socks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author afrikannerd <https://github.com/afrikannerd>
 * @version "0.1"
 */
public class Server{
    /* port number through which the connection will be established */
    private int port;
    
    private ServerSocket serverSock ;
    private volatile Socket socket = null;
    /*Stores list of all ServerTaskManager class instances managing clients connected to the server*/
    public static volatile List<ServerTaskManager> userList = new ArrayList<>();
    public Server(int port) throws IOException{
        this.port = port;
        
        serverSock =  new ServerSocket(this.port);
        System.out.println("Waiting for Connections");
        while(true){
            socket = serverSock.accept();
            System.out.println("New Connection " + socket);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Thread userThread = new ServerTaskManager(socket,input,output);
            userThread.start();
            userList.add((ServerTaskManager) userThread);
            if(!userList.isEmpty()){
                userList.forEach((user)->{
                    System.out.println(user.getName().concat(" is online"));
                });
            }
        }
    }
    
    public static void main(String...args){
        try{
           HashMap<String,Double> hash = new HashMap<>();
           hash.put("first", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("second", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("third", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("fourth", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("fifth", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("sixth", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("seventh", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.put("eighth", Math.pow(Math.random(), Math.pow(Math.random(), (Math.random()*Math.PI))));
           hash.forEach((k,v)->{
               System.out.println(k+" "+v);
           });
           List<Integer> lst =Arrays.asList(2,3,5,7,85,54,8,96,54,3,2);
           System.out.println(
                   lst.stream().map(k->k).reduce(0,(c,k)->c+k)
           );
            new Server(8000);
        }catch(Exception e){
            
        }
    }

    
}
