package packet;

import java.util.PriorityQueue;

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

    public byte[] getFileBody(){

        return null;
    }

    public boolean isComplete(){

        return false;
    }

    public String getFileName(){
        return fileName;
    }

}
