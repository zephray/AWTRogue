/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awtrogue;

import java.awt.Point;

/**
 *
 * @author zephray
 */
public class Tile {
    public Point position;
    public int id;
    
    public Tile(Point position, int id) {
        this.position = position;
        this.id = id;
    }
}
