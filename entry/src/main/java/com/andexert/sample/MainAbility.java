/*
 * The MIT License (MIT)
 * Copyright (c) 2021 Huawei Device Co., Ltd.
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
 */

package com.andexert.sample;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.ResourceTable;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * 日历演示页面
 */
public class MainAbility extends Ability {
    private DayPickerView dayPickerView;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_main);
        dayPickerView = (DayPickerView) findComponentById(ResourceTable.Id_pickerView);
        dayPickerView.setController(new DatePickerController() {
            @Override
            public int getMaxYear() {
                // 日历将最大显示到此接口返回的年份
                return 2023;
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                // 每当用户选择一个新日期时，就会调用此接口
            }

            @Override
            public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
                // 每当用户选择了两个日期，就会调用此接口，可以获取到这两个选中的日期
            }
        });
    }

}
