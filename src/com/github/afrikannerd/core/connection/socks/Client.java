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
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author afrikannerd <https://github.com/afrikannerd>
 * @version "0.1"
 */
public class Client {
    
    public static void main(String[]args){
        Scanner in = new Scanner(System.in);
        try{
        Socket socket = new Socket("127.0.0.1",8000);
        DataInputStream incoming = new DataInputStream(socket.getInputStream());
        DataOutputStream outgoing = new DataOutputStream(socket.getOutputStream());
        while(true){
            System.out.println(incoming.readUTF());
            if(!Server.userList.isEmpty()){
                for (ServerTaskManager user: Server.userList)
                    System.out.println(user.getId() + " is online");
            }
            
            String data = in.nextLine();
            outgoing.writeUTF(data);
            if("quit".equalsIgnoreCase(data)){
                System.out.println("Closing connection");
                socket.close();
                System.out.println("Connection closed");
                break;
            }
            String received = incoming.readUTF();
            System.out.println("Incoming Message: " + received);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
