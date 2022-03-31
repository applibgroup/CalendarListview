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

package com.andexert.calendarlistview.library;

import ohos.agp.components.AttrSet;
import ohos.agp.components.element.Element;
import ohos.agp.utils.Color;

/**
 * 自定义属性工具类.
 */

public class AttrUtil {

    AttrSet attrSet;

    public AttrUtil(AttrSet attrSet) {
        this.attrSet = attrSet;
    }

    /**
     * 获取自定义String.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */

    public String getStringValue(String key, String defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getStringValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Interger.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public int getIntegerValue(String key, int defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getIntegerValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Boolean.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public boolean getBooleanValue(String key, boolean defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getBoolValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Float.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public float getFloatValue(String key, float defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getFloatValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Long.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public long getLongValue(String key, long defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getLongValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Element.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public Element getElement(String key, Element defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getElement();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Dimension.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public int getDimensionValue(String key, int defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getDimensionValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义Color,默认值为Color类型.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public Color getColorValue(String key, Color defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getColorValue();
        } else {
            return defValue;
        }
    }

    /**
     * 获取自定义，默认值为int类型.
     *
     * @param key      键值
     * @param defValue 默认值
     * @return 取到的自定义属性，如没有，则返回默认值
     */
    public int getColorValue(String key, int defValue) {
        if (attrSet.getAttr(key).isPresent()) {
            return attrSet.getAttr(key).get().getColorValue().getValue();
        } else {
            return defValue;
        }
    }

}
