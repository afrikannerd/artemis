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
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author afrikannerd <https://github.com/afrikannerd>
 * @version "0.1"
 */
public class ServerTaskManager extends Thread  {

    private DataInputStream incoming;
    private DataOutputStream outgoing;
    private Socket socket;
    ServerTaskManager(Socket socket, DataInputStream input, DataOutputStream output) {
        this.incoming = input;
        this.outgoing = output;
        this.socket = socket;
    }
    
    @Override
    public void run() 
    {
        String received;
        while(true){
            try {
                outgoing.writeUTF("Hello you are connected at " + socket + " ... Say something to me");
                received = incoming.readUTF();
                if("quit".equalsIgnoreCase(received)){
                    System.out.println("You opted to quit the connection " + this.socket);
                    System.out.println("Closing connection " + this.socket + " started at " +  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()));
                    Thread.sleep(10000);
                    Server.userList.remove(this);
                    Server.userList.forEach((user) -> {
                        System.out.println(user.getId() + " is online");
                    });
                    this.socket.close();
                    
                    System.out.println("Connection Closed at " +  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()));
                    
                    break;
                }
                outgoing.writeUTF("Garbage in Garbage Out "+received);
                
            } catch (IOException ex) {
                
            } catch (InterruptedException ex) {
                
            }
        }
        
        try{
            outgoing.close();
            incoming.close();
            socket.close();
        }catch(IOException ex){
            
        }
    }

    
    
}
