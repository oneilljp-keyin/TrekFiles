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

public class Torpedo extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(3, 2, -4, 13, 4, 20),
            Block.box(3.5, 1.5, -5, 12.5, 4.5, 21),
            Block.box(4, 3, -6, 12, 5, 22),
            Block.box(4, 1, -6, 12, 3, 22),
            Block.box(4.5, 1, -6.5, 11.5, 5, 22.5),
            Block.box(5.5, 1.5, -7.5, 10.5, 4.5, 23.5),
            Block.box(7, 2, -8, 9, 4, 8),
            Block.box(7, 2, 8, 9, 4, 24),
            Block.box(4.5, 4.5, -6, 11.5, 5.5, 22),
            Block.box(4.5, 0.5, -6, 11.5, 1.5, 22),
            Block.box(6, 5, -5, 10, 6, 21),
            Block.box(6, 0, -5, 10, 1, 21),
            Block.box(5, 3, -7, 11, 5, -6.5),
            Block.box(5, 3, 22.5, 11, 5, 23),
            Block.box(5, 1, -7, 11, 3, -6.5),
            Block.box(5, 1, 22.5, 11, 3, 23)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public Torpedo(Properties properties) {
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
