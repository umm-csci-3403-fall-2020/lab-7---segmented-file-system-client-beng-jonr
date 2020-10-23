package packet;

class BodyPacket extends Packet {
    
    protected int packetNumber;
    protected boolean isFinal;
    Byte[] bodyData; //Could possibly be in superclass, and HeaderPacket just calculates it as a string?

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