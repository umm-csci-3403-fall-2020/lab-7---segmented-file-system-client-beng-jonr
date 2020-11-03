package packet;

import static org.junit.Assert.*;

import java.net.DatagramPacket;
import java.util.Random;

import org.junit.Test;

import packet.*;

import java.lang.System.*;

public class PacketCreationTest {
    
    @Test
    public void packetCreated() {
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        Random r = new Random();
        
        r.nextBytes(inputPacket);

        dummyInput.setData(inputPacket);
        dummyInput.setLength(1028);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertNotNull(createdPacket);
    }

    @Test
    public void headerPacketCreated() {
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        Random r = new Random();

        r.nextBytes(inputPacket);

        inputPacket[0] = new Integer(2).byteValue();
        
        dummyInput.setData(inputPacket);
        dummyInput.setLength(1028);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertNotNull(createdPacket);
    }

    @Test
    public void bodyPacketCreated() {
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        Random r = new Random();

        r.nextBytes(inputPacket);

        inputPacket[0] = new Integer(1).byteValue();
        inputPacket[1] = new Integer(1).byteValue();

        dummyInput.setData(inputPacket);
        dummyInput.setLength(1028);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertNotNull(createdPacket);
        assertTrue("The created packet should be a body packet", createdPacket instanceof BodyPacket);
        assertFalse("The created packet should not be the last packet", ((BodyPacket)createdPacket).isFinal());
        assertTrue("The created packet should have file id 1", createdPacket.getFileId() == new Integer(1).byteValue());

    }

    @Test
    public void finalBodyPacketCreated() {
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        Random r = new Random();

        r.nextBytes(inputPacket);

        inputPacket[0] = new Integer(3).byteValue();
        inputPacket[1] = new Integer(1).byteValue();
        
        dummyInput.setData(inputPacket);
        dummyInput.setLength(1028);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertNotNull(createdPacket);
        assertTrue("The created packet should be a body packet", createdPacket instanceof BodyPacket);
        assertTrue("The created packet should be the last packet", ((BodyPacket)createdPacket).isFinal());
        assertTrue("The created packet should have file id 1", createdPacket.getFileId() == new Integer(1).byteValue());

    }

    @Test
    public void headerPacketContainsCorrectNameSingleByte() {
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        inputPacket[0] = new Integer(2).byteValue();
        inputPacket[1] = new Integer(1).byteValue();
        inputPacket[2] = new String("a").getBytes()[0];

        dummyInput.setData(inputPacket);
        dummyInput.setLength(3);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertNotNull(createdPacket);
        assertTrue("The created packet should be a header packet", createdPacket instanceof HeaderPacket);
        assertEquals("a", ((HeaderPacket) createdPacket).getFileName());
        assertTrue("The created packet should have file id 1", createdPacket.getFileId() == new Integer(1).byteValue());
    }

    @Test
    public void headerPacketContainsCorrectNameMultiByte() {
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        byte[] packetName = new String("reallyRatherExcessivelyLongFileName").getBytes();
        inputPacket[0] = new Integer(2).byteValue();
        inputPacket[1] = new Integer(1).byteValue();
        System.arraycopy(packetName, 0, inputPacket, 2, packetName.length);

        dummyInput.setData(inputPacket);
        dummyInput.setLength(packetName.length + 2);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertNotNull(createdPacket);
        assertTrue("The created packet should be a header packet", createdPacket instanceof HeaderPacket);
        assertEquals("reallyRatherExcessivelyLongFileName", ((HeaderPacket) createdPacket).getFileName());
        assertTrue("The created packet should have file id 1", createdPacket.getFileId() == new Integer(1).byteValue());
    }

    @Test
    public void bodyPacketContainsCorrectBody(){
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        Random r = new Random();

        byte[] bodyValue = new byte[1024];
        inputPacket[0] = new Integer(1).byteValue();
        inputPacket[1] = new Integer(1).byteValue();
        inputPacket[2] = ((Integer)r.nextInt(255)).byteValue();
        inputPacket[3] = ((Integer)r.nextInt(255)).byteValue();
        int packetNumber = (256 * Byte.toUnsignedInt(inputPacket[2])) + Byte.toUnsignedInt(inputPacket[3]);

        r.nextBytes(bodyValue);
        System.arraycopy(bodyValue, 0, inputPacket, 4, 1024);
        
        dummyInput.setData(inputPacket);
        dummyInput.setLength(1028);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertArrayEquals("Correct body is maintained", bodyValue, createdPacket.getBodyData());
        assertEquals("Correct packet number is maintained", packetNumber, ((BodyPacket)createdPacket).getPacketNumber());
        assertTrue("The created packet should have file id 1", createdPacket.getFileId() == new Integer(1).byteValue());

    }

    @Test
    public void smallerFinalPacketContainsCorrectBody(){
        DatagramPacket dummyInput = new DatagramPacket(new byte[1028], 1028);
        byte[] inputPacket = new byte[1028];
        Random r = new Random();

        int dataSize = r.nextInt(1023) + 1; //guaranteed to not be full size or empty

        byte[] bodyValue = new byte[dataSize];
        inputPacket[0] = new Integer(3).byteValue();
        inputPacket[1] = new Integer(1).byteValue();
        inputPacket[2] = ((Integer)r.nextInt(255)).byteValue();
        inputPacket[3] = ((Integer)r.nextInt(255)).byteValue();
        int packetNumber = (256 * Byte.toUnsignedInt(inputPacket[2])) + Byte.toUnsignedInt(inputPacket[3]);

        r.nextBytes(bodyValue);
        System.arraycopy(bodyValue, 0, inputPacket, 4, dataSize);
        
        dummyInput.setData(inputPacket);
        dummyInput.setLength(dataSize + 4);

        Packet createdPacket = PacketFactory.parsePacket(dummyInput);
        assertArrayEquals("Correct body is maintained", bodyValue, createdPacket.getBodyData());
        assertEquals("Correct packet number is maintained", packetNumber, ((BodyPacket)createdPacket).getPacketNumber());
        assertTrue("The created packet should have file id 1", createdPacket.getFileId() == new Integer(1).byteValue());
    }
}

