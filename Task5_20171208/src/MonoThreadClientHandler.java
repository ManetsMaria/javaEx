import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    public MonoThreadClientHandler(Socket client) {
        clientDialog = client;
    }

    @Override
    public void run() {

        try {
            // канал записи в сокет
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            // канал чтения из сокета
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");

            System.out.println("DataOutputStream  created");
            System.out.println("Server reading from channel");
            String entry = in.readUTF();
            String keyWord = in.readUTF();
            System.out.println("Server try writing to channel");
            out.writeUTF(EncodingAndDecoding.decode(entry, keyWord));
            System.out.println("Server Wrote message to clientDialog.");
            // освобождаем буфер сетевых сообщений
            out.flush();
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета
            in.close();
            out.close();

            //закрываем сокет общения с клиентом
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}