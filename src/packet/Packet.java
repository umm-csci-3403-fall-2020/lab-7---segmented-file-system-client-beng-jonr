package packet;

public abstract class Packet {
    
    private Byte fileId;

    public Byte getFileId() {
        return fileId;
    }

    protected Packet(Byte fileId) {
        this.fileId = fileId;
    }

    protected Packet() {

    }


    // add a void method to add the body data, HeaderPacket makes it into a string there?
    // no, that's not right, because the header packet needs 1 more byte of data 

    
}
