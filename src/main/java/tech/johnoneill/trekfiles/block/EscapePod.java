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

public class EscapePod extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(-3, 26, -4, 0, 30, 2),
            Block.box(0, 26, -8, 16, 30, -3),
            Block.box(-6, 26, 2, -3, 30, 6),
            Block.box(-7.5, 26, 6, -4.5, 30, 8),
            Block.box(-7.5, 26, 8, -4.5, 30, 10),
            Block.box(-6, 26, 10, -3, 30, 14),
            Block.box(-3, 26, 14, 0, 30, 20),
            Block.box(0, 26, 19, 16, 30, 24),
            Block.box(16, 26, 14, 19, 30, 20),
            Block.box(19, 26, 10, 22, 30, 14),
            Block.box(20.5, 26, 8, 23.5, 30, 10),
            Block.box(19, 26, 2, 22, 30, 6),
            Block.box(16, 26, -4, 19, 30, 2),
            Block.box(19, 26, 6, 20.5, 28, 10),
            Block.box(16, 26, 2, 19, 28, 14),
            Block.box(0, 26, -4, 16, 28, 20),
            Block.box(-3, 26, 2, 0, 28, 14),
            Block.box(-4.5, 26, 6, -3, 28, 10),
            Block.box(20.5, 26, 6, 23.5, 30, 8),
            Block.box(18, 22, 8, 22, 26, 22),
            Block.box(22, 22, 8, 26, 26, 16),
            Block.box(26, 22, 6, 28, 26, 10),
            Block.box(22, 22, 0, 26, 26, 8),
            Block.box(18, 22, -6, 22, 26, 8),
            Block.box(-2, 22, -11, 18, 26, 8),
            Block.box(-6, 22, -6, -2, 26, 8),
            Block.box(-10, 22, 0, -6, 26, 8),
            Block.box(-12, 22, 6, -10, 26, 10),
            Block.box(-10, 22, 8, -6, 26, 16),
            Block.box(-6, 22, 8, -2, 26, 22),
            Block.box(-2, 22, 8, 18, 26, 27),
            Block.box(-8.125, -16, 10, -6, -2, 14),
            Block.box(-6, -14, 14, -4, -2, 20),
            Block.box(-4, -16, 20, -2, -2, 25),
            Block.box(0, -16, -12, 5, -14, -10),
            Block.box(0, -14, -12, 16, -2, -10),
            Block.box(11, -16, -12, 16, -14, -10),
            Block.box(22, -16, 10, 24.1, -2, 14.1),
            Block.box(20, -14, 14, 22, -2, 20),
            Block.box(18, -16, 20, 20, -2, 25),
            Block.box(-4, -2, -13, 20, 22, 29),
            Block.box(-8, -2, 8, -4, 22, 24),
            Block.box(-12, -2, 8, -8, 22, 18),
            Block.box(-13.75, -2, 5, -12, 22, 11),
            Block.box(-12, -2, -2, -8, 22, 8),
            Block.box(-8, -2, -8, -4, 22, 8),
            Block.box(20, -2, 8, 24, 22, 24),
            Block.box(24, -2, 8, 28, 22, 18),
            Block.box(28, -2, 5, 29.75, 22, 11),
            Block.box(24, -2, -2, 28, 22, 8),
            Block.box(20, -2, -8, 24, 22, 8),
            Block.box(-14.75, 6.5, 4, 30.75, 10.5, 11.75),
            Block.box(-13, 6.5, 11, 29, 10.5, 19),
            Block.box(-9, 6.5, 18, 25, 10.5, 25),
            Block.box(8, 6.5, 23, 21, 10.5, 30),
            Block.box(-13, 6.5, -3, 29, 10.5, 5),
            Block.box(-9, 6.5, -9, 25, 10.5, -2),
            Block.box(-5, 6.5, -14, 21, 10.5, -7),
            Block.box(-5, 6.5, 23, 8, 10.5, 30),
            Block.box(-1, 0.5, 29, 17, 16, 31),
            Block.box(0, -11, -10, 16, -2, 26),
            Block.box(-8, -11, 2, -4, -2, 14),
            Block.box(20, -11, 2, 24, -2, 14),
            Block.box(-4, -11, -4, 0, -2, 20),
            Block.box(16, -11, -4, 20, -2, 20),
            Block.box(-11, -11, 6, -8, -2, 10),
            Block.box(24, -11, 6, 27, -2, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public EscapePod(Properties properties) {
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
