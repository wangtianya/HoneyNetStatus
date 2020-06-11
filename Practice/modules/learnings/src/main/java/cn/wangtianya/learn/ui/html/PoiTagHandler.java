package cn.wangtianya.learn.ui.html;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.XMLReader;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

/**
 * 增加解析<span...>的tag，支持style里面的背景色和前景色属性.
 * User: liujian06
 * Date: 2014/11/11
 * Time: 20:30
 */
public class PoiTagHandler implements Html.TagHandler {

    // 系统是argb格式, 如果服务端下发RGBA则转化成ARGB
    private boolean rgba = false;

    public PoiTagHandler() {
    }

    public PoiTagHandler(boolean rgba) {
        this.rgba = rgba;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if ("selfdefine".equalsIgnoreCase(tag)) {
            if (opening) {
                String backgroundColor = null;
                String foreColor = null;
                String textDecoration = null;
                try {
                    String style = getAttribute(xmlReader, "style");
                    if (!TextUtils.isEmpty(style)) {
                        Map<String, String> map = getKVMap(style);
                        if (map != null) {
                            backgroundColor = covertRGBAtoARGB(map.get("background-color"));
                            foreColor = covertRGBAtoARGB(map.get("color"));
                            textDecoration = map.get("text-decoration");
                        }
                    }
                } catch (IllegalAccessException e) {
//                    MLog.e(PoiTagHandler.class.getSimpleName(), "exception", e);
                } catch (NoSuchFieldException e) {
//                    MLog.e(PoiTagHandler.class.getSimpleName(), "exception", e);
                }
                start(output, new SPAN(backgroundColor, foreColor, textDecoration));
            } else {
                int backcolor = 0;
                int forecolor = 0;
                String textDecoration = null;

                try {
                    SPAN span = (SPAN) getLast(output, SPAN.class);
                    if (span != null) {
                        if (span.backgroundColor != null) {
                            backcolor = Color.parseColor(span.backgroundColor);
                        }
                        if (span.foregroundColor != null) {
                            forecolor = Color.parseColor(span.foregroundColor);
                        }
                        textDecoration = span.textdecoration;
                    }
                } catch (RuntimeException e) {
//                    MLog.e(PoiTagHandler.class.getSimpleName(), "exception", e);
                }
                List<Object> objs = new ArrayList<Object>();
                if (backcolor != 0) {
                    objs.add(new BackgroundColorSpan(backcolor));
                }
                if (forecolor != 0) {
                    objs.add(new ForegroundColorSpan(forecolor));
                }

                if (textDecoration != null) {
                    objs.add(new StrikethroughSpan());
                }
                end(output, SPAN.class, objs.toArray());
            }
        }
    }

    /**
     * 转化RGBA为ARGB
     *
     * @param s RGBA色值
     *
     * @return ARGB色值
     */
    private String covertRGBAtoARGB(String s) {
        if (s != null && rgba && s.length() == 9) {
            return s.substring(0, 1) + s.substring(7) + s.substring(1, 7);
        } else {
            return s;
        }
    }

    /**
     * @see Html
     */
    private static void start(Editable text, Object mark) {
        int len = text.length();
        text.setSpan(mark, len, len, Spanned.SPAN_MARK_MARK);
    }

    /**
     * @see Html
     */
    private static void end(Editable text, Class<?> kind, Object[] replaces) {
        int len = text.length();
        Object obj = getLast(text, kind);
        int where = text.getSpanStart(obj);
        text.removeSpan(obj);
        if (where != len) {
            for (Object replace : replaces) {
                text.setSpan(replace, where, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return;
    }

    /**
     * @see Html
     */
    private static Object getLast(Spanned text, Class<?> kind) {
        /*
         * This knows that the last returned object from getSpans()
		 * will be the most recently added.
		 */
        Object[] objs = text.getSpans(0, text.length(), kind);
        if (objs.length == 0) {
            return null;
        }
        return objs[objs.length - 1];
    }

    /**
     * 获取xml标签里面的属性
     *
     * @param attr
     *
     * @return
     */
    private String getAttribute(XMLReader xmlReader, String attr) throws NoSuchFieldException, IllegalAccessException {
        Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
        elementField.setAccessible(true);
        Object element = elementField.get(xmlReader);
        Field attsField = element.getClass().getDeclaredField("theAtts");
        attsField.setAccessible(true);
        Object atts = attsField.get(element);
        Field dataField = atts.getClass().getDeclaredField("data");
        dataField.setAccessible(true);
        String[] data = (String[]) dataField.get(atts);
        Field lengthField = atts.getClass().getDeclaredField("length");
        lengthField.setAccessible(true);
        int len = (Integer) lengthField.get(atts);

        String foundAttrValue = null;

        for (int i = 0; i < len; i++) {
            if (attr.equals(data[i * 5 + 1])) {
                foundAttrValue = data[i * 5 + 4];
            }
        }

        return foundAttrValue;
    }

    private Map<String, String> getKVMap(String attrs) {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(attrs)) {
            String[] array = attrs.split(";");
            if (array != null && array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    String[] kv = array[i].split(":");
                    if (kv != null && kv.length == 2) {
                        map.put(kv[0], kv[1]);
                    }
                }
            }
        }
        return map;
    }

    private static class SPAN {
        public String backgroundColor;
        public String foregroundColor;
        public String textdecoration;

        public SPAN(String b, String f, String t) {
            backgroundColor = b;
            foregroundColor = f;
            textdecoration = t;
        }
    }

}