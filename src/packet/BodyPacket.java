package packet;

public class BodyPacket extends Packet {
    
    protected int packetNumber;
    protected boolean isFinal;

    protected BodyPacket(Byte fileId, int packetNumber) {
        super(fileId);
        this.packetNumber = packetNumber;
    }

    protected BodyPacket(Byte fileId, int packetNumber, boolean isFinal) {
        super(fileId);
        this.packetNumber = packetNumber;
        this.isFinal = isFinal;
    }

    public int getPacketNumber(){
        return packetNumber;
    }

    public boolean isFinal(){
        return isFinal;
    }

}