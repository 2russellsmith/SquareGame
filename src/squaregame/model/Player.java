package squaregame.model;

import lombok.Getter;
import squaregame.squares.SquareLogic;
import squaregame.view.AISelectorComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Russell on 5/5/18.
 */
@Getter
public class Player {
    public Color color;
    public Color textColor;
    public AIOption aiOption;

    public Player (Color color, AIOption aiOption) {
        this.color = color;
        this.setTextColor();
        this.aiOption = aiOption;
    }



    public Color getColor() {
        return color;
    }
    public void setTextColor() {
        // original coefficients
        final double cr = 0.241;
        final double cg = 0.691;
        final double cb = 0.068;
        // another set of coefficients
        //      final double cr = 0.299;
        //      final double cg = 0.587;
        //      final double cb = 0.114;

        double r, g, b;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();

        // compute the weighted distance
        double result = Math.sqrt(cr * r * r + cg * g * g + cb * b * b);
        if (result > 126) {
            this.textColor = Color.BLACK;
        } else {
            this.textColor = Color.WHITE;
        }
    }

    public String getName() {
        return aiOption.getId();
    }

    public SquareLogic getStartingLogic() {
        try {
            return aiOption.getStartingSquareLogic();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AIOption getAiOption() {
        return this.aiOption;
    }

    public boolean isPlaying() {
        return aiOption != null;
    }
}
