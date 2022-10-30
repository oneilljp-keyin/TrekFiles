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
import tech.johnoneill.trekfiles.TrekFiles;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ShuttleType18 extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(0, 9, -4, 16, 14, 0),
            Block.box(2, 0, -7, 4, 1, 5),
            Block.box(12, 0, -7, 14, 1, 5),
            Block.box(0, 0, 20, 2, 1, 26),
            Block.box(14, 0, 20, 16, 1, 26),
            Block.box(0, 3, -15.75, 16, 7, -12),
            Block.box(-8, 1, 0, 24, 9, 28),
            Block.box(0, 9, 0, 16, 17, 16),
            Block.box(0, 9, 21, 16, 17, 26),
            Block.box(16, 9, 21, 18, 12, 27),
            Block.box(-2, 9, 21, 0, 12, 27),
            Block.box(16, 13, 13, 16.5, 16, 22),
            Block.box(-0.5, 13, 13, -5.551115123125783e-17, 16, 22),
            Block.box(-14, 6, 17.75, -11, 10, 18),
            Block.box(-13.75, 6.25, 17.5, -13, 9.75, 17.75),
            Block.box(29, 6.25, 17.5, 29.75, 9.75, 17.75),
            Block.box(-12.75, 6.25, 17.5, -11.25, 9.75, 17.75),
            Block.box(27.25, 6.25, 17.5, 28.75, 9.75, 17.75),
            Block.box(27, 6, 17.75, 30, 10, 18),
            Block.box(0, 9, 26, 16, 12, 28),
            Block.box(0, 5, 28, 5, 8, 28.25),
            Block.box(11, 5, 28, 16, 8, 28.25),
            Block.box(0, 9, 16, 16, 17, 21),
            Block.box(4, 17.25, 18, 12, 19.25, 29),
            Block.box(10, 17.75, 15, 11, 18.75, 18),
            Block.box(12, 17.5, 20, 13, 18.5, 29),
            Block.box(3, 17.5, 20, 4, 18.5, 29),
            Block.box(5, 17.75, 15, 6, 18.75, 18),
            Block.box(5, 17, 6, 11, 17.25, 14),
            Block.box(5, 17, 18, 11, 17.25, 26),
            Block.box(-15.25, 6, 19, -15, 10, 29),
            Block.box(31, 6, 19, 31.25, 10, 29),
            Block.box(31.25, 8.5, 19.5, 31.3, 9.5, 28.5),
            Block.box(-15.3, 8.5, 19.5, -15.25, 9.5, 28.5),
            Block.box(31.25, 6.5, 19.5, 31.3, 7.5, 28.5),
            Block.box(-15.3, 6.5, 19.5, -15.25, 7.5, 28.5),
            Block.box(-15, 1, 18, -5, 11, 30),
            Block.box(21, 1, 18, 31, 11, 30),
            Block.box(-2, 2, -12, 18, 8, -8),
            Block.box(-2.25, 4.5, -12.25, -1.75, 5.5, -10.25),
            Block.box(-2.25, 4.5, -10.25, 18.25, 5.5, -7.25),
            Block.box(-1.75, 4.5, -12.25, 17.75, 5.5, -9.25),
            Block.box(-0.25, 4.5, -16, 16.25, 5.5, -11),
            Block.box(-4.25, 4.5, -8.25, 20.25, 5.5, -3.25),
            Block.box(-6.25, 4.5, -4.25, 22.25, 5.5, 0.75),
            Block.box(-8.25, 4.5, -0.25, 24.25, 5.5, 19.75),
            Block.box(17.75, 4.5, -12.25, 18.25, 5.5, -10.25),
            Block.box(15.25, 12.5, 25.25, 16.25, 14.5, 26.25),
            Block.box(-0.25, 12.5, 25.25, 0.75, 14.5, 26.25),
            Block.box(-4, 1, -8, 20, 9, -4),
            Block.box(2, 2, -13, 14, 12, -9),
            Block.box(0, 9, -8, 16, 11, -4),
            Block.box(-6, 1, -4, 22, 9, 0)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public ShuttleType18(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
        runCalculation(SHAPE.orElse(Shapes.block()));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            SHAPES.put(direction, TrekFiles.calculateShapes(direction, shape));
        }
    }
}
