import groovy.swing.SwingBuilder
import groovy.swing.j2d.GraphicsBuilder
import groovy.swing.j2d.GraphicsPanel

def gb = new GraphicsBuilder()

def go = gb.group {
   def foreground = color(r:1,g:1)
   def background = color('black')
   (0..9).each { i ->
      (0..19).each { j ->
         def cell = "c_${i}_${j}"
         rect( x:20*j, y:20*i, w: 20, h: 20, bc: background,
               f: animate( [foreground,background],
                            startValue: background,
                            duration: 5000, start: false,
                            id: "$cell" ),
               mouseEntered: {e ->
                  gb."$cell".restart()
               }
         )
      }
   }
}
  
def swing = SwingBuilder.build {
   frame( title: "Groodles", size: [410,230],
          locationRelativeTo: null, show: true ){
      panel( new GraphicsPanel(), graphicsOperation: go )
   }
}