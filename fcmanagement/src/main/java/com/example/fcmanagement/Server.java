package com.example.fcmanagement;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Server {

    private final static String QUEUE = "messages";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("prawn-01.rmq.cloudamqp.com");
        factory.setUsername("ahpmdfzr");
        factory.setPassword("peFA7z9DlvjLU6N_IgUGg2U6g_r9xo-f");
        factory.setVirtualHost("ahpmdfzr");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE, false, false, false, null);

            System.out.println("Server started. Type the message: ");

            try (Scanner in = new Scanner(System.in)) {
                String message;

                while (true) {
                    System.out.println("Type the message to the client (or type exit to leave)...");

                    message = in.nextLine();

                    if ("exit".equalsIgnoreCase(message)) {
                        System.out.println("Server Finished");
                        break;
                    }

                    channel.basicPublish("", QUEUE, null, message.getBytes());
                    System.out.println("Sent: " + message);
                }
            }
        }
    }
}
