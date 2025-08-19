package me.ogali.shapes.renderer.domain;

import me.ogali.shapes.renderer.display.RenderSettings;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Represents a line between two points
 */
public record Edge(Location start, Location end) {
    public Edge(Location start, Location end) {
        this.start = start.clone();
        this.end = end.clone();
    }

    @Override
    public Location start() {
        return start.clone();
    }

    @Override
    public Location end() {
        return end.clone();
    }

    public BlockDisplay draw(RenderSettings settings) {
        if (start.getWorld() == null || start.getWorld() != end.getWorld())
            throw new IllegalArgumentException("World of start and end must be matching and non-null");

        Vector direction = end.toVector().subtract(start.toVector());
        double distance = direction.length();
        direction.normalize();

        // Convert to JOML vector
        Vector3f jomlDir = new Vector3f((float) direction.getX(),
                (float) direction.getY(),
                (float) direction.getZ());

        // Calculate rotation to align with direction
        Quaternionf rotation = new Quaternionf().rotateTo(new Vector3f(0, 0, 1), jomlDir);

        BlockDisplay display = start.getWorld().spawn(start, BlockDisplay.class);
        display.setBlock(settings.getLineMaterial().createBlockData());
        display.setGlowColorOverride(settings.getLineColor());
        display.setPersistent(settings.isPersistent());
        display.setGlowing(settings.isGlowing());

        Transformation transformation = new Transformation(
                new Vector3f(0, 0, 0), // no translation
                rotation, // align with direction
                new Vector3f(settings.getLineThickness(), settings.getLineThickness(), (float) distance),
                new Quaternionf() // no additional rotation
        );

        display.setTransformation(transformation);
        return display;
    }

    public double getLength() {
        return start.distance(end);
    }
}