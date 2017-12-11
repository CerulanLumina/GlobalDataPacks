package net.cerulan.globaldatapacks;

import static net.cerulan.globaldatapacks.reference.Messages.INVALID_FINGERPRINT_MESSAGE;
import static net.cerulan.globaldatapacks.reference.Messages.NO_FINGERPRINT_MESSAGE;
import static net.cerulan.globaldatapacks.reference.Reference.Mod.DEPENDENCIES;
import static net.cerulan.globaldatapacks.reference.Reference.Mod.FINGERPRINT;
import static net.cerulan.globaldatapacks.reference.Reference.Mod.ID;
import static net.cerulan.globaldatapacks.reference.Reference.Mod.MINECRAFT;
import static net.cerulan.globaldatapacks.reference.Reference.Mod.NAME;
import static net.cerulan.globaldatapacks.reference.Reference.Mod.VERSION;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES, acceptedMinecraftVersions = MINECRAFT, acceptableRemoteVersions="*")
public final class GlobalDataPacks {

    @Instance
    private static GlobalDataPacks INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger(NAME);
    
    public Config config;

    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		this.config = new Config(new Configuration(event.getModConfigurationDirectory().toPath().resolve("globaldatapacks").resolve(event.getSuggestedConfigurationFile().getName()).toFile()));
    	config.load();
    	MinecraftForge.EVENT_BUS.register(new Handler(event.getModConfigurationDirectory().toPath().resolve("globaldatapacks").toFile()));
    }
    
    @Nonnull
    public static GlobalDataPacks instance() {
        return INSTANCE;
    }

    @Nonnull
    public static Logger logger() {
        return LOGGER;
    }

    @EventHandler
    public static void onInvalidFingerprint(@Nonnull FMLFingerprintViolationEvent event) {
        if (FINGERPRINT.contains("@")) {
            logger().info(NO_FINGERPRINT_MESSAGE);
        } else {
            logger().warn(INVALID_FINGERPRINT_MESSAGE);
        }
    }

}
