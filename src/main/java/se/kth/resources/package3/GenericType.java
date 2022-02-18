package se.kth.resources.package3;
import com.google.inject.internal.util.Maps;

import java.util.Map;


/**
 * Represents various types of worlds that may exist
 */
public enum GenericType {
    NORMAL("DEFAULT"),
    FLAT("FLAT"),
    VERSION_1_1("DEFAULT_1_1"),
    LARGE_BIOMES("LARGEBIOMES"),
    AMPLIFIED("AMPLIFIED");

    private final static Map<String, GenericType> BY_NAME = Maps.newHashMap();
    private final String name;

    private GenericType(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this WorldType
     *
     * @return Name of this type
     */
    public String getName() {
        return name;
    }

    /**
     * Gets a Worldtype by its name
     *
     * @param name Name of the WorldType to get
     * @return Requested WorldType, or null if not found
     */
    public static GenericType getByName(String name) {
        return BY_NAME.get(name.toUpperCase());
    }

    static {
        for (GenericType type : values()) {
            BY_NAME.put(type.name, type);
        }
    }
}
