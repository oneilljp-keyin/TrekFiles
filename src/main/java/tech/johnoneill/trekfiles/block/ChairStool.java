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

public class ChairStool extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.box(11.5, 0, 4, 12, 0.5, 12),
            Block.box(4, 0, 4, 4.5, 0.5, 12),
            Block.box(4.5, 0, 11.5, 11.5, 0.5, 12),
            Block.box(4.5, 0, 4, 11.5, 0.5, 4.5),
            Block.box(4.5, 0, 7.9, 6.5, 0.25, 8.15),
            Block.box(7.85, 0, 4.5, 8.1, 0.25, 6.5),
            Block.box(7.85, 0, 9.5, 8.1, 0.25, 11.5),
            Block.box(9.5, 0, 7.9, 11.5, 0.25, 8.15),
            Block.box(7, 1, 7, 9, 7, 9),
            Block.box(7.5, 7, 7.5, 8.5, 10, 8.5),
            Block.box(4.5, 10, 4.5, 11.5, 10.5, 5),
            Block.box(4.5, 10.5, 6, 11.5, 11, 11.5),
            Block.box(11, 10.5, 5, 11.5, 11.5, 6),
            Block.box(4.5, 10.5, 5, 5, 11.5, 6),
            Block.box(4.5, 10.5, 4.5, 11.5, 12, 5),
            Block.box(4.5, 10, 5, 5, 10.5, 6),
            Block.box(11, 10, 5, 11.5, 10.5, 6),
            Block.box(4.5, 10, 6, 11.5, 10.5, 11.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public ChairStool(Properties properties) {
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
