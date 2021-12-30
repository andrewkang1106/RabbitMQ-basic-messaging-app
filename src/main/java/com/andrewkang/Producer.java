package com.andrewkang;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class Producer {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        //need connection to rabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); //Prob not necessary if only running on local host, but necessary if setting custom?
        //surround in try so that automatically close connection even if exception thrown
        try(Connection connection = factory.newConnection()){ //connection is physical socket connection to rabbitMQ
           //API (sending, consuming done through channel)
            Channel channel = connection.createChannel();
            //can call queueDeclare as many times as you want. If queue exists w/ same name, will skip. else create.
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "test string" + LocalDateTime.now();
            // Empty string exchanges --> default exchanges. (default is direct exchange)
            channel.basicPublish("", QUEUE_NAME, false, null, message.getBytes());

            //to look inside the queue for messages
            //C:\Program Files\RabbitMQ Server\rabbitmq_server-3.9.11\sbin>rabbitmqctl list_queues
            System.out.println("msg: '"+ message + "' has been sent");
        }
    }
}
