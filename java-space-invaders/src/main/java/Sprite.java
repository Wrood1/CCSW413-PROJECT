import java.awt.Image;
import java.util.Objects;

/**
 * Base class for game sprites.
 */
public class Sprite implements Cloneable {
    // Instance variables
    private boolean visible; // Indicates whether the sprite is visible
    private Image image; // Represents the graphical image associated with the sprite
    protected int x;     // x-coordinate of the sprite

    protected int y;// y-coordinate of the sprite
    protected boolean dying;     // Indicates whether the sprite is in a dying state

    protected int dx;

    /*
     * Constructor
     */
    public Sprite() {
        visible = true;
    }

    /**
     * Marks the sprite as "dying".
     * This typically means that the sprite will be removed from the game soon.
     */
    public void die() {
        visible = false;
    }

    /**
     * Checks if the sprite is visible.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the sprite.
     */
    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Sets the image associated with the sprite.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the image associated with the sprite.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the x-coordinate of the sprite.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the sprite.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the y-coordinate of the sprite.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the x-coordinate of the sprite.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets whether the sprite is in a dying state.
     */
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    /**
     * Checks if the sprite is in a dying state.
     */
    public boolean isDying() {
        return this.dying;
    }

    /**
     * Clones the sprite.
     * Performs a shallow copy of the object and a deep copy of the Image object.
     */
    @Override
    protected Object clone() {
        try {
            Sprite clonedSprite = (Sprite) super.clone(); // Performs shallow copy
            // Deep copy of the image (assuming Image is immutable)
            clonedSprite.image = this.image;
            return clonedSprite;
        } catch (CloneNotSupportedException e) {
            // This should never happen since we implement Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * Checks if this sprite is equal to another object.
     * Two sprites are considered equal if their attributes are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sprite)) return false;
        Sprite sprite = (Sprite) o;
        return visible == sprite.visible &&
                x == sprite.x &&
                y == sprite.y &&
                dying == sprite.dying &&
                Objects.equals(image, sprite.image);
    }

    /**
      Generates a hash code for sprite
      ---- The hash code is based on sprite's attributes.
     */
    @Override
    public int hashCode() {
        return Objects.hash(visible, image, x, y, dying);
    }
}
