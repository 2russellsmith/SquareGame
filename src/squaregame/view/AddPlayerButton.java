package squaregame.view;

import squaregame.model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddPlayerButton extends JButton implements ActionListener {

    public JScrollPane scrollPane;
    public Player player;

    public AddPlayerButton(JScrollPane jScrollPane, Player p) throws IOException {
        super(new ImageIcon(ImageIO.read(new File("src/squaregame/addPlayer.png"))));
        this.player = p;
        this.scrollPane = jScrollPane;
        this.setActionCommand("AddPlayer");
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.addActionListener(this);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scrollPane.setVisible(true);
        this.setVisible(false);
    }
}
