package com.layalty.app.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by 19569 on 8/14/2017.
 */

public class AppUtils {

    private static final int QR_SIZE = 480;
    /**
     * method: responsible for generation Bitmap image from given string input
     *
     * @param content
     *            : string value for that QR code have to be generated
     * @return: bitmap
     */
    public static Bitmap generateQR(String content) {
        Bitmap returnBitmap = null;
        try {
			/*
			 * Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new
			 * Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			 * hintMap.put(EncodeHintType.ERROR_CORRECTION,
			 * ErrorCorrectionLevel.L);
			 */
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(
                    EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 2); /* default = 4 */
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(content,
                    BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE, hints);
            // int width = matrix.getWidth();
            int width = matrix.getHeight();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int grey = matrix.get(x, y) ? 0x00 : 0xff;
                    pixels[y * width + x] = 0xff000000 | (0x00010101 * grey);
                }
            }
            returnBitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            returnBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            // returnBitmap.set
        } catch (Exception e) {
            // Log.d("", e.toString());
            e.printStackTrace();
        }
        return returnBitmap;
    }

}
