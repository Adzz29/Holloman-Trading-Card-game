import java.util.HashSet;
import java.util.TreeSet;

public class CardTest {
    public static void main(String[] args) {
        // Enable assertions at runtime with the -ea JVM argument.

        // Test Card construction and toString
        Card card1 = new Card(1, "Ace of Spades", Rank.UNIQUE);
        assert card1.toString().contains("Ace of Spades") : "toString does not work as expected";

        // Test equals and hashCode
        Card card2 = new Card(1, "Ace of Spades", Rank.UNIQUE);
        assert card1.equals(card2) : "equals or hashCode does not work as expected";

        // Test compareTo
        Card card3 = new Card(2, "King of Hearts", Rank.RARE);
        assert card1.compareTo(card3) < 0 : "compareTo does not work as expected with rank";
        Card card4 = new Card(1, "Ace of Clubs", Rank.UNIQUE);
        assert card1.compareTo(card4) > 0 : "compareTo does not work as expected with name";
        Card card5 = new Card(1, "Ace of Spades", Rank.UNIQUE);
        assert card1.compareTo(card5) == 0 : "compareTo does not work as expected with identical cards";

        // Test HashSet - should not allow duplicate elements
        HashSet<Card> hashSet = new HashSet<>();
        hashSet.add(card1);
        hashSet.add(card2); // Attempt to add duplicate
        assert hashSet.size() == 1 : "HashSet does not correctly identify duplicates";

        // Test TreeSet - should sort elements and not allow duplicates
        TreeSet<Card> treeSet = new TreeSet<>();
        treeSet.add(card1);
        treeSet.add(card3);
        treeSet.add(card4);
        treeSet.add(card5); // Attempt to add duplicate
        assert treeSet.size() == 3 : "TreeSet size incorrect - may not correctly handle duplicates";
        assert treeSet.first().equals(card1) || treeSet.first().equals(card4) : "TreeSet does not correctly sort cards";

        System.out.println("All tests passed!");
    }
}
