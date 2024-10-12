import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CardInputStream extends InputStream {
    private BufferedReader reader;

    public CardInputStream(InputStream input) {
        // Wrap the input stream in a BufferedReader for easy reading of text lines.
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public int read() throws IOException {
        // Direct byte reading is not supported in this class, as it's focused on higher-level operations.
        throw new UnsupportedOperationException("Direct byte reading not supported. Use readCard() for reading cards.");
    }

    public Card readCard() throws IOException {
        // Attempt to read the next card from the input stream.
        String line = reader.readLine();
        // If the line indicates the start of a card, proceed to read its details.
        if (line != null && line.equals("CARD")) {
            long id = Long.parseLong(reader.readLine()); // Read the card's ID.
            String name = reader.readLine(); // Read the card's name.
            Rank rank = Rank.valueOf(reader.readLine()); // Read the card's rank.
            long price = Long.parseLong(reader.readLine()); // Read the card's price.

            // Construct and return a Card object with the read details.
            Card card = new Card(id, name, rank);
            card.setPrice(price); // Set the price for the card.
            return card;
        }
        // If the line is null or does not indicate the start of a card, return null to signal the end of card data.
        return null;
    }

    public String readResponse() throws IOException {
        // Read and return the next line from the input stream, intended for non-card responses like "OK".
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        // Close the BufferedReader and by extension the underlying InputStream.
        reader.close();
    }
}
