package packet;

public abstract class Packet {
    
    private byte fileId;
    protected byte[] bodyData;

    public byte getFileId() {
        return fileId;
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    protected Packet(byte fileId) {
        this.fileId = fileId;
    }

    protected Packet() {

    }


    // add a void method to add the body data, HeaderPacket makes it into a string there?
    // no, that's not right, because the header packet needs 1 more byte of data 

    
}
