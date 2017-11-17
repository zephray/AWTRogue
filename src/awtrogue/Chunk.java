/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awtrogue;

import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author zephray
 */
public class Chunk {
    public Point startPosition;
    public ArrayList<Tile> tiles;
    
    public Chunk(Point startPosition) {
        this.startPosition = startPosition;
        tiles = new ArrayList<>();
    }
    
    public Tile getTileAt(Point position) {
        for (Tile tile: tiles) {
            if ((tile.position.x == position.x)&&(tile.position.y == position.y)) {
                return tile;
            }
        }
        return null;
    }
}
