package packet;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class FileBuilder {
    protected PriorityQueue<BodyPacket> fileData = new PriorityQueue<BodyPacket>((BodyPacket x, BodyPacket y) ->  {return x.getPacketNumber() - y.getPacketNumber(); });
    protected String fileName;
    int numPackets = 0;

    public FileBuilder() {

    }

    public boolean addPacket(Packet p){
        if (p instanceof HeaderPacket) {
            addHeader((HeaderPacket) p);
        } else if (p instanceof BodyPacket){
            addBody((BodyPacket) p);
        }

        return true;
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

    public boolean isComplete(){
        return fileName != null
            && numPackets != 0
            && fileData.size() == numPackets;
    }

    public String getFileName(){
        return fileName;
    }

}
