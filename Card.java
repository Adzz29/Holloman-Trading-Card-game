 public class Card implements Comparable<Card> {
    private long id;
    private String name;
    private Rank rank;
    private long price = 0;
    private static int cbv = 0;

    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Card[ID: " + id + ", Name: " + name + ", Rank: " + rank + ", Price: " + price + "]";
    }

    @Override
    public int hashCode() {
        // Use prime numbers and fields that define equality
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return id == card.id && name.equals(card.name) && rank == card.rank;
    }

    @Override
    public int compareTo(Card other) {
        cbv++; // Increment the comparison counter
        int rankComparison = rank.compareTo(other.rank);
        if (rankComparison != 0) return -rankComparison; // Unique first
        int nameComparison = name.compareTo(other.name);
        if (nameComparison != 0) return nameComparison;
        return Long.compare(id, other.id);
    }

    public void setPrice(long price) {
    this.price = price;
}


}
