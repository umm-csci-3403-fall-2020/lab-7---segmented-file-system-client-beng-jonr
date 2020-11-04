package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import packet.Packet;
import packet.PacketFactory;

public class FileRetriever {

        DatagramSocket sock;
        FileManager manager = new FileManager();
        String serverName;
        int port;

        public FileRetriever(String server, int port) throws UnknownHostException, IOException {
                // Save the server and port for use in `downloadFiles()`
                //...

                sock = new DatagramSocket();
                serverName = server;
                this.port = port;
	}

	public void downloadFiles() throws IOException {
                // Do all the heavy lifting here.
                // This should
                //   * Connect to the server
                //   * Download packets in some sort of loop
                //   * Handle the packets as they come in by, e.g.,
                //     handing them to some PacketManager class
                // Your loop will need to be able to ask someone
                // if you've received all the packets, and can thus
                // terminate. You might have a method like
                // PacketManager.allPacketsReceived() that you could
                // call for that, but there are a bunch of possible
                // ways.

                InetAddress address = InetAddress.getByName(serverName);
                DatagramPacket pack = new DatagramPacket(new byte[1028], 1028, address, port);
                sock.send(pack);
        
                while(!(manager.isComplete())){
                        sock.receive(pack);
                        Packet inputPacket = PacketFactory.parsePacket(pack);
                        manager.addPacket(inputPacket);
                }

                sock.close();
	}

}
