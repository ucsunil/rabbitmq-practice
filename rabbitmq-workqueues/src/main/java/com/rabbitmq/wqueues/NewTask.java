package com.rabbitmq.wqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Sunil on 3/8/2016.
 */
public class NewTask {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // For now create own arguments
        if(args == null || args.length < 3) {
            args = new String[] {"lorem...", "ipsum...",
            "dolor...", "sit...", "amet...", "consecteteur..."};
        }

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = getMessage(args);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if(strings.length < 1) {
            return "Hello World!";
        }
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if(length == 0)
            return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for(int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
