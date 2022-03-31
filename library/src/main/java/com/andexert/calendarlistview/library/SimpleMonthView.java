/***********************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2014 Robin Chutaux
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.andexert.calendarlistview.library;

import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.agp.utils.RectFloat;
import ohos.agp.utils.TextAlignment;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

class SimpleMonthView extends Component implements Component.DrawTask, Component.EstimateSizeListener, Component.TouchEventListener {

    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_YEAR = "year";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_DAY = "selected_begin_day";
    public static final String VIEW_PARAMS_SELECTED_LAST_DAY = "selected_last_day";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_MONTH = "selected_begin_month";
    public static final String VIEW_PARAMS_SELECTED_LAST_MONTH = "selected_last_month";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_YEAR = "selected_begin_year";
    public static final String VIEW_PARAMS_SELECTED_LAST_YEAR = "selected_last_year";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";

    private static final float SELECTED_CIRCLE_ALPHA = 0.5f;
    private static int DEFAULT_HEIGHT = 32;
    private static final int DEFAULT_NUM_ROWS = 6;
    private static int DAY_SELECTED_RADIUS;
    private static int DAY_SEPARATOR_WIDTH = 1;
    private static int MINI_DAY_NUMBER_TEXT_SIZE;
    private static int MIN_HEIGHT = 10;
    private static int MONTH_WEEK_TEXT_SIZE;
    private static int MONTH_HEADER_HEIGHT;
    private static int MONTH_TITLE_TEXT_SIZE;
    private int mPadding = 0;
    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;
    private Paint mMonthDayLabelPaint;
    private Paint mMonthNumPaint;
    private Paint mMonthTitlePaint;
    private Paint mSelectedCirclePaint;
    private int mCurrentDayTextColor;
    private int mMonthTextColor;
    private int mWeekColor;
    private int mDayNumColor;
    private int mSelectedDayTextColor;
    private int mPreviousDayColor;
    private int mSelectedDayBgColor;
    private final StringBuilder mStringBuilder;
    private boolean mHasToday = false;
    private boolean mIsPrev = false;
    private int mSelectedBeginDay = -1;
    private int mSelectedLastDay = -1;
    private int mSelectedBeginMonth = -1;
    private int mSelectedLastMonth = -1;
    private int mSelectedBeginYear = -1;
    private int mSelectedLastYear = -1;
    private int mToday = -1;
    private int mWeekStart = 1;
    private int mNumDays = 7;
    private int mNumCells = mNumDays;
    private int mDayOfWeekStart = 0;
    private int mMonth;
    private Boolean mDrawRect;
    private int mRowHeight = DEFAULT_HEIGHT;
    private int mWidth;
    private int mYear;
    private final GregorianCalendar gc;
    private final Calendar mCalendar;
    private final Calendar mDayLabelCalendar;
    private final Boolean isPrevDayEnabled;
    private int mNumRows = DEFAULT_NUM_ROWS;
    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();
    private OnDayClickListener mOnDayClickListener;
    private float downX;
    private float downY;

    public SimpleMonthView(Context context, AttrUtil attrUtil) {
        super(context);

        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        gc = new GregorianCalendar();

        mDayOfWeekTypeface = "sans-serif";
        mMonthTitleTypeface = "sans-serif";
        mCurrentDayTextColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_CURRENT_DAY, 0xff999999);
        mMonthTextColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_MONTH_NAME, 0xff999999);
        mWeekColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_DAY_NAME, 0xff999999);
        mDayNumColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_NORMAL_DAY, 0xff999999);
        mPreviousDayColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_PREVIOUS_DAY, 0xff999999);
        mSelectedDayBgColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_SELECTED_DAY_BACKGROUND, 0xffE75F49);
        mSelectedDayTextColor = attrUtil.getColorValue(DayPickerViewAttrs.COLOR_SELECTED_DAY_TEXT, 0xfff2f2f2);
        mDrawRect = attrUtil.getBooleanValue(DayPickerViewAttrs.DRAW_ROUND_RECT, false);
        MINI_DAY_NUMBER_TEXT_SIZE = attrUtil.getDimensionValue(DayPickerViewAttrs.TEXT_SIZE_DAY, vpToPx(getContext(), 16));
        MONTH_TITLE_TEXT_SIZE = attrUtil.getDimensionValue(DayPickerViewAttrs.TEXT_SIZE_MONTH, vpToPx(getContext(), 16));
        MONTH_WEEK_TEXT_SIZE = attrUtil.getDimensionValue(DayPickerViewAttrs.TEXT_SIZE_DAY_NAME, vpToPx(getContext(), 16));
        MONTH_HEADER_HEIGHT = attrUtil.getDimensionValue(DayPickerViewAttrs.HEADER_MONTH_HEIGHT, vpToPx(getContext(), 50));
        DAY_SELECTED_RADIUS = attrUtil.getDimensionValue(DayPickerViewAttrs.SELECTED_DAY_RADIUS, vpToPx(getContext(), 16));
        mRowHeight = (attrUtil.getDimensionValue(DayPickerViewAttrs.CALENDAR_HEIGHT, vpToPx(getContext(), 270) - MONTH_HEADER_HEIGHT) / 6);
        isPrevDayEnabled = attrUtil.getBooleanValue(DayPickerViewAttrs.ENABLE_PREVIOUS_DAY, true);

        mStringBuilder = new StringBuilder(50);
        initView();

    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_HEIGHT - (MONTH_WEEK_TEXT_SIZE / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(mMonthDayLabelPaint, mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault()), x, y);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (MONTH_HEADER_HEIGHT - MONTH_WEEK_TEXT_SIZE) / 2 + (MONTH_TITLE_TEXT_SIZE / 3);
        StringBuilder stringBuilder = new StringBuilder(getMonthAndYearString().toLowerCase());
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        canvas.drawText(mMonthTitlePaint, stringBuilder.toString(), x, y);
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart) - mWeekStart;
    }

    private String getMonthAndYearString() {
        mStringBuilder.setLength(0);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        // 获取当前语言环境
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.equals("中文")) {
            return year + "年" + month + "月";
        } else {
            switch (month) {
                case 1:
                    return "January" + " " + year;
                case 2:
                    return "February" + " " + year;
                case 3:
                    return "March" + " " + year;
                case 4:
                    return "April" + " " + year;
                case 5:
                    return "May" + " " + year;
                case 6:
                    return "June" + " " + year;
                case 7:
                    return "July" + " " + year;
                case 8:
                    return "August" + " " + year;
                case 9:
                    return "September" + " " + year;
                case 10:
                    return "October" + " " + year;
                case 11:
                    return "November" + " " + year;
                case 12:
                    return "December" + " " + year;
                default:
                    return "";
            }
        }
    }

    private void onDayClick(SimpleMonthAdapter.CalendarDay calendarDay) {
        if (mOnDayClickListener != null && (isPrevDayEnabled || !((calendarDay.month == gc.get(Calendar.MONTH)) && (calendarDay.year == gc.get(Calendar.YEAR)) && calendarDay.day < gc.get(Calendar.DAY_OF_MONTH)))) {
            mOnDayClickListener.onDayClick(this, calendarDay);
        }
    }

    private boolean sameDay(int monthDay, GregorianCalendar gc) {
        return (mYear == gc.get(Calendar.YEAR)) && (mMonth == gc.get(Calendar.MONTH)) && (monthDay == gc.get(Calendar.DAY_OF_MONTH));
    }

    private boolean prevDay(int monthDay, GregorianCalendar gc) {
        return ((mYear < gc.get(Calendar.YEAR))) || (mYear == gc.get(Calendar.YEAR) && mMonth < gc.get(Calendar.MONTH)) || (mMonth == gc.get(Calendar.MONTH) && monthDay < gc.get(Calendar.DAY_OF_MONTH));
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2 - DAY_SEPARATOR_WIDTH + MONTH_HEADER_HEIGHT;
        int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 1;

        while (day <= mNumCells) {
            int x = paddingDay * (1 + dayOffset * 2) + mPadding;
            if ((mMonth == mSelectedBeginMonth && mSelectedBeginDay == day && mSelectedBeginYear == mYear) || (mMonth == mSelectedLastMonth && mSelectedLastDay == day && mSelectedLastYear == mYear)) {
                if (mDrawRect) {
                    RectFloat rectF = new RectFloat(x - DAY_SELECTED_RADIUS, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) - DAY_SELECTED_RADIUS, x + DAY_SELECTED_RADIUS, (y - MINI_DAY_NUMBER_TEXT_SIZE / 3) + DAY_SELECTED_RADIUS);
                    canvas.drawRoundRect(rectF, 10.0f, 10.0f, mSelectedCirclePaint);
                } else {
                    canvas.drawCircle(x, y - MINI_DAY_NUMBER_TEXT_SIZE / 3, DAY_SELECTED_RADIUS, mSelectedCirclePaint);
                }
            }
            if (mHasToday && (mToday == day)) {
                mMonthNumPaint.setColor(new Color(mCurrentDayTextColor));
                mMonthNumPaint.setFont(Font.DEFAULT_BOLD);
            } else {
                mMonthNumPaint.setColor(new Color(mDayNumColor));
                mMonthNumPaint.setFont(Font.DEFAULT);
            }

            if ((mMonth == mSelectedBeginMonth && mSelectedBeginDay == day && mSelectedBeginYear == mYear) ||
                    (mMonth == mSelectedLastMonth && mSelectedLastDay == day && mSelectedLastYear == mYear)) {
                mMonthNumPaint.setColor(new Color(mSelectedDayTextColor));
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear == mSelectedLastYear &&
                    mSelectedBeginMonth == mSelectedLastMonth &&
                    mSelectedBeginDay == mSelectedLastDay &&
                    day == mSelectedBeginDay &&
                    mMonth == mSelectedBeginMonth &&
                    mYear == mSelectedBeginYear)) {
                mMonthNumPaint.setColor(new Color(mSelectedDayBgColor));
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear == mSelectedLastYear && mSelectedBeginYear == mYear) &&
                    (((mMonth == mSelectedBeginMonth && mSelectedLastMonth == mSelectedBeginMonth) && ((mSelectedBeginDay < mSelectedLastDay && day > mSelectedBeginDay && day < mSelectedLastDay) || (mSelectedBeginDay > mSelectedLastDay && day < mSelectedBeginDay && day > mSelectedLastDay))) ||
                            ((mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedBeginMonth && day > mSelectedBeginDay) || (mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedLastMonth && day < mSelectedLastDay)) ||
                            ((mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedBeginMonth && day < mSelectedBeginDay) || (mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedLastMonth && day > mSelectedLastDay)))) {
                mMonthNumPaint.setColor(new Color(mSelectedDayBgColor));
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear != mSelectedLastYear && ((mSelectedBeginYear == mYear && mMonth == mSelectedBeginMonth) || (mSelectedLastYear == mYear && mMonth == mSelectedLastMonth)) &&
                    (((mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedBeginMonth && day < mSelectedBeginDay) || (mSelectedBeginMonth < mSelectedLastMonth && mMonth == mSelectedLastMonth && day > mSelectedLastDay)) ||
                            ((mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedBeginMonth && day > mSelectedBeginDay) || (mSelectedBeginMonth > mSelectedLastMonth && mMonth == mSelectedLastMonth && day < mSelectedLastDay))))) {
                mMonthNumPaint.setColor(new Color(mSelectedDayBgColor));
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear == mSelectedLastYear && mYear == mSelectedBeginYear) &&
                    ((mMonth > mSelectedBeginMonth && mMonth < mSelectedLastMonth && mSelectedBeginMonth < mSelectedLastMonth) ||
                            (mMonth < mSelectedBeginMonth && mMonth > mSelectedLastMonth && mSelectedBeginMonth > mSelectedLastMonth))) {
                mMonthNumPaint.setColor(new Color(mSelectedDayBgColor));
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear != mSelectedLastYear) &&
                    ((mSelectedBeginYear < mSelectedLastYear && ((mMonth > mSelectedBeginMonth && mYear == mSelectedBeginYear) || (mMonth < mSelectedLastMonth && mYear == mSelectedLastYear))) ||
                            (mSelectedBeginYear > mSelectedLastYear && ((mMonth < mSelectedBeginMonth && mYear == mSelectedBeginYear) || (mMonth > mSelectedLastMonth && mYear == mSelectedLastYear))))) {
                mMonthNumPaint.setColor(new Color(mSelectedDayBgColor));
            }

            if ((mSelectedBeginDay != -1 && mSelectedLastDay != -1 && mSelectedBeginYear != mSelectedLastYear) &&
                    (mSelectedBeginYear < mSelectedLastYear && mYear > mSelectedBeginYear && mYear < mSelectedLastYear)) {
                mMonthNumPaint.setColor(new Color(mSelectedDayBgColor));
            }

            if (!isPrevDayEnabled && prevDay(day, gc) && gc.get(Calendar.MONTH) == mMonth && gc.get(Calendar.YEAR) == mYear) {
                mMonthNumPaint.setColor(new Color(mPreviousDayColor));
                mMonthNumPaint.setFont(new Font.Builder("default").makeItalic(true).build());
            }

            canvas.drawText(mMonthNumPaint, String.format("%d", day), x, y);

            dayOffset++;
            if (dayOffset == mNumDays) {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

    public SimpleMonthAdapter.CalendarDay getDayFromLocation(float x, float y) {
        int padding = mPadding;
        if ((x < padding) || (x > mWidth - mPadding)) {
            return null;
        }

        int yDay = (int) (y - MONTH_HEADER_HEIGHT) / mRowHeight;
        int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

        if (mMonth > 11 || mMonth < 0 || CalendarUtils.getDaysInMonth(mMonth, mYear) < day || day < 1) {
            return null;
        }

        return new SimpleMonthAdapter.CalendarDay(mYear, mMonth, day);
    }

    protected void initView() {
        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MONTH_TITLE_TEXT_SIZE);

        mMonthTitlePaint.setFont(new Font.Builder(mMonthTitleTypeface).setWeight(Font.BOLD).build());
        mMonthTitlePaint.setColor(new Color(mMonthTextColor));
        mMonthTitlePaint.setTextAlign(TextAlignment.CENTER);
        mMonthTitlePaint.setStyle(Paint.Style.FILL_STYLE);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(new Color(mSelectedDayBgColor));
        mSelectedCirclePaint.setTextAlign(TextAlignment.CENTER);
        mSelectedCirclePaint.setStyle(Paint.Style.FILL_STYLE);
        mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(MONTH_WEEK_TEXT_SIZE);
        mMonthDayLabelPaint.setColor(new Color(mWeekColor));
        mMonthDayLabelPaint.setFont(new Font.Builder(mDayOfWeekTypeface).setWeight(Font.REGULAR).build());
        mMonthDayLabelPaint.setStyle(Paint.Style.FILL_STYLE);
        mMonthDayLabelPaint.setTextAlign(TextAlignment.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthNumPaint.setStyle(Paint.Style.FILL_STYLE);
        mMonthNumPaint.setTextAlign(TextAlignment.CENTER);
        mMonthNumPaint.setFakeBoldText(false);

        addDrawTask(this);
        setEstimateSizeListener(this);
        setTouchEventListener(this);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
    }

    @Override
    public boolean onEstimateSize(int widthEstimateConfig, int heightEstimateConfig) {
        mWidth = EstimateSpec.getSize(widthEstimateConfig);
        setEstimatedSize(widthEstimateConfig, EstimateSpec.getSizeWithMode(mRowHeight * mNumRows + MONTH_HEADER_HEIGHT, EstimateSpec.PRECISE));
        return true;
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent event) {
        if (event.getAction() == TouchEvent.PRIMARY_POINT_DOWN) {
            downX = getRawX(event);
            downY = getRawY(event);
        } else if (event.getAction() == TouchEvent.PRIMARY_POINT_UP) {
            float dx = getRawX(event) - downX;
            float dy = getRawY(event) - downY;
            float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (distance < 24) { // 移动距离小于24px，说明是点击
                SimpleMonthAdapter.CalendarDay calendarDay = getDayFromLocation(getXInComponent(this, event), getYInComponent(this, event));
                if (calendarDay != null) {
                    onDayClick(calendarDay);
                }
            }
        }
        return true;
    }

    public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
        postLayout();
    }

    public void setMonthParams(HashMap<String, Integer> params) {
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
        setTag(params);

        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
            mRowHeight = params.get(VIEW_PARAMS_HEIGHT);
            if (mRowHeight < MIN_HEIGHT) {
                mRowHeight = MIN_HEIGHT;
            }
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_DAY)) {
            mSelectedBeginDay = params.get(VIEW_PARAMS_SELECTED_BEGIN_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_DAY)) {
            mSelectedLastDay = params.get(VIEW_PARAMS_SELECTED_LAST_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_MONTH)) {
            mSelectedBeginMonth = params.get(VIEW_PARAMS_SELECTED_BEGIN_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_MONTH)) {
            mSelectedLastMonth = params.get(VIEW_PARAMS_SELECTED_LAST_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_YEAR)) {
            mSelectedBeginYear = params.get(VIEW_PARAMS_SELECTED_BEGIN_YEAR);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_YEAR)) {
            mSelectedLastYear = params.get(VIEW_PARAMS_SELECTED_LAST_YEAR);
        }

        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);

        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        mNumCells = CalendarUtils.getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, gc)) {
                mHasToday = true;
                mToday = day;
            }

            mIsPrev = prevDay(day, gc);
        }

        mNumRows = calculateNumRows();
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, SimpleMonthAdapter.CalendarDay calendarDay);
    }

    private int vpToPx(Context context, float vp) {
        return (int) (context.getResourceManager().getDeviceCapability().screenDensity / 160 * vp);
    }

    private float getXInComponent(Component component, TouchEvent touchEvent) {
        int[] locationOnScreen = component.getLocationOnScreen();
        return getRawX(touchEvent) - locationOnScreen[0];
    }

    private float getYInComponent(Component component, TouchEvent touchEvent) {
        int[] locationOnScreen = component.getLocationOnScreen();
        return getRawY(touchEvent) - locationOnScreen[1];
    }

    private float getRawX(TouchEvent touchEvent) {
        return touchEvent.getPointerScreenPosition(0).getX();
    }

    private float getRawY(TouchEvent touchEvent) {
        return touchEvent.getPointerScreenPosition(0).getY();
    }

}