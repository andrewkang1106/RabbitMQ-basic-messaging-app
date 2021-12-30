package com.andrewkang;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

//Will be implementing as more modified version of Producer w/ more complex strings represented w/ use of Thread.sleep().
public class NewTask {
    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            //String message = "test string" + LocalDateTime.now();
            String message = String.join(" ", args);

            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("msg: '"+ message + "' has been sent" + LocalDateTime.now());
        }
    }
}
