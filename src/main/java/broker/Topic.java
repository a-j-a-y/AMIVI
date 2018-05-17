package broker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
////????????????set the memory mapped file as well for new segment????????????

class Topic {
    private String topicName;   // this topic's name
    private String defaultPath = "defaultPath";        // path without topic name
    private Deque<Segment> segments = new ArrayDeque<>();  // 'Double Ended Queue' of all segments in this topic
    private BrokerProperties props;

    Topic (String topicName, OffsetRecord offsetRecord) throws IOException {
        this.topicName = topicName;
        this.props = props;
        // creates the first segment for this new topic
        if (createTopicPath()) {
            segments.push(createSegment());
        } else {
            System.out.println("\nTOPIC ERROR: error in setting the new topic path\n");
        }
    }

    // create the folder for this topic. this folder will contain all the segment files of this topic
    private boolean createTopicPath() {
        return new File(defaultPath+topicName).mkdirs();
    }

    BrokerProperties getTopicProps() {
        return props;
    }

    // gets the active segment, i.e here the last segment of the segment-queue
    Segment getActiveSegment() {
        return segments.getLast();
    }

    // creates a new active segment for the current topic and sets the necessary variables inside the segment.
    Segment createSegment() {
        Segment newSegment = null;
        try {
            // create the very first segment of this new topic
            newSegment = new Segment("SEGMENT-NAME", defaultPath + topicName, defaultPath + topicName + "MM-FILE-NAME", props);
            return newSegment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Deque<Segment> getSegments() {
        return segments;
    }
}
