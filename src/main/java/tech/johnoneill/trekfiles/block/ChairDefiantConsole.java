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

public class ChairDefiantConsole extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(4, 0, 2, 12, 1, 10),
            Block.box(5, 1, 3, 11, 3.5, 9),
            Block.box(5.25, 3.5, 3.25, 10.75, 4.5, 8.75),
            Block.box(5, 4.5, 3, 11, 7, 9),
            Block.box(2, 8.1, 0.6, 14, 20.1, 3.1),
            Block.box(2, 6.6, 0.6, 14, 8.6, 11.6),
            Block.box(3, 6.6, 11.6, 13, 8.6, 12.6),
            Block.box(3, 20.1, 0.6, 13, 21.1, 3.1),
            Block.box(1.9, 6.8, 0.5, 14.1, 10, 3.9),
            Block.box(3.5, 10, 0.5, 12.5, 11, 1.5),
            Block.box(5.5, 11, 0.5, 10.5, 12, 1.5),
            Block.box(7.5, 12, 0.5, 8.5, 13, 1.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public ChairDefiantConsole(Properties properties) {
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
