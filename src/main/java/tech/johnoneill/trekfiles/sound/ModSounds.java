package tech.johnoneill.trekfiles.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.johnoneill.trekfiles.TrekFiles;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TrekFiles.MOD_ID);

    public static final RegistryObject<SoundEvent> DEFAULT_DOOR_OPEN = registerSoundEvent("default_door_open");
    public static final RegistryObject<SoundEvent> DEFAULT_DOOR_CLOSE = registerSoundEvent("default_door_close");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(TrekFiles.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
