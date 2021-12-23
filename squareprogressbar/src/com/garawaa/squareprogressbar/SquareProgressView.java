package com.garawaa.squareprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

import com.garawaa.squareprogressbar.utils.CalculationUtil;
import com.garawaa.squareprogressbar.utils.PercentStyle;

public class SquareProgressView extends View {

    private double progress;
    private Paint progressBarPaint;
    private Paint outlinePaint;
    private Paint centerlinePaint;
    private Paint textPaint;

    private float widthInDp = 10;
    private float strokewidth = 0;



    private float outlinestrokewidth = 2;
    private float centerlinestrokewidth = 3;




    private Canvas canvas;

    private boolean outline = false;
    private boolean startline = false;
    private boolean showProgress = false;
    private boolean centerline = false;



    private boolean ccw = false;
    private boolean roundedCorners = false;

    public boolean isCcw() {
        return ccw;
    }

    public void setCcw(boolean ccw) {
        this.ccw = ccw;
        this.invalidate();
    }
    public float getRoundedCornersRadius() {
        return roundedCornersRadius;
    }

    public void setRoundedCornersRadius(float roundedCornersRadius) {
        this.roundedCornersRadius = roundedCornersRadius;
        this.invalidate();
    }
    public float getOutlinestrokewidth() {
        return outlinestrokewidth;
    }

    public void setOutlinestrokewidth(float outlinestrokewidth) {
        this.outlinestrokewidth = outlinestrokewidth;
        this.invalidate();
    }

    public float getCenterlinestrokewidth() {
        return centerlinestrokewidth;
    }

    public void setCenterlinestrokewidth(float centerlinestrokewidth) {
        this.centerlinestrokewidth = centerlinestrokewidth;
        this.invalidate();
    }
    private float roundedCornersRadius = 0;

    private PercentStyle percentSettings = new PercentStyle(Align.CENTER, 150,
            true);
    private boolean clearOnHundred = false;
    private boolean isIndeterminate = false;
    private int indeterminate_count = 1;

    private float indeterminate_width = 20.0f;

    public SquareProgressView(Context context) {
        super(context);
        initializePaints(context);
    }

