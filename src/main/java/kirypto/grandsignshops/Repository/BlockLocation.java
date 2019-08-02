package kirypto.grandsignshops.Repository;

import net.minecraft.util.math.BlockPos;

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

    public static BlockLocation of(int dimension, BlockPos blockPos) {
        return new BlockLocation(dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ());
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
