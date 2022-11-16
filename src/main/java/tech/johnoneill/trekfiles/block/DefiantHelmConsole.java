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

public class DefiantHelmConsole extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(0, 0, 13, 16, 1, 22),
            Block.box(12, 0, 22, 16, 1, 30),
            Block.box(0, 0, 22, 4, 1, 30),
            Block.box(0, 0, 30, 16, 1, 32),
            Block.box(0, 0, 5, 16, 1, 13),
            Block.box(1, 15.9, 5.5, 15, 16, 13.5),
            Block.box(-10, 15.9, 6.5, 1, 16, 19),
            Block.box(15, 15.9, 6.5, 26, 16, 19),
            Block.box(0.9, 0.1, 3, 3.4, 16, 8),
            Block.box(12.6, 0.1, 3, 15.1, 16, 8),
            Block.box(-10, 0, 5, 0, 1, 10),
            Block.box(-10, 9.5, 6, 1, 15, 13.5),
            Block.box(1, 11.5, 4, 15, 15, 13.5),
            Block.box(-10, 9.5, 13.5, 1, 12, 17.5),
            Block.box(15, 9.5, 6, 26, 15, 13.5),
            Block.box(14.9, 13, 6.5, 26, 15.95, 19),
            Block.box(15, 9.5, 13.5, 26, 12, 17.5),
            Block.box(-10.1, 13, 6.5, 1, 15.95, 19),
            Block.box(1, 12, 4.5, 15, 15.9, 13.5),
            Block.box(16, 0, 5, 26, 1, 10),
            Block.box(3, 16, 4, 13, 20, 6),
            Block.box(16, 0, 10, 26, 1, 15),
            Block.box(-10, 0, 10, 0, 1, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public DefiantHelmConsole(Properties properties) {
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
