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

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.app.Context;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SimpleMonthAdapter extends BaseItemProvider implements SimpleMonthView.OnDayClickListener {

    private static final int MONTHS_IN_YEAR = 12;
    private final AttrUtil attrUtil;
    private final Context mContext;
    private final DatePickerController mController;
    private final Calendar calendar;
    private final SelectedDays<CalendarDay> selectedDays;
    private Integer firstMonth;
    private Integer lastMonth;

    public SimpleMonthAdapter(Context context, DatePickerController datePickerController, AttrUtil attrUtil) {
        this.attrUtil = attrUtil;
        calendar = Calendar.getInstance();

        String firstMonthStr = attrUtil.getStringValue(DayPickerViewAttrs.FIRST_MONTH, "-1");
        String lastMonthStr = attrUtil.getStringValue(DayPickerViewAttrs.LAST_MONTH, "-1");

        try {
            firstMonth = Integer.parseInt(firstMonthStr);
        } catch (Exception e) {
            firstMonth = monthStrToInt(firstMonthStr);
        }
        try {
            lastMonth = Integer.parseInt(lastMonthStr);
        } catch (Exception e) {
            lastMonth = monthStrToInt(lastMonthStr);
        }
        firstMonth = firstMonth < 0 || firstMonth > 11 ? calendar.get(Calendar.MONTH) : firstMonth;
        lastMonth = lastMonth < 0 || lastMonth > 11 ? (calendar.get(Calendar.MONTH) - 1) % MONTHS_IN_YEAR : lastMonth;

        selectedDays = new SelectedDays<>();
        mContext = context;
        mController = datePickerController;
        init();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer parent) {
        ViewHolder viewHolder;
        if (false) {
            viewHolder = (ViewHolder) convertComponent.getTag();
        } else {
            convertComponent = new SimpleMonthView(mContext, attrUtil);
            convertComponent.setPadding(10, 10, 10, 10);
            viewHolder = new ViewHolder(convertComponent, this);
            convertComponent.setTag(viewHolder);
        }
        onBindViewHolder(viewHolder, position);
        return convertComponent;
    }

    private void onBindViewHolder(ViewHolder viewHolder, int position) {
        final SimpleMonthView simpleMonthView = viewHolder.simpleMonthView;
        final HashMap<String, Integer> drawingParams = new HashMap<String, Integer>();
        int month;
        int year;

        month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
        year = position / MONTHS_IN_YEAR + calendar.get(Calendar.YEAR) + ((firstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);

        int selectedFirstDay = -1;
        int selectedLastDay = -1;
        int selectedFirstMonth = -1;
        int selectedLastMonth = -1;
        int selectedFirstYear = -1;
        int selectedLastYear = -1;

        if (selectedDays.getFirst() != null) {
            selectedFirstDay = selectedDays.getFirst().day;
            selectedFirstMonth = selectedDays.getFirst().month;
            selectedFirstYear = selectedDays.getFirst().year;
        }

        if (selectedDays.getLast() != null) {
            selectedLastDay = selectedDays.getLast().day;
            selectedLastMonth = selectedDays.getLast().month;
            selectedLastYear = selectedDays.getLast().year;
        }

        simpleMonthView.reuse();

        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, selectedFirstYear);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, selectedLastYear);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, selectedFirstMonth);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, selectedLastMonth);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, selectedFirstDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_LAST_DAY, selectedLastDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        simpleMonthView.setMonthParams(drawingParams);
        simpleMonthView.invalidate();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        int itemCount = (((mController.getMaxYear() - calendar.get(Calendar.YEAR)) + 1) * MONTHS_IN_YEAR);

        if (firstMonth != -1) {
            itemCount -= firstMonth;
        }

        if (lastMonth != -1) {
            itemCount -= (MONTHS_IN_YEAR - lastMonth) - 1;
        }

        return itemCount;
    }

    private class ViewHolder {

        final SimpleMonthView simpleMonthView;

        public ViewHolder(Component itemView, SimpleMonthView.OnDayClickListener onDayClickListener) {
            simpleMonthView = (SimpleMonthView) itemView;
            simpleMonthView.setLayoutConfig(new ComponentContainer.LayoutConfig(ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT));
            simpleMonthView.setClickable(true);
            simpleMonthView.setOnDayClickListener(onDayClickListener);
        }
    }

    private void init() {
        if (attrUtil.getBooleanValue(DayPickerViewAttrs.CURRENT_DAY_SELECTED, false)) {
            onDayTapped(new CalendarDay(System.currentTimeMillis()));
        }
    }

    @Override
    public void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    private void onDayTapped(CalendarDay calendarDay) {
        mController.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
        setSelectedDay(calendarDay);
    }

    private void setSelectedDay(CalendarDay calendarDay) {
        if (selectedDays.getFirst() != null && selectedDays.getLast() == null) {
            selectedDays.setLast(calendarDay);

            if (selectedDays.getFirst().month < calendarDay.month) {
                for (int i = 0; i < selectedDays.getFirst().month - calendarDay.month - 1; ++i) {
                    mController.onDayOfMonthSelected(selectedDays.getFirst().year, selectedDays.getFirst().month + i, selectedDays.getFirst().day);
                }
            }

            mController.onDateRangeSelected(selectedDays);
        } else if (selectedDays.getLast() != null) {
            selectedDays.setFirst(calendarDay);
            selectedDays.setLast(null);
        } else {
            selectedDays.setFirst(calendarDay);
        }

        notifyDataChanged();
    }

    public static class CalendarDay implements Serializable {
        private static final long serialVersionUID = -5456695978688356202L;
        private Calendar calendar;

        int day;
        int month;
        int year;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = this.calendar.get(Calendar.MONTH);
            year = this.calendar.get(Calendar.YEAR);
            day = this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        public void set(CalendarDay calendarDay) {
            year = calendarDay.year;
            month = calendarDay.month;
            day = calendarDay.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public Date getDate() {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.set(year, month, day);
            return calendar.getTime();
        }

        @Override
        public String toString() {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ year: ");
            stringBuilder.append(year);
            stringBuilder.append(", month: ");
            stringBuilder.append(month);
            stringBuilder.append(", day: ");
            stringBuilder.append(day);
            stringBuilder.append(" }");

            return stringBuilder.toString();
        }
    }

    public SelectedDays<CalendarDay> getSelectedDays() {
        return selectedDays;
    }


    public static class SelectedDays<K> implements Serializable {
        private static final long serialVersionUID = 3942549765282708376L;
        private K first;
        private K last;

        public K getFirst() {
            return first;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        public K getLast() {
            return last;
        }

        public void setLast(K last) {
            this.last = last;
        }
    }

    private Integer monthStrToInt(String month) {
        switch (month) {
            case "january":
                return 0;
            case "february":
                return 1;
            case "march":
                return 2;
            case "april":
                return 3;
            case "may":
                return 4;
            case "june":
                return 5;
            case "july":
                return 6;
            case "august":
                return 7;
            case "september":
                return 8;
            case "october":
                return 9;
            case "november":
                return 10;
            case "december":
                return 11;
            default:
                return -1;
        }
    }

}