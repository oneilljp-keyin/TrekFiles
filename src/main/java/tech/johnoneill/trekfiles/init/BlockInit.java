package tech.johnoneill.trekfiles.init;

import java.util.function.Function;

import com.google.common.base.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.johnoneill.trekfiles.TrekFiles;
import tech.johnoneill.trekfiles.block.*;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			TrekFiles.MOD_ID);

	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;

	public static final RegistryObject<Block> HOLODECK_BLOCK = register("holodeck_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_ONE = register("lcars_block_one",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_TWO = register("lcars_block_two",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_THREE = register("lcars_block_three",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_FOUR = register("lcars_block_four",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_FIVE = register("lcars_block_five",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_SIX = register("lcars_block_six",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> LCARS_BLOCK_SEVEN = register("lcars_block_seven",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<Block> WALL_PANEL_FULL_UPPER = register("wall_panel_full_upper",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> WALL_PANEL_FULL_LOWER = register("wall_panel_full_lower",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> WALL_PANEL_UPPER = register("wall_panel_upper",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<Block> WALL_PANEL_LOWER = register("wall_panel_lower",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<DoorBlock> DOOR_DEFIANT_SINGLE = register("door_defiant_single",
			() -> new DoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<DoorBlock> DOOR_DEFIANT_DOUBLE = register("door_defiant_double",
			() -> new DoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<DoorBlock> DOOR_GALAXY_TEN_FORWARD = register("door_galaxy_ten_forward",
			() -> new DoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<PressurePlateBlock> INVISIBLE_PRESSURE_PLATE = register("door_sensor",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<TrapDoorBlock> HATCH = register("hatch",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<TrapDoorBlock> HATCH_AIRLOCK = register("hatch_airlock",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<TrapDoorBlock> HATCH_GRATE = register("hatch_grate",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<TrapDoorBlock> HATCH_TRIM = register("hatch_trim",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<TrapDoorBlock> TRANSPORTER_PAD = register("transporter_pad",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<FenceBlock> TABLE = register("table",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<FenceBlock> SAFETY_FENCE_RED = register("safety_rail_red",
			() -> new FenceBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> SAFETY_FENCE_WHITE = register("safety_rail_white",
			() -> new FenceBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<RotatableChair> CAPTAINS_CHAIR_TOS = register("captains_chair_tos",
			() -> new RotatableChair(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<RotatedPillarBlock> CONDUIT_LARGE_UP_BLUE = register("conduit_large_up_blue",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<RotatedPillarBlock> CONDUIT_LARGE_UP_RED = register("conduit_large_up_red",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<RotatedPillarBlock> CONDUIT_LARGE_UP_YELLOW = register("conduit_large_up_yellow",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<RotatedPillarBlock> CONDUIT_LARGE_DOWN_BLUE = register("conduit_large_down_blue",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<RotatedPillarBlock> CONDUIT_LARGE_DOWN_RED = register("conduit_large_down_red",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<RotatedPillarBlock> CONDUIT_LARGE_DOWN_YELLOW = register("conduit_large_down_yellow",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<FenceBlock> CONDUIT_MEDIUM_UP_BLUE = register("conduit_medium_up_blue",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_MEDIUM_UP_RED = register("conduit_medium_up_red",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_MEDIUM_UP_YELLOW = register("conduit_medium_up_yellow",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_MEDIUM_DOWN_BLUE = register("conduit_medium_down_blue",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_MEDIUM_DOWN_RED = register("conduit_medium_down_red",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_MEDIUM_DOWN_YELLOW = register("conduit_medium_down_yellow",
			() -> new ConduitMedium(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	public static final RegistryObject<FenceBlock> CONDUIT_SMALL_UP_BLUE = register("conduit_small_up_blue",
			() -> new ConduitSmall(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_SMALL_UP_RED = register("conduit_small_up_red",
			() -> new ConduitSmall(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_SMALL_UP_YELLOW = register("conduit_small_up_yellow",
			() -> new ConduitSmall(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_SMALL_DOWN_BLUE = register("conduit_small_down_blue",
			() -> new ConduitSmall(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_SMALL_DOWN_RED = register("conduit_small_down_red",
			() -> new ConduitSmall(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));
	public static final RegistryObject<FenceBlock> CONDUIT_SMALL_DOWN_YELLOW = register("conduit_small_down_yellow",
			() -> new ConduitSmall(BlockBehaviour.Properties.of(Material.HEAVY_METAL).sound(SoundType.METAL)),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(TrekFiles.TREKFILES_TAB)));

	private static <T extends Block> RegistryObject<T> registerBlock(final String name,
			final Supplier<? extends T> block) {
		return BLOCKS.register(name, block);
	}

	private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
			Function<RegistryObject<T>, Supplier<? extends Item>> item) {
		RegistryObject<T> obj = registerBlock(name, block);
		ITEMS.register(name, item.apply(obj));
		return obj;
	}
}
