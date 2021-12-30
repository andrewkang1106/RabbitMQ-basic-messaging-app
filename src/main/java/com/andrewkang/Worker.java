package com.andrewkang;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker {

    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL + C");

        channel.basicConsume("hello-world", true, (consumerTag, delivery) -> {
            String m = new String(delivery.getBody(), "UTF-8");
            System.out.println("I just received a message = " + m);
        }, consumerTag -> {}); //consumerTag is a tag you get, an ID, from the server when you connect to the server and open up a channel

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
           String message = new String(delivery.getBody(), "UTF-8");
           System.out.println(" [x] Received '" + message + "'");
           try{
               doWork(message);
           } finally {
               System.out.println(" [x] Done");
           }
        };
        boolean autoAck = true;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });


        //we're about to tell the server to deliver us the messages from the queue. Since it will push us messages asynchronously,
        //we provide a callback in the form of an object that will buffer the msg until we're ready to use them
        //This is what the DeliverCallback subclass does.
    }
    private static void doWork(String task){
        for (char ch: task.toCharArray()){
            if (ch == '.'){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
