package net.cerulan.globaldatapacks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Handler {
	
	private Path dataPackDirectory;
	private Path advancementDirectory, functionDirectory, lootTableDirectory;
	public Handler(File configDirectory) {
		dataPackDirectory = configDirectory.toPath().resolve("datapack");
		advancementDirectory = dataPackDirectory.resolve("advancements");
		functionDirectory = dataPackDirectory.resolve("functions");
		lootTableDirectory = dataPackDirectory.resolve("loot_tables");
		try {
			Files.createDirectories(advancementDirectory);
			Files.createDirectories(functionDirectory);
			Files.createDirectories(lootTableDirectory);
		} catch (IOException e) {
			throw new RuntimeException("Unable to create datapack directories.", e);
		}
		
		
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if(event.getWorld().isRemote) return;
		Path worldDataDir = DimensionManager.getCurrentSaveRootDirectory().toPath().resolve("data");
		Path worldAdv = worldDataDir.resolve("advancements");
		Path worldFunc = worldDataDir.resolve("functions");
		Path worldLoot = worldDataDir.resolve("loot_tables");
		
		try {
			
			if (!Files.exists(worldDataDir)) {
				Files.createDirectory(worldDataDir);
			}
			
			Files.createDirectories(worldAdv);
			Files.createDirectories(worldFunc);
			Files.createDirectories(worldLoot);
			if (GlobalDataPacks.instance().config.shouldCopyAdvancements()) {
				
				Files.list(advancementDirectory).forEach((path) -> {
					Path advNamespace = worldAdv.resolve(path.getFileName());
					try {
						FileUtils.deleteDirectory(advNamespace.toFile());
						FileUtils.copyDirectory(path.toFile(), advNamespace.toFile());
					} catch (IOException e) {
						throw new RuntimeException("Error writing new datapack information.", e);
					}
				});
			}
			if (GlobalDataPacks.instance().config.shouldCopyFunctions()) {
				Files.list(functionDirectory).forEach((path) -> {
					Path funcNamespace = worldFunc.resolve(path.getFileName());
					try {
						FileUtils.deleteDirectory(funcNamespace.toFile());
						FileUtils.copyDirectory(path.toFile(), funcNamespace.toFile());
					} catch (IOException e) {
						throw new RuntimeException("Error writing new datapack information.", e);
					}
				});
			}
			if (GlobalDataPacks.instance().config.shouldCopyLootTables()) {
				Files.list(lootTableDirectory).forEach((path) -> {
					Path lootNamespace = worldLoot.resolve(path.getFileName());
					try {
						FileUtils.deleteDirectory(lootNamespace.toFile());
						FileUtils.copyDirectory(path.toFile(), lootNamespace.toFile());
					} catch (IOException e) {
						throw new RuntimeException("Error writing new datapack information.", e);
					}
				});
			}
			
			
		} catch (IOException e) {
			throw new RuntimeException("Error managing world datapack directories.", e);
		}
		FMLCommonHandler.instance().getMinecraftServerInstance().reload();
		
	}
	
}