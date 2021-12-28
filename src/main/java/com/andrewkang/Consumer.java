package com.andrewkang;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        //surround in try so that automatically close connection even if exception thrown
        Connection connection = factory.newConnection(); //connection is physical socket connection to rabbitMQ
        //API (sending, consuming done through channel)
        Channel channel = connection.createChannel();
        //can call queueDeclare as many times as you want. If queue exists w/ same name, will skip. else create.
        channel.queueDeclare("hello-world", false, false, false, null);
        channel.basicConsume("hello-world", true, (consumerTag, delivery) -> {
            String m = new String(delivery.getBody(), "UTF-8");
            System.out.println("I just received a message = " + m);
        }, consumerTag -> {}); //consumerTag is a tag you get, an ID, from the server when you connect to the server and open up a channel
    }
}
