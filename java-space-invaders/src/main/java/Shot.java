import javax.swing.*;

public class Shot extends Sprite {

    private final String shot = "/img/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot(){
    }
    public Shot(int x, int y) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
        setVisible(true); // The shot becomes visible when fired
    }

    @Override
    public Shot clone() {
        return (Shot) super.clone();
    }
}
