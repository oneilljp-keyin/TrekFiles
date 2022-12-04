package tech.johnoneill.trekfiles.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

public class WallDiagonalPanel extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(14.14, 0, 14.14, 14.844999999999999, 16, 14.844999999999999),
            Block.box(13.433, 0, 13.433, 14.138, 16, 14.138),
            Block.box(12.726, 0, 12.726, 13.431000000000001, 16, 13.431000000000001),
            Block.box(12.019, 0, 12.019, 12.724, 16, 12.724),
            Block.box(11.312, 0, 11.312, 12.017, 16, 12.017),
            Block.box(0, 0, 0, 0.705, 16, 0.705),
            Block.box(9.191, 0, 9.191, 9.895999999999999, 16, 9.895999999999999),
            Block.box(8.484, 0, 8.484, 9.189, 16, 9.189),
            Block.box(7.777, 0, 7.777, 8.482, 16, 8.482),
            Block.box(7.07, 0, 7.07, 7.7749999999999995, 16, 7.7749999999999995),
            Block.box(6.363, 0, 6.363, 7.0680000000000005, 16, 7.0680000000000005),
            Block.box(5.656, 0, 5.656, 6.361, 16, 6.361),
            Block.box(4.949, 0, 4.949, 5.654, 16, 5.654),
            Block.box(0.707, 0, 0.707, 1.412, 16, 1.412),
            Block.box(1.414, 0, 1.414, 2.1189999999999998, 16, 2.1189999999999998),
            Block.box(2.121, 0, 2.121, 2.8259999999999996, 16, 2.8259999999999996),
            Block.box(2.828, 0, 2.828, 3.5329999999999995, 16, 3.5329999999999995),
            Block.box(3.535, 0, 3.535, 4.24, 16, 4.24),
            Block.box(4.242, 0, 4.242, 4.947, 16, 4.947),
            Block.box(14.847, 0, 14.847, 15.554, 16, 15.554),
            Block.box(15.293, 0, 15.293, 16, 16, 16),
            Block.box(10.605, 0, 10.605, 11.309999999999999, 16, 11.309999999999999),
            Block.box(9.898, 0, 9.898, 10.603, 16, 10.603)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));
    public WallDiagonalPanel(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
        runCalculation(SHAPE.orElse(Shapes.block()));
    }


    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        } else {
            return null;
        }
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
