package kirypto.grandsignshops;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    public static BlockLocation of(World world, BlockPos blockPos) {
        return new BlockLocation(world.provider.getDimension(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
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
}
