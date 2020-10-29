package packet;

public class BodyPacket extends Packet {
    
    protected int packetNumber;
    protected boolean isFinal;

    protected BodyPacket(Byte fileId, int packetNumber) {
        super(fileId);
        this.packetNumber = packetNumber;
    }

    public int getPacketNumber(){
        return packetNumber;
    }

    public boolean isFinal(){
        return isFinal;
    }

}