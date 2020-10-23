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

    }

    // add a void method to add the body data, HeaderPacket makes it into a string there?
    // no, that's not right, because the header packet needs 1 more byte of data 

    protected class HeaderPacket extends Packet {

        private String fileName;

        protected HeaderPacket (Byte fileId) {
            super(fileId);
        }

        protected HeaderPacket (Byte fileId, String fileName) {
            super(fileId);
            this.fileName = fileName;
        }
    }

    protected class BodyPacket extends Packet {
        
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
    
}
