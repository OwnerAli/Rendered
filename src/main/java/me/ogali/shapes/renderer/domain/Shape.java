package me.ogali.shapes.renderer.domain;


import me.ogali.shapes.renderer.display.DisplayInfo;
import org.bukkit.Location;

import java.util.List;

/**
 * Represents any 3D shape that can be rendered
 */
public interface Shape {
    List<Edge> getEdges();
    List<DisplayInfo> getDisplays();
    Location getCenter();
    String getName();
}