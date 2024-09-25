package com.example.fcmanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Server {

    private final static String QUEUE = "messages";
    private final static String LOG_FILE = "audit_log.txt"; // Nome do arquivo de log

    public static void main(String[] args) throws Exception {
        // Limpa o arquivo de log ao iniciar o programa
        resetLogFile();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("prawn-01.rmq.cloudamqp.com");
        factory.setUsername("ahpmdfzr");
        factory.setPassword("peFA7z9DlvjLU6N_IgUGg2U6g_r9xo-f");
        factory.setVirtualHost("ahpmdfzr");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE, false, false, false, null);

            System.out.println("Server started.");

            // Iniciar o consumidor de auditoria em uma nova thread
            AuditConsumer auditConsumer = new AuditConsumer(channel);
            auditConsumer.start(); // Inicia a thread

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
                    System.out.println("8 - Check all the messages");
                    System.out.println("0 - Exit");
                    System.out.print("Select the option and press ENTER: ");

                    int option = in.nextInt(); // Scanner is in
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
                        // Mostrar as mensagens auditadas
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

                        System.out.println(
                            "======================\n" +
                            "type: \"/exit\" to go back to the channel menu" +
                            "\n======================"
                        );

                        while (true) {
                            System.out.println("\n\nType the message to the client: ");
                            String message;
                            message = in.nextLine();

                            if (message.equalsIgnoreCase("/exit")) {
                                System.out.println("Finished by user!");
                                break;
                            }

                            String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                            String fullMessage = "[" + date + "] - " + messageTopic + ": " + message;

                            channel.basicPublish("", QUEUE, null, fullMessage.getBytes());

                            System.out.println(
                                "======================\n" +
                                "Sent: " + fullMessage +
                                "\n======================"
                            );
                        }
                    }
                }
            }
        }
    }

    // Método para limpar o arquivo de log ao iniciar o servidor
    private static void resetLogFile() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {
            writer.write(""); // Escreve uma string vazia para limpar o conteúdo
        } catch (IOException e) {
            System.out.println("Error resetting the log file: " + e.getMessage());
        }
    }

    // Método para exibir as mensagens auditadas
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
