package com.andrewkang;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("hello", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL + C");

        channel.basicConsume("hello", true, (consumerTag, delivery) -> {
            String m = new String(delivery.getBody(), "UTF-8");
            System.out.println("I just received a message = " + m);
        }, consumerTag -> {}); //consumerTag is a tag you get, an ID, from the server when you connect to the server and open up a channel

        /*
        * DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        *   String message = new String(delivery.getBody(), "UTF-8");
        *   System.out.println(" [x] Received '" + message + "'");
        * };
        * channel.basicConsume(QUEUE_Name, true, deliverCallback, consumerTag -> {});
        *
        * //we're about to tell the server to deliver us the messages from the queue. Since it will push us messages asynchronously,
        * we provide a callback in the form of an object that will buffer the msg until we're ready to use them
        * This is what the DeliverCallback subclass does.
        *
        * */

    }
}
