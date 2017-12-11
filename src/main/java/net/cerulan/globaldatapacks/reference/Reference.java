package net.cerulan.globaldatapacks.reference;

public final class Reference {

    public static final class Mod {

        public static final String ID = "@MOD_MODID@";
        public static final String NAME = "@MOD_NAME@";

        public static final String VERSION = "@MOD_VERSION@";
        public static final String DEPENDENCIES = "required-after:forge@[@FORGE_VERSION@,)";
        public static final String MINECRAFT = "[@MINECRAFT_VERSION@,)";

        public static final String FINGERPRINT = "@FINGERPRINT@";

    }

}
