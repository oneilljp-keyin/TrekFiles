package tech.johnoneill.trekfiles.painting;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.johnoneill.trekfiles.TrekFiles;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, TrekFiles.MOD_ID);

    public static final RegistryObject<PaintingVariant> VIEWSCREEN_DEFIANT = PAINTING_VARIANTS.register("viewscreen_defiant",
            ()-> new PaintingVariant(64,32));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANTS.register(eventBus);
    }
}
