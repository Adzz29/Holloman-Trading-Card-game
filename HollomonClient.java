import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HollomonClient implements AutoCloseable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public HollomonClient(String server, int port) throws IOException {
        this.socket = new Socket(server, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public List<Card> login(String username, String password) throws IOException {
        out.println(username.toLowerCase());
        out.println(password);

        String response = in.readLine();
        if (response != null && response.contains("logged in successfully")) {
            return readCardsFromServer();
        } else {
            return null;
        }
    }

    public long getCredits() throws IOException {
        out.println("CREDITS");
        String creditsString = in.readLine();
        in.readLine(); // Read and discard the "OK" response.
        return Long.parseLong(creditsString);
    }

    public List<Card> getCards() throws IOException {
        out.println("CARDS");
        return readCardsFromServer();
    }

    public List<Card> getOffers() throws IOException {
        out.println("OFFERS");
        return readCardsFromServer();
    }

    private List<Card> readCardsFromServer() throws IOException {
        List<Card> cards = new ArrayList<>();
        CardInputStream cardStream = new CardInputStream(socket.getInputStream());
        Card card;
        while ((card = cardStream.readCard()) != null) {
            cards.add(card);
        }
        Collections.sort(cards);
        return cards;
    }

    @Override
    public void close() throws IOException {
        if (out != null) out.close();
        if (in != null) in.close();
        if (socket != null) socket.close();
    }

    public static void main(String[] args) {
        try (HollomonClient client = new HollomonClient("netsrv.cim.rhul.ac.uk", 1812)) {
            List<Card> cards = client.login("bottest", "kittybearingsskull");
            if (cards != null) {
                System.out.println("Login successful. Cards: " + cards);
                // Further actions here
            } else {
                System.out.println("Login failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
