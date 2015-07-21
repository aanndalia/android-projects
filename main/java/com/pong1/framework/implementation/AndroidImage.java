package com.pong1.framework.implementation;

import android.graphics.Bitmap;

import com.pong1.framework.Image;
import com.pong1.framework.Graphics;

public class AndroidImage implements Image {
    Bitmap bitmap;
    Graphics.ImageFormat format;
    
    public AndroidImage(Bitmap bitmap, Graphics.ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public Graphics.ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
