package me.ogali.shapes.renderer.shapes;

import me.ogali.shapes.renderer.display.DisplayInfo;
import me.ogali.shapes.renderer.display.RenderSettings;
import me.ogali.shapes.renderer.domain.Edge;
import me.ogali.shapes.renderer.domain.Shape;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Cuboid shape implementation
 */
public class Cuboid implements Shape {
    private final Location cornerOne;
    private final Location cornerTwo;
    private final float lineThickness;
    private final List<DisplayInfo> displays;

    public Cuboid(Location cornerOne, Location cornerTwo, float lineThickness) {
        this.cornerOne = cornerOne.clone();
        this.cornerTwo = cornerTwo.clone();
        this.lineThickness = lineThickness;
        this.displays = new ArrayList<>();
    }

    public Cuboid addDisplay(DisplayInfo display) {
        displays.add(display);
        return this;
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        // Calculate all 8 vertices inclusively
        double minX = Math.min(cornerOne.getBlockX(), cornerTwo.getBlockX());
        double maxX = Math.max(cornerOne.getBlockX(), cornerTwo.getBlockX()) + 1;
        double minY = Math.min(cornerOne.getBlockY(), cornerTwo.getBlockY());
        double maxY = Math.max(cornerOne.getBlockY(), cornerTwo.getBlockY()) + 1;
        double minZ = Math.min(cornerOne.getBlockZ(), cornerTwo.getBlockZ());
        double maxZ = Math.max(cornerOne.getBlockZ(), cornerTwo.getBlockZ()) + 1;

        World world = cornerOne.getWorld();

        // Bottom vertices (block corners, not centers)
        Location bottomNW = new Location(world, minX, minY, maxZ);
        Location bottomNE = new Location(world, maxX, minY, maxZ);
        Location bottomSE = new Location(world, maxX, minY, minZ);
        Location bottomSW = new Location(world, minX, minY, minZ);

        // Top vertices
        Location topNW = new Location(world, minX, maxY, maxZ);
        Location topNE = new Location(world, maxX, maxY, maxZ);
        Location topSE = new Location(world, maxX, maxY, minZ);
        Location topSW = new Location(world, minX, maxY, minZ);

        // Bottom face edges
        edges.add(new Edge(bottomNW, bottomNE));
        edges.add(new Edge(bottomNE, bottomSE));
        edges.add(new Edge(bottomSE, bottomSW));
        edges.add(new Edge(bottomSW, bottomNW));

        // Top face edges
        edges.add(new Edge(topNW, topNE));
        edges.add(new Edge(topNE, topSE));
        edges.add(new Edge(topSE, topSW));
        edges.add(new Edge(topSW, topNW));

        // Vertical edges
        edges.add(new Edge(bottomNW, topNW));

        // Left edge back facing S
        Location bottomNEClone = bottomNE.clone();
        bottomNEClone.setX(bottomNEClone.getX() - 0.04);
        bottomNEClone.setY(bottomNEClone.getY() + 0.01);

        Location topNEClone = topNE.clone();
        topNEClone.setX(topNEClone.getX() - 0.04);
        topNEClone.setY(topNEClone.getY() + 0.01);

        edges.add(new Edge(bottomNEClone, topNEClone));

        // Left edge front facing S
        Location bottomSEClone = bottomSE.clone();
        bottomSEClone.setX(bottomSEClone.getX() - 0.04);
        bottomSEClone.setY(bottomSEClone.getY() + 0.01);
        bottomSEClone.setZ(bottomSEClone.getZ() + 0.04);

        Location topSEClone = topSE.clone();
        topSEClone.setX(topSEClone.getX() - 0.04);
        topSEClone.setY(topSEClone.getY() + 0.01);
        topSEClone.setZ(topSEClone.getZ() + 0.04);
        edges.add(new Edge(bottomSEClone, topSEClone));

        // Right edge front facing S
        Location bottomSWClone = bottomSW.clone();
        bottomSWClone.setY(bottomSWClone.getY() + 0.01);
        bottomSWClone.setZ(bottomSWClone.getZ() + 0.04);

        Location topSWClone = topSW.clone();
        topSWClone.setY(topSWClone.getY() + 0.01);
        topSWClone.setZ(topSWClone.getZ() + 0.04);

        edges.add(new Edge(bottomSWClone, topSWClone));

        return edges;
    }

    @Override
    public List<DisplayInfo> getDisplays() {
        return new ArrayList<>(displays);
    }

    @Override
    public Location getCenter() {
        double centerX = (cornerOne.getX() + cornerTwo.getX()) / 2;
        double centerY = (cornerOne.getY() + cornerTwo.getY()) / 2;
        double centerZ = (cornerOne.getZ() + cornerTwo.getZ()) / 2;
        return new Location(cornerOne.getWorld(), centerX, centerY, centerZ);
    }

    @Override
    public String getName() {
        return "cuboid";
    }

    private RenderSettings createRenderSettings() {
        // You can customize these based on region properties or player preferences
        Color lineColor = Color.RED; // Could be based on region type
        Material lineMaterial = Material.RED_TERRACOTTA; // Could be customizable

        return RenderSettings.builder()
                .lineColor(lineColor)
                .lineMaterial(lineMaterial)
                .lineThickness(0.04f)
                .glowing(true)
                .persistent(false)
                .build();
    }
}
