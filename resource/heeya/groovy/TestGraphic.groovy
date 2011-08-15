package net.ion.heeya.test;

import groovy.swing.j2d.GraphicsOperation;
import groovy.swing.j2d.GraphicsRenderer
import groovy.util.GroovyTestCase;
import java.awt.Color

class TestGraphic extends GroovyTestCase {

	public void testname() throws Exception {
		def gfx = new GraphicsRenderer()

		gfx.renderToFile("d://test.png", 500, 400, {
			antialias("on")
			circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
			rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
		}) ;
	
	
	
	}
	
	public void testMy() throws Exception {
		def gfx = new GraphicsRenderer()
		gfx.renderToFile("d://groovy2.png", 500, 400, {
		antialias("on")
		circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
		rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
		}) ;
	}
	
	public void testDrawGroovy() throws Exception {
		def gfx = new GraphicsRenderer()
		
		gfx.renderToFile("d://groovy.png", 500, 400){
			antialias("on")
			star( ir: 40, or: 100, cx: 160, cy: 120, borderWidth: 4, fill: [118,167,183] as Color ){
				transformations{ scale( y: 0.6 ) }
			}
			
			def fontFile = new File("c://windows/fonts/TAHOMA.TTF")
			// font( Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(50.0f))
			text( text: 'Groovy', borderWidth: 4, fill: 'white' ){
			   transformations{
				   translate( x:80, y:30 )
				   scale( y: 1.2 )
				}
			}
		}
	}
	
	public void testExec() throws Exception {
		GraphicsClass.draw()
	}
}
