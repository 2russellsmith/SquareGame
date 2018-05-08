package squaregame.controller;

import squaregame.squares.SquareLogic;

import java.util.List;

public class SquareLogicClassLoader extends ClassLoader {

    public List<SquareLogic> getAiOptions() {
        Package[] p = this.getPackages();
        return null;
    }
}
