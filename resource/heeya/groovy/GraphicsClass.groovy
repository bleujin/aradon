package net.ion.heeya.test

import groovy.swing.j2d.GraphicsRenderer
import groovy.swing.j2d.GraphicsOperation;

class GraphicsClass {
	
	public static void draw(){
		def gfx = new GraphicsRenderer()
		gfx.renderToFile("d://test1.png", 500, 400){
					antialias("on")
					circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
					rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
				}
	}
	
}
