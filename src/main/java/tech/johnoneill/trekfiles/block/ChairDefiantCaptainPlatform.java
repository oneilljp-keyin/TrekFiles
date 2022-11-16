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

public class ChairDefiantCaptainPlatform extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(2, 12.1, 4.6, 14, 19.1, 7.1),
            Block.box(3, 19.1, 4.6, 13, 21.6, 7.1),
            Block.box(4, 21.6, 4.6, 12, 24.1, 7.1),
            Block.box(2, 10.6, 4.6, 14, 12.6, 15.6),
            Block.box(14.5, 14.1, 7, 16.5, 16.1, 14.1),
            Block.box(-0.5, 14.1, 7, 1.5, 16.1, 14.1),
            Block.box(3, 10.6, 15.6, 13, 12.6, 16.6),
            Block.box(5, 24.1, 4.6, 11, 26.6, 6.6),
            Block.box(1.9, 10.8, 4.5, 14.1, 14, 7.9),
            Block.box(1, 17.4, 3.5, 3, 17.9, 3.75),
            Block.box(0.5, 16.1, 3.6, 15.5, 18.1, 7),
            Block.box(-0.5, 12.1, 2.6, 16.5, 16.1, 7),
            Block.box(-0.5, 11.1, 3.6, 16.5, 12.1, 15.1),
            Block.box(5, 8, 7, 11, 10.5, 13),
            Block.box(5.25, 7, 7.25, 10.75, 8, 12.75),
            Block.box(5, 5, 7, 11, 7, 13),
            Block.box(4, 4, 6, 12, 5, 14),
            Block.box(-6, 3, 16, 0, 4, 32),
            Block.box(-14, 14, 8, -8, 15, 24),
            Block.box(22.5, 5, 11, 24.5, 16, 13),
            Block.box(20.5, 3, 11, 24.5, 5, 13),
            Block.box(23.5, 16, 11, 24.5, 17, 13),
            Block.box(22.5, 5, 19, 24.5, 16, 21),
            Block.box(20.5, 3, 19, 24.5, 5, 21),
            Block.box(23.5, 16, 19, 24.5, 17, 21),
            Block.box(24.49264, 13.96751, 8, 30.49264, 14.96751, 24),
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
