import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MulticastPacketMonitor {

    public static void main(String[] args) {
        new MulticastPacketMonitor().start();
    }
    
    public void start() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                InetAddress group = InetAddress.getByName("224.2.2.3");
                MulticastSocket socket = new MulticastSocket(54327);
                socket.joinGroup(group);
                System.out.println("join multicast group...");

                while (true) {
                    byte[] buffer = new byte[64 * 1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    byte[] data = packet.getData();
                    int length = packet.getLength();
                    for (int i=0; i<length; i++) {
                        byte d = data[i];
                        int n = d & 0xFF;
                        String hex = Integer.toHexString(n);
                        System.out.print(hex);
                        if (i < length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        });
    }
}
