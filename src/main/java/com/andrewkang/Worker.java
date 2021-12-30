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
        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL + C");
        channel.basicQos(1); //only accepts 1 unacknowledged msg at a time

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
           String message = new String(delivery.getBody(), "UTF-8");
           System.out.println(" [x] Received '" + message + "'");
           try{
               doWork(message);
           } finally {
               System.out.println(" [x] Done");
               //missing basickAck would eat more memory bc messages will be redelieverd when the client quits but won't be released
               channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
           }
        };
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });

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
