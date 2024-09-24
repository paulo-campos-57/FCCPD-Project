package com.example.fcmanagement;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

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

            System.out.println("Server started.");

            try (Scanner in = new Scanner(System.in)) {

                while (true) {
                    System.out.println("Select the message topic: ");
                    System.out.println("1 - Training day");
                    System.out.println("2 - Player injury");
                    System.out.println("3 - We've bought a player");
                    System.out.println("4 - We've sold a player");
                    System.out.println("5 - About marketing");
                    System.out.println("6 - Cleaning");
                    System.out.println("7 - General warnings");
                    System.out.println("0 - Exit");
                    System.out.print("Select the option and press ENTER: ");

                    int option = in.nextInt();
                    String messageTopic = "";
                    boolean sendMessage = true;

                    if (option == 1) {
                        messageTopic = "Training Day";
                    } else if (option == 2) {
                        messageTopic = "Player Injury";
                    } else if (option == 3) {
                        messageTopic = "New player arrival";
                    } else if (option == 4) {
                        messageTopic = "Player leaving the club";
                    } else if (option == 5) {
                        messageTopic = "Marketing warning";
                    } else if (option == 6) {
                        messageTopic = "Cleaning";
                    } else if (option == 7) {
                        messageTopic = "General Warning";
                    } else if (option == 0) {
                        System.out.println("Server finished.");
                        break;
                    } else {
                        System.out.println("Invalid option, please, try again.");
                        sendMessage = false;
                    }

                    if (sendMessage) {
                        in.nextLine(); // Consume leftover new line

                        String message;
                        System.out.println("Type the message to the client:");
                        message = in.nextLine();

                        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        String fullMessage = "[" + date + "] - " + messageTopic + ": " + message;

                        channel.basicPublish("", QUEUE, null, fullMessage.getBytes());
                        System.out.println("Sent: " + fullMessage);
                    }
                }
            }
        }
    }
}
