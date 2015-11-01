package com.libqa.application.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class GetThumbImage {

	public void resizeImage(File src, File dest, int width, int height) throws IOException{
		
		int RATIO = 0;
		int SAME = -1;
		
		Image srcImg = setImage(src);
		
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);
		int destWidth = -1, destHeight = -1;
		
		if(width == SAME) destWidth = srcWidth;
		else if(width > 0) destWidth = width;
		
		if(height == SAME) destHeight = srcHeight;
		else if(height > 0) destHeight = height;
		
		if(width == RATIO && height == RATIO){
			
			destWidth = srcWidth;
			destHeight = srcHeight;
			
		}
		else if(width == RATIO){
			
			double ratio = ((double) destHeight) / ((double) srcHeight);
			destWidth = (int) ((double) srcWidth * ratio) - 1;
			
		}
		else if(height == RATIO){
			
		   double ratio = ((double) destWidth) / ((double) srcWidth);
		   destHeight = (int) ((double) srcHeight * ratio) - 1;
		   
		}
		
		
		Image imgTarget = srcImg.getScaledInstance(destWidth,destHeight,Image.SCALE_SMOOTH);
		int pixels[] = new int[destWidth * destHeight];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth);
		try{
			
			pg.grabPixels();
			
		}
		catch(InterruptedException e){
			
			throw new IOException(e.getMessage());
			
		}
		
		BufferedImage destImg=new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);
		ImageIO.write(destImg, "jpg", dest);
		
	}
		
	
	
	private Image setImage(File src) throws IOException{
		Image srcImg = null;
		String suffix = src.getName().substring(src.getName().lastIndexOf('.') + 1).toLowerCase();
		
		if(suffix.equals("bmp")) srcImg = ImageIO.read(src);
		else srcImg = new ImageIcon(src.toURI().toURL()).getImage();
		
		return srcImg;
	
	}
	
}
