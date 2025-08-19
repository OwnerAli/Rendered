package me.ogali.shapes.renderer.display;

import lombok.Getter;
import me.ogali.shapes.utils.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Information for creating display entities
 */
@Getter
public class DisplayInfo {
    public enum Type { TEXT, BLOCK, ITEM }
    private final Type type;
    private final Location location;
    private final String text;
    private final Material material;
    private final ItemStack itemStack;
    private Double scale;
    
    private DisplayInfo(Type type, Location location, String text, Material material, 
                       ItemStack itemStack) {
        this.type = type;
        this.location = location.clone();
        this.text = text;
        this.material = material;
        this.itemStack = itemStack;
    }

    private DisplayInfo(Type type, Location location, String text, Material material,
                        ItemStack itemStack, Double scale) {
        this.type = type;
        this.location = location.clone();
        this.text = text;
        this.material = material;
        this.itemStack = itemStack;
        this.scale = scale;
    }
    
    public static DisplayInfo text(Location location, String text) {
        return new DisplayInfo(Type.TEXT, location, Chat.colorize(text), null, null);
    }
    
    public static DisplayInfo block(Location location, Material material) {
        return new DisplayInfo(Type.BLOCK, location, null, material, null);
    }

    public static DisplayInfo block(Location location, Material material, Double scale) {
        return new DisplayInfo(Type.BLOCK, location, null, material, null, scale);
    }
    
    public static DisplayInfo item(Location location, ItemStack itemStack) {
        return new DisplayInfo(Type.ITEM, location, null, null, itemStack);
    }

    public Location getLocation() { return location.clone(); }
}
