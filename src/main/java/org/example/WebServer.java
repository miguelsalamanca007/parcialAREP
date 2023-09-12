package org.example;

import java.net.*;
import java.io.*;
public class WebServer {

        public static void main(String[] args) throws IOException {

            ServerSocket serverSocket = null;

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
                if(isFirstLine){
                    firstLine = inputLine;
                    isFirstLine = false;
                }
                System.out.println("Recibí: " + inputLine);
                if (!in.ready()) {break; }
            }
            String fullCommand = firstLine.split("=")[1].replace("%20", "").replace(" HTTP/1.1","");
            String command = fullCommand.split("\\(")[0];
            String[] variables = fullCommand.split("\\(")[1].replace(")","").split(",");
            outputLine =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<title>Title of the document</title>\n" +
                            "</head>" +
                            "<body>" +
                            "<h1>Mi propio mensaje</h1>" +
                            "</body>" +
                            "</html>";
            out.println("HTTP / 1.1/ OK");
            out.println("Content-type: text/html");
            out.println();
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
    }

