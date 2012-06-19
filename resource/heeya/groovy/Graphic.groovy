
net.ion.heeya.test.MyDrawer2.main(new String[0]);

//def gfx = new GraphicsRenderer()
//width = 500 
//height = 400 
//
//class myDraw extends groovy.lang.Closure {
//	public void run(){
//		antialias("on")
//		circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
//		rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
//	}
//}

// gfx.render(500, 400){
//            antialias("on")
//            circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
//            rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
//        }

//fontFile = new File("c:/windows/font/TAHOMA.TTF");

//gfx.renderToFile("d://groovy2.png", width, height, {
//	antialias("on")
//	circle( cx: 80, cy: 80, radius: 40,borderColor: 'red',borderWidth:2, fill: 'orange' )
//	rect( x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red' )
//}) ;


//}



//antialias( 'on' )
//// create the Groovy star
//star( ir: 40, or: 100, cx: 160, cy: 120, borderWidth: 4, fill: [118,167,183] as Color ){
//   transformations{
//      scale( y: 0.6 )
//   }
//}

//def out = new FileOutputStream("d://rect.png");
//ImageIO.write(img, "png", out);
//out.flush();
//out.close();


//
//
//swing = new SwingBuilder()
//pb = new GraphicsBuilder()
//
//go = pb.group {
//  rect( id: 'preview',  x: 10, y: 10, width: 50, height: 50, borderColor: 'black', fill:'red')
//}
//
//swing.edt {
//  frame( title: 'title', size: [400,400], show: true) {
//      panel(border: new TitledBorder( new EtchedBorder(), 'Preview'))
//     {
//         borderLayout()
//          scrollPane() {
//             panel(id: 'gp', new GraphicsPanel( minimumSize:[200,200], preferredSize:[200,200]), graphicsOperation: go)
//          }
//      }
//  }
//  swing.gp.preferredSize = [600, 600]
//  //swing.gp.minimumSize = [600, 600]
//}