package me.ogali.shapes.renderer.shapes;

import me.ogali.shapes.renderer.display.DisplayInfo;
import me.ogali.shapes.renderer.domain.Edge;
import me.ogali.shapes.renderer.domain.Shape;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Sphere implements Shape {
    private final Location center;
    private final double radius;
    private final int segments;

    public Sphere(Location center, double radius, int segments) {
        this.center = center.clone();
        this.radius = radius;
        this.segments = segments;
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        // High-resolution sphere with proper geodesic distribution
        int latLines = segments;
        int lonLines = segments * 2;

        // Create latitude circles (parallels)
        for (int lat = 0; lat <= latLines; lat++) {
            double phi = Math.PI * lat / latLines;
            double y = Math.cos(phi) * radius;
            double ringRadius = Math.sin(phi) * radius;

            if (ringRadius > 0.1) {
                List<Location> circle = createCircle(center, y, ringRadius, lonLines);
                connectPoints(circle, edges, true); // true for closed circle
            }
        }
        return edges;
    }

    @Override
    public List<DisplayInfo> getDisplays() {
        return List.of();
    }

    @Override
    public Location getCenter() {
        return center.clone();
    }

    @Override
    public String getName() {
        return "sphere";
    }

    private List<Location> createCircle(Location center, double yOffset, double radius, int segments) {
        List<Location> points = new ArrayList<>();
        for (int i = 0; i < segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            points.add(new Location(center.getWorld(),
                    center.getX() + x, center.getY() + yOffset, center.getZ() + z));
        }
        return points;
    }

    private void connectPoints(List<Location> points, List<Edge> edges, boolean closed) {
        for (int i = 0; i < points.size() - (closed ? 0 : 1); i++) {
            Location current = points.get(i);
            Location next = points.get((i + 1) % points.size());
            edges.add(new Edge(current, next));
        }
    }
}