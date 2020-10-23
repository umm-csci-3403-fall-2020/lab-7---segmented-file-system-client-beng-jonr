package packet;

class HeaderPacket extends Packet {

    private String fileName;

    protected HeaderPacket (Byte fileId) {
        super(fileId);
    }

    protected HeaderPacket (Byte fileId, String fileName) {
        super(fileId);
        this.fileName = fileName;
    }
}