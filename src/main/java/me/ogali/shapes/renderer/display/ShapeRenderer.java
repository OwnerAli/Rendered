package me.ogali.shapes.renderer.display;

import lombok.Getter;
import me.ogali.shapes.renderer.domain.Edge;
import me.ogali.shapes.renderer.domain.Shape;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract rendering engine capable of rendering any 3D shape with lines and display entities
 */
public class ShapeRenderer {
    private final Map<String, Set<Entity>> activeRenders = new ConcurrentHashMap<>();

    @Getter
    private final RenderSettings defaultSettings;

    public ShapeRenderer() {
        this.defaultSettings = RenderSettings.builder()
                .lineMaterial(Material.BLACK_CONCRETE)
                .lineColor(Color.RED)
                .lineThickness(0.04f)
                .glowing(true)
                .persistent(false)
                .build();
    }

    public ShapeRenderer(RenderSettings renderSettings) {
        this.defaultSettings = renderSettings;
    }

    /**
     * Renders a shape with custom settings
     */
    public void renderShape(String id, Shape shape, RenderSettings settings) {
        Set<Entity> entities = new HashSet<>();

        // Render all edges
        for (Edge edge : shape.getEdges()) {
            BlockDisplay line = edge.draw(settings);
            entities.add(line);
        }

        activeRenders.put(id, entities);
    }

    /**
     * Renders a shape with custom settings
     */
    public void renderShape(String id, Shape shape) {
        Set<Entity> entities = new HashSet<>();

        // Render all edges
        for (Edge edge : shape.getEdges()) {
            BlockDisplay line = edge.draw(defaultSettings);
            entities.add(line);
        }

        activeRenders.put(id, entities);
    }

    /**
     * Clears all renders for a player
     */
    public void clearRender(String id) {
        Set<Entity> entities = activeRenders.remove(id);
        if (entities != null) {
            entities.forEach(Entity::remove);
        }
    }

    /**
     * Clears all renders
     */
    public void clearAll() {
        activeRenders.values().forEach(entities ->
                entities.forEach(Entity::remove));
        activeRenders.clear();
    }

    private Entity createDisplay(Player player, DisplayInfo info, RenderSettings settings) {
        Location loc = info.getLocation();

        return switch (info.getType()) {
            case TEXT -> player.getWorld().spawn(loc, TextDisplay.class, textDisplay -> {
                textDisplay.setText(info.getText());
                textDisplay.setPersistent(settings.isPersistent());
                textDisplay.setBillboard(Display.Billboard.CENTER);
                textDisplay.setBackgroundColor(Color.BLACK);
            });
            case BLOCK -> player.getWorld().spawn(loc, BlockDisplay.class, blockDisplay -> {
                blockDisplay.setBlock(info.getMaterial().createBlockData());
                blockDisplay.setPersistent(settings.isPersistent());
                double scale = info.getScale() != null ? info.getScale() : 1;
                Transformation transformation = new Transformation(
                        new Vector3f(), // no translation
                        new AxisAngle4f(), // no left rotation
                        new Vector3f((float) scale, (float) scale, (float) scale), // scale up by a factor of 2 on all axes
                        new AxisAngle4f() // no right rotation
                );
                blockDisplay.setTransformation(transformation);
            });
            case ITEM -> player.getWorld().spawn(loc, ItemDisplay.class, itemDisplay -> {
                itemDisplay.setItemStack(info.getItemStack());
                itemDisplay.setGlowColorOverride(settings.getLineColor());
                itemDisplay.setPersistent(settings.isPersistent());
                itemDisplay.setGlowing(settings.isGlowing());
                itemDisplay.setBillboard(Display.Billboard.CENTER);
            });
        };
    }
}
