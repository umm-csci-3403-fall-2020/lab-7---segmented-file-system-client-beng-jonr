package packet;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;


public class FileBuilderTest {
    
    @Test
    public void fileBuilderCreated() {
        FileBuilder testBuilder = new FileBuilder();

        assertNotNull(testBuilder);
    }

    @Test
    public void addHeaderPacketDirectly() {
        FileBuilder testBuilder = new FileBuilder();

        HeaderPacket header = new HeaderPacket(new Integer(0).byteValue(), "testName");
        testBuilder.addHeader(header);

        assertEquals("testName", testBuilder.getFileName());
    }

    @Test
    public void addBodyPacketDirectly() {
        FileBuilder testBuilder = new FileBuilder();
        Random r = new Random();

        byte[] fileData = new byte[1024];
        r.nextBytes(fileData);
        
        BodyPacket body = new BodyPacket(new Integer(0).byteValue(), 0, false);
        body.bodyData = fileData;

        testBuilder.addBody(body);

        assertTrue(testBuilder.fileData.contains(body));
    }

    @Test
    public void addMultipleBodyPacketsDirectly(){
        FileBuilder testBuilder = new FileBuilder();
        Random r = new Random();

        byte[] fileData = new byte[1024];
        BodyPacket[] toAdd = new BodyPacket[5];

        for(int i = 0; i < 5; i++){
            r.nextBytes(fileData);
        
            BodyPacket body = new BodyPacket(new Integer(0).byteValue(), i, false);
            body.bodyData = fileData;
            
            toAdd[i] = body;
            testBuilder.addBody(body);
        }

        //assertArrayEquals(toAdd, (BodyPacket[])testBuilder.fileData.toArray());
        for(int i = 0; i < 5; i++){
            assertEquals(toAdd[i], (BodyPacket)testBuilder.fileData.toArray()[i]);
        }
    }

    @Test
    public void addHeaderPacket(){
        FileBuilder testBuilder = new FileBuilder();

        HeaderPacket header = new HeaderPacket(new Integer(0).byteValue(), "testName");
        testBuilder.addPacket(header);

        assertEquals("testName", testBuilder.getFileName());
    }

    @Test
    public void addBodyPacket(){
        FileBuilder testBuilder = new FileBuilder();
        Random r = new Random();

        byte[] fileData = new byte[1024];
        r.nextBytes(fileData);
        
        BodyPacket body = new BodyPacket(new Integer(0).byteValue(), 0, false);
        body.bodyData = fileData;

        testBuilder.addPacket(body);

        assertTrue(testBuilder.fileData.contains(body));
    }

    @Test
    public void addMultipleBodyPackets(){
        FileBuilder testBuilder = new FileBuilder();
        Random r = new Random();

        byte[] fileData = new byte[1024];
        BodyPacket[] toAdd = new BodyPacket[5];

        for(int i = 0; i < 5; i++){
            r.nextBytes(fileData);
        
            BodyPacket body = new BodyPacket(new Integer(0).byteValue(), i, false);
            body.bodyData = fileData;
            
            toAdd[i] = body;
            testBuilder.addPacket(body);
        }

        //assertArrayEquals(toAdd, (BodyPacket[])testBuilder.fileData.toArray());
        for(int i = 0; i < 5; i++){
            assertEquals(toAdd[i], (BodyPacket)testBuilder.fileData.toArray()[i]);
        }
    }

    @Test
    public void addMixedPackets(){
        FileBuilder testBuilder = new FileBuilder();
        Random r = new Random();

        byte[] fileData = new byte[1024];
        BodyPacket[] toAdd = new BodyPacket[5];

        for(int i = 0; i < 5; i++){
            r.nextBytes(fileData);
        
            BodyPacket body = new BodyPacket(new Integer(0).byteValue(), i, false);
            body.bodyData = fileData;
            
            toAdd[i] = body;
            testBuilder.addBody(body);
        }

        HeaderPacket header = new HeaderPacket(new Integer(0).byteValue(), "testName");
        testBuilder.addPacket(header);

        assertEquals("testName", testBuilder.getFileName());
        //assertArrayEquals(toAdd, (BodyPacket[])testBuilder.fileData.toArray());
        for(int i = 0; i < 5; i++){
            assertEquals(toAdd[i], (BodyPacket)testBuilder.fileData.toArray()[i]);
        }
    }

    @Test
    public void returnFullBodyData(){
        FileBuilder testBuilder = new FileBuilder();
        Random r = new Random();

        byte[] fileData = new byte[1024];
        BodyPacket[] toAdd = new BodyPacket[5];
        byte[][] allAdded = new byte[5][1024];
        //byte[] allAdded = new byte[1024*5];

        int position = 0;
        for(int i = 0; i < 5; i++){
            r.nextBytes(fileData);
        
            BodyPacket body = new BodyPacket(new Integer(0).byteValue(), i, i == 4);
            body.bodyData = fileData;
            
            toAdd[i] = body;
            //allAdded[i] = fileData;
            //System.arraycopy(fileData, 0, allAdded, position, 1024);
            //position += 1024;
            allAdded[i] = fileData;
            testBuilder.addBody(body);
        }

        byte[][] outputData = testBuilder.getFileBody();
        assertEquals(5, outputData.length);
        for(int i = 0; i < outputData.length; i++) {
            assertArrayEquals(allAdded[i], outputData[i]);
        }
        /*
        byte[][] fileBody = testBuilder.getFileBody();
        for (int i = 0; i < 5; i++){
            assertArrayEquals(allAdded[i], fileBody[i]);
        }
        */

    }
}
