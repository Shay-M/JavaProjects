package network;

import com.google.gson.Gson;
import hardware.DiskController;
import hardware.IoOperations;
import network.act.*;
import network.act.response.*;
import simplefilesystem.FileSystem;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static simplefilesystem.SysParameters.BLOCK_SIZE;
import static simplefilesystem.SysParameters.NUM_BLOCKS;

public class Server {
    private static final String SPACE_SPLIT = " ";
    private static final Charset charset = Charset.defaultCharset();
    private static final int PORT = 4242;
    // private final FileSystem m_fileSystem;
    private final Map<Request, Action> m_actions = new HashMap<>();

    // remove me!!!!!!
    private static final String PATH = "save/disks/";
    private static final String DISK_FILE_TYPE = ".dsk";
    private static final String NAME_TXT = "name.txt";
    /////////////


    public static void main(String[] args) {
        DiskController diskController = DiskController.getInstance(Path.of(PATH), DISK_FILE_TYPE, NUM_BLOCKS, BLOCK_SIZE);
        IoOperations ioOperations = diskController.get(1);
        FileSystem fileSystem = new FileSystem(ioOperations);
        fileSystem.createFile(NAME_TXT);

        fileSystem.createFile("abc");

        final Server server = new Server(fileSystem);
        server.run();
    }

    public Server(final FileSystem fileSystem) {
        initializeActions(fileSystem);


    }

    private void run() {
        new Thread(() -> {
            try (var serverSocket = ServerSocketChannel.open()) {
                serverSocket.bind(new InetSocketAddress(PORT));
                for (; ; ) {
                    var client = serverSocket.accept();
                    new Thread(() -> handleClient(client)).start();
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();


        ///

        for (; ; ) {
            // new Thread(() -> { //
            try (var socketUdp = new DatagramSocket(PORT)) {
                final byte[] buf = new byte[256];
                var packet = new DatagramPacket(buf, buf.length);
                socketUdp.receive(packet);

                final InetAddress address = packet.getAddress();
                final int port = packet.getPort();

                final var response = "Hello World".getBytes();

                packet = new DatagramPacket(response, response.length, address, port);
                socketUdp.send(packet);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            // }).start(); //
        }

    }


    private void initializeActions(final FileSystem fileSystem) {
        m_actions.put(Request.DIR, new Dir(fileSystem));
        m_actions.put(Request.REM, new RemoveFile(fileSystem)); // X
        m_actions.put(Request.GET, new GetFile(fileSystem));
        m_actions.put(Request.INF, new InfoFile(fileSystem)); // X
        m_actions.put(Request.CRE, new CreateFile(fileSystem));
    }

    private void handleClient(SocketChannel client) {
        try (client) {
            startTalkingToClient(client);
        }
        catch (IOException e) {
            System.out.println("Error from client");
        }
    }

    private void startTalkingToClient(final SocketChannel client) throws IOException {
        System.out.println("Got connection from:  " + client.getRemoteAddress());
        final var buffer = ByteBuffer.allocate(1024);
        while (client.read(buffer) > 0) {
            buffer.flip();
            final var request = charset.decode(buffer).toString();
            System.out.printf("> %s\n", request);

            final var split = request.split(SPACE_SPLIT);
            try {
                final var command = Request.valueOf(split[0].toUpperCase());
                final var actionFromResponse = m_actions.get(command);
                writeResponse(client, actionFromResponse.doAction(split));
            }
            catch (IllegalArgumentException ex) {
                final ByteBuffer byteBuffer = charset.encode("sad");
                client.write(byteBuffer);
            }
            buffer.clear();
        }
    }

    private void writeResponse(final SocketChannel client, final Result response) {
        final var gson = new Gson();
        final var json = gson.toJson(response);
        final ByteBuffer byteBuffer = charset.encode(json);
        try {
            client.write(byteBuffer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

