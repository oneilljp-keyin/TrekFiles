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

public class RotatableChair extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(13, 8, 2, 14, 11, 13),
            Block.box(2, 11, 2, 3, 11.3, 13),
            Block.box(13, 11, 2, 14, 11.3, 13),
            Block.box(14, 7, 1, 17, 9, 2),
            Block.box(-1, 7, 1, 2, 9, 2),
            Block.box(14.5, 11, 9, 15.5, 11.1, 12),
            Block.box(0.5, 11, 9, 1.5, 11.1, 12),
            Block.box(14.5, 11, 4, 15.5, 11.1, 5),
            Block.box(14.5, 11, 5, 15.5, 11.1, 6),
            Block.box(14.5, 11, 6, 15.5, 11.1, 7),
            Block.box(0.7, 11, 11, 1.3, 11.2, 11.5),
            Block.box(0.7, 11, 10, 1.3, 11.2, 10.5),
            Block.box(14.7, 11, 9.5, 15.3, 11.2, 10),
            Block.box(14.7, 11, 10.5, 15.3, 11.2, 11.5),
            Block.box(0.5, 11, 4, 1.5, 11.1, 6),
            Block.box(2.5, 13, 2, 13.5, 16, 2.4),
            Block.box(2, 0, 5, 14, 2, 15),
            Block.box(5, 0, 4, 11, 5, 10),
            Block.box(-1, 5, 1, 17, 7, 13),
            Block.box(-1, 7, 2, 2, 10, 3),
            Block.box(14, 7, 2, 17, 10, 3),
            Block.box(-1, 7, 3, 2, 11, 13),
            Block.box(14, 7, 3, 17, 11, 13),
            Block.box(2.5, 7, 1, 13.5, 16, 2),
            Block.box(2, 7, 2, 14, 8, 13),
            Block.box(2, 8, 2, 3, 11, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public RotatableChair(Properties properties) {
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
