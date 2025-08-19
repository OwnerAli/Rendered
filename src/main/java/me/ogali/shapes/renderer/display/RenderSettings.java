package me.ogali.shapes.renderer.display;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;

/**
 * Configuration for rendering appearance
 */
@Getter
@Builder
public class RenderSettings {
    private final Color lineColor;
    private final Material lineMaterial;
    private final float lineThickness;
    private final boolean glowing;
    private final boolean persistent;

    public static RenderSettings defaultRenderSettings() {
        return RenderSettings.builder()
                .lineMaterial(Material.BLACK_CONCRETE)
                .lineColor(Color.RED)
                .lineThickness(0.04f)
                .glowing(true)
                .persistent(false)
                .build();
    }
}
