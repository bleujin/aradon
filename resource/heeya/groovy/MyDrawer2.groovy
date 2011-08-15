package net.ion.heeya.test

import java.awt.Color
import groovy.swing.j2d.GraphicsOperation;
import groovy.swing.j2d.GraphicsRenderer

public class MyDrawer2 {

	public MyDrawer2(){
	}
	
	public void testDrawGroovy() throws Exception {
		def gfx = new GraphicsRenderer()

		gfx.renderToFile("d://groovy3.png", 500, 400, {
			antialias("on")
			circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
			rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
		}) ;
	}
	
	public static void main(String[] args) {
		new MyDrawer2().testDrawGroovy() ;
	}
}
