package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import tech.johnoneill.trekfiles.TrekFiles;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ChairDefiantCaptainPlatform extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(4, 4, 6, 12, 5, 14),
            Block.box(-6, 3, 16, 0, 4, 32),
            Block.box(22.5, 5, 11, 24.5, 16, 13),
            Block.box(20.5, 3, 11, 24.5, 5, 13),
            Block.box(23.5, 16, 11, 24.5, 17, 13),
            Block.box(22.5, 5, 19, 24.5, 16, 21),
            Block.box(20.5, 3, 19, 24.5, 5, 21),
            Block.box(23.5, 16, 19, 24.5, 17, 21),
            Block.box(16, 3, 16, 22, 4, 32),
            Block.box(-6, 3, -1, 0, 4, 16),
            Block.box(16, 3, -1, 22, 4, 16),
            Block.box(16, 0, 16, 21, 3, 31),
            Block.box(-5, 0, 16, 0, 3, 31),
            Block.box(0, 0, 16, 16, 3, 31),
            Block.box(-5, 0, 0, 0, 3, 16),
            Block.box(0, 0, 0, 16, 3, 16),
            Block.box(16, 0, 0, 21, 3, 16),
            Block.box(0, 3, 16, 16, 4, 32),
            Block.box(0, 3, -1, 16, 4, 16),
            Block.box(-8, 3, 19, -4, 5, 21),
            Block.box(-8, 16, 11, -7, 17, 13),
            Block.box(-8, 16, 19, -7, 17, 21),
            Block.box(-8, 5, 19, -6, 16, 21),
            Block.box(-8, 3, 11, -4, 5, 13),
            Block.box(-8, 5, 11, -6, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public ChairDefiantCaptainPlatform(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
        runCalculation(SHAPE.orElse(Shapes.block()));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }
}