    public SquareProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializePaints(context);
    }

    public SquareProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaints(context);
    }

    private void initializePaints(Context context) {
        progressBarPaint = new Paint();
        progressBarPaint.setColor(context.getResources().getColor(
                android.R.color.holo_green_dark));
        progressBarPaint.setStrokeWidth(CalculationUtil.convertDpToPx(
                widthInDp, getContext()));
        progressBarPaint.setAntiAlias(true);
        progressBarPaint.setStyle(Style.STROKE);

        outlinePaint = new Paint();
        outlinePaint.setColor(context.getResources().getColor(
                android.R.color.black));
        outlinePaint.setStrokeWidth(outlinestrokewidth);
        outlinePaint.setAntiAlias(true);
        outlinePaint.setStyle(Style.STROKE);

        centerlinePaint = new Paint();
        centerlinePaint.setColor(context.getResources().getColor(
                android.R.color.darker_gray));
        centerlinePaint.setStrokeWidth(centerlinestrokewidth);
        centerlinePaint.setAntiAlias(true);
        centerlinePaint.setStyle(Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(context.getResources().getColor(
                android.R.color.black));
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        strokewidth = CalculationUtil.convertDpToPx(widthInDp, getContext());
        int cW = canvas.getWidth();
        int cH = canvas.getHeight();
        float scope = (2 * cW) + (2 * cH) - (4 * strokewidth);
        float hSw = strokewidth / 2;

        if (isOutline()) {
            drawOutline();
        }

        if (isStartline()) {
            drawStartline();
        }

        if (isShowProgress()) {
            drawPercent(percentSettings);
        }

        if (isCenterline()) {
            drawCenterline(strokewidth);
        }

        if ((isClearOnHundred() && progress == 100.0) || (progress <= 0.0)) {
            return;
        }


        if (!isCcw()){
            Path path = new Path();
            DrawStop drawEnd = getDrawEnd((scope / 100) * Float.valueOf(String.valueOf(progress)), canvas);

            if (drawEnd.place == Place.TOP) {
                if (drawEnd.location > (cW / 2) && progress < 100.0) {
                    path.moveTo(cW / 2, hSw);
                    path.lineTo(drawEnd.location, hSw);
                } else {
                    path.moveTo(cW / 2, hSw);
                    path.lineTo(cW - hSw-roundedCornersRadius, hSw);
                    path.quadTo(cW-hSw, hSw, cW-hSw, hSw+roundedCornersRadius);
                    path.lineTo(cW - hSw, cH - hSw-roundedCornersRadius);
                    path.quadTo(cW-hSw, cH-hSw, cW-hSw-roundedCornersRadius, cH-hSw);
                    path.lineTo(hSw+roundedCornersRadius, cH - hSw);
                    path.quadTo(hSw, cH-hSw, hSw, cH-hSw-roundedCornersRadius);
                    path.lineTo(hSw, hSw+roundedCornersRadius);
                    path.quadTo(hSw, hSw, hSw+roundedCornersRadius, hSw);
                    //path.lineTo(strokewidth, hSw);
                    path.lineTo(drawEnd.location, hSw);
                }
                canvas.drawPath(path, progressBarPaint);
            }

            if (drawEnd.place == Place.RIGHT) {
                path.moveTo(cW / 2, hSw);
                path.lineTo(cW - hSw-roundedCornersRadius, hSw);
                path.quadTo(cW-hSw, hSw, cW-hSw, hSw+roundedCornersRadius);
                path.lineTo(cW - hSw, 0
                        + drawEnd.location);
                canvas.drawPath(path, progressBarPaint);
            }

            if (drawEnd.place == Place.BOTTOM) {
                path.moveTo(cW / 2, hSw);
                path.lineTo(cW - hSw-roundedCornersRadius, hSw);
                path.quadTo(cW-hSw, hSw, cW-hSw, hSw+roundedCornersRadius);
                path.lineTo(cW - hSw, cH - hSw-roundedCornersRadius);
                path.quadTo(cW-hSw, cH-hSw, cW-hSw-roundedCornersRadius, cH-hSw);
                //path.lineTo(cW - strokewidth, cH - hSw);
                path.lineTo(drawEnd.location, cH - hSw);
                canvas.drawPath(path, progressBarPaint);
            }

            if (drawEnd.place == Place.LEFT) {
                path.moveTo(cW / 2, hSw);
                path.lineTo(cW - hSw-roundedCornersRadius, hSw);
                path.quadTo(cW-hSw, hSw, cW-hSw, hSw+roundedCornersRadius);
                path.lineTo(cW - hSw, cH - hSw-roundedCornersRadius);
                path.quadTo(cW-hSw, cH-hSw, cW-hSw-roundedCornersRadius, cH-hSw);
                path.lineTo(hSw+roundedCornersRadius, cH - hSw);
                path.quadTo(hSw, cH-hSw, hSw, cH-hSw-roundedCornersRadius);
                //path.lineTo(hSw, cH - strokewidth);
                path.lineTo(hSw, drawEnd.location);
                canvas.drawPath(path, progressBarPaint);
            }

        } else {

            Path path = new Path();
            DrawStop drawEnd = getDrawEndCCW((scope / 100) * Float.valueOf(String.valueOf(progress)), canvas);

            if (drawEnd.place == Place.TOP) {
                if (drawEnd.location < (cW / 2) && progress < 100.0) {
                    path.moveTo(cW / 2, hSw);
                    path.lineTo(drawEnd.location, hSw);
                } else {
                    path.moveTo(cW / 2, hSw);
                    path.lineTo(hSw + roundedCornersRadius, hSw);
                    path.quadTo(hSw, hSw, hSw, hSw+roundedCornersRadius);
                    path.lineTo(hSw, cH - hSw-roundedCornersRadius);
                    path.quadTo(hSw, cH-hSw, hSw+roundedCornersRadius, cH-hSw);
                    path.lineTo(cW-hSw -roundedCornersRadius, cH - hSw);
                    path.quadTo(cW-hSw, cH-hSw, cW-hSw, cH-hSw-roundedCornersRadius);
                    path.lineTo(cW -hSw, hSw+roundedCornersRadius);
                    path.quadTo(cW-hSw, hSw, cW-hSw-roundedCornersRadius, hSw);
                    //path.lineTo(strokewidth, hSw);
                    path.lineTo(drawEnd.location, hSw);
                }
                canvas.drawPath(path, progressBarPaint);
            }

            if (drawEnd.place == Place.RIGHT) {


                path.moveTo(cW / 2, hSw);
                path.lineTo(hSw+roundedCornersRadius, hSw);
                path.quadTo(hSw, hSw, hSw, hSw+roundedCornersRadius);
                path.lineTo(hSw, cH - hSw-roundedCornersRadius);
                path.quadTo(hSw, cH-hSw, hSw+roundedCornersRadius, cH-hSw);
                path.lineTo(cW-hSw-roundedCornersRadius, cH - hSw);
                path.quadTo(cW-hSw, cH-hSw, cW-hSw, cH-hSw-roundedCornersRadius);
                //path.lineTo(hSw, cH - strokewidth);
                path.lineTo(cW-hSw, drawEnd.location);
                canvas.drawPath(path, progressBarPaint);

            }

            if (drawEnd.place == Place.BOTTOM) {
                path.moveTo(cW / 2, hSw);
                path.lineTo(hSw+roundedCornersRadius, hSw);
                path.quadTo(hSw, hSw, hSw, hSw+roundedCornersRadius);
                path.lineTo(hSw, cH - hSw-roundedCornersRadius);
                path.quadTo(hSw, cH-hSw, hSw+roundedCornersRadius, cH-hSw);
                //path.lineTo(cW - strokewidth, cH - hSw);
                path.lineTo(drawEnd.location, cH - hSw);
                canvas.drawPath(path, progressBarPaint);
            }

            if (drawEnd.place == Place.LEFT) {

                path.moveTo(cW / 2, hSw);
                path.lineTo(hSw+roundedCornersRadius, hSw);
                path.quadTo(hSw, hSw, hSw, hSw+roundedCornersRadius);
                path.lineTo(hSw, 0 + drawEnd.location);
                canvas.drawPath(path, progressBarPaint);
            }


        }

    }

    private void drawStartline() {
        Path outlinePath = new Path();
        outlinePath.moveTo(canvas.getWidth() / 2, 0);
        outlinePath.lineTo(canvas.getWidth() / 2, strokewidth);
        canvas.drawPath(outlinePath, outlinePaint);
    }
    private void drawCenterline(float strokewidth) {
        int cW = canvas.getWidth();
        int cH = canvas.getHeight();
        float centerOfStrokeWidth = strokewidth / 2;
        Path centerlinePath = new Path();

/*        RectF rectF = new RectF(centerOfStrokeWidth, centerOfStrokeWidth, cW-centerOfStrokeWidth, cH-centerOfStrokeWidth);
        centerlinePath.addRoundRect(rectF, roundedCornersRadius, roundedCornersRadius, Path.Direction.CW);*/
        centerlinePath.moveTo(cW/2, centerOfStrokeWidth);
        centerlinePath.lineTo(cW - centerOfStrokeWidth-roundedCornersRadius, centerOfStrokeWidth);
        centerlinePath.quadTo(cW-centerOfStrokeWidth,centerOfStrokeWidth, cW-centerOfStrokeWidth, centerOfStrokeWidth+roundedCornersRadius);
        centerlinePath.lineTo(cW - centerOfStrokeWidth, cH - centerOfStrokeWidth-roundedCornersRadius);
        centerlinePath.quadTo(cW-centerOfStrokeWidth, cH-centerOfStrokeWidth, cW-centerOfStrokeWidth-roundedCornersRadius, cH-centerOfStrokeWidth);
        centerlinePath.lineTo(centerOfStrokeWidth+roundedCornersRadius, cH - centerOfStrokeWidth);
        centerlinePath.quadTo(centerOfStrokeWidth, cH-centerOfStrokeWidth, centerOfStrokeWidth, cH-centerOfStrokeWidth-roundedCornersRadius);
        centerlinePath.lineTo(centerOfStrokeWidth, centerOfStrokeWidth+roundedCornersRadius);
        centerlinePath.quadTo(centerOfStrokeWidth, centerOfStrokeWidth, centerOfStrokeWidth+roundedCornersRadius, centerOfStrokeWidth);
        centerlinePath.lineTo(cW/2, centerOfStrokeWidth);
/*        if (roundedCorners) {
            centerlinePaint.setPathEffect(new CornerPathEffect(roundedCornersRadius));
        } else {
            centerlinePaint.setPathEffect(null);
        }*/
        canvas.drawPath(centerlinePath, centerlinePaint);



    }
    private void drawOutline() {
        int cW = canvas.getWidth();
        int cH = canvas.getHeight();
        Path outlinePath = new Path();
        float radius = roundedCornersRadius+15;
/*        RectF rectF = new RectF(1, 1, cW-1, cH-1);
        outlinePath.addRoundRect(rectF, radius, radius, Path.Direction.CW);*/
        outlinePath.moveTo(cW/2, 0);
        outlinePath.lineTo(cW-radius, 0);
        outlinePath.quadTo(cW, 0, cW, radius);
        outlinePath.lineTo(cW, cH-radius);
        outlinePath.quadTo(cW, cH, cW-radius, cH);
        outlinePath.lineTo(radius, cH);
        outlinePath.quadTo(0, cH, 0, cH-radius);
        outlinePath.lineTo(0, radius);
        outlinePath.quadTo(0, 0, radius, 0);
        outlinePath.lineTo(cW/2, 0);
/*        if (roundedCorners) {
            outlinePaint.setPathEffect(new CornerPathEffect(roundedCornersRadius));
        } else {
            outlinePaint.setPathEffect(null);
        }*/
        canvas.drawPath(outlinePath, outlinePaint);

    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
        this.invalidate();
    }

    public void setColor(int color) {
        progressBarPaint.setColor(color);
        this.invalidate();
    }
    public void setCenterlineStokeColor(int color) {
        centerlinePaint.setColor(color);
        this.invalidate();
    }
    public void setWidthInDp(int width) {
        this.widthInDp = width;
        progressBarPaint.setStrokeWidth(CalculationUtil.convertDpToPx(
                widthInDp, getContext()));
        this.invalidate();
    }

    public boolean isOutline() {
        return outline;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
        this.invalidate();
    }

    public boolean isStartline() {
        return startline;
    }

    public void setStartline(boolean startline) {
        this.startline = startline;
        this.invalidate();
    }

    private void drawPercent(PercentStyle setting) {
        textPaint.setTextAlign(setting.getAlign());
        if (setting.getTextSize() == 0) {
            textPaint.setTextSize((canvas.getHeight() / 10) * 4);
        } else {
            textPaint.setTextSize(setting.getTextSize());
        }

        String percentString = new DecimalFormat("###").format(getProgress());
        if (setting.isPercentSign()) {
            percentString = percentString + percentSettings.getCustomText();
        }

        textPaint.setColor(percentSettings.getTextColor());

        canvas.drawText(
                percentString,
                canvas.getWidth() / 2,
                (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint
                        .ascent()) / 2)), textPaint);
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        this.invalidate();
    }

    public void setPercentStyle(PercentStyle percentSettings) {
        this.percentSettings = percentSettings;
        this.invalidate();
    }

    public PercentStyle getPercentStyle() {
        return percentSettings;
    }

    public void setClearOnHundred(boolean clearOnHundred) {
        this.clearOnHundred = clearOnHundred;
        this.invalidate();
    }

    public boolean isClearOnHundred() {
        return clearOnHundred;
    }



    public boolean isCenterline() {
        return centerline;
    }

    public void setCenterline(boolean centerline) {
        this.centerline = centerline;
        this.invalidate();
    }

    public boolean isIndeterminate() {
        return isIndeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        isIndeterminate = indeterminate;
        this.invalidate();
    }

    public DrawStop getDrawEnd(float percent, Canvas canvas) {
        DrawStop drawStop = new DrawStop();
        strokewidth = CalculationUtil.convertDpToPx(widthInDp, getContext());
        float halfOfTheImage = canvas.getWidth() / 2;

        // top right
        if (percent > halfOfTheImage) {
            float second = percent - (halfOfTheImage);

            // right
            if (second > (canvas.getHeight() - strokewidth)) {
                float third = second - (canvas.getHeight() - strokewidth);

                // bottom
                if (third > (canvas.getWidth() - strokewidth)) {
                    float forth = third - (canvas.getWidth() - strokewidth);

                    // left
                    if (forth > (canvas.getHeight() - strokewidth)) {
                        float fifth = forth - (canvas.getHeight() - strokewidth);

                        // top left
                        if (fifth == halfOfTheImage) {
                            drawStop.place = Place.TOP;
                            drawStop.location = halfOfTheImage;
                        } else {
                            drawStop.place = Place.TOP;
                            drawStop.location = strokewidth + fifth;
                        }
                    } else {
                        drawStop.place = Place.LEFT;
                        drawStop.location = canvas.getHeight() - strokewidth - forth;
                    }

                } else {
                    drawStop.place = Place.BOTTOM;
                    drawStop.location = canvas.getWidth() - strokewidth - third;
                }
            } else {
                drawStop.place = Place.RIGHT;
                drawStop.location = strokewidth + second;
            }

        } else {
            drawStop.place = Place.TOP;
            drawStop.location = halfOfTheImage + percent;
        }

        return drawStop;
    }
    public DrawStop getDrawEndCCW(float percent, Canvas canvas) {
        DrawStop drawStop = new DrawStop();
        strokewidth = CalculationUtil.convertDpToPx(widthInDp, getContext());
        float halfOfTheImage = canvas.getWidth() / 2;

        // top left
        if (percent > halfOfTheImage) {
            float second = percent - (halfOfTheImage);

            // left
            if (second > (canvas.getHeight() - strokewidth)) {
                float third = second - (canvas.getHeight() - strokewidth);

                // bottom
                if (third > (canvas.getWidth() - strokewidth)) {
                    float forth = third - (canvas.getWidth() - strokewidth);

                    // right
                    if (forth > (canvas.getHeight() - strokewidth)) {
                        float fifth = forth - (canvas.getHeight() - strokewidth);

                        // top right
                        if (fifth == halfOfTheImage) {
                            drawStop.place = Place.TOP;
                            drawStop.location = halfOfTheImage;
                        } else {
                            drawStop.place = Place.TOP;
                            drawStop.location = canvas.getWidth() - strokewidth - fifth;
                        }
                    } else {
                        drawStop.place = Place.RIGHT;
                        drawStop.location = canvas.getHeight() - strokewidth - forth;
                    }

                } else {
                    drawStop.place = Place.BOTTOM;
                    drawStop.location = strokewidth + third;
                }
            } else {
                drawStop.place = Place.LEFT;
                drawStop.location = strokewidth + second;
            }

        } else {

            drawStop.place = Place.TOP;
            //maybe strokewith calculation
            drawStop.location = halfOfTheImage - percent;
        }

        return drawStop;
    }
    public void setRoundedCorners(boolean roundedCorners, float radius) {
        this.roundedCorners = roundedCorners;
        this.roundedCornersRadius = radius;
        if(roundedCorners){
            this.roundedCornersRadius = radius;
        } else {
            this.roundedCornersRadius = 0;
        }
/*        if (roundedCorners) {
            progressBarPaint.setPathEffect(new CornerPathEffect(roundedCornersRadius));
        } else {
            progressBarPaint.setPathEffect(null);
        }*/
        this.invalidate();
    }



    public boolean isRoundedCorners() {
        return roundedCorners;
    }

    private class DrawStop {

        private Place place;
        private float location;

        public DrawStop() {

        }
    }

    public enum Place {
        TOP, RIGHT, BOTTOM, LEFT
    }
}
