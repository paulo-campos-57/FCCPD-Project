package com.example.fcmanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Server {

    private final static String EXCHANGE = "headers_logs";
    private final static String LOG_FILE = "audit_log.txt";

    public static void main(String[] args) throws Exception {
        resetLogFile();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("prawn-01.rmq.cloudamqp.com");
        factory.setUsername("ahpmdfzr");
        factory.setPassword("peFA7z9DlvjLU6N_IgUGg2U6g_r9xo-f");
        factory.setVirtualHost("ahpmdfzr");

        try (
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()
            ) {

            channel.exchangeDeclare(EXCHANGE, "headers");

            System.out.println("Server started.");

            AuditConsumer auditConsumer = new AuditConsumer(channel);
            auditConsumer.start();

            try (Scanner in = new Scanner(System.in)) {

                while (true) {
                    showMenu();
                    System.out.print("Select the option and press ENTER: ");

                    int option = in.nextInt();
                    in.nextLine(); // Consume leftover new line
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
                    } else if (option == 8) {
                        showAuditLog();
                        sendMessage = false;
                    } else if (option == 0) {
                        System.out.println("Server finished.");
                        break;
                    } else {
                        System.out.println("Invalid option, please, try again.");
                        sendMessage = false;
                    }

                    if (sendMessage) {
                        System.out.print("Type the message to the client: ");
                        String message = in.nextLine();
                        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        String fullMessage = "[" + date + "] - " + messageTopic + ": " + message;

                        List<String> roles = determineRoles(option);
                        
                        for (String role : roles) {
                            // Create headers
                            Map<String, Object> headers = new HashMap<>();
                            headers.put("role", role);

                            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                                    .headers(headers)
                                    .build();

                            channel.basicPublish(EXCHANGE, "", props, fullMessage.getBytes());
                            System.out.println("Sent to " + role + ": " + fullMessage);
                        }
                    }
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("Select the message topic: ");
        System.out.println("1 - Training day");
        System.out.println("2 - Player injury");
        System.out.println("3 - We've bought a player");
        System.out.println("4 - We've sold a player");
        System.out.println("5 - About marketing");
        System.out.println("6 - Cleaning");
        System.out.println("7 - General warnings");
        System.out.println("8 - Check all the messages");
        System.out.println("0 - Exit");
    }

    private static List<String> determineRoles(int option) {
        switch (option) {
            case 1: // Training Day
                return Arrays.asList("player", "coach", "audit");
            case 2: // Player Injury
                return Arrays.asList("coach", "medical", "audit");
            case 3: // New player arrival
                return Arrays.asList("coach", "social_media", "financial", "audit");
            case 4: // Player leaving the club
                return Arrays.asList("coach", "financial", "audit");
            case 5: // Marketing warning
                return Arrays.asList("social_media", "audit");
            case 6: // Cleaning
                return Arrays.asList("janitor", "coach", "audit");
            case 7: // General Warning
                return Arrays.asList("player", "coach", "medical", "janitor", "social_media", "financial", "audit");
            default:
                return Arrays.asList("unknown");
        }
    }

    private static void resetLogFile() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error resetting the log file: " + e.getMessage());
        }
    }

    private static void showAuditLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            System.out.println("Audit Log Messages: ");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the audit log: " + e.getMessage());
        }
    }
}
