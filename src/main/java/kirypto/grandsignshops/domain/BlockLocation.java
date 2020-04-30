package kirypto.grandsignshops.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BlockLocation {

    private final int dimension;
    private final int x;
    private final int y;
    private final int z;

    private BlockLocation(int dimension, int x, int y, int z) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static BlockLocation of(int dimension, int x, int y, int z) {
        return new BlockLocation(dimension, x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BlockLocation that = (BlockLocation) obj;

        return new EqualsBuilder()
                .append(dimension, that.dimension)
                .append(x, that.x)
                .append(y, that.y)
                .append(z, that.z)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(dimension)
                .append(x)
                .append(y)
                .append(z)
                .toHashCode();
    }

    public int getDimension() {
        return dimension;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public static BlockLocation shift(BlockLocation blockLocation, int dx, int dy, int dz) {
        return of(blockLocation.dimension, blockLocation.x + dx, blockLocation.y + dy, blockLocation.z + dz);
    }
}
