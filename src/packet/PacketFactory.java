package packet;

import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketFactory {

    public static Packet parsePacket(DatagramPacket inputPacket) {
        Packet output;
        byte[] inputData = inputPacket.getData();
        int inputLength = inputPacket.getLength();

        byte statusByte = inputData[0];
        byte fileId = inputData[1];

        if (statusByte % 2 == 0) {
            String fileName = new String(inputData, 2, inputLength - 2);
            output = new HeaderPacket(fileId, fileName);
            output.bodyData = Arrays.copyOfRange(inputData, 2, inputLength);
        } else {
            int packetNumber = (256 * Byte.toUnsignedInt(inputData[2])) + Byte.toUnsignedInt(inputData[3]);
            output = new BodyPacket(fileId, packetNumber, (statusByte % 4 == 3));
            output.bodyData = Arrays.copyOfRange(inputData, 4, inputLength);
        }

        return output;
    }
    
}
