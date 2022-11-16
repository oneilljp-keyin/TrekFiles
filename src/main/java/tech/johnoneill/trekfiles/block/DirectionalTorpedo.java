package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import tech.johnoneill.trekfiles.TrekFiles;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class DirectionalTorpedo extends DirectionalSlab {
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    private static final Optional<VoxelShape> FULL_AABB = Optional.of(Block.box(-8, 0, 2, 24, 16, 14));
    private static final Optional<VoxelShape> BOTTOM_AABB = Optional.of(Block.box(-8, 0, 2, 24, 8, 14));
    private static final Optional<VoxelShape> TOP_AABB = Optional.of(Block.box(-8, 8, 2, 24, 16, 14));

    public DirectionalTorpedo(Properties properties) {
        super(properties);
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        SlabType slabtype = state.getValue(TYPE);
        switch (slabtype) {
            case DOUBLE:
                runCalculation(FULL_AABB.orElse(Shapes.block()));
            case TOP:
                runCalculation(TOP_AABB.orElse(Shapes.block()));
            default:
                runCalculation(BOTTOM_AABB.orElse(Shapes.block()));
        }
        return SHAPES.get(state.getValue(FACING));
    }

    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState state1, @NotNull LevelAccessor accessor, @NotNull BlockPos pos, @NotNull BlockPos pos1) {
        return super.updateShape(state, direction, state1, accessor, pos, pos1);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }

}
