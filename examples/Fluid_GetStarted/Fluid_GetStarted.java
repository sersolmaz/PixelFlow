/**
 * 
 * PixelFlow | Copyright (C) 2016 Thomas Diewald - http://thomasdiewald.com
 * 
 * A Processing/Java library for high performance GPU-Computing (GLSL).
 * MIT License: https://opensource.org/licenses/MIT
 * 
 */

package Fluid_GetStarted;

import com.thomasdiewald.pixelflow.java.Fluid;
import com.thomasdiewald.pixelflow.java.PixelFlow;

import processing.core.*;
import processing.opengl.PGraphics2D;

public class Fluid_GetStarted extends PApplet {
  

  // fluid simulation
  Fluid fluid;
  
  // render targets
  PGraphics2D pg_fluid;
  
  public void settings() {
    size(800, 800, P2D);
  }
  
  public void setup() {
       
    // library context
    PixelFlow context = new PixelFlow(this);
    
    // fluid simulation
    fluid = new Fluid(context, width, height, 1);
    
    // some fluid parameters
    fluid.param.dissipation_velocity = 0.70f;
    fluid.param.dissipation_density  = 0.99f;

    // adding data to the fluid simulation
    fluid.addCallback_FluiData(new  Fluid.FluidData(){
      public void update(Fluid fluid) {
        if(mousePressed){
          float px     = mouseX;
          float py     = height-mouseY;
          float vx     = (mouseX - pmouseX) * +15;
          float vy     = (mouseY - pmouseY) * -15;
          fluid.addVelocity(px, py, 14, vx, vy);
          fluid.addDensity (px, py, 20, 0.0f, 0.4f, 1.0f, 1.0f);
          fluid.addDensity (px, py,  8, 1.0f, 1.0f, 1.0f, 1.0f);
        }
      }
    });
   
    // render-target
    pg_fluid = (PGraphics2D) createGraphics(width, height, P2D);
    
    frameRate(60);
  }
  

  public void draw() {    
    // update simulation
    fluid.update();
    
    // clear render target
    pg_fluid.beginDraw();
    pg_fluid.background(0);
    pg_fluid.endDraw();

    // render fluid stuff
    fluid.renderFluidTextures(pg_fluid, 0);

    // display
    image(pg_fluid, 0, 0);
  }
  


  public static void main(String args[]) {
    PApplet.main(new String[] { Fluid_GetStarted.class.getName() });
  }
}