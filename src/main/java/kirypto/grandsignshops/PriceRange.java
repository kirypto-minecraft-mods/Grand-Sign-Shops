package kirypto.grandsignshops;

public class PriceRange {
    private final int low;
    private final int high;

    private PriceRange(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public static PriceRange of(int low, int high) {
        return new PriceRange(low, high);
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }
}
