## CalendarListview
    CalendarListview提供了一种日历选择日期的简便方法

## 截图
<img src="https://gitee.com/HarmonyOS-tpc/CalendarListview/raw/master/CalendarListview.gif" width="40%"/>

## 依赖
```
allprojects{
    repositories{
        mavenCentral()
    }
}
implementation 'io.openharmony.tpc.thirdlib:CalendarListview:1.0.2'
```

## 用法
1. 在布局xml文件中声明一个DayPickerView
	```html	
		<com.andexert.calendarlistview.library.DayPickerView
            xmlns:ohos="http://schemas.huawei.com/res/ohos"
            ohos:id="$+id:pickerView"
            ohos:height="match_parent"
            ohos:width="match_parent"/>
	```
2. 设置接口回调
    ```java
		DayPickerView dayPickerView = (DayPickerView) findComponentById(ResourceTable.Id_pickerView);
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
    ```

## xml自定义属性
| name                       |  format   | description           | default_value     |
| :-------------------------:| :------:  | :-------------------: | :---------------: |
| colorCurrentDay            | Color     | 当前日期的文字颜色       | 0xff999999        |
| colorSelectedDayBackground | Color     | 点击日期时指示器的背景色  | 0xffE75F49        |
| colorSelectedDayText       | Color     | 点击日期的文字颜色       | 0xfff2f2f2        |
| colorPreviousDay           | Color     | 当前月份过去日期的文字颜色 | 0xff999999        |
| colorNormalDay             | Color     | 所有未过日期的文字颜色    | 0xff999999        |
| colorMonthName             | Color     | 每个月的年月标题的文字颜色 | 0xff999999        |
| colorDayName               | Color     | 每个月的星期的文字颜色    | 0xff999999        |
| textSizeDay                | dimension | 日期文字大小            | 16vp              |
| textSizeMonth              | dimension | 每个月的年月标题的文字大小 | 16vp              |
| textSizeDayName            | dimension | 每个月的星期的文字大小    | 16vp              |
| headerMonthHeight          | dimension | 每个月的年月标题的高度    | 50vp              |
| selectedDayRadius          | dimension | 点击日期指示器的半径尺寸   | 16vp              |
| calendarHeight             | dimension | 每个月减去年月标题的高度   | 270vp             |
| enablePreviousDay          | boolean   | 启用当前月份的过去日期     | true              |
| currentDaySelected         | boolean   | 默认情况下选择当前日期     | false             |
| drawRoundRect              | boolean   | 指示器的形状是否为矩形     | false             |
| firstMonth                 | Integer   | 开始显示的月份           | 当前日期的月份       |
| lastMonth                  | Integer   | 最后显示的月份           | firstMonth的前一个月 |

注意：如果你想使用`colorPreviousDay`属性，需要先将`enablePreviousDay`设置为false

## 示例entry运行要求
通过DevEco studio,并下载SDK
将项目中的build.gradle文件中dependencies→classpath版本改为对应的版本（即你的IDE新建项目中所用的版本）

## MIT License
```
    The MIT License (MIT)

    Copyright (c) 2014 Robin Chutaux

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
```
