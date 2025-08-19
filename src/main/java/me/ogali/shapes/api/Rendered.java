package me.ogali.shapes.api;

import me.ogali.shapes.renderer.display.RenderSettings;
import me.ogali.shapes.renderer.display.ShapeRenderer;
import me.ogali.shapes.renderer.domain.Shape;
import me.ogali.shapes.renderer.shapes.Cuboid;
import me.ogali.shapes.renderer.shapes.Sphere;
import org.bukkit.Location;

@SuppressWarnings("unused")
public final class Rendered {
    private static Rendered instance;

    /**
     * Checks if the API has been initialized.
     *
     * @return true if the API is initialized, false otherwise
     */
    public static boolean isInitialized() {
        return instance != null;
    }

    /**
     * Renders a cuboid shape using default render settings.
     * Default settings: BLACK_CONCRETE material, RED color, 0.04f thickness, glowing enabled, persistence disabled.
     *
     * @param id        The unique identifier for this rendered shape
     * @param cornerOne The first corner location of the cuboid
     * @param cornerTwo The second corner location of the cuboid
     */
    public void renderCuboid(String id, Location cornerOne, Location cornerTwo) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.renderShape(id, new Cuboid(cornerOne, cornerTwo, shapeRenderer.getDefaultSettings().getLineThickness()));
    }

    /**
     * Renders a cuboid shape using custom render settings.
     *
     * @param id             The unique identifier for this rendered shape
     * @param cornerOne      The first corner location of the cuboid
     * @param cornerTwo      The second corner location of the cuboid
     * @param renderSettings Custom settings to use for rendering
     */
    public void renderCuboidWithCustomSettings(String id, Location cornerOne, Location cornerTwo, RenderSettings renderSettings) {
        ShapeRenderer shapeRenderer = new ShapeRenderer(renderSettings);
        shapeRenderer.renderShape(id, new Cuboid(cornerOne, cornerTwo, renderSettings.getLineThickness()));
    }

    /**
     * Renders a sphere shape using default render settings.
     * Default settings: BLACK_CONCRETE material, RED color, 0.04f thickness, glowing enabled, persistence disabled.
     *
     * @param id       The unique identifier for this rendered shape
     * @param center   The center location of the sphere
     * @param radius   The radius of the sphere
     * @param segments The number of segments to use (higher = smoother sphere)
     */
    public void renderSphere(String id, Location center, double radius, int segments) {
        renderShape(id, new Sphere(center, radius, segments));
    }

    /**
     * Renders a sphere shape using custom render settings.
     *
     * @param id             The unique identifier for this rendered shape
     * @param center         The center location of the sphere
     * @param radius         The radius of the sphere
     * @param segments       The number of segments to use (higher = smoother sphere)
     * @param renderSettings Custom settings to use for rendering
     */
    public void renderSphereWithCustomSettings(String id, Location center, double radius, int segments, RenderSettings renderSettings) {
        renderShape(id, new Sphere(center, radius, segments), renderSettings);
    }

    /**
     * Renders a shape using default render settings.
     * Default settings: BLACK_CONCRETE material, RED color, 0.04f thickness, glowing enabled, persistence disabled.
     *
     * @param id    The unique identifier for this rendered shape
     * @param shape The shape to render
     */
    public void renderShape(String id, Shape shape) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.renderShape(id, shape);
    }

    /**
     * Renders a shape using custom render settings.
     *
     * @param id             The unique identifier for this rendered shape
     * @param shape          The shape to render
     * @param renderSettings Custom settings to use for rendering
     */
    public void renderShape(String id, Shape shape, RenderSettings renderSettings) {
        ShapeRenderer shapeRenderer = new ShapeRenderer(renderSettings);
        shapeRenderer.renderShape(id, shape);
    }

    //#region Lazy Initialization
    public static Rendered getInstance() {
        return Rendered.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final Rendered instance = new Rendered();
    }
    //#endregion
}
