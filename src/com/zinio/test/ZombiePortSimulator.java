package com.zinio.test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZombiePortSimulator {
	private boolean noRead;
    private int listenerPort;

    public ZombiePortSimulator(boolean noRead, int listenerPort) {
        this.noRead = noRead;
        this.listenerPort = listenerPort;
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream dataInputStream = null;
        try {
            serverSocket = new ServerSocket(listenerPort);
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            int counter1 = 0;
            int counter2 = 0;
            int counter3 = 0;
            while (true) {
                if (noRead) {
                    continue;
                }
                if (++counter1 % 32 == 0) {
                    System.out.print((++counter2 % 32 != 0) ? "." : ++counter3 + "k");
                }
                dataInputStream.readByte();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (null != dataInputStream) {
                try {
                    dataInputStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        boolean noRead = !args[0].equalsIgnoreCase("read");
        int listenerPort = Integer.parseInt(args[1]);
        ZombiePortSimulator simulator = new ZombiePortSimulator(noRead, listenerPort);
        simulator.run();
    }
}
