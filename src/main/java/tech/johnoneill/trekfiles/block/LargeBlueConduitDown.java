package tech.johnoneill.trekfiles.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LargeBlueConduitDown extends RotatedPillarBlock {

	public LargeBlueConduitDown(BlockBehaviour.Properties properties) {
		super(properties);
	      this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
	}

}
