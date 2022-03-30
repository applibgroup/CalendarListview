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

import ohos.agp.components.AttrSet;
import ohos.agp.components.DirectionalLayoutManager;
import ohos.agp.components.ListContainer;
import ohos.app.Context;

public class DayPickerView extends ListContainer {

    private Context mContext;
    private SimpleMonthAdapter mAdapter;
    private DatePickerController mController;
    private AttrUtil attrUtil;

    public DayPickerView(Context context) {
        this(context, null);
    }

    public DayPickerView(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    public DayPickerView(Context context, AttrSet attrSet, String defStyle) {
        super(context, attrSet, defStyle);
        mContext = context;
        attrUtil = new AttrUtil(attrSet);
        setLayoutConfig(new LayoutConfig(LayoutConfig.MATCH_PARENT, LayoutConfig.MATCH_PARENT));
        init();
    }

    public void setController(DatePickerController mController) {
        this.mController = mController;
        setUpAdapter();
        setItemProvider(mAdapter);
    }

    private void init() {
        setLayoutManager(new DirectionalLayoutManager());
        setUpListView();
    }

    private void setUpAdapter() {
        if (mAdapter == null) {
            mAdapter = new SimpleMonthAdapter(getContext(), mController, attrUtil);
        }
        mAdapter.notifyDataChanged();
    }

    private void setUpListView() {
        enableScrollBar(AXIS_Y, false);
        setFadeEffectBoundaryWidth(0);
    }

    public SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> getSelectedDays() {
        return mAdapter.getSelectedDays();
    }

    private DatePickerController getController() {
        return mController;
    }

    private AttrUtil getTypedArray() {
        return attrUtil;
    }

}