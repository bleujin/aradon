// requestAnimFrame shim
window.requestAnimFrame = (function()
{
   return  window.requestAnimationFrame       || 
           window.webkitRequestAnimationFrame || 
           window.mozRequestAnimationFrame    || 
           window.oRequestAnimationFrame      || 
           window.msRequestAnimationFrame     || 
           function(callback, frameOffset)
           {
               window.setTimeout(callback, frameOffset);
           };
})();

// helper functions
function el(id)
{
   return document.getElementById(id);
}

// user variables
var width, height, ctx;
// global variables TODO: namespace!
var shimCanvasDefaultSize = 500;
var shimRealtime, shimAnimate, shimError, shimCanvas;

// onload handler
function shimOnload()
{
   // canvas shim init
   shimRealtime = el("control-realtime").checked;
   shimAnimate = el("control-animate").checked;
   shimError = false;
   shimCanvas = el("canvas");
   
   shimCanvas.width = shimCanvas.height = shimCanvasDefaultSize;
   
   // user controls event handlers
   el("control-realtime").addEventListener("change", function() {
         shimRealtime = el("control-realtime").checked;
         //el("control-run").disabled = shimRealtime;
         if (shimRealtime) fnCompile();
      }, false);
   el("control-animate").addEventListener("change", function() {
         shimAnimate = el("control-animate").checked;
         if (shimAnimate) fnShimAnimate();
      }, false);
   el("control-run").addEventListener("click", function() {
         fnCompile();
      }, false);
   el("control-demos").addEventListener("change", function() {
         var demofile = el("control-demos").value;
         if (demofile)
         {
            // Request content of the example .js file
            var oXHR = new XMLHttpRequest();
            oXHR.open("GET", el("control-demos").value, true);
            oXHR.onreadystatechange = function (e) {
                  if (oXHR.readyState === 4)
                  {
                     // Status 200 OK or local dev code is 0
                     if (oXHR.status === 200 || oXHR.status === 0)
                     {
                        el("code").value = oXHR.responseText;
                        if (shimRealtime) fnCompile();
                     }
                  }
               };
            oXHR.send(null);
         }
      }, false);
   
   // init user variables
   width = shimCanvas.width;
   height = shimCanvas.height;
   ctx = shimCanvas.getContext('2d');
   
   // handler for realtime script compile
   el("code").addEventListener('keyup', function(e) {
         // ignore keycodes for cursors etc.
         if ((e.keyCode < 16 || e.keyCode > 40) && shimRealtime) fnCompile();
      }, false);
   
   // init liquid layout
   shimResizeHandler();
   
   // kick off initial compile and animation if checked
   fnCompile();
   if (shimAnimate) fnShimAnimate();
}

// resize handler
function shimResizeHandler()
{
   var code = el("code");
   var w = (window.innerWidth - shimCanvasDefaultSize - 32);
   // minimum width of the code entry area
   if (w > 320) code.style.width = w + "px";
   // approx height of the info section
   code.style.height = (window.innerHeight - 190) + "px";
}

window.addEventListener('resize', shimResizeHandler, false);
window.addEventListener('load', shimOnload, false);

// user code function and compiler
var shimCode = null;
function fnCompile()
{
   try
   {
      // test code for evaluation success - update the function that is used by the animation
      // new lines are added to deal with potential comments on final line of input string
      eval("shimCode = function() {\n" + el("code").value + "\n};");
      // clear any error info that might have previously been displayed
      if (shimError)
      {
         el("errors").style.display = "none";
         shimError = false;
      }
      if (!shimAnimate) fnShimAnimate();
   }
   catch (e)
   {
      // syntax error or similar
      el("errors").innerHTML = e.message;
      el("errors").style.display = "block";
      shimError = true;
   }
}

// animation function
function fnShimAnimate()
{
   ctx.save();
   try
   {
      if (shimCode) shimCode.call(this);
   }
   catch (e)
   {
      // runtime error or similar
      el("errors").innerHTML = e.message;
      el("errors").style.display = "block";
      shimError = true;
   }
   ctx.restore();
   if (shimAnimate) requestAnimFrame(fnShimAnimate);
}