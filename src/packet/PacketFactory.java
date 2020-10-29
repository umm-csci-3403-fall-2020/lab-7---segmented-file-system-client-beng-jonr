package packet;

import java.net.DatagramPacket;

public class PacketFactory {

    public static Packet parsePacket(DatagramPacket inputPacket) {
        Packet output;
        byte[] inputData = inputPacket.getData();
        int inputLength = inputPacket.getLength();

        byte statusByte = inputData[0];
        byte fileId = inputData[1];

        output = null; //obviously just a placeholder


        return output;
    }
    
}
