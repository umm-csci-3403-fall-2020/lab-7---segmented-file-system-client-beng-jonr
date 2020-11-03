package segmentedfilesystem;

import java.io.File;
import java.util.concurrent.ConcurrentSkipListMap;

import packet.FileBuilder;
import packet.Packet;

public class FileManager {
    public final int NUM_FILES_EXPECTED;
    private ConcurrentSkipListMap<Byte, FileBuilder> files;
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
        

        return true; 
        // adding to some structures returns true, but other times,
        // such as when adding to a map, it returns the element added.
        // Currently this function uses the first convention, but it
        // could be switched to the second without causing any problems.
    }

    public boolean isComplete() {
        
        
        return false; // Placeholder
    }


}
