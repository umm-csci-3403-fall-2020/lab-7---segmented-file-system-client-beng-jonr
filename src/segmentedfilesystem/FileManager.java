package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListMap;

import packet.FileBuilder;
import packet.Packet;

public class FileManager {
    public final int NUM_FILES_EXPECTED;
    private ConcurrentSkipListMap<Byte, FileBuilder> files = new ConcurrentSkipListMap<>();
    // ConcurrentSkipListMaps are able to add elements atomically.  This is useful,
    // because file input is frequently streamed in and it could be useful to be able
    // to process packets concurrently.  The map is sorted, which isn't actually
    // a feature we don't particularly care about or even want, but since it isn't
    // actively detrimental, using this data structure is still a good idea for the
    // aforementioned reasons.

    public FileManager(int filesExpected){
        NUM_FILES_EXPECTED = filesExpected;
    }

    public FileManager() {
        NUM_FILES_EXPECTED = 3; //per problem specification
    }

    public boolean addPacket (Packet toAdd){

        Byte targetFile = toAdd.getFileId();
        FileBuilder targetedBuilder = files.putIfAbsent(targetFile, new FileBuilder());
        if(targetedBuilder.addPacket(toAdd)) {
            // putIfAbsent returns the value corresponding to the key provided,
            // and addPacket returns true if the fileBuilder is now done
            FileBuilderRunnable builderRunnable = new FileBuilderRunnable(targetedBuilder);
            Thread writingThread = new Thread(builderRunnable);
            writingThread.start();
        }
        

        return true; 
        // adding to some structures returns true, but other times,
        // such as when adding to a map, it returns the element added.
        // Currently this function uses the first convention, but it
        // could be switched to the second without causing any problems.
    }

    public boolean isComplete() {

        if(files.size() != NUM_FILES_EXPECTED) {
            return false;
        }

        for(FileBuilder b: files.values()) {
            if(!(b.isComplete())) {
                return false;
            }
        }
        return true;
    }

    public class FileBuilderRunnable implements Runnable {
        FileBuilder buildFrom;

        public FileBuilderRunnable(FileBuilder builder) {
            this.buildFrom = builder;
        }

        @Override
        public void run() {
            try {
                FileOutputStream outputStream = new FileOutputStream(buildFrom.getFileName(), true);
                buildFrom.getFileBodyStream().writeTo(outputStream);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }


}
