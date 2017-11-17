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
public class Room {
    public Point begin;
    public Point end;
    
    public Room(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }
}
