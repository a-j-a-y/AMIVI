package producer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Created by minal on 18/03/18.
 */

public class ProducerRecord {

    /**
     * get topic , key and value from the user
     */

    private String topicName;
    private String key;
    private String value;

    public ProducerRecord(String topicName, String key, String value) {
        this.topicName = topicName;
        this.key = key;
        this.value = value;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

//    ProducerRecord( String topicName, CopyOnWriteArrayList list, int list_size){
//        String topic = topicName;
//        CopyOnWriteArrayList valList = list;
//        int size = list_size;
//    }
//    public static void main(String[] args) throws Exception {
//        if (args.length == 0) {
//            System.out.println("Enter topic name: " + args[0]);
//            return;
//        }
//
//        /**Assign topicName to string variable*/
//        String topicName = args[0].toString();
//
//        /**
//         * input keys
//         */
//        ArrayList<String> keyArr = new ArrayList<String>();
//        System.out.println("Enter the key for topic " + topicName + ": ");
//        Scanner input1 = new Scanner(System.in);
//        String key = input1.nextLine();
//        keyArr.add(key);
//
//        /**
//         * inout values
//         */
//        ArrayList<String> valArr = new ArrayList<String>();
//        System.out.println("Enter the value for topic " + topicName + ": ");
//        Scanner input2 = new Scanner(System.in);
//        String val = input2.nextLine();
//        valArr.add(val);
//
//    }
//
//    public


}











