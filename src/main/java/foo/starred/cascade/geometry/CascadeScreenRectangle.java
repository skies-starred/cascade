//? if >= 26.2 {
/*package foo.starred.cascade.geometry;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.joml.Matrix3x2fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CascadeScreenRectangle extends ScreenRectangle {
    public static final CascadeScreenRectangle EMPTY = new CascadeScreenRectangle(0, 0, 0, 0);

    public CascadeScreenRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public static @Nullable CascadeScreenRectangle of(@Nullable ScreenRectangle rectangle) {
        if (rectangle == null) return null;
        if (rectangle instanceof CascadeScreenRectangle rectangle1) return rectangle1;

        return new CascadeScreenRectangle(rectangle.left(), rectangle.top(), rectangle.width(), rectangle.height());
    }

    @Override
    public @NonNull CascadeScreenRectangle transformAxisAligned(@NonNull Matrix3x2fc pose) {
        ScreenRectangle rectangle = super.transformAxisAligned(pose);
        return new CascadeScreenRectangle(rectangle.left(), rectangle.top(), rectangle.width(), rectangle.height());
    }

    @Override
    public @NonNull CascadeScreenRectangle transformMaxBounds(@NonNull Matrix3x2fc pose) {
        ScreenRectangle rectangle = super.transformMaxBounds(pose);
        return new CascadeScreenRectangle(rectangle.left(), rectangle.top(), rectangle.width(), rectangle.height());
    }

    @Override
    public @Nullable CascadeScreenRectangle intersection(@NonNull ScreenRectangle other) {
        ScreenRectangle rectangle = super.intersection(other);
        if (rectangle == null) return null;

        return new CascadeScreenRectangle(rectangle.left(), rectangle.top(), rectangle.width(), rectangle.height());
    }
}
*///? }