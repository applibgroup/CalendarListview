## CalendarListview
    CalendarListview provides an easy way to select dates from a calendar

## Screenshot
<img src="https://gitee.com/HarmonyOS-tpc/CalendarListview/raw/master/CalendarListview.gif" width="40%"/>

## Dependencies
```
allprojects{
    repositories{
        mavenCentral()
    }
}
implementation 'io.openharmony.tpc.thirdlib:CalendarListview:1.0.2'
```

## Usage
1. Declare a DayPickerView in the layout xml file
	```html	
		<com.andexert.calendarlistview.library.DayPickerView
            xmlns:ohos="http://schemas.huawei.com/res/ohos"
            ohos:id="$+id:pickerView"
            ohos:height="match_parent"
            ohos:width="match_parent"/>
	```
2. Set interface callback
    ```java
		DayPickerView dayPickerView = (DayPickerView) findComponentById(ResourceTable.Id_pickerView);
        dayPickerView.setController(new DatePickerController() {
            @Override
            public int getMaxYear() {
                // The calendar will display up to the year returned by this interface
                return 2023;
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                // This interface is called every time the user selects a new date
                }

            @Override
            public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
                // Whenever the user selects two dates, this interface will be called, and the two selected dates can be obtained
                }
        });
    ```

## xml custom attribute
| name                       |  format   | description           | default_value     |
| :-------------------------:| :------:  | :-------------------: | :---------------: |
| colorCurrentDay            | Color     | Text color for the current date       | 0xff999999        |
| colorSelectedDayBackground | Color     | The background color of the indicator when the date is clicked  | 0xffE75F49        |
| colorSelectedDayText       | Color     | Text color for clicked date       | 0xfff2f2f2        |
| colorPreviousDay           | Color     | Text color for past days in the current month | 0xff999999        |
| colorNormalDay             | Color     | Text color for all undated dates    | 0xff999999        |
| colorMonthName             | Color     | Text color for the year-month title of each month | 0xff999999        |
| colorDayName               | Color     | Text color for the week of the month    | 0xff999999        |
| textSizeDay                | dimension | Date text size            | 16vp              |
| textSizeMonth              | dimension | Text size of the year-month title for each month | 16vp              |
| textSizeDayName            | dimension | Text size for the week of the month    | 16vp              |
| headerMonthHeight          | dimension | The height of the year-month header for each month    | 50vp              |
| selectedDayRadius          | dimension | Click the radius size of the date indicator   | 16vp              |
| calendarHeight             | dimension | Subtract the height of the year-month header for each month   | 270vp             |
| enablePreviousDay          | boolean   | Enable past days for the current month     | true              |
| currentDaySelected         | boolean   | The current date is selected by default     | false             |
| drawRoundRect              | boolean   | Whether the shape of the indicator is a rectangle     | false             |
| firstMonth                 | Integer   | Month to start showing           | Month of the current date       |
| lastMonth                  | Integer   | Last displayed month           | The month before the first Month |

Note: If you want to use the `colorPreviousDay` property, you need to set `enablePreviousDay` to false first

## Example entry running requirements
Through DevEco studio, and download SDK
Change the dependencies→classpath version in the build.gradle file in the project to the corresponding version (that is, the version used in the new project of your IDE)

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
