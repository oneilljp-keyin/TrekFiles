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

public class ChairDefiantCaptain extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(2, 8.1, 1.6, 14, 15.1, 4.1),
            Block.box(3, 15.1, 1.6, 13, 17.6, 4.1),
            Block.box(4, 17.6, 1.6, 12, 20.1, 4.1),
            Block.box(2, 6.6, 1.6, 14, 8.6, 12.6),
            Block.box(14.5, 10.1, 4, 16.5, 12.1, 11.1),
            Block.box(-0.5, 10.1, 4, 1.5, 12.1, 11.1),
            Block.box(3, 6.6, 12.6, 13, 8.6, 13.6),
            Block.box(5, 20.1, 1.6, 11, 22.6, 3.6),
            Block.box(1.9, 6.8, 1.5, 14.1, 10, 4.9),
            Block.box(1, 13.4, 0.5, 3, 13.9, 0.75),
            Block.box(0.5, 12.1, 0.6, 15.5, 14.1, 4),
            Block.box(-0.5, 8.1, -0.4, 16.5, 12.1, 4),
            Block.box(-0.5, 7.1, 0.6, 16.5, 8.1, 12.1),
            Block.box(5, 4, 4, 11, 6.5, 10),
            Block.box(5.25, 3, 4.25, 10.75, 4, 9.75),
            Block.box(5, 1, 4, 11, 3, 10),
            Block.box(4, 0, 3, 12, 1, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public ChairDefiantCaptain(Properties properties) {
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
