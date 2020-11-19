package packet;

import java.io.ByteArrayOutputStream;
import java.util.PriorityQueue;

public class FileBuilder {
    protected PriorityQueue<BodyPacket> fileData = new PriorityQueue<BodyPacket>((BodyPacket x, BodyPacket y) ->  {return x.getPacketNumber() - y.getPacketNumber(); });
    protected String fileName;
    int numPackets = 0;
    private boolean finishedWriting = false;

    public FileBuilder() {

    }

    public boolean addPacket(Packet p){
        if (p instanceof HeaderPacket) {
            addHeader((HeaderPacket) p);
        } else if (p instanceof BodyPacket){
            addBody((BodyPacket) p);
        }

        return this.isComplete();
        // Returns true if the builder is ready to write to a file.
    }

    protected boolean addHeader(HeaderPacket p){

        fileName = p.getFileName();
        return true;
    }

    protected boolean addBody(BodyPacket p){
        if(p.isFinal()){
            numPackets = p.getPacketNumber() + 1;
        }
        fileData.add(p);

        return true;
    }

    public byte[][] getFileBody() {

        return fileData.stream()
            .map((BodyPacket x) -> x.getBodyData())
            .toArray(byte[][]::new);

    }

    public ByteArrayOutputStream getFileBodyStream() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        fileData.stream()
            .map((BodyPacket x) -> x.getBodyData())
            .forEach((byte[] b) -> output.write(b, 0, b.length));
        
        return output;
    }

    public boolean isComplete(){
        return fileName != null
            && numPackets != 0
            && fileData.size() == numPackets;
    }

    public boolean isFinishedWriting(){
        return finishedWriting;
    }

    public String getFileName(){
        return fileName;
    }

    public void writeToFile() {

        finishedWriting = true;
    }

}
