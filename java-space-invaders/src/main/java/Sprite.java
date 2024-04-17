import java.awt.Image;

/**
 * 
 * @author
 */
public class Sprite {

        private boolean visible;
        private Image image;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;

        /*
         * Constructor
         */
        public Sprite() {
            visible = true;
        }

        public void die() {
            visible = false;
        }

        public boolean isVisible() {
            return visible;
        }

        protected void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Image getImage() {
            return image;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public void setDying(boolean dying) {
            this.dying = dying;
        }

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
