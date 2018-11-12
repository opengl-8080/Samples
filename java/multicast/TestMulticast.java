import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMulticast {
    private static final int port = 9876;

    public static void main(String[] args) throws IOException {
        InetAddress group = InetAddress.getByName("224.0.0.1");

        if (args.length == 1 && args[0].equals("server")) {
            server(group);
        } else {
            client(group);
        }
    }

    private static void server(InetAddress group) throws IOException {
        try (MulticastSocket socket = new MulticastSocket(port)) {
            socket.joinGroup(group);

            Console console = System.console();
            while (true) {
                String message = console.readLine();
                DatagramPacket packet = new DatagramPacket(message.getBytes("UTF-8"), message.length(), group, port);
                socket.send(packet);
            }
        }
    }

    private static void client(InetAddress group) throws IOException {
        byte[] buffer = new byte[1000];
        try (MulticastSocket socket = new MulticastSocket(port)) {
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                int length = packet.getLength();
                System.out.println("length=" + length);
                byte[] tmp = new byte[length];
                System.arraycopy(packet.getData(), 0, tmp, 0, length);
                String message = new String(tmp, "UTF-8");
                System.out.println(message);
            }
        }
    }
}