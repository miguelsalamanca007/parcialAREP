package org.example;

import java.net.*;
import java.io.*;
public class WebServer {

        public static void main(String[] args) throws IOException {

            while (true) {

                ServerSocket serverSocket = null;

                WebPage page = new WebPage();

                ReflectiveService reflectiveService = new ReflectiveService();

                try {
                    serverSocket = new ServerSocket(36000);
                } catch (IOException e) {
                    System.err.println("Could not listen on port: 36000.");
                    System.exit(1);
                }

                Socket clientSocket = null;
                try {
                    System.out.println("Listo para recibir ...");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;

                String firstLine = "";
                boolean isFirstLine = true;


                while ((inputLine = in.readLine()) != null) {
                    if (isFirstLine) {
                        firstLine = inputLine;
                        isFirstLine = false;
                    }
                    //System.out.println("Recibí: " + inputLine);
                    if (!in.ready()) {
                        break;
                    }
                }

                System.out.println("First Line is: " + firstLine);

                if (!firstLine.equals("GET /favicon.ico HTTP/1.1") && !firstLine.equals("GET / HTTP/1.1")) {
                    String fullCommand = firstLine.split("=")[1].replace("%20", "").replace(" HTTP/1.1", "");
                    String command = fullCommand.split("\\(")[0].toLowerCase();
                    String[] variables = fullCommand.split("\\(")[1].replace(")", "").split(",");

                    System.out.println("Command is: " + command);
                    String result = "";

                    if (command.equals("class")) {
                        result = reflectiveService.classCommand(variables[0]);
                        System.out.println(result);
                    } else if (command.equals("invoke")) {
                        result = reflectiveService.invokeCommand(variables[0], variables[1]);
                        System.out.println(result);
                    } else if (command.equals("unaryinvoke")) {
                        result = reflectiveService.unaryInvokeCommand(variables[0], variables[1], variables[2], variables[3]);
                        System.out.println(result);
                    } else if (command.equals("binaryinvoke")) {
                        result = reflectiveService.binaryInvokeCommand(variables[0], variables[1], variables[2], variables[3], variables[4], variables[5]);
                        System.out.println(result);
                    }

                    outputLine = result;
                    out.println("HTTP / 1.1/ OK");
                    out.println("Content-type: application/json");
                    out.println();
                    out.println(outputLine);
                }

                if(firstLine.equals("GET / HTTP/1.1")){
                    outputLine = page.getPage();
                    out.println("HTTP / 1.1/ OK");
                    out.println("Content-type: text/html");
                    out.println();
                    out.println(outputLine);
                }

                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            }
        }
    }

