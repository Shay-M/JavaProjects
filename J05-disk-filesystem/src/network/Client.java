package network;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Client {
    private static Charset charset = Charset.defaultCharset();
    private static final int PORT = 4242;
    public static void main(String[] args) throws IOException {
        final Client client = new Client();
        // client.runTcp();
        client.runUdp();

    }

    public Client() {

    }

    private void runTcp() {
        try (SocketChannel socket = SocketChannel.open()) {
            var address = new InetSocketAddress("localhost", PORT);
            socket.connect(address);

            try (var sc = new Scanner(System.in);) {
                String line = null;
                var buffer = ByteBuffer.allocate(1024);
                do {
                    line = sc.nextLine();
                    var toBuffer = charset.encode(line);


                    socket.write(toBuffer);

                    buffer.clear();
                    socket.read(buffer);
                    buffer.flip();
                    var got = charset.decode(buffer);
                    System.out.printf("> %s\n", got);
                } while (!line.equalsIgnoreCase("q"));
                System.out.println("Done!");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void runUdp() {
        try (var socket = new DatagramSocket();) {
            var address = InetAddress.getByName("localhost");

            var buf = "Knock Knock!".getBytes();
            var packet = new DatagramPacket(buf, buf.length, address, PORT);
            socket.send(packet);

            buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            var received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Quote of the Moment: " + received);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}