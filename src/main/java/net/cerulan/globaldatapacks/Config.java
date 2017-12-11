package net.cerulan.globaldatapacks;

import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class Config {

	public static final String NAMESPACE_PATTERN = "^[a-z_]+$";
	
	private Configuration config;
	public Config(Configuration config) {
		this.config = config;
	}
	
	private List<String> namespaces;
	private boolean copyAdvancements;
	private boolean copyFunctions;
	private boolean copyLootTables;
	
	public void load() {
		/*String[] namespacesArr = config.get("globaldatapacks", "namespaces", new String[] { "example_namespace", "other_example_namespace" },
				"Selects the namespaces to be copied to each world's datapack. Namespaces not listed here will not be copied. Any namespaces that are listed here will be copied, overwriting any changes made to the active datapack. Must match snake_case.",
				Pattern.compile(NAMESPACE_PATTERN)).getStringList();
		
		this.namespaces = Arrays.asList(namespacesArr);*/
		
		copyAdvancements = config.get("globaldatapacks", "copyAdvancements", true, "Sets whether or not this mod should copy advancements from the global datapacks to each world").getBoolean();
		copyFunctions = config.get("globaldatapacks", "copyFunctions", true, "Sets whether or not this mod should copy functions from the global datapacks to each world").getBoolean();
		copyLootTables = config.get("globaldatapacks", "copyLootTables", true, "Sets whether or not this mod should copy loot tables from the global datapacks to each world").getBoolean();
		
		this.config.save();
		
	}
	
	
	
	public List<String> getNamespaces() {
		return this.namespaces;
	}



	public boolean shouldCopyAdvancements() {
		return copyAdvancements;
	}



	public boolean shouldCopyFunctions() {
		return copyFunctions;
	}



	public boolean shouldCopyLootTables() {
		return copyLootTables;
	}
	
}
