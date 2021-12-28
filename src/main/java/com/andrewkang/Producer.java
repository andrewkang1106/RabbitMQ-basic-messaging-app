package com.andrewkang;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //need connection to rabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        //surround in try so that automatically close connection even if exception thrown
        try(Connection connection = factory.newConnection()){ //connection is physical socket connection to rabbitMQ
           //API (sending, consuming done through channel)
            Channel channel = connection.createChannel();
            //can call queueDeclare as many times as you want. If queue exists w/ same name, will skip. else create.
            channel.queueDeclare("hello-world", false, false, false, null);
            String message = "test string" + LocalDateTime.now();
            // Empty string exhcnages --> default exchanges. (default is direct exchange)
            channel.basicPublish("", "hello-world", false, null, message.getBytes());

            //to look inside the queue for messages
            //C:\Program Files\RabbitMQ Server\rabbitmq_server-3.9.11\sbin>rabbitmqctl list_queues
    
            System.out.println("msg has been sent");

        }
    }
}
